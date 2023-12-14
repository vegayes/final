package second.project.mungFriend.donation.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import second.project.mungFriend.donation.model.dto.Donation;
import second.project.mungFriend.donation.model.dto.WebHook;
import second.project.mungFriend.donation.model.service.DonationService;

@Slf4j
@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping("/webHook")
public class WebHookController {
	
	@Autowired
	private DonationService service;
	
    /** Iamport 결제 검증 컨트롤러 **/
    private final IamportClient iamportClient = new IamportClient("3671062186288787", "r6GTetsqPbnI0fLkQvIO2WHufe2vrOdzI8AbvaWINf6pFnph0tFDCkAxKxtRydIzK7Kenx9lHGdVxUy1");
    
	@PostMapping("/data")
	@ResponseBody
	public String WebHook(@RequestBody WebHook webHook, Donation donation) throws IamportResponseException, IOException {
	    // 웹훅으로 전달된 데이터 처리
	    String imp_uid = webHook.getImp_uid(); // 예시로 받은 데이터 중 Imp_uid 값을 가져옴
	    
	    IamportResponse<Payment> resp = iamportClient.paymentByImpUid(imp_uid);
	    
	   // 1) 일반결제 제외, 2) 첫 결제 제외하고 정기결제만 doantionPay로 보냄
	    String merchantUid  = resp.getResponse().getMerchantUid();
	    
	    String[] parts = merchantUid.split("_"); // 언더스코어를 기준으로 문자열을 분할하여 배열로 저장
	    

	    
		// parts 배열의 첫 번째 값이 "donation"이 아니거나, 배열의 길이가 5이고 5번째 값이 "1"인 경우
		if (!parts[0].equals("donation") || (parts.length == 5 && parts[4].equals("1"))) {
		     System.out.println("첫 번째 값이 donation이 아니거나, 5번째 값이 1입니다.");
		     
		     return " 끝 ";
		 } else {
				 
			    //1) 회원 번호 저장
				int memberNo = 0;
				if(parts.length == 5) {
					
				    String memberNoString = parts[3]; // 문자열로 저장
				    memberNo = Integer.parseInt(memberNoString); // String을 int로 변환
				    System.out.println("웹훅 멤버 넘버 : " + memberNo);
				 }
			 
				 // 2) amount int로 바꾸기
				BigDecimal amount = resp.getResponse().getAmount();
				int amountAsInt = amount.intValue(); // BigDecimal을 int로 변환
				 
				Date date = resp.getResponse().getPaidAt();
				if (date != null) {
					
					System.out.println("웹훅 : date  + " + date);
				    Instant instant = date.toInstant(); // Date를 Instant로 변환
				    OffsetDateTime paidAt = OffsetDateTime.ofInstant(instant, ZoneId.systemDefault()); // Instant를 OffsetDateTime으로 변환
		
				    // 원하는 형식으로 변환
				    String formattedPaidAt = paidAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				    System.out.println("웹훅 변환된 paidAt: " + formattedPaidAt);
		
				    // OffsetDateTime을 java.util.Date로 변환
				    Date convertedDate = Date.from(paidAt.toInstant());
				    System.out.println("변환된 paidAt: " + convertedDate);
				    donation.setDonationDate(convertedDate);
				}
	
	
	        
	        
	        
				if(resp.getResponse().getStatus().equals("paid")) {
					System.out.println("성공");
					donation.setStatus("Y");
				}else {
					System.out.println("실패 혹은 중단");
					donation.setStatus("N");
				}
				 
				 donation.setMemberNo(memberNo);
				 donation.setImpUid(resp.getResponse().getImpUid());
				 donation.setMerchantUid(resp.getResponse().getMerchantUid());
				 donation.setDonationAmount(amountAsInt);
				 donation.setCardName(resp.getResponse().getCardName());
				 donation.setApplyNum(resp.getResponse().getApplyNum());
				 donation.setDonationType("R"); // 일단 무조건 정기임.
				 donation.setBillingKey(resp.getResponse().getCustomerUid());
				 donation.setReceiptUrl(resp.getResponse().getReceiptUrl());
				 donation.setDonationContent( parts[4]+ " 번째" +" 정기결제" );
				 
				 
		 	}
		
		
			System.out.println("웹훅 최종 Doatnion :" + donation );
			
			int result = service.donationPay(donation);
			
			if(result > 0) {
				System.out.println("웹훅 결제 성공 인서트하고 이제 y 보낼거임. ");
				return "y";
			}else {
				
				return "n";
			}

	}
}

