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

	int imageNo;
	int reviewNo;
	String imagePath;
	String imageReName;
	String imageOriginal;
	String imageOrder;
	
}
