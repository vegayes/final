package second.project.mungFriend.adoptReview.model.service;

import java.io.File;
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

import second.project.mungFriend.admissionApply.model.exception.FileUploadException;
import second.project.mungFriend.adopt.model.dao.AdoptMapper;
import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.adopt.model.dto.Pagination;
import second.project.mungFriend.adoptReview.model.dao.ReviewMapper;
import second.project.mungFriend.adoptReview.model.dto.Review;
import second.project.mungFriend.adoptReview.model.dto.ReviewImage;
import second.project.mungFriend.common.utility.Util;

@Service
@PropertySource("classpath:/config.properties")
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private AdoptMapper adoptMapper;
	
	@Autowired
	private ReviewMapper mapper;

	@Value("${my.review.webpath}")
	private String webPath;
	
	@Value("${my.review.location}")
	private String filePath;
	
	
	/**
	 * 검색어 없을 시 게시글 조회
	 */
	@Override
	public Map<String, Object> selectReviewList(int cp) {
		
		// 1.특정 게시판의 삭제되지 않은 게시글 수 조회
		int listCount = mapper.getListCount();
		
		// 2. 게시글 수 조회 결과 + cp를 이용해 Pagination 객체 생성
		Pagination pagination = new Pagination(listCount, cp);
		
		// 3. 특정 게시판에서 현재 페이지에 해당하는 부분에 대한 게시글 목록 조회 
		// boardCode 에 따른 게시판에서 몇 페이지(Pagination.currnetPage) 에 대한 
		// 게시글 몇 개(pagination.limit)인지 조회
		
		 
		// RowBounds 객체 
		// - 마이바티스에서 페이징 처리를 위해 제공하는 객체
		// - offset 만큼 건너뛰고 
		// 그 다음 지정된 행 개수 만큼 조회

		// 1) offset 계산
		int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();
		
		// 2) RowBounds 객체 생성
		RowBounds rowBounds = new RowBounds(offset,pagination.getLimit() );
			
		List<Review> reviewList = mapper.selectReviewList(rowBounds);
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("pagination", pagination);
		map.put("reviewList", reviewList);
		
		// 리턴값이 2개라 map 으로 리턴
		return map;
		
	}

	

	
	/**
	 * 검색어 있을 시 게시글 조회
	 */
	@Override
	public Map<String, Object> selectSearchReviewList(Map<String, Object> paramMap, int cp) {
		
		int listCount = mapper.getSearchListCount(paramMap); 
		
		Pagination pagination = new Pagination(listCount, cp);
		
		// 1) offset 계산
		int offset 
			= (pagination.getCurrentPage() - 1) * pagination.getLimit();
		
		// 2) RowBounds 객체 생성
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		List<Review> reviewList = mapper.selectSearchReviewList(paramMap,rowBounds);
		
		// 4. pagination, boardList를 Map에 담아서 반환
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", pagination);
		map.put("reviewList", reviewList);
		
		
		return map;
	}


	
	/**
	 * 게시글 삽입
	 * @throws Exception 
	 * @throws IllegalStateException 
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int reviewInsert(Review review, List<MultipartFile> images) throws IllegalStateException, Exception {
		
		int result = mapper.reviewInsert(review);
		
		if(result == 0 ) {return 0;}
		
		int reviewNo = review.getReviewNo(); //insert 이후 반환된 reviewNo
		
		//게시글 삽입 성공 시 업로드된 이미지가 있다면 Review_Img 테이블에 삽입
				if(reviewNo > 0) { 
					
					// 실제 업로드될 파일의 정보를 기록할 list
					List<ReviewImage> uploadList = new ArrayList<ReviewImage>();
					
					
					for(int i=0; i<images.size(); i++ ) {	
						
						if(images.get(i).getSize() > 0) {
							
							ReviewImage img = new ReviewImage();
							
							// img 에 파일 정보 담아서 uploadList 에 추가
							img.setImagePath(webPath); //웹 접근경로
							img.setImageNo(reviewNo);  //게시글 번호
							img.setImageOrder(i); // 이미지 순서
							
							String fileName = images.get(i).getOriginalFilename();
							
							img.setImageOriginal(fileName);  //원본명
							img.setImageReName(Util.fileRename(fileName)); // 변경명
							
							uploadList.add(img);
							System.out.println("img 정보 : " + img );
							
						}
							
					}
					
					
					if( !uploadList.isEmpty() ) {
						
						 result = mapper.insertImageList(uploadList);
						// result : 삽입된 행의 개수
						
						if(result == uploadList.size()) {
							
							// 	uploadList 크기만큼 전부 insert가 수행됐다면 => insert 성공
							for(int i=0; i<uploadList.size(); i++) {
								
								int index = uploadList.get(i).getImageOrder();
								
								String rename = uploadList.get(i).getImageReName();
								
								images.get(index).transferTo(new File(filePath + rename));
							}
							
						}else { 
							
							throw new FileUploadException();
						}
						
						
					}
					
					
					
				}
				
		return result;
	}




	/**
	 * 게시글 상세조회
	 */
	@Override
	public Review selectReview(int reviewNo) {
		
		return mapper.selectReview(reviewNo);
		
	}




	/**
	 * 게시글 조회수 증가
	 */
	@Override
	public int updateCount(int reviewNo){
		
		return mapper.updateCount(reviewNo);
	}



	/**
	 * 게시글 수정
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int reviewUpdate(Review review, List<MultipartFile> images, String deleteList) throws Exception{

		int rowCount = mapper.reviewUpdate(review);
		
		
		if(rowCount > 0) {
			
			if(!deleteList.equals("")) { //삭제할 이미지가 있다면
				
				// 3.delteList 에 작성된 이미지 모두 삭제
				Map<String,Object> deleteMap = new HashMap<String,Object>();
				deleteMap.put("reviewNo", review.getReviewNo());
				deleteMap.put("deleteList", deleteList);
				
				rowCount = mapper.imageDelete(deleteMap);
				
				if(rowCount == 0) { // 이미지 삭제 실패 시 예외 발생시킴 -> rollback
					
					throw new Exception();
					
					}
				}
			
				//새로 업로드된 이미지 분류 작업
				
				// images : 실제 파일이 담긴 List
				//         -> input type="file" 개수만큼 요소가 존재
				//         -> 제출된 파일이 없어도 MultipartFile 객체가 존재
				
				List<ReviewImage> uploadList = new ArrayList<>();
				
				for(int i=0 ; i<images.size(); i++) {
					
					if(images.get(i).getSize() > 0) { // 업로드된 파일이 있을 경우
						
						// BoardImage 객체를 만들어 값 세팅 후 
						// uploadList에 추가
						ReviewImage img = new ReviewImage();
						
						// img에 파일 정보를 담아서 uploadList에 추가
						img.setImagePath(webPath); // 웹 접근 경로
						img.setReviewNo(review.getReviewNo()); // 게시글 번호
						img.setImageOrder(i); // 이미지 순서
						
						// 파일 원본명
						String fileName = images.get(i).getOriginalFilename();
						
						img.setImageOriginal(fileName); // 원본명
						img.setImageReName( Util.fileRename(fileName) ); // 변경명    
						
						uploadList.add(img);
						
						// 오라클은 다중 UPDATE를 지원하지 않기 때문에
						// 하나씩 UPDATE 수행
						
						rowCount = mapper.imageUpdate(img);
						
						if(rowCount == 0) {
							// 수정 실패 == DB에 이미지가 없었다 
							// -> 이미지를 삽입
							rowCount = mapper.imageInsert(img);
						}
					}
				}
				
				
				// 5. uploadList에 있는 이미지들만 서버에 저장(transferTo())
				if(!uploadList.isEmpty()) {
					for(int i=0 ; i< uploadList.size(); i++) {
						
						int index = uploadList.get(i).getImageOrder();
						
						// 파일로 변환
						String rename = uploadList.get(i).getImageReName();
						
						images.get(index).transferTo( new File(filePath + rename)  );                    
					}
				}

		}
		
		return rowCount;
	}



	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteReview(int reviewNo) throws Exception{
		
		int deleteResult = mapper.deleteReview(reviewNo);
		
		if(deleteResult > 0) {
			return deleteResult;
		}else {
			throw new Exception();
		}
		
	}

}



	/**
	 * 챗봇 추천 견종 조회
	 */
	@Override
	public Map<String, Object> selectDogList(int cp, String breedName) {
		// 1. 삭제되지않은 강아지 수 조회
		int listCount = adoptMapper.getSearchDogListCount(breedName);
		
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
		
		List<Dog> dogList = adoptMapper.selectChatbotDogList(rowBounds,breedName);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("pagination", pagination);
		map.put("dogList", dogList);
		
		return map;
	}

}
