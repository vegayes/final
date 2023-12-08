package second.project.mungFriend.donation.model.dto;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RegularCardInfo {
	// 빌링키 얻어오기
	private String pg;
	private String customer_uid;  
	private String cardNumber; 
	private String cardExpiry;  
	private String birth; 
	private String pwd2Digit; 
	
	// 정기결제 
	private String pay_method;
	private String merchantUid;
	private String name;
	private int amount;
	private String buyer_name;
	private String buyer_email;
	
	// 스케쥴
	private long schedule_at;
	
//	private Map<String, Object> schedule;
}
