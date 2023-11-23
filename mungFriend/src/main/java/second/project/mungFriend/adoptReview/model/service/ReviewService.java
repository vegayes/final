package second.project.mungFriend.adoptReview.model.service;

import java.util.Map;

public interface ReviewService {

	Map<String, Object> selectReviewList(int cp);

	Map<String, Object> selectSearchReviewList(String searchType, String searchContent, int cp);
	
	

}
