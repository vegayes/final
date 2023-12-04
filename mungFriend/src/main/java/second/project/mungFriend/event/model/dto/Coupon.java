package second.project.mungFriend.event.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Coupon {
	
	private int couponNo;
	private int memberNo; // (회원 테이블) 외래키
	private int couponContentNo; // (쿠폰 내용 테이블) 외래키
	private String couponDate;
	private String couponStatus;
	private String couponExpiration;
	
	
	private String couponContent;
	private String couponImg;	
	private int correctMin;	
	private int correctMax;	
	
	private int totalScore;
	

}
