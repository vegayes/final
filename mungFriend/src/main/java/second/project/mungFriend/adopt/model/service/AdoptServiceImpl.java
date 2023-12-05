package second.project.mungFriend.adopt.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import second.project.mungFriend.adopt.model.dao.AdoptMapper;
import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.adopt.model.dto.DogImage;
import second.project.mungFriend.adopt.model.dto.Pagination;
import second.project.mungFriend.adopt.model.exception.FileUploadException;
import second.project.mungFriend.adopt.model.exception.ImageDeleteException;
import second.project.mungFriend.common.utility.Util;

@Service
@PropertySource("classpath:/config.properties")
public class AdoptServiceImpl implements AdoptService{
	
	@Value("${my.adopt.webpath}")
	private String webPath;
	
	@Value("${my.adopt.location}")
	private String filePath;
	
	@Autowired
	private AdoptMapper mapper;

	@Override
	public Map<String, Object> selectDogList(int cp) {
		
		// 1. 삭제되지않은 강아지 수 조회
		int listCount = mapper.getListCount();
		
		// 2. 조회결과를 cp를 이용해서 Pagination 객체 생성
		Pagination pagination = new Pagination(listCount, cp);
		
		// 3. 현재 페이지에 해당하는 부분에 대한 몇개(paginatioin.limit) 게시글 목록 조회
		
		// RowBounds 객체
		// - 마이바티스에서 페이징처리를 위해 제공하는 객체
		// offset 만큼 건너뛰고
		// 그 다음 지정된 행 개수만큼 조회
		
		// 1) offset 계산(페이지 넘기기)
		int offset
			= (pagination.getCurrentPage() - 1) * pagination.getLimit();
		
		// 2) RowBounds 객체 생성
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		List<Dog> dogList = mapper.selectDogList(rowBounds);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("pagination", pagination);
		map.put("dogList", dogList);
		
		return map;
	}

	// 게시글만 상세조회
	@Override
	public Dog selectDogDetail(Map<String, Object> map) {

		return mapper.selectDogDetail(map);
	}

	// DB이미지 파일 목록 조회	
	@Override
	public List<String> selectImageList() {
		
		return mapper.selectImageList();
	}
	
	// 좋아요 여부 확인
	@Override
	public int dogLikeCheck(Map<String, Object> map) {
		
		return mapper.dogLikeCheck(map);
	}
	
	// 좋아요 처리
	@Override
	@Transactional(rollbackFor =  Exception.class)
	public int like(Map<String, Integer> paraMap) {

		// check == 0 / 1
		// check값이 무엇이냐에 따라서 DOG_LIKE 테이블에 INSERT / DELETE 정해라
		// DOG_LIKE 테이블 INSERT ( insertDogLike() ) 호출
		// DOG_LIKE 테이블 DELETE ( deleteDogLike() ) 호출
		
		int result = 0;
		
		int check = paraMap.get("check");
		
		if(check == 0) { // 좋아요 x
			
			result = mapper.insertDogLike(paraMap);
			
		}else{ // 좋아요 o
			
			result = mapper.deleteDogLike(paraMap);
		}
		
		if(result == 0) return -1; // 실패 시 -> transaction	
		
		return result;
	}

//	**********************************************************************************************
	
	// 강아지 insert
	@Override
	public int dogRegiInsert(Dog dog, List<MultipartFile> images) throws IllegalStateException, IOException {
		
		// DOG_BREED에 BREED_NAME이 있는지 확인 분기처리
		// 견종확인
		String breedConfirm = mapper.breedConfirm(dog);
		
		if(breedConfirm  == null) {
			
			// 견종 추가
			mapper.breedAdd(dog);
		}
		
		// 1. DOG 테이블 INSERT 하기(입력사항들)
		// -> dogNo(시퀀스로 생성한 번호) 반환 받기
		
		int result = mapper.insertDog(dog);
		
		// 실패 시 서비스 종료
		if(result == 0) return 0;
		
		// 얻어온 셋팅값 얻어오기
		int dogNo = dog.getDogNo();
		
		// 2. 게시글 삽입 성공 시
		// 업로드된 이미지가 있다면 DOG_IMG테이블에 삽입하는 dao 호출
		if(dogNo > 0) {
			
			// admission에서 받아온 프로필 값
			if(dog.getImgPath()!= null && dog.getImgRename() != null) {
				
				dog.setDogNo(dogNo);
				
				int insertAdmImg  = mapper.insertAdmImg(dog);
			}		
			
			
			// 실제 업로드된 파일의 정보를 기록할 List
			List<DogImage> uploadList = new ArrayList<DogImage>();
			
			// images에 담겨있는 파일 중 실제 업로드된 파일만 분류
			for(int i = 0; i < images.size(); i++) {
				
				// i번째 요소에 업로드된 파일이 있다면
				if(images.get(i).getSize() > 0) {
					
					DogImage img = new DogImage();
					
					img.setImagePath(webPath); // 웹 접근 경로
					img.setDogNo(dogNo); // 강아지 번호
					img.setImageOrder(i); // 이미지순서
					
					String fileName = images.get(i).getOriginalFilename();
					
					img.setImageOriginal(fileName); // 원본명
					img.setImageReName( Util.fileRename(fileName) ); // 변경명
					
					uploadList.add(img);
				}
				
				
			} // 분류작업 종료
			

			// 분류작업 후 uploadList가 있는 경우
			if(!uploadList.isEmpty()) {
				
				// DOG_IMG테이블에 insert -> 삽입된 행의 갯수 반환
				result = mapper.insertImage(uploadList);
				
				if(result == uploadList.size()) {
					
					// 서버에 파일 저장
					for(int i = 0; i < uploadList.size(); i++) {
						
						int index = uploadList.get(i).getImageOrder();
						
						String rename = uploadList.get(i).getImageReName();
						
						images.get(index).transferTo( new File(filePath + rename) );
					}
				}else { // 일부 또는 전체 insert 실패 -> rollback을 위한 예외 발생
					
					throw new FileUploadException();
					
				}
				
			}
			
		}
		
		return dogNo;
	}

