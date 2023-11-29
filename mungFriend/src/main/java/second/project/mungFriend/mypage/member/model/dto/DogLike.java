package second.project.mungFriend.mypage.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DogLike {
	
	private int likeNo;
	private int dogNo;
	private int memberNo;
	
	// DOG 테이블 join
	private String dogName;
	private String dogBirthDay;
	private String dogGender;
	
	// DOG_IMG 테이블 join
	private String thumbnail;
	
}