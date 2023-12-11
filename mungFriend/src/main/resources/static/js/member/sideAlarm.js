
// 알림 읽음 표시
function deleteAlarm(alarmNo,element) {

    fetch("/deleteAlarm?alarmNo="+alarmNo)
    .then(resp => resp.text())
    .then(result => {
        if(result > 0){
            console.log("삭제완료");
            element.closest('li').style.display = 'none';
            selectAlarmList(memberNo); // 목록을 다시 조회해서 삭제된 글을 제거
        }else{
            console.log("삭제실패");
        }
    })
    .catch(err => console.log(err));


}


function selectAlarmList(memberNo) {

    fetch("/selectAlarm?memberNo="+memberNo)
    .then(resp => resp.json())
    .then(alarmList => {
        console.log("알림 목록 : ", alarmList);
       
    })
    .catch(err => console.log(err));


}