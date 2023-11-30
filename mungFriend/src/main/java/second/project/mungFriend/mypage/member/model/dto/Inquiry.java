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
	  private Long inquiryId; // INQUIRY_ID
	    private String userId; // USER_ID
	    private String title; // INQUIRY_TITLE
	    private String content; // INQUIRY_CONTENT
	    private LocalDateTime inquiryDate; // INQUIRY_DATE
	    private String status; // INQUIRY_STATUS
	    private String answerContent; // ANSWER_CONTENT
	    private LocalDateTime answerDate; // ANSWER_DATE

}
