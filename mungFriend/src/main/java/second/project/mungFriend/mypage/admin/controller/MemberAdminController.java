package second.project.mungFriend.mypage.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import second.project.mungFriend.mypage.admin.model.service.MemberAdminService;

@Controller
@RequestMapping("/mypage/admin")
public class MemberAdminController {
	
	@Autowired
	private MemberAdminService service;
	
	// 회원관리 화면 전환
	@GetMapping("/memberListAdmin")
	public String memberListAdminPage(Model model,
									  @RequestParam(value="cp", required = false, defaultValue = "1") int cp) {
		
		// 회원리스트 조회하기
		Map<String, Object> map = service.selectMemberList(cp);
		
		// System.out.println(memberList);
		
		model.addAttribute("map", map);
		
		return "mypage/admin/memberList_admin";
	}
	
	// 관리자 회원 탈퇴하기
	@GetMapping("/memberDel")
	public String memberDel(int memberNo, Model model, RedirectAttributes ra,
							@RequestParam(value="cp", required = false, defaultValue = "1") int cp) {
		
		int result = service.memberDel(memberNo);
		
		if(result > 0) { // 탈퇴 성공
			
			Map<String, Object> map = service.selectMemberList(cp);
			
			ra.addFlashAttribute("message", "탈퇴하기가 성공했습니다");
			
			model.addAttribute("map", map);
			
		} else { // 탈퇴 실패
			
			ra.addFlashAttribute("message", "탈퇴하기가 실패했습니다");
			
		}
		
		return "mypage/admin/memberList_admin";
		
	}

}
