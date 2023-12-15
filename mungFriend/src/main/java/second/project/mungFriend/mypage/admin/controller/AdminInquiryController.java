package second.project.mungFriend.mypage.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	    
	 // 1:1 게시글 상세 조회
		@GetMapping("/adminInquiry/{inquiryNo}")
		public String adminInquiryDetail(@PathVariable("inquiryNo") int inquiryNo, Model model) {
			
			//System.out.println("상세 조회 No :" + inquiryNo);
			
			Inquiry inquiryDetail = adminInquiryService.adminInquiryDetail(inquiryNo);
			
			//System.out.println("상세 조회 :" + inquiryDetail);
			
			model.addAttribute("inquiry", inquiryDetail);
			return "mypage/admin/adminInquiry";
		}
		
		
		
		@PostMapping("/admninInquiryBox")
		public String completeAnswer(int inquiryNo, @RequestParam String adminReply, RedirectAttributes redirectAttributes, Model model) {
		    int result = adminInquiryService.completeAnswer(inquiryNo, adminReply);
		    
		    //System.out.println(result);
		    
		    String path = null;
		    
		    if(result > 0) {
		    	redirectAttributes.addFlashAttribute("successMessage", "답변이 완료되었습니다.");
		    	path = "mypage/admin/adminInquiryBox";
		    	List<Inquiry> inquiryList = adminInquiryService.adminInquiryBox();
		    	
		    	model.addAttribute("inquiryList",inquiryList);
//		    	Inquiry inquiryDetail = adminInquiryService.adminInquiryDetail(inquiryNo);
		    } else {
		    	redirectAttributes.addFlashAttribute("successMessage", "답변 실패");
		    	path = "redirect:/completeAnswer/{inquiryNo}";
		    }
		    return path;
		}
		
		
		@GetMapping("/searchByNickname")
		public String searchByNickname(@RequestParam("nickname") String nickname, Model model) {
		    System.out.println("닉네임 : " + nickname);
		    List<Inquiry> inquiries = adminInquiryService.findInquiriesByNickname(nickname);

		   //System.out.println("출력 :" + inquiries);

		    if (inquiries.isEmpty()) {
		        model.addAttribute("message", "검색 결과가 없습니다");
		    } else {
		        model.addAttribute("inquiryList", inquiries);
		    }
		    
		    return "mypage/admin/adminInquiryBox"; // 뷰 페이지 경로 수정 필요
		}
		
}
		
		
		
		
	



	