package second.project.mungFriend.mypage.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import second.project.mungFriend.mypage.member.model.dto.Inquiry;

@Mapper
public interface UserInquiryMapper {

	/** 1:1 등록
	 * @param inquiry
	 * @return int
	 */
	int userInquiry(Inquiry inquiry);

	/** inquiryList 조회하기
	 * @return List
	 */
	List<Inquiry> inquiryList(int memberNo);
     
	/**
	 * 1:1문의 상세 조회
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
