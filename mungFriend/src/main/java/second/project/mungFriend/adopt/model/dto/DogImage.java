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

	private int imageNo;
	private int dogNo;
	private String imagePath;
	private String imageReName;
	private String imageOriginal;
	private int imageOrder;
	
}
