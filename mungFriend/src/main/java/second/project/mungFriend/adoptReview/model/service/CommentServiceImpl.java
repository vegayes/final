package second.project.mungFriend.adoptReview.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import second.project.mungFriend.admissionApply.model.exception.FileUploadException;
import second.project.mungFriend.adoptReview.model.dao.ReviewMapper;
import second.project.mungFriend.adoptReview.model.dto.ReviewComment;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	ReviewMapper mapper;
	
	
	/**
	 * 댓글 리스트 조회
	 */
	@Override
	public List<ReviewComment> selectCommentList(int boardNo) {
		
		return mapper.selectCommentList(boardNo);
	}

	
	/**
	 * 댓글 삽입
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertComment(ReviewComment comment) throws Exception{
		
		int result = mapper.insertComment(comment);
		
		if(result > 0) {
			
			return result;
		}else {
			
			throw new Exception();
		}
	}

	
	/**
	 * 댓글 삭제
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteComment(int commentNo) throws Exception{
		
		int result = mapper.deleteComment(commentNo);
		
		if(result > 0) {
			
			return result;
		}else {
			
			throw new Exception();
		}
	}

	
	/**
	 * 댓글 수정
	 * @throws Exception 
	 */
	@Override
	public int updateComment(ReviewComment comment) throws Exception {
		
		int result = mapper.updateComment(comment);
		
		if(result > 0) {
			
			return result;
		}else {
			
			throw new Exception();
		}
	}

}
