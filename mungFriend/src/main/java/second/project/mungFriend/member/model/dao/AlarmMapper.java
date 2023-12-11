package second.project.mungFriend.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.common.dto.Alarm;

@Mapper
public interface AlarmMapper {

	List<Alarm> selectAlarm(int memberNo);

	int deleteAlarm(int alarmNo);

	// 유효기간 1주일 내 쿠폰 조회
	Alarm selectcouponAlarm(String formatedNow);
		
	
	
	// 유효기간 1주일 내의 쿠폰 있으면 insert
	int insertAlarm(int memberNo);
	
	//쿠폰에 알림여부 수정하기
	int updateAlarm(int memberNo);


}
