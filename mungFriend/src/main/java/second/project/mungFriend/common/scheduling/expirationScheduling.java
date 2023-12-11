package second.project.mungFriend.common.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import second.project.mungFriend.event.model.service.EventService;

// @Controller, @Service, @Repository의 부모 어노테이션
@Component // Bean 등록을 하겠다고 명시하는 어노테이션
public class expirationScheduling {
	
	@Autowired
	private EventService service;

	
	//cron = "초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT)
//	@Scheduled(cron = "0,30 * * * * *") // 매 분 0초, 30초 마다 수행
//	@Scheduled(cron = "0 0 0 * * *") // 매일 자정
	@Scheduled(cron = "0 0 0,12 * * * ") // 매일 자정 정오마다
	public void couponExpiration() { 
			
		System.out.println("=======DB 스케쥴링 진행 ==========");
        // 현재 날짜/시간
        Date now = new Date();
       
        // 현재 날짜/시간 출력
        //System.out.println("현재 시간 :" + now); // Thu May 03 14:43:32 KST 2022
        
        // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        // 포맷팅 적용
        String formatedNow = formatter.format(now);
        
        // 포맷팅 현재 날짜/시간 출력
        //System.out.println("포맷팅 시간 :" +formatedNow); // 2022년 05월 03일 14시 43분 32초
		
		// 1) DB에서 유효기간 지나면 update진행해야하는 사항이 있는지 파악
		int updateCount = service.countExpiration(formatedNow);
		
		// 유효기간 2주 내의 쿠폰 조회 후 알림테이블에 삽입
		int couponAlarm = service.selectcouponAlarm(formatedNow);
		System.out.println("알림테이블 삽입결과 : " + couponAlarm);
		
		
		//System.err.println("updateCount : " + updateCount);
		
		if(updateCount > 0) { // DB에서 유효기간이 지난 쿠폰
			System.out.println("유효기간이 지난 쿠폰이 있음");
			int updateCountExpiration = service.updateCountExpiration(formatedNow);
			
			if(updateCountExpiration > 0) {
				System.out.println("쿠폰 상태 변경 완료");
			}
		}
		
	}
	

	
}

/*
 * @Scheduled
 * 
 * * Spring에서 제공하는 스케줄러 - 스케줄러 : 시간에 따른 특정 작업(Job)의 순서를 지정하는 방법.
 * 
 * 설정 방법 
 * 1) servlet-context.xml -> Namespaces 탭 -> task 체크 후 저장
 * 2) servlet-context.xml -> Source 탭 -> <task:annotation-driven/> 추가
 * 
 *
 * @Scheduled 속성
 *  - fixedDelay : 이전 작업이 끝난 시점으로 부터 고정된 시간(ms)을 설정.
 * 	  @Scheduled(fixedDelay  = 10000) // 이전 작업이 끝난 후 10초 뒤에 실행
 *    
 *    
 *  - fixedRate : 이전 작업이 수행되기 시작한 시점으로 부터 고정된 시간(ms)을 설정.
 *    @Scheduled(fixedRate = 10000) // 이전 작업이 시작된 후 10초 뒤에 실행
 *    
 *    
 * * cron 속성 : UNIX계열 잡 스케쥴러 표현식으로 작성 - cron="초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT) 
 * ex) 2019년 9월 16일 월요일 10시 30분 20초 cron="20 30 10 16 9 2 " // 연도 생략 가능
 * 
 * - 특수문자 * : 모든 수. 
 * - : 두 수 사이의 값. ex) 10-15 -> 10이상 15이하 
 * , : 특정 값 지정. ex) 3,4,7 -> 3,4,7 지정 
 * / : 값의 증가. ex) 0/5 -> 0부터 시작하여 5마다 
 * ? : 특별한 값이 없음. (월, 요일만 해당) 
 * L : 마지막. (월, 요일만 해당)
 * @Scheduled(cron="0 * * * * *") // 매 분마다 실행
 * 
 * 
 * 
 * 
 * * 주의사항 - @Scheduled 어노테이션은 매개변수가 없는 메소드에만 적용 가능.
 * 
 */

	