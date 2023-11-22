package second.project.mungFriend.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Member {

	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberEmail;
	private String memberName;
	private String memberTel;
	private String memberNickname;
	private String memberAddress;
	private String memberProfile;
	private String memberEnrollDate;
	private String adminYn;
	private String memberDelYn;
	
}
