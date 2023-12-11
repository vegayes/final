package second.project.mungFriend.event.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import second.project.mungFriend.common.dto.Alarm;
import second.project.mungFriend.event.model.dao.EventMapper;
import second.project.mungFriend.event.model.dto.Coupon;
import second.project.mungFriend.event.model.dto.Quiz;
import second.project.mungFriend.member.model.dao.AlarmMapper;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventMapper mapper;

	@Autowired
	private AlarmMapper alarmMapper;
	
	/**
	 * 유효기간이 지난 쿠폰 개수 확인
	 */
	@Override
	public int countExpiration(String formatedNow) {
		return mapper.countExpiration(formatedNow);
	}

	/**
	 * 유효기간이 지난 쿠폰 
	 */
	@Override
	public int updateCountExpiration(String formatedNow) {
		return mapper.updateCountExpiration(formatedNow);
	}

	/**
	 * 게임에 참여했는지 확인
	 */
	@Override
	public int eventCheck(int memberNo) {
		return mapper.eventCheck(memberNo);
	}

	/**
	 * 랜덤으로 퀴즈 뽑기
	 */
	@Override
	public List<Quiz> chooseQuiz() {
		return mapper.chooseQuiz();
	}

	/**
	 * 쿠폰 선택 (점수)
	 */
	@Override
	public int couponSelect(int totalScore) {
		return mapper.couponSelect(totalScore);
	}

	/**
	 * 쿠폰 증정
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int couponInsert(Coupon newCoupon) {
		return mapper.couponInsert(newCoupon);
	}

	
	/**
	 * 유효기간 1주일 내의 쿠폰 조회
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int selectcouponAlarm(String formatedNow) {
		
		Alarm alarm = alarmMapper.selectcouponAlarm(formatedNow);
		if(alarm != null) {
			int memberNo = alarm.getMemberNo();
			System.out.println("쿠폰에서 조회한 회원번호 : " +  memberNo);
			
			if(memberNo != 0) {
				int insertResult = alarmMapper.insertAlarm(memberNo);
				System.out.println("유효기간 내 쿠폰 발견 -> 삽입결과 : " + insertResult);
				if(insertResult > 0) {
					int updateResult = alarmMapper.updateAlarm(memberNo);
				}
				return insertResult;
			}
		}
		
		return 0;
	}

}
