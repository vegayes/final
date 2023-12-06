package second.project.mungFriend.mypage.admin.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import second.project.mungFriend.mypage.admin.model.dao.AdminInquiryMapper;
import second.project.mungFriend.mypage.member.model.dto.Inquiry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminInquiryServiceImpl implements AdminInquiryService {

    @Autowired
    private AdminInquiryMapper mapper;

   

	@Override
	public List<Inquiry> adminInquiryBox() {
		return mapper.adminInquiryBox();
		
	}



	@Override
	public Inquiry adminInquiryDetail(int inquiryNo) {
		return mapper.adminInquiryDetail(inquiryNo);
	}
	
	@Override
	public int completeAnswer(int inquiryNo, String adminReply) {
		
		Map<String, Object> map = new HashMap();
		
		map.put("inquiryNo", inquiryNo);
		map.put("adminReply", adminReply);
		
		return mapper.completeAnswer(map);
	}


}
