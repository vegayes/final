package second.project.mungFriend.mypage.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import second.project.mungFriend.mypage.admin.model.service.AdminStatusService;

@RequestMapping("/mypage/admin")
@Controller
public class AdminStatusController {
	
	@Autowired
	private AdminStatusService service;
	
	// 후원 현황 페이지 이동
	@GetMapping("/donationStatus")
	public String donationStatus(Model model) {
		
		//회원 후원내역
		List<Map<String,Object>> memberDonationStatus = service.memberDonationList();
		
		//비회원 후원내역
		List<Map<String,Object>> nonMemberDonationStatus = service. nonMemberDonationList();
		
		System.out.println("회원 후원내역 : " + memberDonationStatus);
		System.out.println("비회원 후원내역 : " + nonMemberDonationStatus);
		
		model.addAttribute("member", memberDonationStatus);
		model.addAttribute("nonMember", nonMemberDonationStatus);
		
		return"mypage/admin/DonationStatus";
	}
	
	
	
	// 유기견 현황 페이지 이동
		@GetMapping("/dogStatus")
		public String dogStatus(Model model) {
			
			List<Map<String,Object>> dogStatus = service.selectDogList();
			
			
			List<Map<String,Object>> adoptStatus = service.selectAdoptList();
			//System.out.println("입양 현황 : " + adoptStatus);
			
			model.addAttribute("dogStatus", dogStatus);
			model.addAttribute("adoptStatus", adoptStatus);
			
			return"mypage/admin/DogStatus";
		}
	
}
