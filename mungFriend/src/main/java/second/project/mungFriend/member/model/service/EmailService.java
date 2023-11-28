package second.project.mungFriend.member.model.service;

import java.util.Map;

public interface EmailService {

	int signUp(String email);

	int checkAuthKey(Map<String, Object> paramMap);

}
