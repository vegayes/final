package second.project.mungFriend.mypage.admin.model.service;

import java.util.List;

import second.project.mungFriend.admissionApply.model.dto.Admission;
import second.project.mungFriend.donation.model.dto.Donation;

public interface ListAdminServcie {

	/** 입소신청 내역
	 * @return
	 */
	List<Admission> selectAdmissionList();

	/** 후원내역
	 * @return
	 */
	List<Donation> selectDonationList();

	/** 검색 후원 내역 가져오기
	 * @param donationSearch
	 * @return
	 */
	List<Donation> searchDonationList(String donationSearch);

}
