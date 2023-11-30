package second.project.mungFriend.mypage.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import second.project.mungFriend.mypage.member.model.dto.Inquiry;

@Mapper
public interface UserInquiryMapper {
     
	  void insertInquiry(Inquiry inquiry);
	    List<Inquiry> findAllInquiries();
	    void updateInquiryStatus(@Param("inquiryId") Long inquiryId, @Param("status") String status);
	
	
}
