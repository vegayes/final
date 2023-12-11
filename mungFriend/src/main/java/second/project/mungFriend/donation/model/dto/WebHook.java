package second.project.mungFriend.donation.model.dto;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class WebHook {
	// 전달 받은 값
	private String imp_uid;
	private String merchant_uid;
	private String status;

}
