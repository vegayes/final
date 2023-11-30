package second.project.mungFriend.mypage.member.controller;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;
import second.project.mungFriend.mypage.member.model.dto.Inquiry;

@RequestMapping("/mypage/member")
@Controller
public class UserInquiryController {
   
	

	// 리스트로 
	@GetMapping("/userInquiryBox")
	public String userInquiryBox() {
	return "mypage/member/userInquiryBox";
		
	}
	
	
	// 문의하기 버튼 누르면 문의하기 페이지 로 이동
	@GetMapping("/userInquiry")
	public String userInquiry() {
	return "mypage/member/userInquiry";
	
}
	
/*
 * // 문의 등록 처리
 * 
 * @PostMapping("/submit") public String submitInquiry(@ModelAttribute Inquiry
 * inquiry, Principal principal) { inquiry.setUserId(principal.getName()); // 현재
 * 로그인된 사용자 ID 설정 userInquiryService.submitInquiry(inquiry); return
 * "redirect:/user/inquiries/box"; // 문의 목록 페이지로 리다이렉트 }
 * 
 * // 사용자 문의 목록 페이지
 * 
 * @GetMapping("/box") public String listUserInquiries(Model model, Principal
 * principal) { String userId = principal.getName(); // 현재 로그인된 사용자 ID
 * List<Inquiry> inquiries = userInquiryService.getUserInquiries(userId);
 * model.addAttribute("inquiries", inquiries); return "userInquiryBox"; }
 */
	
}
