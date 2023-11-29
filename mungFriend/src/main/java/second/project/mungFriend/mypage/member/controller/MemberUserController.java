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
  
//	// 찜한목록 화면 전환
//	@GetMapping("/myPageLikeList")
//	public String myPageLikeList() {
//		return "mypage/member/myPageLikeList";
//	}


	// 정보수정 화면 전환
	@GetMapping("/updateInfoUser")
	public String updateInfoUserPage() {
		return "mypage/member/updateInfo_user";
	}
	
	// 정보수정
	@PostMapping("/updateInfoUser")
	public String updateInfoUser(@SessionAttribute("loginMember") Member loginMember,
								Member updateMember,
								String[] memberAddress,
								RedirectAttributes ra) {
		
		/*
		 * @SessionAttribute("loginMember") Member loginMember
		 *  : Session에서 얻어온 "loginMember"에 해당하는 객체를
		 *    매개변수 Member loginMember에 저장
		 * 
		 * Member updateMember
		 *  : 수정할 회원 정보 담긴 커맨드 객체
		 *  
		 * String[] memberAddress
		 *  : name="memberAddress"인 input 3개의 값(주소)
		 * 
		 * RedirectAttributes ra : 리다이렉트 시 값 전달용 객체
		 * 
		 * */
		
		// 주소 하나로 합치기(a^^^b^^^c)
		if(updateMember.getMemberAddress().equals(",,")) {
			updateMember.setMemberAddress(null);
		} else {
			// updateMember 에 주소문자열 세팅
			String addr = String.join("^^^", memberAddress);
			updateMember.setMemberAddress(addr);
		}
		
		// 로그인한 회원의 번호를 updateMember에 세팅
		updateMember.setMemberNo(loginMember.getMemberNo());
		
		
		// DB 회원 정보 수정 (update) 서비스 호출
		int result = service.updateInfoUser(updateMember);
		
		String message = null;
		
		// 결과값으로 성공
		if(result > 0) {
			// -> 성공 시 Session에 로그인된 회원 정보도 수정(동기화)
			loginMember.setMemberTel(updateMember.getMemberTel());
			loginMember.setMemberEmail(updateMember.getMemberEmail());
			loginMember.setMemberNickname(updateMember.getMemberNickname());
			loginMember.setMemberAddress(updateMember.getMemberAddress());
			
			message = "회원 정보 수정 성공";
			
		} else {
			// 실패에 따른 처리
			message = "회원 정보 수정 실패";
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:updateInfoUser"; // 상대경로 (/mypage/member/updateInfoUser)
		
	}
	
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
