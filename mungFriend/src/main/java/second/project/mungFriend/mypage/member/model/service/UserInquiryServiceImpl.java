package second.project.mungFriend.mypage.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import second.project.mungFriend.mypage.member.model.dao.UserInquiryMapper;
import second.project.mungFriend.mypage.member.model.dto.Inquiry;

public class UserInquiryServiceImpl {
  
	
	 @Autowired
	    private UserInquiryMapper userInquiryMapper;

	    public void submitInquiry(Inquiry inquiry) {
	        userInquiryMapper.insertInquiry(inquiry);
	    }

//	    public List<Inquiry> getUserInquiries(String userId) {
//	        return userInquiryMapper.findInquiriesByUserId(userId);
//	    }
}
