package second.project.mungFriend.mypage.admin.model.service;

import java.util.List;
import java.util.Map;

import second.project.mungFriend.mypage.member.model.dto.Inquiry;

public interface AdminInquiryService {

	

	List<Inquiry> adminInquiryBox();
	
	
	/**1:1 문의 상세 조회
	 * 
	 * @param inquiryNo
	 * @return
	 */
	Inquiry adminInquiryDetail(int inquiryNo);


	int completeAnswer(int inquiryNo, String adminReply);


	List<Inquiry> findInquiriesByNickname(String nickname);


	





	
}
