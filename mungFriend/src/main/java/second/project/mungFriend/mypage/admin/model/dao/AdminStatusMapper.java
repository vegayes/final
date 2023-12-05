package second.project.mungFriend.mypage.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminStatusMapper {

	
	List<Map<String, Object>> selectDogList();
	
	List<Map<String, Object>> memberDonationList();
	
	List<Map<String, Object>> nonMemberDonationList();

	List<Map<String, Object>> selectAdoptList();
	
}
