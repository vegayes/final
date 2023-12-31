package second.project.mungFriend.donation.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.ScheduleData;
import com.siot.IamportRestClient.request.ScheduleEntry;
import com.siot.IamportRestClient.request.UnscheduleData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import second.project.mungFriend.donation.model.dto.RegularCardInfo;
import second.project.mungFriend.donation.model.service.DonationService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/regular")
@PropertySource("classpath:/config.properties")
public class ScheduleDonation {
	
	@Autowired
	private DonationService service;
	
//	@Value("${iamport.imp.key}")
//	private String key;
//
//	@Value("${iamport.imp.secret}")
//	private String secret;
	
	private final IamportClient iamportClient = new IamportClient("3671062186288787", "r6GTetsqPbnI0fLkQvIO2WHufe2vrOdzI8AbvaWINf6pFnph0tFDCkAxKxtRydIzK7Kenx9lHGdVxUy1");
    
 	/** 결제 (정기)  예약
 	 * @param customerUid
 	 * @param Schedule
 	 * @return
 	 * @throws IamportResponseException
 	 * @throws IOException
 	 */
 	@PostMapping("/schedule/{customerUid}")
 	@ResponseBody
	public IamportResponse<List<Schedule>> subscribeSchedule(@PathVariable String customerUid ,
															@RequestBody List<RegularCardInfo> Schedule/*ScheduleData scheduleData*/) throws IamportResponseException, IOException {
		
		ScheduleData scheduleData = new ScheduleData(customerUid);
		
		for(RegularCardInfo oneSchedule : Schedule) {
			
			//System.out.println("나 들어옴");
			//System.out.println("unix 시간 : " + oneSchedule.getSchedule_at());
			
			
//            Date date = new Date(oneSchedule.getSchedule_at() * 1000L); // Unix 타임스탬프를 밀리초로 변환하여 Date 객체 생성
//            System.out.println("Date 객체로 변환된 날짜: " + date);

			Date date = new Date(oneSchedule.getSchedule_at()*1000L);

			// SimpleDateFormat을 사용하여 날짜와 시간 형식 지정하기
//			System.out.println("Date 객체로 변환된 날짜: " + date);
			
//			Date schedule_at = new Date(oneSchedule.getSchedule_at());
			
	        BigDecimal amount = new BigDecimal(oneSchedule.getAmount());
	        
	        //System.out.println("정기결제 예약금액 : " + amount);

            ScheduleEntry entry = new ScheduleEntry(oneSchedule.getMerchantUid(), date, amount);

            entry.setName(oneSchedule.getName());
            entry.setBuyerName(oneSchedule.getBuyer_name());
            entry.setBuyerEmail(oneSchedule.getBuyer_email());

            scheduleData.addSchedule(entry);

		}
		
		
		return iamportClient.subscribeSchedule(scheduleData);
	}
 	
 	
 	/** 예약내역 취소
 	 * @param customerUid
 	 * @return
 	 * @throws IamportResponseException
 	 * @throws IOException
 	 */
 	@PostMapping("/schedule")
 	@ResponseBody
	public IamportResponse<List<Schedule>> unsubscribeSchedule(@RequestBody RegularCardInfo cancelData) throws IamportResponseException, IOException {

 		//System.out.println("cancelData :" + cancelData);
 		//System.out.println("빌링키 :" + cancelData.getCustomer_uid());
 		//System.out.println("list :" + cancelData.getMerchantList());
 		
 		UnscheduleData cancelSchedule = new UnscheduleData(cancelData.getCustomer_uid());
 		
 		if(cancelData != null) {
 			for(String cancel : cancelData.getMerchantList()) {
 				//System.out.println("취소 내용 :" + cancel);
 				cancelSchedule.addMerchantUid(cancel);
 			}
 			
 		}
 		
 		
 		return  iamportClient.unsubscribeSchedule(cancelSchedule);
	}
 	
	/** 예약내역 취소 성공 시, DB 값 바꾸기 
 	 * @param customerUid
 	 * @return
 	 * @throws IamportResponseException
 	 * @throws IOException
 	 */
 	@PostMapping("/cancelCheck")
 	@ResponseBody
	public int cancelCheck(@RequestBody String merchantData) throws IamportResponseException, IOException {

 		merchantData = merchantData.replace("\"", "");

 		int result = service.cancelCheck(merchantData);

 		return result;
	}
 	
 	
 	
 	
 	
 	
	
	
}
