package second.project.mungFriend.donation.controller;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping("/donationPay")
	@ResponseBody
	public String donationPay(@RequestBody Donation donation,
			@SessionAttribute(value ="loginMember", required = false) Member loginMember) throws Exception{
		
		System.out.println("결제 성공");
		
		System.out.println("donation : " + donation);
		System.out.println("donation Type : " + donation.getDonationType() );
		System.out.println("billingKey : " + donation.getBillingKey() );
		
        String paidAtString = donation.getPaidAt(); // donation 객체에서 paidAt 값 가져오기

        // 주어진 문자열을 OffsetDateTime으로 파싱
        if (paidAtString != null) {
            
            // 원하는 형식으로 변환

            OffsetDateTime paidAt = OffsetDateTime.parse(paidAtString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

	         // OffsetDateTime을 java.util.Date로 변환
	        Instant instant = paidAt.toInstant();
	        Date date = Date.from(instant);
	         System.out.println("변환된 paidAt: " + date);
	         donation.setDonationDate(date);
        }
        
		
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
	
	@PostMapping("/regular/detail")
	@ResponseBody
	public List<Donation> regularList(@RequestBody String merchant_uid,
			@SessionAttribute(value ="loginMember", required = false) Member loginMember) throws Exception{
		
		System.out.println("정보 분석");
		System.out.println("merchnat_uid :" + merchant_uid);
		
        // 맨 뒤의 '_'의 인덱스 찾기
        int lastIndex = merchant_uid.lastIndexOf('_');

        // 맨 뒤의 '_' 이전까지의 부분 문자열 추출
        String extractedValue = merchant_uid.substring(1, lastIndex);

		
		//1) 해당 정기결제와 관련된 결제 정보들 가져오기
		List<Donation> regularList = service.selectRegularList(extractedValue);
		
		
		return regularList;
	}

	
	@GetMapping("/memberInfo")
	@ResponseBody
	public Donation selectMemberInfo(@RequestParam int donationNo) throws Exception{
		
		System.out.println("정보 분석");
		System.out.println("donationNo :" + donationNo);
		
		//1) 후원 정보 분석
		Donation memberInfo = service.selectMemberInfo(donationNo);
		
		
		return memberInfo;
	}
	
	

}
