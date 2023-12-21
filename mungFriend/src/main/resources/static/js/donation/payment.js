// 결제 진행 정보 확인 (일반 결제 , 정기 결제 구분)
function paymentInfoCheck(){
	var donationFlag = true;
	
	var donationType = document.querySelector('input[name="donationType"]:checked'); // 후원 주기
	var donationAmount = document.querySelector('input[name="donationAmount"]:checked'); // 후원 금액 
	var donationCheck = document.querySelectorAll('.donationProcessCheck input[type="checkbox"]:checked'); // 후원 동의
	var donationContent = document.querySelector('.donationContent'); // 후원 내용
	
	var donationName = ''; // 후원자 이름
	var donationEmail = ''; // 후원자 이메일
	
	
	
	if(loginMember == null){
		var donationInputName = document.querySelector('input[name="donationName"]').value; // 후원자 입력 이름 (비회원)
    	var donationInputEmail = document.querySelector('input[name="donationEmail"]').value; // 후원자 입력 이메일 (비회원)
		console.log("로그인안됨");
		
		// 입력값 유효성?
		if(donationInputName.length == 0 ){
			alert("후원자 이름을 작성해주세요!")
			document.querySelector('input[name="donationName').focus();
			return;
		}
		if(donationInputEmail.length == 0){
			alert("후원자 이메일을 작성해주세요!")
			document.querySelector('input[name="donationEmail').focus();
			return;
		}
	
		donationName = donationInputName;
		donationEmail = donationInputEmail;
		console.log("로그인안됨 이룸 : " + donationName);
		console.log("로그인안됨 이메일 : " + donationInputEmail);
	}else{
		console.log("로그인됨");		
		donationName =  loginMember.memberName;
		donationEmail =  loginMember.memberEmail;
	}
		
	/* 후원하기의 체크된 내용 확인하기*/
	if(donationType == null){ // 후원하기 주기가 체크가 되지 않았다면 다시 실행하게 하기
		alert("후원 주기를 체크해주세요~");
		donationFlag = false;
		return;
	}else if(donationAmount == null){
		
		alert("후원 금액을 선택해주세요~");
		donationFlag = false;
		return;
	}else if (donationCheck.length != 2){
		alert("동의해주세요");
		donationFlag = false;
/*		const donation
		donationCheck.focus();
*/
		return;	
	}
	
	/* 직접입력의 경우*/	
	if(donationAmount.value === 'input'){
		console.log("금액이 input으로 눌림");	
		
		/* 재사용 */
		donationAmount = document.querySelector('#inputAmount'); // 입력된 후원 금액 
		
		console.log("입력된 후원 금액 : " + donationAmount.value);	
		
		if(donationAmount.value == 0){
			alert("후원 금액을 입력해주세요!");
			donationFlag = false;
			donationAmount.focus();
			return ;
		}
		
	}	
	
	if(!donationFlag){	// 결제창 못감. 
		return;
	}else{
        // 결제정보를 결제api 진행하면서 넣어야 하는거 아니야? 왜 미리 넣어?
        // => 미리 넣는다고 했을 때 loginMember의 정보를 넣음
	    console.log("type : " + donationType.value);
	    console.log("amount : " + donationAmount.value);
	    
	    // 결제 정보
	    var paymentData = {
	            pg: "html5_inicis",		//KG이니시스 pg파라미터 값
	            pay_method: "card",		//결제 방법
	            merchant_uid: "nobody_" + new Date().getTime() + "_1",//주문번호 전달
		        // 라디오 버튼에서 선택한 값을 결제 정보에 추가
		        name: '멍프랜드 ' + (donationType.value === '일시' ? '일시 후원' : '정기 후원'),
		        amount: parseInt(donationAmount.value), // 문자열을 숫자로 변환하여 저장	  
		        buyer_name : donationName,
		        buyer_email : donationEmail,
		        // // notice_url : 'http://43.202.237.39/donation/donationPay',
				// notice_url : 'https://a51a-116-47-77-52.ngrok-free.app/donation/payment'
		};	
		
	if(donationType.value === '일시'){
		requestPay(paymentData);
	}else if(donationType.value === '정기'){

		var cardInfo; 
		
	    // 로딩창 표시 함수
	    function showLoading() {
	        var loadingElement = document.getElementById("load");
	        loadingElement.style.display = "flex";
	    }
	
	    // 로딩창 숨김 함수
	    function hideLoading() {
	        var loadingElement = document.getElementById("load");
	        loadingElement.style.display = "none";
	    }

		// 1) 모달창 열기
		openCardInfo(paymentData.amount); 

		// 2) 제출 버튼 누르면 유효성 검사 후에 값 받아오기
		var regularBtn = document.getElementById("regularBtn");
 
		regularBtn.addEventListener("click", function() {
			showLoading();
			cardInfo = regularCardInfoCheck();

			if(cardInfo  !== undefined){
				console.log("하이");

				var billing = billingKeyCreate(cardInfo,loginMember.memberNo);


				console.log("billing : " + JSON.stringify(billing));

				// 결제검증
				fetch('/billingkey/' + billing.customer_uid, {
					method:"POST",
					body: JSON.stringify(billing),
					headers: {
						"Content-Type": "application/json"
					}
				})
				.then(function(rsp) {
					return rsp.json();
				})
				.then(function(data) {
					console.log("정기결제 빌링키 발급 들어옴??????");
					
					if(data.code == 0){
						// 빌링키 발급 조회
						billingKeyCheck(data.response.customerUid)
						.then(function(billingCode) {
							console.log("빌링키 조회  : " + billingCode);
					
							if(billingCode === 0){
								console.log("빌링키 조회 성공! ");

								// 정기결제 고유 번호 (식별 : 날짜 + 회원번호)
								var newMerchant = "donation_regular_" + new Date().getTime() + "_" + loginMember.memberNo + "_" + "1"//주문번호 전달

								paymentData.merchantUid = newMerchant;
								paymentData.customer_uid = billing.customer_uid;
								paymentData.cardNumber = billing.cardNumber;
								paymentData.cardExpiry = billing.cardExpiry;
								paymentData.birth = billing.birth;
								paymentData.pwd2Digit = billing.pwd2Digit;
	

								// 1회 결제
								regularOnePay(paymentData, donationContent, donationType);
								
							}
						})
						.catch(function(error) {
							console.error("빌링키 조회 중 에러 발생:", error);
							// 에러 처리
						});

					}else if (data.code == -1){
						alert("카드정보가 불일치 합니다. 다시 정보를 기입해주세요!");
						hideLoading();
					}
				})
				.catch(function(error) {
					alert("결제에 실패하였습니다.", "에러 내용: " + error, "error");
				});



			}else{
				//alert("카드 정보를 정확하게 작성해주세요!");
				console.log("카드 정보를 정확하게 작성해주세요!");
				
			}
		})
	
	}else {
		console.log("뭔가 잘못됨.");
	}
	
}




// 결제 진행 
function requestPay(paymentData) {
	var IMP = window.IMP;
	    IMP.init("imp82107782");

		console.log("일반 결제 : " + JSON.stringify(paymentData));
	
	    IMP.request_pay(paymentData,
	        function (rsp) {
				
		        if (rsp.success) { // 결제 성공
					alert("결제 성공");
					
					console.log("결제 성공 후 imp_uid 값 : " + rsp.imp_uid);
					
					fetch('/payment/' + rsp.imp_uid, {
					    method: 'POST'
					})
					.then(function(response) {
					    return response.json();
					})
					.then(function(data) {

						console.log(data);
							
			            var donation = {
	                        impUid: data.response.impUid,
	                        applyNum :data.response.applyNum,
	                        merchantUid: data.response.merchantUid,
	                        cardName :data.response.cardName,
	                        paidAt: data.response.paidAt,
	                        receiptUrl : data.response.receiptUrl,
	                        status : data.response.status,
	
							memberName : data.response.buyerName,
							memberEmail : data.response.buyerEmail,
	                        
	                        donationContent : donationContent.value,
							donationType : donationType.value,
							donationAmount : data.response.amount,
			            };

						console.log("값 :" + donation);
						
						// DB에 값 전달
						paymentDBInput(donation);
				
					})
					.catch(function(error) {
					    alert("결제에 실패하였습니다.", "에러 내용: " + error, "error");
					});
				}
			});

	  }
}

