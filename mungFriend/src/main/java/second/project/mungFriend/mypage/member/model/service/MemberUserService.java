package second.project.mungFriend.mypage.member.model.service;

public interface MemberUserService {

	/** 회원탈퇴
	 * @param memberPw
	 * @param memberNo
	 * @return int
	 */
	int secessionUser(String memberPw, int memberNo);

}
