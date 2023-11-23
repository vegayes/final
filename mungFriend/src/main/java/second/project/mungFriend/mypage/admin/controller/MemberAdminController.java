package second.project.mungFriend.mypage.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage/admin")
public class MemberAdminController {
	
	// 회원관리 화면 전환
	@GetMapping("/memberListAdmin")
	public String memberListAdminPage() {
		return "mypage/admin/memberList_admin";
	}

}
