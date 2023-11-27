package second.project.mungFriend.donation.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import second.project.mungFriend.donation.model.dao.DonationMapper;
import second.project.mungFriend.donation.model.dto.Donation;

@Service
public class DonationServiceImpl implements DonationService {
	
	@Autowired
	private DonationMapper mapper;

	/**
	 * 후원 성공 ( 후원 내역 보내기 ) 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int donationPay(Donation donation) {
		
		System.out.println("service Donation : " + donation);
		
		if(donation.getMemberNo() != 0) {  // 회원인 경우
			return mapper.donationMemberPay(donation);
		}
		
		
		//return mapper.donationPay(donation);
		return 0;
	}
	

	
	
	
}
