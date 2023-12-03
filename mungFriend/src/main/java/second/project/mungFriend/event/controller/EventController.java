package second.project.mungFriend.event.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import second.project.mungFriend.event.model.dto.Quiz;
import second.project.mungFriend.event.model.service.EventService;
import second.project.mungFriend.member.model.dto.Member;

@Controller
@RequestMapping("/event")
@SessionAttributes({"loginMember"})
public class EventController {

	@Autowired
	private EventService service;
	
	/** 쿠폰이 있는지 확인하기
	 * @param loginMember
	 * @param ra
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@GetMapping("/check")
	@ResponseBody
	public int eventCheck(  
			@SessionAttribute(value ="loginMember", required = false) Member loginMember,
			RedirectAttributes ra
			) throws IllegalStateException, IOException {

		int check = service.eventCheck(loginMember.getMemberNo());
				
		System.out.println("chcekc" + check);
		return check;
	}
	
	
	
	@GetMapping("/quiz/choices")
	@ResponseBody
	public List<List<String>> getQuizChoices() {
		
	    List<Quiz> choose = service.chooseQuiz();
	    List<List<String>> quizChoices = new ArrayList<>();

	    for (Quiz quizElement : choose) {
	        List<String> choices = new ArrayList<>();
	        choices.add(quizElement.getQuizChoice1());
	        choices.add(quizElement.getQuizChoice2());
	        choices.add(quizElement.getQuizChoice3());
	        choices.add(quizElement.getQuizChoice4());

	        quizChoices.add(choices);
	    }
	    System.out.println(quizChoices);
	    return quizChoices;
	}
	
	@GetMapping("/quiz/answers")
	@ResponseBody
	public List<Integer> getQuizAnswers() {
	    List<Quiz> choose = service.chooseQuiz();
	    List<Integer> quizAnswers = new ArrayList<>();

	    for (Quiz quizElement : choose) {
	        quizAnswers.add(quizElement.getAnswerNo());
	    }
	    System.out.println(quizAnswers);
	    
	    return quizAnswers;
	}	

	
	@GetMapping("/quiz/images")
	@ResponseBody
	public List<List<Map<String, String>>> getQuizImages() {
	    List<Quiz> choose = service.chooseQuiz();
	    List<List<Map<String, String>>> quizImages = new ArrayList<>();

	    for (Quiz quizElement : choose) {
	        List<Map<String, String>> images = new ArrayList<>();

	        Map<String, String> eyes = new HashMap<>();
	        eyes.put("variable", "eyes");
	        eyes.put("imgUrl", quizElement.getQuizEyes());
	        images.add(eyes);

	        Map<String, String> nose = new HashMap<>();
	        nose.put("variable", "nose");
	        nose.put("imgUrl", quizElement.getQuizNose());
	        images.add(nose);
	        
	        Map<String, String> mouth = new HashMap<>();
	        mouth.put("variable", "mouth");
	        mouth.put("imgUrl", quizElement.getQuizMouth());
	        images.add(mouth);

	        Map<String, String> leftEar = new HashMap<>();
	        leftEar.put("variable", "leftEar");
	        leftEar.put("imgUrl", quizElement.getQuizLeftEar());
	        images.add(leftEar);

	        Map<String, String> rightEar = new HashMap<>();
	        rightEar.put("variable", "rightEar");
	        rightEar.put("imgUrl", quizElement.getQuizRightEar());
	        images.add(rightEar);
	        
	        Map<String, String> answerDog = new HashMap<>();
	        answerDog.put("variable", "answerDog");
	        answerDog.put("imgUrl", quizElement.getQuizDogImg());
	        images.add(answerDog);

	        quizImages.add(images);
	    }
	    
	    System.out.println(quizImages);

	    return quizImages;
	}
	
}
