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
		


//       return "centerInfo/map";
//		return "admissionApply/freeAdmission";  
//		return "mypage/admin/DogStatus";
//	return "member/findId";
		return "common/header";
//		return "mypage/member/myPageReservationList_user";
//		return "admissionApply/freeAdmission";
	}
	
	
	@GetMapping("/loginError")
	public String loginError(RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", "로그인 후 이용해주세요");
		
		return "redirect:/";
	}
	
	
	
}
