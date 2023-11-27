package second.project.mungFriend.donation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import second.project.mungFriend.donation.model.dto.Donation;
import second.project.mungFriend.donation.model.service.DonationService;
import second.project.mungFriend.member.model.dto.Member;

@Controller
@RequestMapping("/donation")
@SessionAttributes({"loginMember"})
public class DonationController {
	
	@Autowired
	private DonationService service;
	
	@PostMapping("donationPay")
	@ResponseBody
	public String donationPay(@RequestBody Donation donation,
			@SessionAttribute(value ="loginMember", required = false) Member loginMember) throws Exception{

		
	
		
		System.out.println("결제 성공");
		
		System.out.println("donation : " + donation);
		System.out.println("donation Type : " + donation.getDonationType() );
		System.out.println("billingKey : " + donation.getBillingKey() );
		
		// 후원 주기 선택
		if(donation.getDonationType().equals("정기")) {
			donation.setDonationType("R");
		}else {
			System.out.println("일시");
			donation.setDonationType("O");
		}
		
		// 후원 결제 상태
		if(donation.getStatus().equals("paid")) {
			System.out.println("성공");
			donation.setStatus("Y");
		}else {
			System.out.println("실패 혹은 중단");
			donation.setStatus("N");
		}
		
		
		if(loginMember != null) { // 로그인이 된 경우
			System.out.println("로그인  회원 정보 : "+  loginMember.getMemberNo());
			donation.setMemberNo(loginMember.getMemberNo());
		}
        
		int result = service.donationPay(donation);
		
		if(result > 0) {
			System.out.println("결제 성공 인서트하고 이제 y 보낼거임. ");
			return "y";
		}else {
			
			return "n";
		}
	}
	
	
	

}
