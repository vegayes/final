package second.project.mungFriend.mypage.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import second.project.mungFriend.member.model.dto.Member;
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
			,@SessionAttribute("loginMember") Member loginMember
			,SessionStatus status
			,RedirectAttributes ra) {
		
		int memberNo = loginMember.getMemberNo();
		
		int result = service.secessionUser(memberPw, memberNo);
		
		// System.out.println(result);
		
		String path = "redirect:";
		String message = null;
		
		if(result > 0) { // 탈퇴 성공시
			message = "탈퇴되었습니다.";
			path += "/";
			status.setComplete();
			
		} else { // 탈퇴 실패시
			message = "입력하신 비밀번호가 일치하지 않습니다.";
			path += "secessionUser";
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
		
	}

}
