package second.project.mungFriend.mypage.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/mypage/admin")
@Controller
public class AdminInquiryController {

	
	
	
	@GetMapping("/adminInquiryBox")
	public String adminInquiryBox() {
	return "mypage/admin/adminInquiryBox";
	}
}
