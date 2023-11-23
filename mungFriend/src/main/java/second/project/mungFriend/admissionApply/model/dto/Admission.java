package second.project.mungFriend.admissionApply.model.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Admission {
	
	private int admNo;
	private String admProfile;
	private String admName;
	private String admBirthday;
	private String admPhone;	
	private String admBreed;
	private String admGender;
	private String admNeuteringYN;	
	private String admVaccineYN;	
	private String admSignificant;
	private String admApplyDate;
	private String admHopeDate;
	private String admYN;
	private int admMemberNo;
	

}
