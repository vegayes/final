
function cancelReservations() {
    const selectedCheckboxes = document.querySelectorAll('input[name="selectedReservations"]:checked');

    if (selectedCheckboxes.length > 0) {

        console.log('취소할 예약들:', selectedCheckboxes);

        






    } else {
        alert('예약을 선택해주세요.');
    }
}