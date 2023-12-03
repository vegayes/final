package second.project.mungFriend.event.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Quiz {
	
	private int quizNo;
	private String quizContent;
	private String quizChoice1;
	private String quizChoice2;
	private String quizChoice3;
	private String quizChoice4;
	
	
	private int quizAnswerNo;
	private int answerNo;
	
	private int quizImgNo;
	private String quizDogImg;
	private String quizEyes;
	private String quizNose;
	private String quizMouth;
	private String quizLeftEar;
	private String quizRightEar;
	
}
