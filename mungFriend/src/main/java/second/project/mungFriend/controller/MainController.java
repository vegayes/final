package second.project.mungFriend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class MainController {

	@RequestMapping("/")
	public String mainForward(Model model) {
		
		model.addAttribute("name", "홍길동");

//		return "common/main";
		return "mypage/admin/donationList_admin";
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
