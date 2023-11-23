//package second.project.mungFriend.adoptReview.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.SessionAttributes;
//
//import second.project.mungFriend.adoptReview.model.service.ReviewService;
//
//@SessionAttributes({"loginMember"})
//@RequestMapping("/adoptReview")
//@Controller
//public class ReviewController {
//	
//	@Autowired
//	private ReviewService service;
//	
//	
//	@GetMapping("/reviewList")
//	public String selectReviewList(
//			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
//			@RequestParam(value = "searchContent", required = false) String searchContent
//			) {
//			
//			System.out.println("searchContent : " + searchContent);
//		
//			
//			if(searchContent == null) { // 검색어가 없을 때 
//				
//				
//				// 게시글 목록 조회 서비스 호출
//				Map<String, Object> map = service.selectBoardList(boardCode, cp);
//				
//				// 조회 결과를 request scope에 세팅 후 forward
//				model.addAttribute("map", map);
//				
//			}else {
//				
//				System.out.println("paramMap : " + paramMap);
//				paramMap.put("boardCode", boardCode);
//				
//				Map<String, Object> map = service.selectBoardList(paramMap, cp);
//				
//				
//				model.addAttribute("map", map);
//				
//			}
//		
//		
//			return"adoptReview/ReviewList";
//	}
//	
//	
//}
