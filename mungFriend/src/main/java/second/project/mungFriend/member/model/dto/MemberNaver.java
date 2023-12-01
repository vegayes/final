package second.project.mungFriend.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberNaver {
    private String naverId;
    private String naverEmail;
    private String naverName;
	private String naverNickName;
	private String mobile;
	private String naverPw;
}
