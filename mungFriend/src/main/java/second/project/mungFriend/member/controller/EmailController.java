package second.project.mungFriend.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import second.project.mungFriend.member.model.service.EmailService;

@Controller
@RequestMapping("/sendEmail")
public class EmailController {
	
	@Autowired
	private EmailService service;
	
	@GetMapping("/signUp")
	@ResponseBody
	public int signUp(String email) {
		return service.signUp(email);
	}
	
	@GetMapping("/checkAuthKey")
	@ResponseBody
	public int checkAuthKey(@RequestParam Map<String, Object> paramMap) {
		
		System.out.println("수신된 인증코드");
		System.out.println(paramMap);
		
		return service.checkAuthKey(paramMap);
		
	}

}
