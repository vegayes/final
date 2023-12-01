package second.project.mungFriend.pm.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Pm {
	
	private int activityNo;
	private int memberNo;
	private String acitivityImg;
	private String activityContent;
	private String activityDate;

}
