/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
            퀴즈 이미지 처음 위치 설정
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
function getRandomPosition(container) {
    // const x = Math.floor(Math.random() * (container.offsetWidth-50) + container.offsetLeft+40); // 50은 rectangle의 가로 크기
    // const y = Math.floor(Math.random() * (container.offsetHeight-50) + container.offsetTop+30); // 50은 rectangle의 세로 크기
    const minX = container.offsetLeft + 20; 
    const maxX = container.offsetWidth - 50 + container.offsetLeft - 40; 
    const minY = container.offsetTop+30; 
    const maxY = container.offsetHeight - 50 + container.offsetTop - 30; 
    
    const x = Math.floor(Math.random() * (maxX - minX + 1) + minX);
    const y = Math.floor(Math.random() * (maxY - minY + 1) + minY);
    
    return { x, y };
}

function getRandomAngle() {
    return Math.random() * 360; // 0에서 360 사이의 랜덤한 각도
}

function rotateRectangle(rectangle, rotationSpeed) {
    let rotation = 0;

    function rotate() {
        rotation += rotationSpeed / 60; // 60 프레임 기준으로 계산 (1초에 60프레임)
        rectangle.style.transform = `rotate(${rotation}deg)`;
        requestAnimationFrame(rotate);
    }
    rotate();
}

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
              퀴즈 이미지  이동 / 충돌
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
function moveRectangle(rectangle, container, speed) {
    const newPosition = getRandomPosition(container);
    let angle = getRandomAngle();
    let radians = angle * (Math.PI / 180); // 각도를 라디안으로 변환
    let dx = Math.cos(radians) * speed; // x 방향으로의 속도
    let dy = Math.sin(radians) * speed; // y 방향으로의 속도
   
    const rotationSpeed = 150;

    function move() {

        const leftCollision = rectangle.offsetLeft <= container.offsetLeft;
        const rightCollision = rectangle.offsetLeft + rectangle.offsetWidth >= container.offsetLeft + container.offsetWidth;
        const topCollision = rectangle.offsetTop <= container.offsetTop;
        const bottomCollision = rectangle.offsetTop + rectangle.offsetHeight >= container.offsetTop + container.offsetHeight;
    
        if ( leftCollision || rightCollision) {

            let normal = { x: -1, y: 0 };
            let incident = { x: dx, y: dy }; // 진행 방향으로의 벡터
                            // 10 20 
            let reflection = reflect(incident, normal); //두 값을 이용해서 진행방향(반사각) 정하기 
            dx = reflection.x; 
            dy = reflection.y;

        }
        if (topCollision || bottomCollision){
           
            let normal = { x: 0, y: -1 };
            let incident = { x: dx, y: dy }; // 진행 방향으로의 벡터
            let reflection = reflect(incident, normal); //두 값을 이용해서 진행방향(반사각) 정하기 

            dx = reflection.x;
            dy = reflection.y;

            
        }

        rectangle.style.left = `${rectangle.offsetLeft + dx}px`;
        rectangle.style.top = `${rectangle.offsetTop + dy}px`;

        requestAnimationFrame(move);
    }

    move();
    rotateRectangle(rectangle, rotationSpeed);
}

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
               퀴즈 이미지 충돌 후 반사
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
function reflect(incident, normal) {

    if (normal === null) {
        // 충돌 없으면 진행방향으로 계속 진행
        return incident;
    }
    const dotProduct = incident.x * normal.x + incident.y * normal.y;
                        // -10 
    const reflection = {
        x: incident.x - 2 * dotProduct * normal.x,
            // -1-2 * (-10) *(-1)
        y: incident.y - 2 * dotProduct * normal.y,
    };

    return reflection;
}

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
                퀴즈 이미지 정보
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
const quizImgData = [
    [
    {
        variable : "eyes",
        imgUrl : "images/퍼그 눈.jpg"
    },
    {
        variable : "nose",
        imgUrl : "images/퍼그 코.jpg"
    },
    {
        variable : "mouth",
        imgUrl : "images/퍼그 입.jpg"
    },
    {
        variable : "leftEar",
        imgUrl : "images/퍼그 왼쪽 귀.jpg"
    },
    {
        variable : "rightEar",
        imgUrl : "images/퍼그 오른쪽 귀.jpg"
    },
    {
        variable : "answerDog",
        imgUrl : "images/퍼그.jpg"       
    }
    ],
    [
        {
            variable : "eyes",
            imgUrl : "images/포메 눈.jpg"
        },
        {
            variable : "nose",
            imgUrl : "images/포메 코.jpg"
        },
        {
            variable : "mouth",
            imgUrl : "images/포메 입.jpg"
        },
        {
            variable : "leftEar",
            imgUrl : "images/포메 왼쪽 귀.jpg"
        },
        {
            variable : "rightEar",
            imgUrl : "images/포메 오른쪽 귀.jpg"
        },
        {
            variable : "answerDog",
            imgUrl : "images/포메.jpg"       
        }
    ],
    [
        {
            variable : "eyes",
            imgUrl : "images/보더콜리 눈.jpg"
        },
        {
            variable : "nose",
            imgUrl : "images/보더콜리 코.jpg"
        },
        {
            variable : "leftEar",
            imgUrl : "images/보더콜리 왼쪽 귀.jpg"
        },
        {
            variable : "rightEar",
            imgUrl : "images/보더콜리 오른쪽 귀.jpg"
        },
        {
            variable : "answerDog",
            imgUrl : "images/보더콜리.jpg"       
        }
    ]

];

