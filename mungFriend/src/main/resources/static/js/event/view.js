let quizChoice = [];
let quizAnswer = [];
let quizImgData = [];

document.addEventListener("DOMContentLoaded", () => {
	
	// 1) 로그인이 되어있는가?
	if(loginMember != null){
    fetch("/event/check")
    .then(response => response.text()) 
	.then(result => {
			console.log(" 체크 :" + result);
		
			if(result > 0){
				
				console.log("쿠폰 있음 :: 게임 참여 불가");
								
				// 퀴즈 선택지 가져오기
				fetch("/event/quiz/choices")
				    .then(response => response.json())
				    .then(data => {
				        quizChoice = data;
				        
				        console.log("quiz선택 " + quizChoice);
				    })
				    .catch(error => console.error("Error fetching quiz choices:", error));

				// 퀴즈 정답 가져오기
				fetch("/event/quiz/answers")
				    .then(response => response.json())
				    .then(data => {
				        //quizAnswer = data;
				        
				        console.log("quizAnswer선택 " + quizAnswer);
				    })
				    .catch(error => console.error("Error fetching quiz answers:", error));
				
				// 퀴즈 이미지 가져오기
				fetch("/event/quiz/images")
				    .then(response => response.json())
				    .then(data => {
				        //quizImgData = data;
				        
				        console.log("quizImgData선택 " + quizImgData);
				    })
				    .catch(error => console.error("Error fetching quiz images:", error));

					openEvent();
				}else{
				console.log("쿠폰 없음 :: 게임 참여 가능");
				}
		})	
	}
});