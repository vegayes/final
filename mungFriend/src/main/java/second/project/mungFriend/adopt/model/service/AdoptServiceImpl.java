package second.project.mungFriend.adopt.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import second.project.mungFriend.adopt.model.dao.AdoptMapper;
import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.adopt.model.dto.Pagination;

@Service
public class AdoptServiceImpl implements AdoptService{
	
	@Autowired
	private AdoptMapper mapper;

	@Override
	public Map<String, Object> selectDogList(int cp) {
		
		// 1. 삭제되지않은 강아지 수 조회
		int listCount = mapper.getListCount();
		
		// 2. 조회결과를 cp를 이용해서 Pagination 객체 생성
		Pagination pagination = new Pagination(listCount, cp);
		
		// 3. 현재 페이지에 해당하는 부분에 대한 몇개(paginatioin.limit) 게시글 목록 조회
		
		// RowBounds 객체
		// - 마이바티스에서 페이징처리를 위해 제공하는 객체
		// offset 만큼 건너뛰고
		// 그 다음 지정된 행 개수만큼 조회
		
		// 1) offset 계산(페이지 넘기기)
		int offset
			= (pagination.getCurrentPage() - 1) * pagination.getLimit();
		
		// 2) RowBounds 객체 생성
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		List<Dog> dogList = mapper.selectDogList(rowBounds);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("pagination", pagination);
		map.put("dogList", dogList);
		
		return map;
	}

	// 게시글만 상세조회
	@Override
	public Dog selectDogDetail(Map<String, Object> map) {

		return mapper.selectDogDetail(map);
	}

	// DB이미지 파일 목록 조회	
	@Override
	public List<String> selectImageList() {
		
		return mapper.selectImageList();
	}
	
	// 좋아요 여부 확인
	@Override
	public int dogLikeCheck(Map<String, Object> map) {
		
		return mapper.dogLikeCheck(map);
	}
	
	// 좋아요 처리
	@Override
	@Transactional(rollbackFor =  Exception.class)
	public int like(Map<String, Integer> paraMap) {

		// check == 0 / 1
		// check값이 무엇이냐에 따라서 DOG_LIKE 테이블에 INSERT / DELETE 정해라
		// DOG_LIKE 테이블 INSERT ( insertDogLike() ) 호출
		// DOG_LIKE 테이블 DELETE ( deleteDogLike() ) 호출
		
		int result = 0;
		
		int check = paraMap.get("check");
		
		if(check == 0) { // 좋아요 x
			
			result = mapper.insertDogLike(paraMap);
			
		}else{ // 좋아요 o
			
			result = mapper.deleteDogLike(paraMap);
		}
		
		if(result == 0) return -1; // 실패 시 -> transaction	
		
		return result;
	}
	
//	**********************************************************************************************
	
	// 게시글 삽입
	@Override
	public int dogRegiInsert(int memberNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	

}
