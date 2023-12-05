package second.project.mungFriend.mypage.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.admissionApply.model.dao.FreeAdmissionMapper;
import second.project.mungFriend.admissionApply.model.dto.Admission;
import second.project.mungFriend.donation.model.dao.DonationMapper;
import second.project.mungFriend.donation.model.dto.Donation;
import second.project.mungFriend.mypage.admin.model.dto.Pagination;
import second.project.mungFriend.mypage.admin.model.dto.Pagination10;

@Service
public class ListAdminServiceImpl implements ListAdminServcie{
	
	@Autowired
	private FreeAdmissionMapper mapperAdm;
	// xml파일 안에서 mapper 여러개 쓸 수 없기 때문에 이전에 작업했던 mapper안에 사용함
	
	@Autowired
	private DonationMapper mapperDonation;
	
	
	/**
	 * 무료입소 신청 내역 조회하기
	 */
	@Override
	public List<Admission> selectAdmissionList() {
		return mapperAdm.selectAdmissionList();
	}
	
	
	/**
	 * 후원 내역 조회하기
	 
	@Override
	public List<Donation> selectDonationList() {
		return mapperDonation.selectDonationList();
	}*/
	@Override
	public Map<String, Object> selectDonationList(int cp) {
		
		 int listCount = mapperDonation.countDonationList(cp);
		
		 Pagination10 pagination = new Pagination10(listCount, cp);
	
		 int offset = (pagination.getCurrentPage()-1) * pagination.getLimit();
		
		 RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
	
		 List<Donation> donationList = mapperDonation.selectDonationList(rowBounds);
		
		  
		  // 4. pagination , boardList를 Map에 담아서 반환
		  Map<String, Object> map = new HashMap<String, Object>();
		  map.put("pagination", pagination);
		  map.put("donationList", donationList);
		
		return map;
	}


	/**
	 * 검색한 후원 내역 조회하기
	 
	@Override
	public List<Donation> searchDonationList(String donationSearch) {
		return mapperDonation.searchDonationList(donationSearch);
	}

*/
	@Override
	public Map<String, Object> searchDonationList(String donationSearch, int cp) {
		System.out.println("donationSearch : " + donationSearch);
		int listCount = mapperDonation.countSearchDonationList(donationSearch);
		
		System.out.println("list Count : " + listCount);

		Pagination pagination = new Pagination(listCount, cp);

		int offset = (pagination.getCurrentPage()-1) * pagination.getLimit();

		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());

		List<Donation> searchDonationList = mapperDonation.searchDonationList(donationSearch, rowBounds);
		
		  
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", pagination);
	    map.put("donationList", searchDonationList);

		return map;
	}


	/**
	 * 입소신청 내역 객체 가져오기
	 */
	@Override
	public Admission selectAdmissionInfo(int admNo) {
		return mapperAdm.selectAdmissionInfo(admNo);
	}


	/**
	 * 입소신청 내역을 토대로 강아지 등록 완료 후
	 */
	@Override
	public int updateAdm(int admNo) {
		return mapperAdm.updateAdm(admNo);
	}






	
	
	
}
