package second.project.mungFriend.donation.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import second.project.mungFriend.donation.model.dto.Donation;

@RestController
public class PaymentController {
	
	private IamportClient api;
	
	private IamportClient client = new IamportClient("3671062186288787", "r6GTetsqPbnI0fLkQvIO2WHufe2vrOdzI8AbvaWINf6pFnph0tFDCkAxKxtRydIzK7Kenx9lHGdVxUy1");
	
	@ResponseBody
	@RequestMapping(value = "/verify_iamport/{imp_uid}", method = RequestMethod.POST)
	public IamportResponse<Payment> verifyIamportPOST(@PathVariable(value = "imp_uid") String imp_uid) throws IamportResponseException, IOException {
		
		System.out.println("뭐야");
		
		
			return client.paymentByImpUid(imp_uid);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public String donationPay(@RequestBody Donation donation) {
		
		System.out.println("뭐");
		System.out.println("donation: " + donation);
		
		
			return "y";
	}

	
	
}
