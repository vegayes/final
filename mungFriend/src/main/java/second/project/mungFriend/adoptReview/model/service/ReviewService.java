package second.project.mungFriend.adoptReview.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import second.project.mungFriend.adoptReview.model.dto.Review;

public interface ReviewService {

	// 게시글 조회
	Map<String, Object> selectReviewList(int cp);

	Map<String, Object> selectSearchReviewList(Map<String, Object> paramMap, int cp);

	
	
	// 게시글 삽입
	int reviewInsert(Review review, List<MultipartFile> images) throws Exception;

	// 게시글 조회
	Review selectReview(int reviewNo);

	// 조회수 증가
	int updateCount(int reviewNo);

	// 게시글 수정
	int reviewUpdate(Review review, List<MultipartFile> images, String deleteList) throws Exception;

	// 게시글 삭제
	int deleteReview(int reviewNo) throws Exception;

	
	// 챗봇 추천 견종 조회
	Map<String, Object> selectDogList(int cp, String breedName);
	
	

}
