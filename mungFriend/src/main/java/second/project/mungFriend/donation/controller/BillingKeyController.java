package second.project.mungFriend.donation.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.BillingCustomerData;
import com.siot.IamportRestClient.response.BillingCustomer;
import com.siot.IamportRestClient.response.IamportResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import second.project.mungFriend.donation.model.dto.CardInfo;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/billingkey")
@PropertySource("classpath:/config.properties")
public class BillingKeyController {
  
//	@Value("${iamport.imp.key}")
//	private String key;
//
//	@Value("${iamport.imp.secret}")
//	private String secret;
//	
	
    /** Iamport 결제 검증 컨트롤러 **/
//    private final IamportClient iamportClient;
//    private final IamportClient iamportClient = new IamportClient(key, secret);
    private final IamportClient iamportClient = new IamportClient("3671062186288787", "r6GTetsqPbnI0fLkQvIO2WHufe2vrOdzI8AbvaWINf6pFnph0tFDCkAxKxtRydIzK7Kenx9lHGdVxUy1");
    
    
    /*빌링키 발급*/
    @PostMapping("/{customerUid}")
    public IamportResponse<BillingCustomer> postBillingCustomer(@PathVariable String customerUid ,
    															@RequestBody CardInfo cardData
    															/*, BillingCustomerData billingData*/) throws IOException, IamportResponseException {
    	
    	System.out.println("cardInfo:" + cardData);
    	
    	BillingCustomerData bcd = new BillingCustomerData(cardData.getCustomer_uid(),
														cardData.getCardNumber(),
														cardData.getCardExpiry(),
														cardData.getBirth());
    	
    	bcd.setPg(cardData.getPg());
    	bcd.setPwd2Digit(cardData.getPwd2Digit());
    	
        return iamportClient.postBillingCustomer(customerUid, bcd);
    }
    
    /*빌링키 조회*/
    @GetMapping("/{customerUid}")
    public IamportResponse<BillingCustomer> getBillingCustomer(@PathVariable String customerUid) throws IOException, IamportResponseException {
        return iamportClient.getBillingCustomer(customerUid);
    }
    
    
    
    
    
    
//    @PostMapping("/{customerUid}")
//    public IamportResponse<Payment> onetimePayment(/*OnetimePaymentData onetimeData*/) throws IamportResponseException, IOException {
//    	System.out.println("Hello. World");
//    	OnetimePaymentData onetimeData = new OnetimePaymentData();
//        return iamportClient.onetimePayment(onetimeData);
//    }
//    
    
    

}
