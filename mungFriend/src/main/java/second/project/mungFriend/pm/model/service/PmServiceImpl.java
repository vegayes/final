package second.project.mungFriend.pm.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import second.project.mungFriend.common.utility.Util;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.mypage.admin.model.dao.MemberAdminDAO;
import second.project.mungFriend.mypage.admin.model.dto.Pagination;
import second.project.mungFriend.pm.model.dao.PmMapper;
import second.project.mungFriend.pm.model.dto.Pm;
import second.project.mungFriend.pm.model.dto.PmPagination;

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

	// 활동일지 조회하기
	@Override
	public Map<String, Object> selectPmList(int cp) {
		// 1. 활동일지 전체 리스트 수 조회
		int listCount = mapper.getListCount();
		
		// 2. 1번 조회 결과 + cp 를 이용해서 Pagination 객체 생성
		// -> 내부 필드가 모두 계산되어 초기화됨
		PmPagination pagination = new PmPagination(listCount, cp);
		
		// 3. 현재 페이지에 해당하는 부분에 대한 회원 리스트 조회
		// ex) 100개
		//     10개 씩 보여준다
		//     1page -> 100 ~ 91
		//     2page -> 90  ~ 81
		// 몇페이지(pagination.currentPage)에 대한
		// 회원 리스트가 몇개(pagination.limit)인지 조회
		
		// RowBounds 객체
		// - 마이바티스에서 페이징처리를 위해 제공하는 객체
		// - offset 만큼 건너뛰고
		// 그 다음 지정된 행 개수만큼(limit) 만큼 조회

		// 1) offset 계산
		int offset
			= (pagination.getCurrentPage() - 1) * pagination.getLimit();
		
		// 2) RowBounds 객체 생성
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());

		List<Pm> pmList = mapper.selectPmList(rowBounds);
		
		// 4. pagination, memberList를 Map에 담아서 반환
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", pagination);
		map.put("pmList", pmList);
		
		return map;
	}

	// 활동일지 상세 조회하기
	@Override
	public Pm activityLogSelectDetail(int activityNo) {
		return mapper.activityLogSelectDetail(activityNo);
	}

	// 활동일지 수정하기
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int activityLogUpdate(MultipartFile updateImage, Pm pm) throws Exception {
		
		// System.out.println("수정하기 들어옴?" );
		
		// 활동사진 이미지 변경 실패 대비
		String temp = pm.getAcitivityImg(); // 기존에 가지고 있던 이전 이미지 저장
		
		String rename = null; // 변경 이름 저장 변수
		
		if(updateImage.getSize() > 0) { // 수정된 이미지가 있을 경우
			
			// 1) 파일 이름 변경
			rename = Util.fileRename(updateImage.getOriginalFilename());
			
			System.out.println("수정 Img : " + webPath + rename);
			// 2) 바뀐 이미지 이름 setting
			pm.setAcitivityImg(webPath + rename);
			
			
			
		} else { // 수정된 이미지가 없는 경우 (이미지 삭제 버튼)
			
			pm.setAcitivityImg(null);
			
		}
		
		System.out.println("수정 전 pm  : " + pm);
		
		// 활동사진 수정 mapper 메서드 호출
		int result = mapper.activityLogUpdate(pm);
		
		if(result > 0) { // DB에 이미지 경로 업데이트 성공했다면
			
			// 업로드된 새 이미지가 있을 경우
			if(rename != null) {
				
				// 메모리에 임시 저장되어있는 파일을 서버에 진짜로 저장하는 것
				updateImage.transferTo(new File(filePath + rename));
				pm.setAcitivityImg(webPath + rename);
			}
			
		} else {
			
			// 이전 이미지로 다시 세팅
			pm.setAcitivityImg(temp);
			
		}
		
		return result;
	}

	// 활동일지 삭제하기
	@Override
	public int activityLogDelete(int activityNo) {
		return mapper.activityLogDelete(activityNo);
	}
	

}