// DB에 넣는 작업
function paymentDBInput(donation){
		// 컨트롤러에 데이터를 전달하여 DB에 입력하는 로직
		fetch("/donation/donationPay", {
			method: "POST",
			body: JSON.stringify(donation),
			headers: {
				"Content-Type": "application/json"
			}
		})
		.then(response => response.text())
		.then(result => {
			if (result === "y") {
				console.log("안녕 나 결제 DB 성공하고 들어옴.")
			
				if(loginMember != null){
					location.href = '/mypage/member/donationList';
						// 로그인되면 후원 내역으로 넘어가기
						return; 
				}
				location.href = '/';
				
			} else {
				alert("DB 후원 정보 입력 실패");
				return false;
			}
		})
		.catch(function(error) {
			alert("오류가 발생하였습니다.", "에러 내용: " + error, "error");
		});	
}

// 결제 예약하기
function regularSchduel(onePay){
	
	const merchantUid = onePay.response.merchantUid;
	const new_str = merchantUid.substr(0, merchantUid.length-1);

	console.log("merchant:"+new_str); // 결과 확인

	const cardData = [];

	const currentTimeStamp = Math.floor(Date.now() / 1000); // 현재 시간의 Unix 타임스탬프 
	const oneMinute = 120; // *********************** 30일을 기준! ******************************


	console.log("currentTimeStamp :" +currentTimeStamp);

	for (let i = 2; i <= 12; i++) { // *************************************************************12로 바꾸기**************************
		const data = {
			customer_uid : onePay.response.customerUid,
			merchantUid: `${new_str}${i}`,
			schedule_at: currentTimeStamp + (oneMinute * (i-1)), // 현재 시간으로부터 1분씩 증가
			currency: "KRW",
			amount: onePay.response.amount,
			name: onePay.response.name,
			buyer_name: onePay.response.buyerName,
			buyer_email: onePay.response.buyerEmail
		};
		cardData.push(data);
	}

	console.log(cardData); // 생성된 데이터 확인

	if(cardData != null){

		fetch('/regular/schedule/' + onePay.response.customerUid, {
			method:"POST",
			body: JSON.stringify(cardData),
			headers: {
				"Content-Type": "application/json"
			}
		})
			.then(resp => resp.json())
			.then(function(schedule) {


				console.log("안녕 나는 스케쥴이야");
				console.log(schedule);

				if(schedule.code == 0){ // 성공


				}
			})
			.catch(err => {
				console.log(err);
				throw err; 
			});
	
	}
}	            