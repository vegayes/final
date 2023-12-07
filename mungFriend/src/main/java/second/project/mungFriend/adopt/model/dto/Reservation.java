package second.project.mungFriend.adopt.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Reservation {	

	private int reserveNo;
	private int dogNo;
	private int memberNo;
	
	@JsonProperty("date")
	private String reserveDate;
	
    @JsonProperty("time")
	private String reserveTime;
    
	private String reserveFl;
	private String applicationDate;
	
	
	private String dogName;
	private String memberName;
	private String memberEmail;
	
}
