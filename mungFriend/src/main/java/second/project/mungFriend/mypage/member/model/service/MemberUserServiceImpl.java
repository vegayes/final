package second.project.mungFriend.mypage.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.mypage.member.model.dao.MemberUserDAO;

@Service
public class MemberUserServiceImpl implements MemberUserService{
	
	@Autowired
	private MemberUserDAO dao;

	// 회원탈퇴
	@Override
	public int secessionUser(String memberPw, int memberNo) {
		return 0;
	}
	
	

}
