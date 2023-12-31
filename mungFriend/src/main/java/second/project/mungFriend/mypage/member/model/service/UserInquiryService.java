package second.project.mungFriend.mypage.member.model.service;

import java.util.List;

import second.project.mungFriend.mypage.member.model.dto.Inquiry;

public interface UserInquiryService {

	/** 1:1 등록
	 * @param inquiry
	 * @return int
	 */
	int userInquiry(Inquiry inquiry);

	/** inquiryList 조회하기
	 * @return List
	 */
	List<Inquiry> inquiryList(int memberNo);
     
	
	/**1:1 문의 상세 조회
	 * 
	 * @param inquiryNo
	 * @return
	 */
	Inquiry selectInquiryDetail(int inquiryNo);
    
	
	/**
	 * 문의 삭제
	 * @param inquiryNo
	 * @return
	 */
	int deleteInquiry(int inquiryNo);
    

}
	
	
	

