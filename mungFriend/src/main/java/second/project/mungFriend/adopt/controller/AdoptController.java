package second.project.mungFriend.adopt.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import second.project.mungFriend.adopt.model.service.AdoptService;

@SessionAttributes("{loginMember}")
@RequestMapping("/adopt")
@Controller
public class AdoptController {
	
	@Autowired
	private AdoptService service;
	
	// 강아지 목록 조회
	@GetMapping("/dogList")
	public String selectDogList(@RequestParam(value="cp", required= false, defaultValue="1") int cp,
			Model model) {
		
		Map<String, Object> map = service.selectDogList(cp);
		
		model.addAttribute("map", map);
		
		return "adopt/dogList";
	}
	
	// 강아지 insert
	@GetMapping("/dogRegistration")
	public String dogRegistration() {
		
		return "adopt/dogRegistration";
	}
	
	

}
