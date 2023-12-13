
document.addEventListener('DOMContentLoaded', function() {
    // 페이지 로딩 완료 시 실행되는 코드
    setInitialCheckboxState();
});

function setInitialCheckboxState() {
    const checkboxes = document.querySelectorAll('input[name="selectedReservations"]');
    const reserveStateList = document.querySelectorAll('#reserve-state');

    checkboxes.forEach((checkbox, index) => {
        const reserveState = reserveStateList[index].textContent.trim();

        if (reserveState === '예약 취소') {
            checkbox.disabled = true;
            checkbox.style.backgroundColor = '#d3d3d3';  // 회색 배경
        }
    });
}

function reserveDone() {
    // 예약 완료 시 호출되는 함수
    window.location.reload();  // 여기서 리로드
}
function cancelReservations() {
    const selectedCheckboxes = document.querySelectorAll('input[name="selectedReservations"]:checked');

    if (selectedCheckboxes.length > 0) {
        const reservationData = Array.from(selectedCheckboxes).map(checkbox => {
            const reserveNo = parseInt(checkbox.parentElement.querySelector('#reserve-no').textContent, 10);
            const memberEmail = checkbox.parentElement.querySelector('#reserve-email').textContent;

            return { reserveNo, memberEmail };
        });

        console.log(reservationData);

        // fetch API를 사용하여 서버로 데이터 전송
        fetch('/mypage/admin/cancelReservations', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(reservationData),
        })
        .then(response => response.json())
        .then(data => {
            // 서버에서의 응답을 처리
            console.log(data);

            const result = data.result;

            if (result === 'success') {
                alert("예약이 정상적으로 취소 완료되었습니다.");
                reserveDone();
            } else {
                alert("예약을 실패하였습니다. 다시 시도해주세요.");
            }
        })
        .catch(error => {
            console.error('Error during reservation:', error);
        });
    } else {
        alert('예약을 선택해주세요.');
    }
}


