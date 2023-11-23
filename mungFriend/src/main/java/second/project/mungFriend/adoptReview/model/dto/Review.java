package second.project.mungFriend.adoptReview.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Review {
	private int reviewNo;
	private String reviewTitle;
	private String reviewContent;
	private String reviewDate;
	private char reviewDelYN;
}
