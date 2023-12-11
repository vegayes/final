package second.project.mungFriend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import second.project.mungFriend.adopt.model.dao.AdoptMapper;
import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.common.service.DogBreedService;
import second.project.mungFriend.common.service.MainService;


@Controller
public class MainController {
	
   @Autowired
    private MainService service;

	@RequestMapping("/")
	public String mainForward(Model model) {
		
		// 강아지 목록 조회 (일반 전체 조회)
		List<Dog> dogList = service.selectDogList();
		
		model.addAttribute("dogList", dogList);		
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
