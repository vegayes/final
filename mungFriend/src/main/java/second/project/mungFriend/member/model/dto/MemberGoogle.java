package second.project.mungFriend.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberGoogle {
	private String googleEmail;
	private String googleId;
	private String googlePw;
	private String googleName;
	private String googleNickName;
	private String googleMobile;
}
