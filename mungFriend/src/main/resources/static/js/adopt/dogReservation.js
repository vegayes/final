// 예약하기
function showCalendar() {

    // 로그인 여부 검사
    if(loginMemberNo == ""){
        alert("로그인 후 이용해주세요")
        return;
    }

    // 예약하기 버튼 숨기기
    const registButton = document.getElementById('regist');
    registButton.style.display = 'none';

    // 달력 보이기
    const calendarContainer = document.getElementById('calendar-container');
    calendarContainer.style.display = 'flex'; // display를 flex로 변경
    calendarContainer.style.alignItems = 'center';
    
    // 예약 날짜 선택 옆에 margin주기
    const dateInfo = document.getElementById('dateInfo');
    dateInfo.style.marginRight = '5px';
    
    // dateInput의 값이 변경될 때마다 이벤트 리스너 등록
    const dateInput = document.getElementById('dateInput');
    const timeContainer = document.getElementById('time-container');

    // x버튼 스타일 적용
    const cancelBtn = document.getElementById('cancelBtn');
    cancelBtn.style.border = 'none';
    cancelBtn.style.backgroundColor = 'white';    

    // 스타일 추가
    dateInput.style.width = '150px';
    dateInput.style.height = '25px';
    dateInput.style.marginRight = '7px';


    dateInput.addEventListener('input', function () {
        // 현재 날짜를 가져오는 함수
        function getCurrentDate() {
            const today = new Date();
            const year = today.getFullYear();
            let month = today.getMonth() + 1;
            let day = today.getDate();

            // 한 자리 숫자인 경우 앞에 0을 추가
            month = month < 10 ? '0' + month : month;
            day = day < 10 ? '0' + day : day;

            return `${year}-${month}-${day}`;
        }

        // 선택한 날짜
        const selectedDate = this.value;
        console.log(selectedDate);

        // 현재 날짜
        const currentDate = getCurrentDate();

        // 선택한 날짜가 현재 날짜 이전이라면 경고 메시지 출력 및 선택 취소
        if (selectedDate < currentDate || selectedDate == currentDate) {
            alert('오늘 이후의 날짜만 선택 가능합니다.');
            this.value = '';

            timeContainer.style.display = 'none';

            // 예약하기 버튼을 감추기
            const reserveBtn = document.getElementById('reserveBtn');
            reserveBtn.style.display = 'none';

            return; // 함수 종료
        }

        // 선택된 날짜에 따라 시간 옵션을 업데이트
        updateAvailableTimes(selectedDate);

        // 달력을 보여줄 때 동시에 시간을 보여주도록 토글
        timeContainer.style.display = 'block';

        // 예약하기 버튼을 보이도록 변경
        const reserveBtn = document.getElementById('reserveBtn');
        reserveBtn.style.display = 'block';
    });

    // #calendedr-img 클래스 추가
    const calendedrImg = document.getElementById('calendedr-img');
    calendedrImg.classList.add('calendedr-img');

    // 예약하기 버튼의 기존 스타일 클래스를 제거
    registButton.classList.remove('your-existing-style-class');
}

function hideCalendar() {

    // 예약하기 버튼 보이기
    const registButton = document.getElementById('regist');
    registButton.style.display = 'flex'; // display를 flex로 변경

    // 달력 감추기
    const calendarContainer = document.getElementById('calendar-container');
    calendarContainer.style.display = 'none';

    // #calendedr-img 클래스 제거
    const calendedrImg = document.getElementById('calendedr-img');
    calendedrImg.classList.remove('calendedr-img');

    // 예약하기 버튼에 기존 스타일 클래스를 다시 추가
    registButton.classList.add('your-existing-style-class');

    // 달력을 감춤과 동시에 시간을 감춤
    const timeContainer = document.getElementById('time-container');
    timeContainer.style.display = 'none';

    // 예약하기 버튼 감춤
    const reserveBtn = document.getElementById("reserveBtn");
    reserveBtn.style.display = 'none';

}

function reserveDone() {
    // 예약완료 보이기
    const reserveDone = document.getElementById('reserveDone');

    // 예약완료 엘리먼트가 정상적으로 가져와지면 스타일 변경
    if (reserveDone) {
        reserveDone.style.display = 'flex';
    } else {
        console.error('Error: reserveDone element not found.');
    }
    
    // 예약하기 버튼 감추기
    const registButton = document.getElementById('regist');
    registButton.style.display = 'none';

    // 달력 감추기
    const calendarContainer = document.getElementById('calendar-container');
    calendarContainer.style.display = 'none';

    // #calendedr-img 클래스 제거
    const calendedrImg = document.getElementById('calendedr-img');
    calendedrImg.classList.remove('calendedr-img');

    // 예약하기 버튼에 기존 스타일 클래스를 다시 추가
    registButton.classList.add('your-existing-style-class');

    // 달력을 감춤과 동시에 시간을 감춤
    const timeContainer = document.getElementById('time-container');
    timeContainer.style.display = 'none';

    // 예약하기 버튼 감춤
    const reserveBtn = document.getElementById("reserveBtn");
    reserveBtn.style.display = 'none';
}



