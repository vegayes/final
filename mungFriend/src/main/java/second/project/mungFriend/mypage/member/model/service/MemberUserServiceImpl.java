package second.project.mungFriend.mypage.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.mypage.member.model.dao.MemberUserMapper;

@Service
public class MemberUserServiceImpl implements MemberUserService{
	
	@Autowired
	private MemberUserMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	// 정보수정
	@Override
	public int updateInfoUser(Member updateMember) {
		
		return mapper.updateInfoUser(updateMember);
	}
	
	// 회원탈퇴
	@Override
	public int secessionUser(String memberPw, int memberNo) {
		
		// 1. 회원 번호가 일치하는 회원의 비밀번호 조회
		String encPw = mapper.selectEncPw(memberNo);
		
		// 2. 비밀번호가 일치하면
		if(bcrypt.matches(memberPw, encPw)) {
			// MEMBER_DEL_YN -> 'Y'로 바꾸고 1 반환
			return mapper.secessionUser(memberNo);
		}
		
		// 3. 비밀번호가 일치하지 않으면 -> 0 반환
		return 0;
		
	}

	
	

}
