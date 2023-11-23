package second.project.mungFriend.mypage.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import second.project.mungFriend.mypage.member.model.dto.MemberUser;
import second.project.mungFriend.mypage.member.model.service.MemberUserService;

@Controller
@RequestMapping("/mypage/member")
@SessionAttributes({"loginMember"})
public class MemberUserController {
	
	@Autowired
	private MemberUserService service;
	
	// 정보수정 화면 전환
	@GetMapping("/updateInfoUser")
	public String updateInfoUserPage() {
		return "mypage/member/updateInfo_user";
	}
	
	// 정보수정
	
	// 회원탈퇴 화면 전환
	@GetMapping("/secessionUser")
	public String secessionUserPage() {
		return "mypage/member/secession_user";
	}
	
	// 회원탈퇴
	@PostMapping("/secessionUser")
	public String secessionUser(String memberPw
			,@SessionAttribute("loginMember") MemberUser loginMember
			,SessionStatus status) {
		
		int memberNo = loginMember.getMemberNo();
		
		int result = service.secessionUser(memberPw, memberNo);
		
		return "";
		
	}

}
