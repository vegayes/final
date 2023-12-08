package second.project.mungFriend.adopt.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.adopt.model.dto.DogImage;
import second.project.mungFriend.adopt.model.dto.Reservation;

@Mapper
public interface AdoptMapper {
	
//	*****************조회******************

	// 삭제되지않은 강아지 수 조회
	public int getListCount();

	// 현재 페이지에 해당하는 부분에 대한 강아지목록 조회 (일반 전체 조회)
	public List<Dog> selectDogList(RowBounds rowBounds);

	// 현재 페이지에 해당하는 부분에 대한 강아지목록 조회 (필터 조회)
	public List<Dog> searchDogList(RowBounds rowBounds, Dog selectedFilters);
	
	// 게시글만 상세조회
	public Dog selectDogDetail(Map<String, Object> map);

	// DB 이미지 파일 목록 조회
	public List<String> selectImageList();
	
	// 좋아요 여부 확인
	public int dogLikeCheck(Map<String, Object> map);
	
	// 강아지 예약여부 확인
	public int reserveCheck(Map<String, Object> map);

	// 좋아요 삽입
	public int insertDogLike(Map<String, Integer> paraMap);

	// 좋아요 삭제
	public int deleteDogLike(Map<String, Integer> paraMap);
	
	
//	*****************insert******************
	
	// 견종확인
	public String breedConfirm(Dog dog);
	
	// 견종 추가
	public void breedAdd(Dog dog);

	// 강아지 정보 삽입
	public int insertDog(Dog dog);

	// 업로드된 이미지 삽입
	public int insertImage(List<DogImage> uploadList);
	
	// 입소신청 내역에서 가져온 이미지 삽입 
	public int insertAdmImg(Dog dog);
	
//	*****************update******************

	// 수정화면 띄우기용 게시글 상세조회
	public Dog selectDogDetailForUpdate(Map<String, Object> map);
	
	// 강아지 update
	public int dogUpdate(Dog dog);
	
	// 이미지 삭제
	public int imageDelete(Map<String, Object> deleteMap);
	
	// 이미지 수정
	public int imageUpdate(DogImage img);
	
	// 이미지 삽입
	public int imageInsert(DogImage img);
	
//	*****************delete******************

	// 강아지 delete
	public int dogDelete(Map<String, Object> map);

//	*****************강아지 예약******************

	
	// 강아지 예약하기
	public int dogReservation(
			@Param("reservationData") Reservation reservationData, 
			@Param("dogNo") int dogNo, 
			@Param("memberNo") int memberNo);
	
	
//	*****************************챗봇에서 사용*****************************************************************
	// 검색한 강아지 마리 수 조회
	public int getSearchDogListCount(String breedName);
	
	// 강아지 검색결과 
	public List<Dog> selectChatbotDogList(RowBounds rowBounds, String breedName);
	
	
	

}
