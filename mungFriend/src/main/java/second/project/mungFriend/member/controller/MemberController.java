package second.project.mungFriend.member.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import second.project.mungFriend.common.dto.Alarm;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.dto.MemberGoogle;
import second.project.mungFriend.member.model.dto.MemberKakao;
import second.project.mungFriend.member.model.dto.MemberLoginApi;
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
        model.addAttribute("kakaoUrl", service.getKakaoLogin());
		//model.addAttribute("googleUrl", service.getGoogleUrlLogin());
		
		return "member/login";
	}
	
	// 로그인
	@PostMapping("/login")
	public String login(Member inputMember, Model model, HttpSession session,
						@RequestHeader("referer") String referer, RedirectAttributes ra) {
		
		Member loginMember = service.login(inputMember);
		
		System.out.println(loginMember);
		
		String path = "redirect:";
		
		if(loginMember != null) { // 로그인 성공
			path += "/";
			ra.addFlashAttribute("message", loginMember.getMemberNickname() + "님 환영합니다.");
			model.addAttribute("loginMember", loginMember);
			//로그인 시 알림목록 얻어오기
			List<Alarm> alarmList = service.selectAlarm(loginMember.getMemberNo()); 
			//System.out.println("알림목록 : " +alarmList);
			session.setAttribute("alarmList", alarmList);
			
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
	@PostMapping("/signUp")
	public String signUp(Member inputMember,
						@RequestParam("uploadImage") MultipartFile profileImage, // 업로드 파일
						String[] memberAddress,
						RedirectAttributes ra,
						Model model) throws Exception{
		
		// Member inputMember : 커맨드 객체 (제출된 파라미터가 저장된 객체)
		
		// String[] memberAddress :
		// input name = "memberAddress" 3개가 저장된 배열
		
		// RedirectAttributes ra :
		// 리다이렉트 시 데이터를 request scope로 전달하는 객체
		
		System.out.println("주소 : " + inputMember.getMemberAddress());
		
		// 01234,서울 성동구 어쩌구,2층
		// 만약에 입력하지 않았다면 ,, 이런식으로 구분자만 나옴
		// 주소를 입력하지 않은 경우 null 로 변경
		if(inputMember.getMemberAddress().equals(",,")) {
			inputMember.setMemberAddress(null);
			
		} else {
			// String.join("구분자", String[])
			// 배열의 요소를 하나의 문자열로 변경
			// 요소 사이에 구분자를 추가함
			String addr = String.join("^^^", memberAddress);
			inputMember.setMemberAddress(addr);
			
		}
		
		
		
		// 회원 가입 서비스 호출
		int result = service.signUp(profileImage, inputMember);
		
		System.out.println(result);
		
		// 가입 성공 여부에 따라서 주소 결정
		String path = "redirect:";
		String message = null;
		
		if(result > 0) { // 가입 성공
			path = "member/login"; // 로그인 페이지로
			
			message = inputMember.getMemberNickname() + "님의 가입을 환영합니다.";

	        model.addAttribute("naverUrl", service.getNaverLogin());
	        model.addAttribute("kakaoUrl", service.getKakaoLogin());
			model.addAttribute("message", message);
			
		} else { // 가입 실패
			
			// 회원 가입 페이지
			// path += "/member/signUp"; // 절대경로
			path += "signUp"; // 상대 경로
			
			message = "회원 가입 실패";
			
		}
		
		// 리다이렉트 시 session에 잠깐 올라갔다 request로 복귀하도록 세팅
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
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
	public String findPw2Page(String id, Model model) {
		
		model.addAttribute("id", id); // 비밀번호찾기2 화면에서 보여줄 id 가져와서 model에 담기
		
		return "member/findPw2";
	}
	
	// 비밀번호찾기
	@PostMapping("/findPw")
	public String findPw(Member updateMember, Model model, RedirectAttributes ra) {
		
		// System.out.println(updateMember);
		
		int result = service.findPw(updateMember);
		
		// System.out.println(result);
		
		String path = "redirect:";
		String message = null;
		
		if(result > 0) { // 비밀번호 변경 성공
			
			path += "/member/login";
			message = "비밀번호 변경 성공";
			
		} else { // 비밀번호 변경 실패
			
			path += "/member/findPw2";
			message = "비밀번호 변경 실패";
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
		
	}
	
	// 네이버 로그인 확인 후 자동으로 콜백
	@GetMapping("/oauth2/code/naver")
	 public String callback(HttpServletRequest request, Model model, RedirectAttributes ra)  throws Exception {
		MemberLoginApi memberLoginApi = service.getNaverInfo(request.getParameter("code"));
		 System.out.println("네이버~~~~"+memberLoginApi.toString());
		 
		 // 디비에 해당 정보를 가지고 로그인 하기
		 Member loginMember = service.loginNaver(memberLoginApi);
			
		 System.out.println("11111111111111111====>"+loginMember);
		
		 String path = "redirect:";
		
		 if(loginMember != null) { // 로그인 성공
			 path += "/";
			 ra.addFlashAttribute("message", loginMember.getMemberNickname() + "님 환영합니다.");
			 model.addAttribute("loginMember", loginMember);		
		 } 
		 else { // 회원정보가 없을때 
			 path = "member/signUp";
			 model.addAttribute("memberLoginApi", memberLoginApi);		
			 model.addAttribute("loginApiMessage", "네이버 로그인 정보가 없어 회원가입 페이지로 이동합니다. 회원가입 후 이용해주세요.");		
		 }
		
		 return path;
    }
	
	// 카카오 로그인 확인 후 자동으로 콜백
	@GetMapping("/oauth2/code/kakao")
	 public String callbackKakao(HttpServletRequest request, Model model, RedirectAttributes ra)  throws Exception {
		 MemberLoginApi memberLoginApi = service.getKakaoInfo(request.getParameter("code"));
		 System.out.println("카카오톡~~~~"+memberLoginApi);
		 
		 // 디비에 해당 정보를 가지고 로그인 하기
		 Member loginMember = service.loginKakao(memberLoginApi);
			
		 System.out.println(loginMember);
		
		 String path = "redirect:";
		
		 if(loginMember != null) { // 로그인 성공
			 path += "/";
			 ra.addFlashAttribute("message", loginMember.getMemberNickname() + "님 환영합니다.");
			 model.addAttribute("loginMember", loginMember);		
		 } else { // 회원정보가 없을때 
			 path = "member/signUp";
			 model.addAttribute("memberLoginApi", memberLoginApi);		
			 model.addAttribute("loginApiMessage", "카카오 로그인 정보가 없어 회원가입 페이지로 이동합니다. 회원가입 후 이용해주세요.");		
		 }
		
		 return path;
    }
	
	// 구글 로그인 확인 후 자동으로 콜백
	@GetMapping("/oauth2/code/google")
	 public String callbackGoogle(HttpServletRequest request, Model model, RedirectAttributes ra)  throws Exception {
		MemberLoginApi memberLoginApi = service.getGoogleInfo(request.getParameter("code"));
		 System.out.println("구글~~~~"+request.getParameter("code"));
		 
		 // 디비에 해당 정보를 가지고 로그인 하기
		 Member loginMember = service.loginGoogle(memberLoginApi);
			
		 System.out.println(loginMember);

		 String path = "redirect:";
		
		 if(loginMember != null) { // 로그인 성공
			 path += "/";
			 ra.addFlashAttribute("message", loginMember.getMemberNickname() + "님 환영합니다.");
			 model.addAttribute("loginMember", loginMember);		
		 } else {  // 회원정보가 없을때 
			 path = "member/signUp";
			 model.addAttribute("memberLoginApi", memberLoginApi);		
			 model.addAttribute("loginApiMessage", "구글 로그인 정보가 없어 회원가입 페이지로 이동합니다. 회원가입 후 이용해주세요.");	
		 }
		
		 return path;
    }
	
}
