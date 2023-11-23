package second.project.mungFriend.adoptReview.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Review {
	private int reviewNo;
	private int memberNo;
	private String reviewTitle;
	private String reviewContent;
	private int reviewCount;
	private String reviewDate;
	private char reviewDelYN;
	
	
	
	
	// 이미지 목록
    private List<ReviewImage> imageList;
    
    // 댓글 목록
    private List<ReviewComment> commentList;
}
