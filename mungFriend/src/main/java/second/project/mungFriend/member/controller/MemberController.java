package second.project.mungFriend.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.service.MemberService;

@Controller
@RequestMapping("/member")

public class MemberController {
	
	@Autowired
	private MemberService service;
	
	// 로그인 화면 전환
	@GetMapping("/login")
	public String loginPage() {
		return "member/login";
	}
	
	// 로그인
	@PostMapping("/login")
	public String login(Member inputMember, Model model) {
		
		Member loginMember = service.login(inputMember);
		
		System.out.println(loginMember);
		
		return "common/header";
	}
	
	// 회원가입
	@GetMapping("/signUp")
	public String signUp() {
		
		return "";
	}

}
