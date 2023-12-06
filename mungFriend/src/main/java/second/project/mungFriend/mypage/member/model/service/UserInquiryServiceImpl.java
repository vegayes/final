package second.project.mungFriend.mypage.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.mypage.member.model.dao.UserInquiryDAO;
import second.project.mungFriend.mypage.member.model.dao.UserInquiryMapper;
import second.project.mungFriend.mypage.member.model.dto.Inquiry;

@Service
public class UserInquiryServiceImpl implements UserInquiryService {
	
	
	  private final UserInquiryMapper mapper;

	    @Autowired
	    public UserInquiryServiceImpl(UserInquiryMapper mapper) {
	        this.mapper = mapper;
	    }
	@Autowired
	private UserInquiryDAO dao;

	// 1:1 등록
	@Override
	public int userInquiry(Inquiry inquiry) {
		return dao.userInquiry(inquiry);
	}

	// inquiryList 조회하기
	@Override
	public List<Inquiry> inquiryList(int memberNo) {
		return dao.inquiryList(memberNo);
	}

	@Override
	public Inquiry selectInquiryDetail(int inquiryNo) {
		return dao.selectInquiryDetail(inquiryNo);
	}
     
	// 문의 삭제
	@Override
	public int deleteInquiry(int inquiryNo) {
		 return mapper.deleteInquiry(inquiryNo);
	}

	

	
  
}
