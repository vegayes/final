package second.project.mungFriend.mypage.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.donation.model.dao.DonationMapper;
import second.project.mungFriend.donation.model.dto.Donation;
import second.project.mungFriend.event.model.dao.EventMapper;
import second.project.mungFriend.event.model.dto.Coupon;

@Service
public class ListUserServiceImpl implements ListUserService {
	
	@Autowired
	private DonationMapper mapperDonation;
	
	@Autowired
	private EventMapper mapperEvent;
	
	

	/**
	 * 사용자 후원내역
	 */
	@Override
	public List<Donation> userDonationList(int memberNo) {
		return mapperDonation.userDonationList(memberNo);
	}


	/**
	 * 사용자 쿠폰내역
	 */
	@Override
	public List<Coupon> selectCouponList(int memberNo) {
		return mapperEvent.selectCouponList(memberNo);
	}



}
