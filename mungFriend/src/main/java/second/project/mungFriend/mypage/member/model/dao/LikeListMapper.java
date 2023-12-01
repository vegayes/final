package second.project.mungFriend.mypage.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import second.project.mungFriend.mypage.member.model.dto.DogLike;

@Mapper
public interface LikeListMapper {
	
	// 좋아요 목록 조회
	public List<DogLike> selectLikeList(int memberNo);


	// 좋아요 목록 삭제
	public int likeDelete(@Param("dogNo")int dogNo, @Param("memberNo")int memberNo);


}