const quizNumber = ["①", "②", "③", "④"];
const quizChoice = [
    ["비글", "치와와", "웰시코기" , "퍼그"],
    ["포메라니안", "시츄", "푸들" , "시바이누"],
    ["비숑", "사모예드", "보더콜리" , "폼피츠"]
];

const quizAnswer = [4, 1, 3];

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
                    퀴즈  점수
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
var scoreArr = []; // 현재 점수
function scoreUpdate() {

    console.log("점수 : " + scoreArr)

    const scoreState = document.querySelectorAll(".correctState img");
    for(let i= 0 ; i < scoreArr.length; i++){

        if(scoreArr[i] == 'O'){
            scoreState[i].src = "images/success.jpg";
        }else{
            scoreState[i].src = "images/fail.jpg";
        }
    }

    if(scoreArr.length == quizAnswer.length){
        scoreArr = [];
    }
}

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
                퀴즈 문제 setting
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
let currentIndex = 0; //인덱스 설정

function loadCurrentImageSet() {

    console.log(currentIndex);

    /*나중에 없앨 거 */
    const scoreState = document.querySelectorAll(".correctState img");
    if(currentIndex == 0){
       for(let i= 0 ; i < scoreState.length; i++){
         scoreState[i].src = "images/origin.jpg";
        }
    }
    /****************************************************************************/

    // 이미지 업로드
    const container = document.getElementById('container'); 
    container.innerHTML = '';
    const currentImageSet = quizImgData[currentIndex];

    currentImageSet.forEach(data => {
        if (data.variable !== "answerDog") {
            const rectangle = createRectangle(container, data.variable, data.imgUrl);
            moveRectangle(rectangle, container, 3);
        }
    });

    // 객관식 업로드
    const quizContent = document.querySelectorAll(".quizContent");
    const quizNum = document.querySelectorAll(".quizNum");
    const currentChoiceSet = quizChoice[currentIndex];


    console.log(currentImageSet);
    console.log(currentChoiceSet);

    for(let i = 0; i < quizContent.length; i++){
        quizNum[i].innerText = quizNumber[i];
        quizContent[i].innerText = currentChoiceSet[i];
    }
}


/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
                현재 퀴즈의 이미지 출력
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
function createRectangle(container, id , imageUrl) {
    const newPosition = getRandomPosition(container);

    const rectangle = document.createElement("div");
    rectangle.classList.add("imgSize");
    rectangle.style.position = "absolute";
    rectangle.style.left = `${newPosition.x}px`;
    rectangle.style.top = `${newPosition.y}px`;

    // id 추가
    rectangle.id = id;

    // 이미지 태그 생성
    const img = document.createElement("img");
    img.classList.add("dogImg");
    img.src = imageUrl; // 이미지 URL 설정
    img.alt = "Image"; // 이미지 대체 텍스트 설정 (필요에 따라 변경 가능)

    // 이미지 태그를 사각형(div) 안에 추가
    rectangle.appendChild(img);

    
    // container에 rectangle 추가
    container.appendChild(rectangle);

    return rectangle;
}

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
          정답 제출 버튼 눌렀을 경우 
                    혹은
             시간이 초과된 경우
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/

