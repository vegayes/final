function donationInfoCheck(receiptUrl, memberNo){

	console.log("영수증 :" + receiptUrl);	
	console.log("회원 정보 :" + memberNo);
	
	// 원래는 모달창 띄워서 영수증 보여주기, 정기결제 취소 
	// 1) 영수증 조회하게하기
	window.open(receiptUrl, '영수증', 'width=500, height=500, top = 250px, left = 700px, location = yes"');	
	
	
	
	
	
}


function donationCancel(billingKey,receiptUrl,memberNo){
	
	//1) 취소
	fetch('/regular/schedule/' , {
		method:"POST",
		body: JSON.stringify(billingKey),
		headers: {
			"Content-Type": "application/json"
		}
	})
    .then(resp => resp.json())
    .then(function(result) {
        console.log("결제 취소 성공?" + JSON.stringify(result));
    })
    .catch(err => {
        throw err; 
    });
	
	
	
	
	
}