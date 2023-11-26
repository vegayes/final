package second.project.mungFriend.mypage.admin.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.mypage.admin.model.dao.MemberAdminDAO;

@Service
public class MemberAdminServiceImpl implements MemberAdminService{
	
	@Autowired
	private MemberAdminDAO dao;

	// 회원리스트 조회하기
	@Override
	public List<Member> selectMemberList() {
		
		return dao.selectMemberList();
	}

	// 관리자 회원 탈퇴하기
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int memberDel(int memberNo) {
		
		return dao.memberDel(memberNo);
	}

}
