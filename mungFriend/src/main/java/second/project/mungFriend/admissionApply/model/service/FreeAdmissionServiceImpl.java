package second.project.mungFriend.admissionApply.model.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import second.project.mungFriend.admissionApply.model.dao.FreeAdmissionMapper;
import second.project.mungFriend.admissionApply.model.dto.Admission;
import second.project.mungFriend.admissionApply.model.exception.FileUploadException;
import second.project.mungFriend.common.utility.Util;

@Service
@PropertySource("classpath:/config.properties")
public class FreeAdmissionServiceImpl implements FreeAdmissionService {

	@Autowired
	private FreeAdmissionMapper mapper;
	
	@Value("${my.admission.webpath}")
	private String webPath;
	
	@Value("${my.admission.location}")
	private String filePath;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int admissionInsert(Admission admission, MultipartFile admissionProfile) throws IllegalStateException, IOException {
		
		String rename = null; // 강아지 프로필 이름 저장 변수
		
		if(admissionProfile != null && !admissionProfile.isEmpty()) { // 업로드된 이미지가 있을 경우
			
			// 1) 파일 이름 변경
			rename = Util.fileRename(admissionProfile.getOriginalFilename());
			
			// 2) 바뀐 이미지 이름 setting
			admission.setAdmProfile(webPath + rename);
			System.out.println("바뀐 이름 : " + admission.getAdmProfile());
			System.out.println("filePath : " + filePath + rename);
			
		} else { 
			admission.setAdmProfile(null);
		}
		
		System.out.println("최종 admission : " + admission);
		
		
		// 강아지 프로필 이미지 가지고 삽입 DAO 메서드 호출
		int result = mapper.admissionInsert(admission);
		

		if(result > 0) { // DB에 이미지 경로 업데이트 성공했다면
			
			// 업로드된 새 이미지가 있을 경우
			if(rename != null) {
				
				// 메모리에 임시 저장되어있는 파일을 서버에 진짜로 저장하는 것
				admissionProfile.transferTo(new File(filePath + rename));
			}
			
			
		} else { // 실패
			
			throw new FileUploadException();	
		}
		
		return result;
	}

}
