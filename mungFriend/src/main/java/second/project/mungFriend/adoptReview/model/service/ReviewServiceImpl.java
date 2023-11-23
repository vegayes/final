package second.project.mungFriend.adoptReview.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.adopt.model.dto.Pagination;
import second.project.mungFriend.adoptReview.model.dao.ReviewMapper;
import second.project.mungFriend.adoptReview.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewMapper mapper;

	
	
	@Override
	public Map<String, Object> selectReviewList(int cp) {
		
		// 1.특정 게시판의 삭제되지 않은 게시글 수 조회
		int listCount = mapper.getListCount();
		
		// 2. 게시글 수 조회 결과 + cp를 이용해 Pagination 객체 생성
		Pagination pagination = new Pagination(listCount, cp);
		
		// 3. 특정 게시판에서 현재 페이지에 해당하는 부분에 대한 게시글 목록 조회 
		// boardCode 에 따른 게시판에서 몇 페이지(Pagination.currnetPage) 에 대한 
		// 게시글 몇 개(pagination.limit)인지 조회
		
		 
		// RowBounds 객체 
		// - 마이바티스에서 페이징 처리를 위해 제공하는 객체
		// - offset 만큼 건너뛰고 
		// 그 다음 지정된 행 개수 만큼 조회

		// 1) offset 계산
		int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();
		
		// 2) RowBounds 객체 생성
		RowBounds rowBounds = new RowBounds(offset,pagination.getLimit() );
			
		List<Review> reviewList = mapper.selectReviewList(rowBounds);
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("pagination", pagination);
		map.put("reviewList", reviewList);
		
		// 리턴값이 2개라 map 으로 리턴
		return map;
		
	}

	
	@Override
	public Map<String, Object> selectSearchReviewList(String searchType, String searchContent, int cp) {
		// TODO Auto-generated method stub
		return null;
	}

}
