package second.project.mungFriend.mypage.member.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import second.project.mungFriend.donation.model.dto.Donation;
import second.project.mungFriend.event.model.dto.Coupon;
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
		public String donationPage(Model model,
									@SessionAttribute("loginMember") Member loginMember,
									@RequestParam(value="cp", required = false, defaultValue = "1") int cp) {
			
			
			System.out.println("로그인 멤버 : " + loginMember.getMemberNo());
			
			// 1) 후원 내역 가져오기
			//List<Donation> selectDonationList = service.userDonationList(loginMember.getMemberNo());
			Map<String, Object> userDonationList = service.userDonationList(loginMember.getMemberNo(), cp);

			
			model.addAttribute("map", userDonationList);	
			
			return "mypage/member/donationList_user";
		}	  
	
		/** 쿠폰 내역 페이지 이동
		 * @param model
		 * @param loginMember
		 * @return
		 */
		@GetMapping("/couponList")
		public String couponPage(Model model
				,@SessionAttribute("loginMember") Member loginMember) {
			
			// 1) 쿠폰 내역 가져오기
			List<Coupon> selectCouponList = service.selectCouponList(loginMember.getMemberNo());
			
			for(Coupon  coupon : selectCouponList) {
				System.out.println("후원 출력 : " + coupon);
			}
			
			model.addAttribute("couponList", selectCouponList);				
			
			return "mypage/member/couponBox_user";
		}
		
		

}
