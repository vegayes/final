package second.project.mungFriend.mypage.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.mypage.member.model.dto.DogLike;

@Mapper
public interface LikeListMapper {

	public List<DogLike> selectLikeList();

//	public List<DogLike> selectLikeList();

}
