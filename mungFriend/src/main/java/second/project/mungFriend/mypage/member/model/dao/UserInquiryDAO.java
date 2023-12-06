package second.project.mungFriend.mypage.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import second.project.mungFriend.mypage.member.model.dto.Inquiry;

@Repository
public class UserInquiryDAO {
	
	@Autowired
	private UserInquiryMapper mapper;
	
	/** 1:1 등록
	 * @param inquiry
	 * @return int
	 */
	public int userInquiry(Inquiry inquiry) {
		return mapper.userInquiry(inquiry);
	}

	/** inquiryList 조회하기
	 * @return List
	 */
	public List<Inquiry> inquiryList(int memberNo) {
		return mapper.inquiryList(memberNo);
	}
	
	/** 1:1 문의 상세 조회
	 * @param inquiryNo
	 * @return Inquiry
	 */
	public Inquiry selectInquiryDetail(int inquiryNo) {
	    return mapper.selectInquiryDetail(inquiryNo);
	}
	



}
