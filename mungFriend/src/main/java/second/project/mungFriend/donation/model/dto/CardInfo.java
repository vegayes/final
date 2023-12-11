package second.project.mungFriend.donation.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CardInfo {

	// 결제 
	private String pg;
	private String customer_uid;  
	private String cardNumber; 
	private String cardExpiry;  
	private String birth; 
	private String pwd2Digit; 
	
}
