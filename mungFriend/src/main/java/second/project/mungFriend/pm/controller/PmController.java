package second.project.mungFriend.pm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pm")
public class PmController {
	
	// 무료입양 절차 화면 전환
	@GetMapping("/adoptProcedure")
	public String adoptProcedurePage() {
		return "PM/adoptProcedure";
	}
	
	// 활동일지 화면 전환
	@GetMapping("/activityLog")
	public String activityLogPage() {
		return "PM/activityLog";
	}
	
	// 활동일지 등록하기 화면 전환
	@GetMapping("/activityLogRegister")
	public String activityLogRegisterPage() {
		return "PM/activityLogRegister_admin";
	}

}
