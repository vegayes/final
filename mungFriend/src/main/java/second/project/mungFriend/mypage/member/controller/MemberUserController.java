package second.project.mungFriend.mypage.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage/member")
public class MemberUserController {
	
	// 찜한목록 화면 전환
	@GetMapping("/myPageLikeList")
	public String myPageLikeList() {
		return "mypage/member/myPageLikeList";
	}
	
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

}
