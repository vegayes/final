package second.project.mungFriend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import second.project.mungFriend.common.dto.Alarm;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.service.MemberService;


@Controller
public class MainController {
	
	@Autowired
	MemberService service;

	@GetMapping("/")
	public String mainForward(HttpSession session) {
		
		Member loginMember = (Member) session.getAttribute("loginMember");
		//System.out.println("메인에서 멤버 : " + loginMember);
		if(loginMember != null) {
			//System.out.println("메인에서 알림 조회");
			List<Alarm> alarmList = service.selectAlarm(loginMember.getMemberNo()); 
			//System.out.println("알림정보 : " + alarmList);
			session.setAttribute("alarmList", alarmList);
		}
		

		return "common/main";
	}
	
	
	@GetMapping("/loginError")
	public String loginError(RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", "로그인 후 이용해주세요");
		
		return "redirect:/";
	}
	
	
	/** 무료입소신청 페이지 이동
	 * @return
	 */
	@GetMapping("/freeAdmission")
	public String freeAdmissionPage() {
		return "admissionApply/freeAdmission";
	}
	
	/** 후원하기 페이지 이동
	 * @return
	 */
	@GetMapping("/donation")
	public String donationPage() {
		return "donation/donation";
	}
	
	
 
	
	
}
