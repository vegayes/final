package second.project.mungFriend.mypage.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.mypage.admin.model.dao.AdminStatusMapper;

@Service
public class AdminStatusServiceImpl implements AdminStatusService{
	
	@Autowired
	private AdminStatusMapper mapper;
	
	
	
	/**
	 * 회원 후원금 개월별 조회
	 */
	@Override
	public List<Map<String, Object>> memberDonationList() {

		
		return   mapper.memberDonationList();

	}

	
	/**
	 * 비회원 후원금 개월별 조회
	 */
	@Override
	public List<Map<String, Object>> nonMemberDonationList() {
		
		return mapper.nonMemberDonationList();
	}
	
	/**
	 * 입소한 개 마리수 개월별 조회
	 */
	@Override
	public List<Map<String, Object>> selectDogList() {
		
		return mapper.selectDogList();
	}

	
	
	/**
	 * 입양된 개 마리수 개월별 조회
	 */
	@Override
	public List<Map<String, Object>> selectAdoptList() {
		
		return mapper.selectAdoptList();
	}



	

}