function updateAvailableTimes(selectedDate) {

    const timeContainer = document.getElementById('time-container');

    // 기존 버튼 모두 제거
    // timeContainer.innerHTML = '';

    // 현재 날짜를 가져오는 함수
    function getCurrentDate() {
        const today = new Date();
        const year = today.getFullYear();
        let month = today.getMonth() + 1;
        let day = today.getDate();

        // 한 자리 숫자인 경우 앞에 0을 추가
        month = month < 10 ? '0' + month : month;
        day = day < 10 ? '0' + day : day;

        return `${year}-${month}-${day}`;
    }
    
    // 현재 날짜
    const currentDate = getCurrentDate();
    console.log("currentDate:", currentDate);

    // 예약 불가한 시간을 서버에서 가져오는 함수
    async function fetchReservedTimes(selectedDate) {
        try {
            const response = await fetch(`/adopt/getReservedTimes?selectedDate=${selectedDate}`);
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error fetching reserved times:', error);
            return [];
        }
    }

    // 날짜가 현재 날짜보다 뒤의 날짜이면 시간 버튼을 추가
    if (selectedDate >= currentDate) {

        updateReservedTimes(selectedDate);

        async function updateReservedTimes(selectedDate) {
            const reservedTimes = await fetchReservedTimes(selectedDate);        
            console.log('reservedTimes::', reservedTimes);
            
            timeContainer.style.display = 'flex';

            // reservedTimes를 사용하여 UI 업데이트
            const buttons = document.querySelectorAll("#time-container button");
        
            buttons.forEach(function(button) {
                const timeValue = button.value;
                console.log('timeValue::', timeValue);
                // timeValue가 reservedTimes 배열에 있으면 
                // isReserved는 true가 되고, 그렇지 않으면 false
                const isReserved = reservedTimes.includes(timeValue);
        
                if (isReserved) {
                    button.disabled = true;
                    button.style.backgroundColor = 'gray'
                    button.style.border = 'none'
                } else {
                    button.disabled = false;
                    button.addEventListener('click', handleTimeButtonClick);
                }
            });
        }
        
    }
}

function handleTimeButtonClick(event) {

    // 이전에 선택된 버튼이 있으면 원래 색상으로 되돌리기
    const previouslySelectedButton = document.querySelector('.selected-time-button');
    if (previouslySelectedButton) {
        previouslySelectedButton.style.backgroundColor = 'white'; // 이전 색상으로 변경
        previouslySelectedButton.style.border = '1px solid black'; 
        previouslySelectedButton.classList.remove('selected-time-button');
    }

    // 현재 클릭된 버튼을 선택된 상태로 변경
    const selectedTime = event.target.value;
    console.log(`Selected time: ${selectedTime}`);
    event.target.style.backgroundColor = '#FA7575'; // 새로운 색상으로 변경
    event.target.style.border = 'none'; // 새로운 색상으로 변경

    event.target.classList.add('selected-time-button');

    // 예약하기 버튼 보이도록 변경
    const reserveBtn = document.getElementById('reserveBtn');
    reserveBtn.style.display = 'block';
}




// 선택된 날짜, 시간 java로 보내기
function submitReservation() {
    // 선택된 날짜와 시간을 가져오기
    const selectedDate = document.getElementById('dateInput').value;
    const selectedTimeButton = document.querySelector('.selected-time-button');
    const selectedTime = selectedTimeButton ? selectedTimeButton.value : '';

    // 날짜와 시간이 선택되었는지 확인
    if (!selectedDate || !selectedTime) {
        alert('날짜와 시간을 선택해주세요.');
        return;
    }

    // 선택된 날짜와 시간을 서버로 전송
    sendReservationData(selectedDate, selectedTime);

    // // 예약하기 버튼 감추기
    // const reserveBtn = document.getElementById('reserveBtn');
    // reserveBtn.style.display = 'none';
}


function sendReservationData(selectedDate, selectedTime) {

    const reservationData = {
        date: selectedDate,
        time: selectedTime
    };

    fetch('/adopt/dogReservation', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(reservationData)
    })
    .then(response => response.json())
    .then(data => {
        
        const result = data.result;

        if(result > 0){
            alert("예약이 완료되었습니다.");
            reserveDone();
        }else{
            alert("예약을 실패하였습니다. 다시 시도해주세요.");
        }
        
    })
    .catch(error => {
        console.error('Error during reservation:', error);
    });
    

}