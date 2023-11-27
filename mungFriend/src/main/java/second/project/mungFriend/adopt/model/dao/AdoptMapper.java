package second.project.mungFriend.adopt.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.adopt.model.dto.DogImage;

@Mapper
public interface AdoptMapper {

	// 삭제되지않은 강아지 수 조회
	public int getListCount();

	// 현재 페이지에 해당하는 부분에 대한 강아지목록 조회 
	public List<Dog> selectDogList(RowBounds rowBounds);

	// 게시글만 상세조회
	public Dog selectDogDetail(Map<String, Object> map);

	// DB 이미지 파일 목록 조회
	public List<String> selectImageList();
	
	// 좋아요 여부 확인
	public int dogLikeCheck(Map<String, Object> map);

	// 좋아요 삽입
	public int insertDogLike(Map<String, Integer> paraMap);

	// 좋아요 삭제
	public int deleteDogLike(Map<String, Integer> paraMap);
	
//	**********************************************************************************************
	
	// 강아지 정보 삽입
	public int insertDog(Dog dog);

	// 업로드된 이미지 삽입
	public int insertImage(List<DogImage> uploadList);

	
	
	

}
