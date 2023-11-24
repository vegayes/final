package second.project.mungFriend.adoptReview.model.service;

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

import second.project.mungFriend.admissionApply.model.exception.FileUploadException;
import second.project.mungFriend.adopt.model.dto.Pagination;
import second.project.mungFriend.adoptReview.model.dao.ReviewMapper;
import second.project.mungFriend.adoptReview.model.dto.Review;
import second.project.mungFriend.adoptReview.model.dto.ReviewImage;
import second.project.mungFriend.common.utility.Util;

@Service
@PropertySource("classpath:/config.properties")
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewMapper mapper;

	@Value("${my.adopt.webpath}")
	private String webPath;
	
	@Value("${my.adopt.location}")
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
		
		int reviewNo = review.getReviewNo();
		
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
				
		return 0;
	}

}
