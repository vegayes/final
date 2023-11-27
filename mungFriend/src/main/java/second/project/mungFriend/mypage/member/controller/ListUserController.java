package second.project.mungFriend.mypage.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import second.project.mungFriend.donation.model.dto.Donation;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.mypage.member.model.service.ListUserService;

@Controller
@RequestMapping("/mypage/member")
@SessionAttributes({"loginMember"})
public class ListUserController {
	

	  @Autowired
		private ListUserService service;
	  
		/** 후원 내역 페이지 이동
		 * @param model
		 * @return
		 */
		@GetMapping("/donationList")
		public String donationPage(Model model
									,@SessionAttribute("loginMember") Member loginMember) {
			
			
			System.out.println("로그인 멤버 : " + loginMember.getMemberNo());
			
			// 1) 후원 내역 가져오기
			List<Donation> selectDonationList = service.userDonationList(loginMember.getMemberNo());
			
			for(Donation donation : selectDonationList) {
				System.out.println("후원 출력 : " + donation);
			}
			
			model.addAttribute("donationList", selectDonationList);	
			
			return "mypage/member/donationList_user";
		}	  
	
	

}