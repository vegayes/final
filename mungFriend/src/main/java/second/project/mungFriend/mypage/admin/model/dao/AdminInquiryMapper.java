package second.project.mungFriend.mypage.admin.model.dao;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import second.project.mungFriend.mypage.member.model.dto.Inquiry;

@Mapper
public interface AdminInquiryMapper {
    
	List<Inquiry> adminInquiryBox();
	
	
	
	
	/**
	 * 1:1문의 상세 조회
	 * @param inquiryNo
	 * @return
	 */
	Inquiry adminInquiryDetail(int inquiryNo);
    
}