function displayAnswer() {
    var check = document.querySelector('input[name="quizSelect"]:checked');
    var answer = quizAnswer[currentIndex];
    if(check != null){
        if( answer == check.value){ //성공
            scoreArr.push('O');
            for(let i = 0; i<quizBtnWraps.length; i++){
                quizBtnWraps[i].classList.add('stopHover');
            }  
        }else{ //실패
            scoreArr.push('x'); 
            for(let i = 0; i<quizBtnWraps.length; i++){
                quizBtnWraps[answer-1].classList.add('answerSelect');
                quizBtnWraps[i].classList.add('stopHover');
            }  
        }
    }else{
        scoreArr.push('x'); 
        for(let i = 0; i<quizBtnWraps.length; i++){
            quizBtnWraps[answer-1].classList.add('answerSelect');
            quizBtnWraps[i].classList.add('stopHover');
        }  
    }

    
    // 점수
    scoreUpdate();

    // 기존 이미지 제거
    const dogImgs = document.querySelectorAll(".imgSize");
    for(let i = 0; i<dogImgs.length; i++){
        dogImgs[i].remove();   
    }

    // 새로운 이미지 생성 및 설정
    const container = document.getElementById("container");
    const newDiv = document.createElement("div");
    newDiv.classList.add("correctImg"); // 클래스 추가
    

    const currentImageSet = quizImgData[currentIndex];
    let answerImageUrl = '';

    currentImageSet.forEach(data => {
        if (data.variable === 'answerDog') {
            answerImageUrl = data.imgUrl;
        }
    });

    if (answerImageUrl) {
        const newImg = document.createElement("img");
        newImg.src = answerImageUrl;

        newDiv.appendChild(newImg); // 이미지를 div에 추가

        container.appendChild(newDiv); // 컨테이너에 새로운 HTML 추가

        
        const quizSubmitBtn = document.querySelector(".quizSubmitBtn");
        if (quizSubmitBtn) {
            quizSubmitBtn.remove();
        }  

        const quizSubmit = document.querySelector(".quizSubmit");

        const nextDiv = document.createElement("div");
        nextDiv.classList.add("quizNextBtn"); // 클래스 추가
        nextDiv.id = "nextQuiz";
        nextDiv.innerText = "다음 문제";
        quizSubmit.appendChild(nextDiv);

        // nextButton에 이벤트 리스너 추가
        nextDiv.addEventListener('click', () => {
            nextQuiz();
        });

        let opacity = 0;
        const fadeInInterval = setInterval(() => {
            opacity += 0.2;
            newImg.style.opacity = opacity;

            if (opacity >= 1) {
                // 나타나기 완료되면 인터벌 종료
                clearInterval(fadeInInterval);
            }
        }, 100);
    }

}



/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
                        타이머
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
let timerInterval; 
const timerElement = document.getElementById('timer');
let timeLeft = 9;
function updateTimer() {
    timerElement.innerText = `${timeLeft}`;
    timeLeft--;

    if (timeLeft < 0) {
        clearInterval(timerInterval);
        displayAnswer();
        timerElement.innerText = '시간 종료';
        timerElement.fontSize = '11px';
    }
}
// let timeLeft = 9;
// timerInterval = setInterval(updateTimer, 1000);


/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
                다음 문제를 눌렀을 때 
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
function nextQuiz() {
    console.log("다음문제누름");

    const radioButtons = document.querySelectorAll('input[type="radio"][name="quizSelect"]');
    /*일단 0번째 인덱스인 경우 */

    for(let i = 0; i<quizBtnWraps.length; i++){
        quizBtnWraps[i].classList.remove('stopHover');
    }  

    if(radioButtons != null){
        radioButtons.forEach(button => {
            button.checked = false;
        });
    }

    // 라디오 버튼 클릭 시 이벤트 처리
    quizBtnWraps.forEach(wrap => {
        wrap.classList.remove('quizSelect');
        wrap.classList.remove('answerSelect');
    });




    currentIndex = (currentIndex + 1) % quizImgData.length;
    const container = document.getElementById("container");

    const nextQuizBtn = document.getElementById("nextQuiz");
    if (nextQuizBtn) {
        nextQuizBtn.remove();
    }

    const quizSubmit = document.querySelector(".quizSubmit");

    const submitDiv = document.createElement("div");
    submitDiv.classList.add("quizSubmitBtn"); 
    submitDiv.id = "submitQuiz";
    submitDiv.innerText = "정답 제출";
   
    loadCurrentImageSet();

    quizSubmit.appendChild(submitDiv);

    submitDiv.addEventListener('click', function(){
        timerElement.innerText = '정답 공개';
        clearInterval(timerInterval);
        displayAnswer();
    });

    timeLeft = 10; 
    updateTimer(); 
    clearInterval(timerInterval);
    timerInterval = setInterval(updateTimer, 1000);


}


/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
                처음에 들어오자마자
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
/*
document.addEventListener("DOMContentLoaded", () => {
    loadCurrentImageSet(); 
    
    timerInterval = setInterval(updateTimer, 1000);

    quizSubmitBtn = document.querySelector(".quizSubmitBtn");

    quizSubmitBtn.addEventListener("click", function(){
        console.log("정답 제출 버튼 누름");
        timerElement.innerText = '정답 공개';
        clearInterval(timerInterval);
        displayAnswer();
    });
    
});
*/

function startGame(){
    subModal.style.display = "none";
    loadCurrentImageSet(); 

    timerInterval = setInterval(updateTimer, 1000);

    quizSubmitBtn = document.querySelector(".quizSubmitBtn");

    quizSubmitBtn.addEventListener("click", function(){
        console.log("정답 제출 버튼 누름");
        timerElement.innerText = '정답 공개';
        clearInterval(timerInterval);
        displayAnswer();
    });

    return;
}

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/