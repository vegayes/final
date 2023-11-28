package second.project.mungFriend.event.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import second.project.mungFriend.event.model.dao.EventMapper;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventMapper mapper;

	/**
	 * 유효기간이 지난 쿠폰 개수 확인
	 */
	@Override
	public int countExpiration(String formatedNow) {
		return mapper.countExpiration(formatedNow);
	}

}
