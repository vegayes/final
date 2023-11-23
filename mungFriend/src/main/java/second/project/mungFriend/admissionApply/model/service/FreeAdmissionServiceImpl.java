package second.project.mungFriend.admissionApply.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.classic.pattern.Util;
import second.project.mungFriend.admissionApply.model.dao.FreeAdmissionMapper;
import second.project.mungFriend.admissionApply.model.dto.Admission;

@Service
public class FreeAdmissionServiceImpl implements FreeAdmissionService {

	@Autowired
	private FreeAdmissionMapper mapper;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int admissionInsert(Admission admission, MultipartFile admProfile) {
		
		String rename = null; // 강아지 프로필 이름 저장 변수
		
		// 1. admission 테이블 INSERT 하기 (내용) 
		// -> boardNo(시퀀스 새성한 번호) 반환 받기 
		int result = mapper.admissionInsert(admission);
		
		// 실패 시 서비스 종료
		if (result == 0 ) {
			System.out.println("실패");
			return 0;
		}
		
		// 실패가 아니라면,
		int admNo = admission.getAdmNo();
		
		/*
		// 2. 삽입 성공 시
		if(admNo > 0) { 
			// 실제 업로드된 파일의 정보를 기록할 List
			List<BoardImage> uploadList = new ArrayList<BoardImage>();
			
			// images에 담겨있는 파일 중 실제 업로드된 파일만 분류
			for(int i = 0; i<images.size(); i++) {
				// i번째 요소에 업로드된 파일이 있다면 
				if(images.get(i).getSize() > 0) {
					
					BoardImage img = new BoardImage();
					// img에 파일 정보를 담아서 uploadList에 추가 
					img.setImagePath(webPath); // 웹 접근 경로
					img.setBoardNo(boardNo); // 게시글 번호 ( dao에서 받아온 SEQ_BOARD_NO)
					img.setImageOrder(i); // 이미지 순서 
					
					// 파일 원본명
					String fileName = images.get(i).getOriginalFilename();
					
					img.setImageOriginal(fileName); // 원본명
					img.setImageReName(Util.fileRename(fileName)); // 변경명
					
					uploadList.add(img);
					
				} 
				
			} // 분류 for문 종료
			
			// 분류 작업 후 uploadList가 비어 있지 않은 경우
			// == 업로드한 파일이 있다.
			
			if(!uploadList.isEmpty()) {
				
				// BOARD_IMG 테이블에 insert 하는 dao 호출 (result 변수 재활용)
				result = mapper.insertImageList(uploadList);
				// result == 삽입된 행의 개수 
				
				// 전체 insert 성공 여부 확인
				// 삽입된 행의 개수와 uploadList의 개수가 같다면 
				// == 전체 insert 성공
				
				if(result == uploadList.size()) { // insert가 모두 성공했으면 
					// 서버에 파일 저장 (transferTo)
					
					for(int i = 0; i<uploadList.size(); i++) {
						
						int index = uploadList.get(i).getImageOrder();
						
						String rename =  uploadList.get(i).getImageReName();
						
						images.get(index).transferTo(new File(filePath + rename));
						
					}
					
					
					
					
					
				}else { // 일부 또는 전체 insert 실패
					// rollback 필요
					
					// @Transactional(rollbackFor = Exception.class)
					// -> 예외가 발생 해야지만 롤백 
					
					// [결론] 
					// 예외를 강제 발생 시켜서 rollback 해야된다.
					// 사용자 정의 예외 만들어서 발생시키기 ( **************************** ) 
					
					throw new FileUploadException();
					
				}
				
			}
			
			
			
		}
		
		*/
		return admNo;
	}

}
