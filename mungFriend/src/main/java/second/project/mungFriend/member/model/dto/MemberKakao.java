package second.project.mungFriend.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberKakao {
    private String kakaoId;
    private String kakaoEmail;
    private String kakaoName;
	private String kakaoNickName;
	private String kakaomobile;
	private String kakaoPw;
}
