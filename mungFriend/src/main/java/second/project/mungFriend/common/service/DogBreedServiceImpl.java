package second.project.mungFriend.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.adopt.model.dto.Pagination;
import second.project.mungFriend.common.dao.DogBreedMapper;
import second.project.mungFriend.common.dto.DogBreed;

@Service
public class DogBreedServiceImpl implements DogBreedService {

	@Autowired
	private DogBreedMapper mapper;

	@Override
	public List<DogBreed> findDogBreed(String searchInput) {
		
		System.out.println("검색 :" + searchInput);
		return mapper.findDogBreed(searchInput);
	}

	@Override
	public Map<String, Object> selectDogList1(String searchInput, int cp) {
		// 1. 삭제되지않은 강아지 수 조회
		int listCount = mapper.getListCount(searchInput);
		
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
		
		List<Dog> dogList = mapper.selectDogList1(rowBounds);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("pagination", pagination);
		map.put("dogList", dogList);
		
		return map;
	}
	
	
  
}
