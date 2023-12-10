package second.project.mungFriend.donation.model.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
		}else { // 비회원의 경우
			return mapper.donationNonMemberPay(donation);
		}
	}

	/**
	 * 후원내역 가져오기
	 */
	@Override
	public List<Donation> selectRegularList(String extractedValue) {
		
		List<Donation> regularList = mapper.selectRegularList(extractedValue);
		System.out.println("regularList" + regularList);
		for(Donation regular : regularList) {
			System.out.println("후원 내 : " + regular);
			Date date = regular.getDonationDate();
			
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String paidAt = fm.format(date);
			System.out.println("후원 내 값  date 값 : " + paidAt );
			regular.setPaidAt(paidAt);
			
		}
		
		return regularList;
	}
	

	
	
	
}
