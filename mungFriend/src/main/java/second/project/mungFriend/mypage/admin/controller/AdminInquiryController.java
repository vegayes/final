package second.project.mungFriend.mypage.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import second.project.mungFriend.mypage.admin.model.service.AdminInquiryService;
import second.project.mungFriend.mypage.member.model.dto.Inquiry;


@RequestMapping("/mypage/admin")
@Controller
public class AdminInquiryController {

	
	  @Autowired
	    private AdminInquiryService adminInquiryService;

	    @GetMapping("/adminInquiryBox")
	    public String adminInquiryBox(Model model) {
	    	
	    	List<Inquiry> inquiryList = adminInquiryService.adminInquiryBox();
	    	
	        model.addAttribute("inquiryList", inquiryList);
	        return "mypage/admin/adminInquiryBox";
	    }
}
	