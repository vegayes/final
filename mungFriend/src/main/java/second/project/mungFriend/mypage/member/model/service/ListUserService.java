package second.project.mungFriend.mypage.member.model.service;

import java.util.List;

import second.project.mungFriend.donation.model.dto.Donation;

public interface ListUserService {

	List<Donation> userDonationList(int memberNo);

}
