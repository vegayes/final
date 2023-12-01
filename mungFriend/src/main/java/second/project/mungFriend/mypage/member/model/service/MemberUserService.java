package second.project.mungFriend.mypage.member.model.service;

import org.springframework.web.multipart.MultipartFile;

import second.project.mungFriend.member.model.dto.Member;

public interface MemberUserService {

	/** 정보수정
	 * @param profileImage
	 * @param updateMember
	 * @param loginMember
	 * @return int
	 */
	int updateInfoUser(MultipartFile profileImage, Member updateMember, Member loginMember) throws Exception;
	
	/** 회원탈퇴
	 * @param memberPw
	 * @param memberNo
	 * @return int
	 */
	int secessionUser(String memberPw, int memberNo);


}
