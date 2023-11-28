package second.project.mungFriend.mypage.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import second.project.mungFriend.mypage.admin.model.dto.Pagination;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.mypage.admin.model.dao.MemberAdminDAO;

@Service
public class MemberAdminServiceImpl implements MemberAdminService{
	
	@Autowired
	private MemberAdminDAO dao;

	// 회원리스트 조회하기
	@Override
	public Map<String, Object> selectMemberList(int cp) {
		
		// 1. 탈퇴하지 않은 회원 리스트 수 조회
		int listCount = dao.getListCount();
		
		// 2. 1번 조회 결과 + cp 를 이용해서 Pagination 객체 생성
		// -> 내부 필드가 모두 계산되어 초기화됨
		Pagination pagination = new Pagination(listCount, cp);
		
		// 3. 현재 페이지에 해당하는 부분에 대한 회원 리스트 조회
		// ex) 100개
		//     10개 씩 보여준다
		//     1page -> 100 ~ 91
		//     2page -> 90  ~ 81
		// 몇페이지(pagination.currentPage)에 대한
		// 회원 리스트가 몇개(pagination.limit)인지 조회
		
		// RowBounds 객체
		// - 마이바티스에서 페이징처리를 위해 제공하는 객체
		// - offset 만큼 건너뛰고
		// 그 다음 지정된 행 개수만큼(limit) 만큼 조회
		
		// 1) offset 계산
		int offset
			= (pagination.getCurrentPage() - 1) * pagination.getLimit();
		
		// 2) RowBounds 객체 생성
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		List<Member> memberList = dao.selectMemberList(rowBounds);
		
		// 4. pagination, memberList를 Map에 담아서 반환
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", pagination);
		map.put("memberList", memberList);
		
		return map;
	}

	// 관리자 회원 탈퇴하기
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int memberDel(int memberNo) {
		
		return dao.memberDel(memberNo);
	}

	// 회원리스트 조회하기 (검색)
	@Override
	public Map<String, Object> selectMemberList(int cp, String memberSearch) {
		
		// 1. 탈퇴하지 않았고, 검색 조건이 일치하는 회원 리스트 수 조회
		int listCount = dao.getSearchListCount(memberSearch); // 오버로딩
		
		// 2. 1번 조회 결과 + cp를 이용해서 Pagination 객체 생성
		// -> 내부 필드가 모두 계산되어 초기화됨
		Pagination pagination = new Pagination(listCount, cp);
		
		// 현재 페이지에 해당하는 부분에 대한 회원 리스트 조회
		// 단, 검색 조건 일치하는 것만
		
		// 1) offset 계산
		int offset
			= (pagination.getCurrentPage() - 1) * pagination.getLimit();
		
		// 2) RowBounds 객체 생성
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		List<Member> memberList = dao.selectSearchMemberList(memberSearch, rowBounds);
		
		// 4. pagination, memberList를 Map에 담아서 반환
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", pagination);
		map.put("memberList", memberList);
		
		return map;
	}

}
