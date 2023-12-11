package second.project.mungFriend.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import second.project.mungFriend.common.dto.Alarm;
import second.project.mungFriend.member.model.dao.AlarmMapper;

@Service
public class AlarmServiceImpl implements AlarmService{

	@Autowired
	AlarmMapper mapper;
	
	
	/**
	 * 알림 읽음 표시
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteAlarm(int alarmNo) throws Exception {
		
		int result = mapper.deleteAlarm(alarmNo);
		
		if(result > 0) {
			
			return result;
			
		}else {
			
			throw new Exception();
		}
	}

	
	
	
}
