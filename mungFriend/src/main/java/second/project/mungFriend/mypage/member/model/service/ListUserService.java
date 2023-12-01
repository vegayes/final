package second.project.mungFriend.mypage.member.model.service;

import java.util.List;
import java.util.Map;

import second.project.mungFriend.event.model.dto.Coupon;

public interface ListUserService {

	/** 사용자 후원 내역
	 * @param memberNo
	 * @return
	 */
	//List<Donation> userDonationList(int memberNo);
	Map<String, Object> userDonationList(int memberNo, int cp);
	
	/** 사용자 쿠폰 내역
	 * @param memberNo
	 * @return
	 */
	List<Coupon> selectCouponList(int memberNo);

	

}
