package second.project.mungFriend.mypage.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.donation.model.dao.DonationMapper;
import second.project.mungFriend.donation.model.dto.Donation;

@Service
public class ListUserServiceImpl implements ListUserService {
	
	@Autowired
	private DonationMapper mapperDonation;
	

	@Override
	public List<Donation> userDonationList(int memberNo) {
		return mapperDonation.userDonationList(memberNo);
	}



}
