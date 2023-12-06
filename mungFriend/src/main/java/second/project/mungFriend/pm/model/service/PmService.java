package second.project.mungFriend.pm.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.pm.model.dto.Pm;

public interface PmService {

	/** 활동일지 등록하기
	 * @param uploadImage
	 * @param pm
	 * @return int
	 * @throws Exception
	 */
	int activityLogRegister(MultipartFile uploadImage, Pm pm) throws Exception;
	
	/** 활동일지 조회하기
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectPmList(int cp);

}
