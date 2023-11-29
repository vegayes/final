package second.project.mungFriend.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.dto.MemberNaver;
import second.project.mungFriend.member.model.service.MemberService;

@Controller
@RequestMapping("/member")
@SessionAttributes({"loginMember"})
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	// 로그인 화면 전환
	@GetMapping("/login")
	public String loginPage(Model model) {
		// 모든 로그인 화면 호출 시 해당 메소드를 호출
		// 여기서 네이버,카카오톡,구글 등 기본적으로 가져올꺼 셋팅을 한다.
        model.addAttribute("naverUrl", service.getNaverLogin());
//        model.addAttribute("kakaoUrl", service.getKakaoLogin());
//		model.addAttribute("googleUrl", service.getGoogleUrlLogin());
		
		return "member/login";
	}
	
	// 로그인
	@PostMapping("/login")
	public String login(Member inputMember, Model model, 
						@RequestHeader("referer") String referer, RedirectAttributes ra) {
		
		Member loginMember = service.login(inputMember);
		
		System.out.println(loginMember);
		
		String path = "redirect:";
		
		if(loginMember != null) { // 로그인 성공
			path += "/";
			ra.addFlashAttribute("message", loginMember.getMemberNickname() + "님 환영합니다.");
			model.addAttribute("loginMember", loginMember);
			
		} else { // 로그인 실패
			path += referer;
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 불일치합니다.");
			
		}
		
		return path;
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}
	
	// 회원가입 화면 전환
	@GetMapping("/signUp")
	public String signUpPage() {
		return "member/signUp";
	}
	
	// 회원가입
	
	// 아이디찾기 화면 전환
	@GetMapping("/findId")
	public String findIdPage() {
		return "member/findId";
	}
	
	// 아이디찾기
	@PostMapping("/findId")
	public String findId(Member inputMember, Model model,
						 @RequestHeader("referer") String referer, RedirectAttributes ra) {
		
		String memberId = service.findId(inputMember);
		
		// System.out.println(memberId);
		
		String path = "";
		
		if(memberId != null) { // 아이디찾기 성공
			path = "member/findIdSuccess";
			model.addAttribute("memberId", memberId);
			
		} else { // 아이디찾기 실패
			path = "redirect:" + referer;
			ra.addFlashAttribute("message", "존재하지 않는 아이디입니다.");
			
		}
		
		return path;
	}
	
	// 비밀번호찾기 화면 전환
	@GetMapping("/findPw")
	public String findPwPage() {
		return "member/findPw";
	}
	
	// 비밀번호찾기2 화면 전환
	@PostMapping("/findPw2")
	public String findPw2Page() {
		return "member/findPw2";
	}
	
	// 비밀번호찾기3 화면 전환
	@PostMapping("/findPw3")
	public String findPw3Page() {
		return "member/findPw3";
	}
	
	// 네이버 로그인 확인 후 자동으로 콜백
	@GetMapping("/oauth2/code/naver")
	 public String callback(HttpServletRequest request, Model model, RedirectAttributes ra)  throws Exception {
		 MemberNaver naverInfo = service.getNaverInfo(request.getParameter("code"));
		 System.out.println("메롱~~~~"+naverInfo.toString());
		 
		 // 디비에 해당 정보를 가지고 로그인 하기
		 // 디비에 해당 정보를 가지고 로그인 하기
		 Member loginMember = service.loginNaver(naverInfo);
			
		 System.out.println(loginMember);
		
		 String path = "redirect:";
		
		 if(loginMember != null) { // 로그인 성공
			 path += "/";
			 ra.addFlashAttribute("message", loginMember.getMemberNickname() + "님 환영합니다.");
			 model.addAttribute("loginMember", loginMember);		
		 } else { // 로그인 실패
			 path += "member/login";
			 ra.addFlashAttribute("message", "아이디 또는 비밀번호가 불일치합니다.");
		 }
		
		 return path;
    }
	
}