	// 수정화면 띄우기용 게시글 상세조회
	@Override
	public Dog selectDogDetailForUpdate(Map<String, Object> map) {
		
		return mapper.selectDogDetailForUpdate(map);
	}
	
	// 강아지 update
	@Override
	public int dogUpdate(Dog dog, List<MultipartFile> images, String deleteList) throws IllegalStateException, IOException {
		
		// DOG_BREED에 BREED_NAME이 있는지 확인 분기처리
		// 견종확인
		String breedConfirm = mapper.breedConfirm(dog);
		
		if(breedConfirm  == null) {
			// 견종 추가
			mapper.breedAdd(dog);
		}
		
		// 1. 강아지 정보만 수정
		int rowCount = mapper.dogUpdate(dog);
		
		if(rowCount > 0) { // 2. 정보수정이 성공했을 때 이미지 수정
			
			 if(!deleteList.equals("")) { // 삭제할 이미지가 있다면
				 
				 // 3. deleteList에 작성된 이미지 모두 삭제
				 Map<String, Object> deleteMap = new HashMap<String, Object>();
				 deleteMap.put("dogNo", dog.getDogNo());
				 deleteMap.put("deleteList", deleteList);
				 
				 rowCount = mapper.imageDelete(deleteMap);
				 
				 if(rowCount == 0) { // 이미지 삭제 실패 시 전체 롤백필요 -> 강제예외발생
					 
					 throw new ImageDeleteException();
				 }
				 
			 }
			 
			 // 4. 새로 업로드된 이미지 분류 작업
			 // 업로드된 이미지가 있다면 DOG_IMG테이블에 삽입하는 dao 호출
			 
			 // 실제 업로드된 파일의 정보를 기록할 List
			 List<DogImage> uploadList = new ArrayList<DogImage>();
			 
			 // images에 담겨있는 파일 중 실제 업로드된 파일만 분류
			 for(int i = 0; i < images.size(); i++) {
				 
				 
				 if(images.get(i).getSize() > 0) { // i번째 요소에 업로드된 파일이 있다면
					 
					 DogImage img = new DogImage();
					 
					 img.setImagePath(webPath); // 웹 접근 경로
					 img.setDogNo(dog.getDogNo()); // 강아지 번호
					 img.setImageOrder(i); // 이미지순서
					 
					 // 파일 원본명
					 String fileName = images.get(i).getOriginalFilename();
					 
					 img.setImageOriginal(fileName); // 원본명
					 img.setImageReName( Util.fileRename(fileName) ); // 변경명
					 
					 uploadList.add(img);
					 
					 rowCount = mapper.imageUpdate(img);
					 
					 if(rowCount == 0) { // 수정 실패 -> DB에 이미지가 없었다는 뜻 -> 이미지 삽입 필요
						 
						 rowCount = mapper.imageInsert(img);
					 }
					 
				 }
					
			 }
				
			// 분류작업 후 uploadList가 있는 경우 리스트에 있는 이미지들만 서버에 저장
			if(!uploadList.isEmpty()) {
									
				for(int i = 0; i < uploadList.size(); i++) {
					
					int index = uploadList.get(i).getImageOrder();
					
					// 파일로 변환
					String rename = uploadList.get(i).getImageReName();
					
					images.get(index).transferTo( new File(filePath + rename) );
				}
									
			}
						
		}
		
		return rowCount;

	}

	// 강아지 delete
	@Override
	public int dogDelete(Map<String, Object> map) {
		
		return mapper.dogDelete(map);
	}
	
	// 강아지 예약하기
	@Override
	public String dogReservation() {
		
		return mapper.dogReservation();
	}

	
	

}
