package second.project.mungFriend.donation.model.service;

import java.util.List;

import second.project.mungFriend.donation.model.dto.Donation;

public interface DonationService {

	int donationPay(Donation donation);

	/** 마이페이지 / 유저 정기 후원 내역 가져오기
	 * @param extractedValue
	 * @return
	 */
	List<Donation> selectRegularList(String extractedValue);

	/** 마이페이지/ 결제 취소 된 내역 DB 변경
	 * @param merchantData
	 * @return
	 */
	int cancelCheck(String merchantData);

	/** 마이페이지 관리자 페이지에서 회원정보 가져오기
	 * @param donationNo
	 * @return
	 */
	Donation selectMemberInfo(int donationNo);

}
