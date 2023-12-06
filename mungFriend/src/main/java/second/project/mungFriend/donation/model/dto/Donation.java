package second.project.mungFriend.donation.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Donation {
	
	private int donationNo;
	private int donationInfoNo; // 이게 진짜임.
	private int memberNo; // (회원 테이블) 외래키
	private String donationContent;
	private String donationType;	
	private int donationAmount;
	private String donationDate;
	private String donationStatus;	
	
	// 회원 정보
	private String memberName;
	private String memberEmail;
	
	
	// 결제 
	private String impUid;  // 포트원 고유 결제 번호
	private String applyNum; // 승인번호
	private String merchantUid; // 주문번호 
	private String cardName; // 카드사 이름
	private String paidAt; // 결제 승인 시각
	private String receiptUrl; // 영수증
	private String status; // 결제 승인 상태
	private String billingKey; // 빌링키 
	
}
