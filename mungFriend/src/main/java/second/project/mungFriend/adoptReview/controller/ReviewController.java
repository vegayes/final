package second.project.mungFriend.adoptReview.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import second.project.mungFriend.adoptReview.model.service.ReviewService;

@SessionAttributes({"loginMember"})
@RequestMapping("/adoptReview")
@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService service;
	
	
	@GetMapping("/reviewList")
	public String selectReviewList(
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@RequestParam(value = "searchContent", required = false) String searchContent,
			@RequestParam(value = "searchType", required = false) String searchType,
			Model model
			) {
			
			System.out.println("현재페이지 : " + cp);
			System.out.println("searchContent : " + searchContent);
			System.out.println("searchType : " + searchType);
			
			if(searchContent == null) { // 검색어가 없을 때 
				
				
				// 게시글 목록 조회 서비스 호출
				Map<String, Object> map = service.selectReviewList(cp);
				
				// 조회 결과를 request scope에 세팅 후 forward
				
				model.addAttribute("map", map);
				// map.put("pagination", pagination);
				// map.put("reviewList", reviewList); 이렇게 구성
				
			}else {  //검색어 있을 때
				
				
				Map<String, Object> map = service.selectSearchReviewList(searchType,searchContent,cp);
				
				map.put("searchType", searchType);
				map.put("searchContent", searchContent);
				model.addAttribute("map", map);
				
			}
		
		
			return"adoptReview/ReviewList";
	}
	
	
	@GetMapping("/reviewDetail/{reviewNo}")
	public String reviewDetail(@PathVariable("reviewNo") int reviewNo,
			Model model) {
			
		System.out.println("reviewNo : " + reviewNo);
		
		return"adoptReview/ReviewDetail";
	}
	
	
	
}

