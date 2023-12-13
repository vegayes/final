	package second.project.mungFriend.adoptReview.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import second.project.mungFriend.adoptReview.model.dto.Review;
import second.project.mungFriend.adoptReview.model.service.ReviewService;
import second.project.mungFriend.member.model.dto.Member;

@SessionAttributes({"loginMember"})
@RequestMapping("/adoptReview")
@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService service;
	
	
	
	@GetMapping("/reviewList/{cp}")
	public String selectReviewList(
			@PathVariable(value="cp", required = false) int cp,
			@RequestParam Map<String, Object> paramMap,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			Model model
			) {
			
			//System.out.println("paramMap : " + paramMap);
			
			//System.out.println("loginMember : " + loginMember);
			//if(loginMember != null) {model.addAttribute("loginMember", loginMember);}
			
			if(paramMap.get("searchContent") == null) { // 검색어가 없을 때 
				
				
				// 게시글 목록 조회 서비스 호출
				Map<String, Object> map = service.selectReviewList(cp);
				
				// 조회 결과를 request scope에 세팅 후 forward
				
				model.addAttribute("map", map);
				// map.put("pagination", pagination);
				// map.put("reviewList", reviewList); 이렇게 구성
				
			}else {  //검색어 있을 때
				
				
//				Map<String, Object> map = service.selectSearchReviewList(searchType,searchContent,cp);
//				
//				map.put("searchType", searchType);
//				map.put("searchContent", searchContent);
				
				Map<String, Object> map = service.selectSearchReviewList(paramMap, cp);
				
				model.addAttribute("map", map);
				
			}
		
		
			return"adoptReview/ReviewList";
	}
	
	
	
	/** 게시글 상세조회
	 * @param reviewNo
	 * @param model
	 * @param ra
	 * @return
	 */
	@GetMapping("/reviewDetail/{reviewNo}")
	public String reviewDetail(@PathVariable("reviewNo") int reviewNo,
			Model model, RedirectAttributes ra,
			@RequestParam(value="cp", required = false, defaultValue = "1" ) int cp) {
			
		Review review = service.selectReview(reviewNo);
		//System.out.println("review : " + review);
	
		String path = null;
		
		
		if(review != null) {
			path = "adoptReview/ReviewDetail";   
			
			int result = service.updateCount(reviewNo);
			if(result > 0) {
				review.setReviewCount(review.getReviewCount()+1);
			}
			model.addAttribute("review",review); 
			model.addAttribute("cp",cp); 

			 
			 
			}else { //조회결과 없으면
				
				path = "redirect:/adoptReview/ReviewList"; //없으면 게시판의 첫페이지로 리다이렉트
				ra.addFlashAttribute("message","해당 게시글이 존재하지 않습니다");
			}
			
			
		return path;

	}
	
	
	
	
	/** 게시글 삽입 페이지 이동
	 * @return
	 */
	@GetMapping("/reviewInsert")
	public String reviewInsert() {
		
		return "adoptReview/ReviewCreate";
	}
	
	
	
	/** 게시글 삽입
	 * @param review
	 * @param images
	 * @param loginMember
	 * @param ra
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reviewInsert")
	public String reviewInsert( Review review
			, @RequestParam(value="images", required = false) List<MultipartFile> images
			, @SessionAttribute("loginMember") Member loginMember
			, RedirectAttributes ra
			)throws Exception {
		
		System.out.println("등록하려는 값 : " + review);
		System.out.println("이미지 리스트 : " + images);
		
		review.setMemberNo(loginMember.getMemberNo());
		review.setMemberNickname(loginMember.getMemberNickname());
		
		int insertResult = service.reviewInsert(review,images);
		System.out.println("게시글 등록결과 : " + insertResult);
		
		if(insertResult > 0) {
			ra.addFlashAttribute("message","등록에 성공했습니다");
		}else {
			ra.addFlashAttribute("message","등록에 실패했습니다");
		}
		
		
		return"redirect:ReviewList/1";
	}
	
	
	/** 게시글 수정 페이지로 이동
	 * @param reviewNo
	 * @param cp
	 * @param model
	 * @return
	 */
	@GetMapping("/updateReview/{reviewNo}")
	public String reviewUpdate(
			@PathVariable ("reviewNo") int reviewNo,
			@RequestParam(value="cp", required = false, defaultValue = "1" ) int cp,
			Model model
			) {
		
		Review review = service.selectReview(reviewNo);
		model.addAttribute("review", review);
		model.addAttribute("cp", cp);
		
		return "adoptReview/ReviewUpdate";
	}
	
	
	
	/** 게시글 수정
	 * @param reviewNo
	 * @param review
	 * @param model
	 * @param images
	 * @param deleteList
	 * @param cp
	 * @param ra
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/updateReview/{reviewNo}")
	public String reviewUpdate(
			@PathVariable ("reviewNo") int reviewNo,
			Review review,
			Model model,
			@RequestParam(value="images", required = false) List<MultipartFile> images,
			@RequestParam(value = "deleteList",required = false) String deleteList, // 삭제할 이미지 순서
			@RequestParam(value="cp", required = false, defaultValue = "1" ) int cp,
			RedirectAttributes ra
			) throws Exception {
		
		review.setReviewNo(reviewNo);
		//System.out.println("삭제할 이미지 순서 : " + deleteList);
		
		int rowCount = service.reviewUpdate(review,images,deleteList);
		
		String message = null;
		
		if(rowCount >0) {
			message = "게시글이 수정되었습니다";
		
		}else {
			message = "게시글 수정 실패";
		}
		
		ra.addFlashAttribute("message",message);
		
		
		return "redirect:/adoptReview/ReviewDetail/" + reviewNo + "?cp=" + cp;
	}
	
	
	/** 게시글 삭제
	 * @param reviewNo
	 * @param cp
	 * @param ra
	 * @return
	 * @throws Exception
	 */
	@GetMapping("deleteReview/{reviewNo}")
	public String deleteReview(
			@PathVariable ("reviewNo") int reviewNo,
			@RequestParam(value="cp", required = false, defaultValue = "1" ) int cp,
			RedirectAttributes ra
			) throws Exception {
		
		int deleteResult = service.deleteReview(reviewNo);
		
		String message = null;
		
		if(deleteResult >0) {
			message = "게시글이 삭제되었습니다";
		
		}else {
			message = "게시글 삭제 실패";
		}
		
		ra.addFlashAttribute("message",message);
		
		
		return "redirect:/adoptReview/ReviewList/"+cp;
	}
	
	
	
	
	
	
}

