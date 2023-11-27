package second.project.mungFriend.adoptReview.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewComment {
	private int commentNo;
	private int reviewNo;
	private int memberNo;
	private String commentContent;
	private String commentDate;
	private int parentComment;
	private char commentDelYN;
	private String memberNickname;
}
