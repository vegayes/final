package second.project.mungFriend.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	// 로그인
	@PostMapping("/login")
	public String login() {
		
		return "";
	}
	
	// 회원가입
	@GetMapping("/signUp")
	public String signUp() {
		
		return "";
	}

}
