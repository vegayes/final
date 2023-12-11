package second.project.mungFriend.mypage.member.model.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.donation.model.dao.DonationMapper;
import second.project.mungFriend.donation.model.dto.Donation;
import second.project.mungFriend.event.model.dao.EventMapper;
import second.project.mungFriend.event.model.dto.Coupon;
import second.project.mungFriend.mypage.admin.model.dto.Pagination10;

@Service
public class ListUserServiceImpl implements ListUserService {
	
	@Autowired
	private DonationMapper mapperDonation;
	
	@Autowired
	private EventMapper mapperEvent;
	
	

	/**
	 * 사용자 후원내역
	 
	@Override
	public List<Donation> userDonationList(int memberNo) {
		return mapperDonation.userDonationList(memberNo);
	}*/
	@Override
	public Map<String, Object> userDonationList(int memberNo, int cp) {
		System.out.println("들어가기전");
		
		int listCount = mapperDonation.countUserDonation(memberNo);
		
		// 2. 1번 조회 결과 + cp 를 이용해서 Pagination 객체 생성
		// -> 내부 필드가 모두 계산되어 초기화됨
		Pagination10 pagination = new Pagination10(listCount, cp);

		int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();

		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		List<Donation> donationList = mapperDonation.userDonationList(memberNo, rowBounds);
		System.out.println("donationList" + donationList);
		for(Donation donation : donationList) {
			System.out.println("후원 내 : " + donation);
			Date date = donation.getDonationDate();
			
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String paidAt = fm.format(date);
			System.out.println("후원 내 값  date 값 : " + paidAt );
			donation.setPaidAt(paidAt);
			
			if(donation.getDonationType().equals("정기")) {
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(date); // 기존 날짜 설정

		        // 11개월 추가
		        cal.add(Calendar.MONTH, 11);

		        // 변경된 날짜를 String 형태로 변환
		        SimpleDateFormat lastDayFormatter = new SimpleDateFormat("yyyy-MM-dd");
		        String paidAtRegular = lastDayFormatter.format(cal.getTime());
		        String paidAtStart = lastDayFormatter.format(date);
		        System.out.println("정기 기간  : " + paidAtStart + " ~ " + paidAtRegular);
		        donation.setPaidAt(paidAtStart + " ~ " + paidAtRegular); // 같은 날짜 두 번 출력
			}
			
		}
		
		// 4. pagination, memberList를 Map에 담아서 반환
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", pagination);
		map.put("donationList", donationList);
		
		return map;
	}

	/**
	 * 사용자 쿠폰내역
	 */
	@Override
	public List<Coupon> selectCouponList(int memberNo) {
		return mapperEvent.selectCouponList(memberNo);
	}






}
