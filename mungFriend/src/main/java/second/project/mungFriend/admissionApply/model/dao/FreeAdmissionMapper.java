package second.project.mungFriend.admissionApply.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.admissionApply.model.dto.Admission;

@Mapper
public interface FreeAdmissionMapper {

	/** 무료입소신청 삽입
	 * @param admission
	 * @return
	 */
	int admissionInsert(Admission admission);

	
	
	
	
	/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	 					마이페이지 관리자
	 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
	/**입소신청 내역 조회
	 * @return
	 */
	List<Admission> selectAdmissionList();





	/** 입소신청 내역  객체 가져오기
	 * @param admNo
	 * @return
	 */
	Admission selectAdmissionInfo(int admNo);



	/**입소신청 내역을 토대로 강아지 등록 완료 후
	 * @param admNo
	 * @return
	 */
	int updateAdm(int admNo);


}
