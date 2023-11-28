package second.project.mungFriend.adoptReview.model.service;

import java.util.List;

import second.project.mungFriend.adoptReview.model.dto.ReviewComment;

public interface CommentService {
	
	// 댓글목록 조회
	List<ReviewComment> selectCommentList(int boardNo);

	// 댓글 삽입
	int insertComment(ReviewComment comment) throws Exception;

	// 댓글 삭제
	int deleteComment(int commentNo) throws Exception;

	// 댓글 수정
	int updateComment(ReviewComment comment) throws Exception;

}
