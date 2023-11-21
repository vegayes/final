package second.project.mungFriend.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import second.project.mungFriend.member.model.dao.MemberDAO;
import second.project.mungFriend.member.model.dto.Member;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDAO dao;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	// 로그인
	@Override
	public Member login(Member inputMember) {
		
		Member loginMember = dao.login(inputMember);
		
//		if(loginMember != null) {
//			
//			if(bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
//				
//				loginMember.setMemberPw(null);
//				
//			} else {
//				
//				loginMember = null;
//				
//			}
//		}
		
		return loginMember;
	}

}
