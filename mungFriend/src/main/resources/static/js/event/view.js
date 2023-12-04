var quizChoice = [];
var quizAnswer = [];
var quizImgData = [];

document.addEventListener("DOMContentLoaded", () => {
	
	// 1) 로그인이 되어있는가?
	if(loginMember != null){
    fetch("/event/check")
    .then(response => response.text()) 
	.then(result => {
			console.log(" 체크 :" + result);
		
			if(result > 0){
				
				console.log("쿠폰 있음 :: 게임 참여 불가");
				/*				
				fetch("/event/quiz/choices")
				    .then(response => response.json())
				    .then(data => {
				        quizChoice = data;
				        
				        console.log("quiz선택 " + quizChoice);
				    })
				    .catch(error => console.error("퀴즈 선택지 에러:", error));

				fetch("/event/quiz/answers")
				    .then(response => response.json())
				    .then(data => {
				        quizAnswer = data;
				        
				        console.log("quizAnswer선택 " + quizAnswer);
				    })
				    .catch(error => console.error("퀴즈 정답 에러:", error));
				
				fetch("/event/quiz/images")
				    .then(response => response.json())
				    .then(data => {
				        quizImgData = data;
				        
				        console.log("quizImgData선택 " + quizImgData);
				    })
				    .catch(error => console.error("퀴즈 이미지 에러 :", error));
				    
			*/
			

		 
				}else{
				console.log("쿠폰 없음 :: 게임 참여 가능");
				
		        	fetch("/event/quiz/allData")
		            .then(response => response.json())
		            .then(data => {
		                quizChoice = data.quizChoices;
		                quizAnswer = data.quizAnswers;
		                quizImgData = data.quizImages;
		
		                console.log("quiz선택 ", quizChoice);
		                console.log("quizAnswer선택 ", quizAnswer);
		                console.log("quizImgData선택 ", quizImgData);
		
		                openEvent();
		            })
		            .catch(error => console.error("퀴즈 에러:", error));
				}
		})	
	}
});