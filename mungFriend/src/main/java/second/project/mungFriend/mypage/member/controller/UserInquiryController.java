package second.project.mungFriend.mypage.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.mypage.member.model.dto.Inquiry;
import second.project.mungFriend.mypage.member.model.service.UserInquiryService;

@RequestMapping("/mypage/member")
@Controller
@SessionAttributes({"loginMember"})
public class UserInquiryController {
   
	@Autowired
	private UserInquiryService service;

	// 리스트로 
	@GetMapping("/userInquiryBox")
	public String userInquiryBoxPage(Model model) {
		
	   // inquiryList 조회하기
	   List<Inquiry> inquiryList = service.inquiryList();
		
	   System.out.println("1:1  = " + inquiryList);
	   
	   model.addAttribute("inquiryList", inquiryList);
	   
	
		return "mypage/member/userInquiryBox";	
	}
	
	
	// 문의하기 버튼 누르면 문의하기 페이지 로 이동
	@GetMapping("/userInquiry")
	public String userInquiryPage() {

		
		return "mypage/member/userInquiry";
	}

	// 1:1 등록
   @PostMapping("/userInquiry")
   public String userInquiry(Inquiry inquiry, Model model, @SessionAttribute("loginMember") Member loginMember) {
	   
	   inquiry.setMemberNo(loginMember.getMemberNo());
	   
	   int result = service.userInquiry(inquiry);
	   
	   System.out.println(result);
	   
	   String path = null;
	   
	   if(result > 0) { // 성공
		   
		   // inquiryList 조회하기
		   List<Inquiry> inquiryList = service.inquiryList();
		   
		   System.out.println("1:1  = " + inquiryList);
		   
		   model.addAttribute("inquiryList", inquiryList);
		   
		   path = "mypage/member/userInquiryBox";
		   
	   } else { // 실패
		   path = "redirect:userInquiryBox";
		   
	   }
	   
	   return path;
   }
   

}
