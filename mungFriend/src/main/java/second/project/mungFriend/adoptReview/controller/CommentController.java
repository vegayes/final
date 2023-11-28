package second.project.mungFriend.adoptReview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import second.project.mungFriend.adoptReview.model.dto.ReviewComment;
import second.project.mungFriend.adoptReview.model.service.CommentService;


//@Controller + @ResponseBody
@RestController // 요청/응답 처리(모든 요청, 응답은 비동기)
public class CommentController {
	
	
	@Autowired
	CommentService service;
	
		
	//댓글 목록 조회					// json 통신 시 한글 깨짐 방지
	@GetMapping(value="/comment", produces="application/json; charset=UTF-8")
	public List<ReviewComment> select(int reviewNo) { 
		List<ReviewComment> list =  service.selectCommentList(reviewNo);	

		return list;
	}
	
	
	//댓글 삽입
	@PostMapping("/insert")
	public int insertComment(@RequestBody ReviewComment comment) throws Exception {
		
		int result = service.insertComment(comment);
		return result;
	}
	
	
	
	//댓글 삭제
	@GetMapping(value="/delete", produces="application/json; charset=UTF-8")
	public int deleteComment(int commentNo) throws Exception {
		
		return service.deleteComment(commentNo);
	}
	
	
	
	
	//댓글 수정
	@PostMapping("/update")
	public int updateComment(@RequestBody ReviewComment comment) throws Exception {
			
		int result = service.updateComment(comment);
		return result;
			
	}
	
	// 답글
	@PostMapping("/reply")
	public int replyComment(@RequestBody ReviewComment comment) throws Exception {
		
		System.out.println("comment : " + comment);
		int result = service.insertComment(comment);
		System.out.println("답글 입력결과 : " + result);
		return result;
	
	}
	
	
	
	
}
