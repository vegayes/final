package second.project.mungFriend.mypage.admin.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.admissionApply.model.dao.FreeAdmissionMapper;
import second.project.mungFriend.admissionApply.model.dto.Admission;

@Service
public class ListAdminServiceImpl implements ListAdminServcie{
	
	@Autowired
	private FreeAdmissionMapper mapper;
	// xml파일 안에서 mapper 여러개 쓸 수 없기 때문에 이전에 작업했던 mapper안에 사용함
	/**
	 * 무료입소 신청 내역 조회하기
	 */
	@Override
	public List<Admission> selectAdmissionList() {
		return mapper.selectAdmissionList();
	}

	
	
	
}
