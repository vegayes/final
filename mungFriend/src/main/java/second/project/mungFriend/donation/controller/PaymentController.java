package second.project.mungFriend.donation.controller;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CardInfo;
import com.siot.IamportRestClient.request.OnetimePaymentData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import second.project.mungFriend.donation.model.dto.RegularCardInfo;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
	
	
    /** Iamport 결제 검증 컨트롤러 **/
    private final IamportClient iamportClient = new IamportClient("3671062186288787", "r6GTetsqPbnI0fLkQvIO2WHufe2vrOdzI8AbvaWINf6pFnph0tFDCkAxKxtRydIzK7Kenx9lHGdVxUy1");

	
    @PostMapping("/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid) throws IamportResponseException, IOException{
    	System.out.println("string :" + imp_uid);
        log.info("일반(일시) 결제 진입");
        return iamportClient.paymentByImpUid(imp_uid);
    }
    

    
    // 정기결제에서 첫(1회) 결제 
    @PostMapping("/one/{merchantUid}")
	public IamportResponse<Payment> onetimePayment(@PathVariable String merchantUid , 
													@RequestBody RegularCardInfo cardData
												/*PathVOnetimePaymentData onetimeData*/) throws IamportResponseException, IOException {

    	System.out.println("cardData :" + cardData);
    	
    	
    	CardInfo card = new CardInfo(cardData.getCardNumber(), cardData.getCardExpiry(), cardData.getBirth(), cardData.getPwd2Digit());
    	BigDecimal amount = new BigDecimal(cardData.getAmount());
    	
    	
    	OnetimePaymentData onetimeData = new OnetimePaymentData(cardData.getMerchantUid(),
    													amount,
														card);
    	
    	
    	
    	onetimeData.setPg(cardData.getPg());
    	onetimeData.setName(cardData.getName());
    	onetimeData.setBuyerName(cardData.getBuyer_name());
    	onetimeData.setBuyerEmail(cardData.getBuyer_email());
    	onetimeData.setCustomer_uid(cardData.getCustomer_uid());
    	
    	
    	System.out.println("cardData :" + cardData);
    	
    	
		return iamportClient.onetimePayment(onetimeData);
//    	return null;
	}
    
    
    
//    @RequestMapping(value="/orderCompleteMobile", produces = "application/text; charset=utf8", method = RequestMethod.GET)
//	public String orderCompleteMobile(
//			@RequestParam(required = false) String imp_uid
//			, @RequestParam(required = false) String merchant_uid
//			, Model model
//			, Locale locale
//			, HttpSession session) throws IamportResponseException, IOException
//	{
//		
//		IamportResponse<Payment> result = api.paymentByImpUid(imp_uid);
//		
//		if(result.getResponse().getAmount().compareTo(BigDecimal.valueOf(100)) == 0) {
//			System.out.println("검증통과");
//		}
//		
//		return "home";
//	}
    

}
