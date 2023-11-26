package second.project.mungFriend.donation.model.dao;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.donation.model.dto.Donation;

@Mapper
public interface DonationMapper {


	int donationMemberPay(Donation donation);
	
	//int donationPay(Donation donation);
	
	

}
