package second.project.mungFriend.mypage.admin.model.service;

import java.util.List;
import java.util.Map;

import second.project.mungFriend.admissionApply.model.dto.Admission;
import second.project.mungFriend.donation.model.dto.Donation;

public interface ListAdminServcie {

	/** 입소신청 내역
	 * @return
	 */
	List<Admission> selectAdmissionList();

	/** 후원내역 조회
	 * @return
	 */
	//List<Donation> selectDonationList();
	Map<String, Object> selectDonationList(int cp);

	/** 검색 후원 내역 가져오기
	 * @param donationSearch
	 * @return
	 */
	//List<Donation> searchDonationList(String donationSearch);
	Map<String, Object> searchDonationList(String donationSearch, int cp);

	/**입소신청 내역 (객체 가져오기)
	 * @param admNo
	 * @return
	 */
	Admission selectAdmissionInfo(int admNo);

	

	

}
