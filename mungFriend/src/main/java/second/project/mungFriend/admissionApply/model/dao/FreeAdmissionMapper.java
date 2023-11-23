package second.project.mungFriend.admissionApply.model.dao;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.admissionApply.model.dto.Admission;

@Mapper
public interface FreeAdmissionMapper {

	/** 무료입소신청 삽입
	 * @param admission
	 * @return
	 */
	int admissionInsert(Admission admission);

}
