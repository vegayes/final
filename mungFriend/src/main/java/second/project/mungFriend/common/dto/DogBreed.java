package second.project.mungFriend.common.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DogBreed {
	
   private int dogNo;
   private String dogName;
   private String thumbnail;	
}
