package second.project.mungFriend.donation.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.BillingCustomerData;
import com.siot.IamportRestClient.request.OnetimePaymentData;
import com.siot.IamportRestClient.response.BillingCustomer;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/billingkey")
public class BillingKeyController {
	
	 /** Iamport 결제 검증 컨트롤러 **/
    private final IamportClient iamportClient = new IamportClient("3671062186288787", "r6GTetsqPbnI0fLkQvIO2WHufe2vrOdzI8AbvaWINf6pFnph0tFDCkAxKxtRydIzK7Kenx9lHGdVxUy1");
    
    
    /** 프론트에서 받은 PG사 결괏값을 통해 아임포트 토큰 발행 **/
//    @PostMapping("/{imp_uid}")
//    public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid) throws IamportResponseException, IOException{
//        return iamportClient.paymentByImpUid(imp_uid);
//    }
    
    @PostMapping("/{customerUid}")
    public IamportResponse<BillingCustomer> postBillingCustomer(@PathVariable String customerUid /*, BillingCustomerData billingData*/) throws IOException, IamportResponseException {
    	System.out.println("Hello. World");
    	
    	BillingCustomerData bcd = new BillingCustomerData("customer_1234",
				"5107-3792-7333-9589",
				"2028-08",
				"010328");
    	
    	bcd.setPg("html5_inicis");
    	bcd.setBirth("010328");
    	bcd.setPwd2Digit("65");
        return iamportClient.postBillingCustomer(customerUid, bcd);
    }
    
//    @PostMapping("/{customerUid}")
//    public IamportResponse<Payment> onetimePayment(/*OnetimePaymentData onetimeData*/) throws IamportResponseException, IOException {
//    	System.out.println("Hello. World");
//    	OnetimePaymentData onetimeData = new OnetimePaymentData();
//        return iamportClient.onetimePayment(onetimeData);
//    }
//    
    
    

}
