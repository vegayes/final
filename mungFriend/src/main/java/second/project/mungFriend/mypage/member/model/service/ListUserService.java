package second.project.mungFriend.mypage.member.model.service;

import java.util.List;

import second.project.mungFriend.donation.model.dto.Donation;
import second.project.mungFriend.event.model.dto.Coupon;

public interface ListUserService {

	/** 사용자 후원 내역
	 * @param memberNo
	 * @return
	 */
	List<Donation> userDonationList(int memberNo);

	/** 사용자 쿠폰 내역
	 * @param memberNo
	 * @return
	 */
	List<Coupon> selectCouponList(int memberNo);

}
