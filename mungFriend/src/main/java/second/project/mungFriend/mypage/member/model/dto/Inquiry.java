package second.project.mungFriend.mypage.member.model.dto;

import java.time.LocalDateTime;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class Inquiry {
	private int inquiryNo;
	private int memberNo;
	private String inquiryTitle;
	private String inquiryContent;
	private String inquiryDate;
	private String inquiryStatus;
	private String inquiryCompleteDate;
	private String memberNickname;
}
