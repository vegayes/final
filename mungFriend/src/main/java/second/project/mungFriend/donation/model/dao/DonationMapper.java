package second.project.mungFriend.donation.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.donation.model.dto.Donation;

@Mapper
public interface DonationMapper {


	/** 회원 후원 
	 * @param donation
	 * @return
	 */
	int donationMemberPay(Donation donation);
	
	//int donationPay(Donation donation);
		
	
	
	
	
	
	
	
	
	/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	 					마이페이지 관리자
	 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/	
	/** 후원내역
	 * @return
	 */
	List<Donation> selectDonationList();
	
	
	
	/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	 					마이페이지 회원
	 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/		
	/** 후원내역
	 * @param memberNo 
	 * @return
	 */
	List<Donation> userDonationList(int memberNo);
	
}
