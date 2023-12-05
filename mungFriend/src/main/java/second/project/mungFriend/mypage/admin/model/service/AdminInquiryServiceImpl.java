package second.project.mungFriend.mypage.admin.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import second.project.mungFriend.mypage.admin.model.dao.AdminInquiryMapper;
import second.project.mungFriend.mypage.member.model.dto.Inquiry;

import java.util.List;

@Service
public class AdminInquiryServiceImpl implements AdminInquiryService {

    @Autowired
    private AdminInquiryMapper mapper;

   

	@Override
	public List<Inquiry> adminInquiryBox() {
		return mapper.adminInquiryBox();
		
	}
}
