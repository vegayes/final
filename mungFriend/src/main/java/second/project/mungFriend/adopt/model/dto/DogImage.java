package second.project.mungFriend.adopt.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DogImage {

	int imageNo;
	int dogNo;
	String imagePath;
	String imageReName;
	String imageOriginal;
	String imageOrder;
	
}
