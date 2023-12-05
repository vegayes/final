package second.project.mungFriend.mypage.admin.model.service;

import java.util.List;
import java.util.Map;

public interface AdminStatusService {

	List<Map<String, Object>> memberDonationList();

	List<Map<String, Object>> selectDogList();

	List<Map<String, Object>> nonMemberDonationList();

	List<Map<String, Object>> selectAdoptList();

}
