var stompClient = null;


function setConnected(connected) {
    $("#chat-image").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#msg").html("");
}


function connect() {

    const mainContent = document.getElementById('main-container');
    mainContent.classList.add('visible');
    document.getElementById('msg').focus();
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.send("/app/sendMessage", {}, JSON.stringify("welcome")); //서버에 보낼 메시지
        stompClient.subscribe('/topic/public', function (message) {

            var descriptionArray = JSON.parse(message.body);
            console.log("descriptionArray : ", descriptionArray);

           for (var i = 0; i < descriptionArray.length; i++) {

               
                const breedMatches = descriptionArray[i].match(/'([^']+)'/g);
                
                if(i == 0){

                    const firstMessage = descriptionArray[0].split('\n');

                    for(var j = 0; j< firstMessage.length; j++){
                        showMessage(firstMessage[j]);
                    }
                    
                }

                if(breedMatches != null){
                    // 추출된 문자열에서 따옴표를 제거하고 견종 리스트 생성
                    const breedList = breedMatches.map(match => match.replace(/'/g, ''));
                
                    // 결과 출력
                    console.log("견종리스트 : ", breedList);

                    showMessage("나에게 어울리는 견종 검색하기");
                    for (var i = 0; i < breedList.length; i++){
                        showLink(breedList[i]);
                        
                    }

                }


                else if(i > 0){
                    console.log("showButton");
                    showButton(descriptionArray[i]);
                }

               
            }   
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
    $("#communicate").empty(); // 자식 요소 제거
    const mainContent = document.getElementById('main-container');
    mainContent.classList.remove('visible');
}


//=================메시지 전송=========================//
function sendMessage() {
    let message = $("#msg").val()
    showMessage("보낸 메시지: " + message);

    stompClient.send("/app/sendMessage", {}, JSON.stringify(message)); //서버에 보낼 메시지
    $("#msg").val(""); // 입력 필드 초기화
}

function sendButton(selectedButtonText) {
   
    showMessage("보낸 메시지: " + selectedButtonText);
    stompClient.send("/app/sendMessage", {}, JSON.stringify(selectedButtonText)); //서버에 보낼 메시지
    $("#msg").val(""); // 입력 필드 초기화
}


//=================메시지 출력=========================//
function showMessage(message) {

    $("#communicate").append("<tr><td><div>" + message + "<br></div><br></td></tr>");
    var table = $("#content-container");
    table.scrollTop(table.prop("scrollHeight"));;
    
}

function showButton(message) {

    $("#communicate").append("<tr><td><button class='selectButton'><p>" + message + "<br></p></button></td></tr><br>");

    var table = $("#content-container");
    
    table.scrollTop(table.prop("scrollHeight"));;
    
}

function showLink(message) {
    
    // 자바스크립트 형식의 링크 조립
    var link = '/adoptReview/searchDogList/' + message;

    // 동적으로 생성된 HTML을 추가
    $("#communicate").append("<tr><td><a class='chatLink' href='" + link + "'>" + message + "</a></td></tr><br>");

    // 스크롤을 가장 아래로 이동
    var table = $("#content-container");
    table.scrollTop(table.prop("scrollHeight"));
}





//================= 이미지 클릭시 동작 =========================//
$(function () {
    $(".form-inline").on('submit', function (e) {
        e.preventDefault();     //form 태그의 페이지 이동 기능을 막음
    }); 

    $("#chat-image").click(function (e) {
        e.preventDefault();
        if ($("#main-container").hasClass("visible")) {
            disconnect();
        } else {
            connect();
        }
    });

    $("#disconnect").click(function () {
        disconnect(); 
    });
    
    $( "#send" ).click(function() { sendMessage(); });   // 보내기 버튼 누르면 실행

    $("#communicate").on("click", ".selectButton", function() {
        var selectedButtonText = $(this).text(); // 버튼의 텍스트 가져오기
        sendButton(selectedButtonText);
    });
});


/*
document.getElementById('connect').addEventListener('click', function () {
    // 챗봇 이미지 클릭 시 실행되는 함수
    document.getElementById('main-container').style.display = 'block';
});

document.getElementById('disconnect').addEventListener('click', function () {
    // 챗봇 이미지 클릭 시 실행되는 함수
    document.getElementById('main-container').style.display = 'none';
});
*/


// const disconnectButton = document.getElementById('disconnect');


