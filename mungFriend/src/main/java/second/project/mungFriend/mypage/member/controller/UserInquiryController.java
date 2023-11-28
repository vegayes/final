package second.project.mungFriend.mypage.member.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import second.project.mungFriend.mypage.member.model.service.MemberUserService;
import second.project.mungFriend.mypage.member.model.service.UserInquiryService;

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
	
}
