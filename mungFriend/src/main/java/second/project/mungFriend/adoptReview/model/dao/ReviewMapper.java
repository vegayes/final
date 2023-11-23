package second.project.mungFriend.adoptReview.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import second.project.mungFriend.adoptReview.model.dto.Review;

@Mapper
public interface ReviewMapper {

	int getListCount();

	List<Review> selectReviewList(RowBounds rowBounds);

}
