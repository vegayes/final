package second.project.mungFriend.member.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import second.project.mungFriend.common.dto.Alarm;
import second.project.mungFriend.member.model.service.AlarmService;
import second.project.mungFriend.member.model.service.MemberService;

@RestController
public class AlarmController {

	@Autowired
	AlarmService service;
	
	@Autowired
	MemberService memberService;
	
	// 알림 읽음 표시
	@GetMapping(value="/deleteAlarm", produces="application/json; charset=UTF-8")
	public int deleteAlarm(int alarmNo) throws Exception {
		System.out.println("알림번호 : " + alarmNo);
		
		int deleteResult = service.deleteAlarm(alarmNo);
		
		return deleteResult;
		
	}
	
	// 알림 목록 조회
	@GetMapping(value="/selectAlarm", produces="application/json; charset=UTF-8")
	public List<Alarm> selectAlarmList(int memberNo) { 
		List<Alarm> list =  memberService.selectAlarm(memberNo);	

		return list;
	}
	
}
