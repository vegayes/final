/* 부모요소 선택된 값 스타일 바꾸기 */
function changeParentBorderStyle(radio) {
  var parentElement = radio.closest('.formElement');
  
  console.log("closet:" + parentElement);
  
  var parent = radio.parentElement;
  console.log("parentElemtn:" + parent);
  
  if (parent) {
    
    parent.style.border = '5px solid blue'; 
    
  }
}

var radioButtons = document.querySelectorAll('.donationButton input[type="radio"]');

radioButtons.forEach(function(radio) {
  radio.addEventListener('change', function() {
    changeParentBorderStyle(radio);
  });
});


/* 후원하기의 체크된 내용 확인하기*/
function checkDonation(){
	
    var donationType = document.querySelector('input[name="cycle"]:checked');
    var donationAmount = document.querySelector('input[name="amount"]:checked');
	var donationCheck = document.querySelectorAll('.donationProcessCheck input[type="checkbox"]:checked');
	
	console.log(donationCheck);
	
	if(donationType == null){ // 후원하기 주기가 체크가 되지 않았다면 다시 실행하게 하기
		alert("후원 주기를 체크해주세요~");
		return;
	}else if(donationAmount == null){
		alert("후원 금액을 선택해주세요~");
		return;
	}else if (donationCheck.length != 2){
		alert("동의해주세요");
/*		const donation
		donationCheck.focus();
*/
		return;	
	}
	
	
	console.log("추출 : " + donationType.value);
	console.log("금액 : " + donationAmount.value);
	
	
	
}












