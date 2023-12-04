package second.project.mungFriend.pm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.pm.model.dto.Pm;
import second.project.mungFriend.pm.model.service.PmService;

@Controller
@RequestMapping("/pm")
@SessionAttributes({"loginMember"})
public class PmController {
	
	@Autowired
	private PmService service;
	
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
	
	// 활동일지 등록하기
	@PostMapping("/activityLogRegister")
	public String activityLogRegister(@SessionAttribute("loginMember") Member loginMember,
									  @RequestParam("uploadImage") MultipartFile uploadImage, // 업로드 파일
									  String activityContent,
									  Pm pm,
									  RedirectAttributes ra)throws Exception{
		
		pm.setMemberNo(loginMember.getMemberNo());
		pm.setActivityContent(activityContent);
		
		// DB 활동일지 등록(insert) 서비스 호출
		int result = service.activityLogRegister(uploadImage, pm);
		
		String message = null;
		String path = "redirect:";
		
		// 결과값으로 성공
		if(result > 0) {
			message = "활동일지 등록 성공";
			path = "pm/activityLog";
			
		} else {
			// 실패에 따른 처리
			message = "활동일지 등록 실패";
			path += "activityLogRegister";
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
		
	}

}
