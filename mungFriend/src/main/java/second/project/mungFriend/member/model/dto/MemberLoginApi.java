package second.project.mungFriend.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberLoginApi {
    private String apiId;
    private String apiEmail;
    private String apiName;
	private String apiNickName;
	private String apiMobile;
	private String apiPw;
	private String apiGubun;
}
