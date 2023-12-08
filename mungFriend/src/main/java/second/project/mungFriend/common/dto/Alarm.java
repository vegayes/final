package second.project.mungFriend.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Alarm {
	private int alarmNo;
	private int memberNo;
	private String checkYN;
	private String ararmCategory;
	private String alarmImgPath;
	private String alarmImgName;
	
}
