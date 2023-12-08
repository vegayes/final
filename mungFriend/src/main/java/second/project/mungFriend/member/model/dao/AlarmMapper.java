package second.project.mungFriend.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.common.dto.Alarm;

@Mapper
public interface AlarmMapper {

	List<Alarm> selectAlarm(int memberNo);

}
