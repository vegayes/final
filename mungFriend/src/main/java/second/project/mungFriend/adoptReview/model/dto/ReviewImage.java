package second.project.mungFriend.adoptReview.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewImage {

	private int imageNo;
	private int reviewNo;
	private String imagePath;
	private String imageReName;
	private String imageOriginal;
	private int imageOrder;
	
}
