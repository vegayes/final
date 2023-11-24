package second.project.mungFriend.member.model.service;

public interface AjaxService {

	/** 아이디 중복검사
	 * @param id
	 * @return int
	 */
	int checkId(String id);

	/** 이메일 중복검사
	 * @param email
	 * @return int
	 */
	int checkEmail(String email);

	/** 닉네임 중복검사
	 * @param nickname
	 * @return int
	 */
	int checkNickname(String nickname);

}
