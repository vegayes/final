package second.project.mungFriend.donation.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import second.project.mungFriend.donation.model.dto.Donation;

@Mapper
public interface DonationMapper {


	/** 회원 후원 
	 * @param donation
	 * @return
	 */
	int donationMemberPay(Donation donation);
	
	/** 비회원 후원
	 * @param donation
	 * @return
	 */
	int donationNonMemberPay(Donation donation);
	
	
	
	
	
	
	
	/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	 					마이페이지 관리자
	 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
	/** 후원내역 개수 조회
	 * @param cp
	 * @return
	 */
	int countDonationList(int cp); 
	
	/** 후원내역
	 * @return
	 */
	//List<Donation> selectDonationList();
	List<Donation> selectDonationList(RowBounds rowBounds);
	
	
	/** 검색한 후원내역 개수 조회
	 * @param donationSearch
	 * @return
	 */
	int countSearchDonationList(String donationSearch);
	
	/** 검색한 후원내역 
	 * @param donationSearch
	 * @return
	 */
	//List<Donation> searchDonationList(String donationSearch);	
	List<Donation> searchDonationList(String donationSearch, RowBounds rowBounds);

	
	/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	 					마이페이지 회원
	 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/		
	/**후원내역 개수 조회
	 * @param cp
	 * @return
	 */
	int countUserDonation(int memberNo);
	
	/** 후원내역
	 * @param memberNo 
	 * @return
	 */
	//List<Donation> userDonationList(int memberNo);
	List<Donation> userDonationList(int memberNo, RowBounds rowBounds);

	/** 정기 후원 내역 
	 * @param extractedValue
	 * @return
	 */
	List<Donation> selectRegularList(String extractedValue);







	






	






	

	
}
