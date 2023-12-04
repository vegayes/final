package second.project.mungFriend.pm.model.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import second.project.mungFriend.common.utility.Util;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.pm.model.dao.PmMapper;
import second.project.mungFriend.pm.model.dto.Pm;

@Service
@PropertySource("classpath:/config.properties") // classpath -------> src/main/resource
public class PmServiceImpl implements PmService{
	
	@Value("${my.PM.webpath}") // @Value --------> 필드에서만 사용 가능
	private String webPath;
	
	@Value("${my.PM.location}")
	private String filePath;
	
	@Autowired
	private PmMapper mapper;

	// 활동일지 등록하기
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int activityLogRegister(MultipartFile uploadImage, Pm pm) throws Exception {
		
		// 활동사진 이미지 변경 실패 대비
		String temp = pm.getAcitivityImg(); // 기존에 가지고 있던 이전 이미지 저장
		
		String rename = null; // 변경 이름 저장 변수
		
		if(uploadImage.getSize() > 0) { // 업로드된 이미지가 있을 경우
			
			// 1) 파일 이름 변경
			rename = Util.fileRename(uploadImage.getOriginalFilename());
			
			// 2) 바뀐 이미지 이름 setting
			pm.setAcitivityImg(webPath + rename);
			
		} else { // 업로드된 이미지가 없는 경우 (이미지 삭제 버튼)
			
			pm.setAcitivityImg(null);
			
		}
		
		System.out.println("최종 pm : " + pm);
		
		// 활동사진 추가 mapper 메서드 호출
		int result = mapper.activityLogRegister(pm);
		
		if(result > 0) { // DB에 이미지 경로 업데이트 성공했다면
			
			// 업로드된 새 이미지가 있을 경우
			if(rename != null) {
				
				// 메모리에 임시 저장되어있는 파일을 서버에 진짜로 저장하는 것
				uploadImage.transferTo(new File(filePath + rename));
				pm.setAcitivityImg(webPath + rename);
			}
			
		} else {
			
			// 이전 이미지로 다시 세팅
			pm.setAcitivityImg(temp);
			
		}
		
		return result;
	}
	

}
