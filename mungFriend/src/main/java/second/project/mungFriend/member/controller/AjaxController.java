package second.project.mungFriend.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import second.project.mungFriend.member.model.service.AjaxService;

@Controller
public class AjaxController {
	
	@Autowired
	private AjaxService service;
	
	// 아이디 중복검사
	@GetMapping("/dupCheck/id")
	@ResponseBody
	public int checkId(String id) {
		return service.checkId(id);
	}

}
