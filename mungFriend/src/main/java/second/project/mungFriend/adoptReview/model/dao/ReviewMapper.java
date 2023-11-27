package second.project.mungFriend.adoptReview.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import second.project.mungFriend.adoptReview.model.dto.Review;
import second.project.mungFriend.adoptReview.model.dto.ReviewImage;

@Mapper
public interface ReviewMapper {

	// 검색어 X
	int getListCount();    // 게시글개수

	List<Review> selectReviewList(RowBounds rowBounds); // 게시글조회

	
	// 검색어 O
	int getSearchListCount(Map<String, Object> paramMap); // 게시글 개수

	List<Review> selectSearchReviewList(Map<String, Object> paramMap, RowBounds rowBounds); // 게시글 조회

	
	// 게시글 삽입
	int reviewInsert(Review review);

	// 이미지 삽입
	int insertImageList(List<ReviewImage> uploadList);

	// 게시글 상세조회
	Review selectReview(int reviewNo);

	// 조회수 증가
	int updateCount(int reviewNo);

	

}
