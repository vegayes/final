package second.project.mungFriend.adoptReview.model.service;

import java.util.List;

import second.project.mungFriend.adoptReview.model.dto.ReviewComment;

public interface CommentService {

	List<ReviewComment> selectCommentList(int boardNo);

	int insertComment(ReviewComment comment) throws Exception;

	int deleteComment(int commentNo) throws Exception;

	int updateComment(ReviewComment comment);

}
