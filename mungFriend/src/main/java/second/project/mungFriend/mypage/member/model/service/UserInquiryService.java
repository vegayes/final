package second.project.mungFriend.mypage.member.model.service;

import java.util.List;

import second.project.mungFriend.mypage.member.model.dto.Inquiry;

public interface UserInquiryService {
               
	
	  void submitInquiry(Inquiry inquiry); // 문의 제출
	    List<Inquiry> getUserInquiries(String userId); // 사용자의 문의 목록 조회
	}
	
	
	

