/* 부모요소 선택된 값 스타일 바꾸기 */
function changeParentBorderStyle(radio) {
  var radioGroup = document.querySelectorAll('input[name="' + radio.getAttribute('name') + '"]');
  
  radioGroup.forEach(function(radioButton) {
    var parentElement = radioButton.parentElement;
    
    if (parentElement) {
      parentElement.classList.remove('selected'); // 모든 버튼의 선택 상태 제거
    }
  });
  
  var parent = radio.parentElement;
  
  if (parent) {
    parent.classList.add('selected'); // 선택된 버튼에 선택 상태 추가
  }
}

var radioButtons = document.querySelectorAll('.donationButton input[type="radio"]');

radioButtons.forEach(function(radio) {
  radio.addEventListener('change', function() {
    changeParentBorderStyle(radio);
  });
});



/* 직접입력 창 display */
const donationRadio = document.querySelectorAll('input[type = "radio"]:not([value="input"])'); // 다른 후원 금액
const donationInputBtn = document.querySelector('input[value="input"]');
const donationInputArea = document.getElementById("inputAmountArea");
var inputFlag = false; // 안열린 상태

donationInputBtn.addEventListener('click', function() {
	
	console.log("누름");
	console.log(donationInputBtn.value);
	console.log(typeof donationInputBtn.value);
	
	if(!inputFlag){
		donationInputArea.style.display = "flex";		
		inputFlag = true;
	}
	
})

for(let i = 0; i <donationRadio.length; i++){
	
	donationRadio[i].addEventListener('click', function() {
		console.log("다른 버튼 눌림");
		
		if(inputFlag){
			donationInputArea.style.display = "none";		
			inputFlag = false;
		}
		
		
	});
}



/* 금액 합산 */
$('#btn_add').click(function(evt) {
    $('.inputAmount').each(function(idx, ele) {
        $(ele).val(parseInt($(ele).val())+100);
    });
    evt.preventDefault();
});
$('#btn_add2').click(function(evt) {
    $('.inputAmount').each(function(idx, ele) {
        $(ele).val(parseInt($(ele).val())+10000);
    });
    evt.preventDefault();
});
$('#btn_add3').click(function(evt) {
    $('.inputAmount').each(function(idx, ele) {
        $(ele).val(parseInt($(ele).val())+50000);
    });
    evt.preventDefault();
});
$('#btn_add4').click(function(evt) {
    $('.inputAmount').each(function(idx, ele) {
        $(ele).val(parseInt($(ele).val())+100000);
    });
    evt.preventDefault();
});

// 직접입력 금액 초기화 
const inputAmount = document.getElementById("inputAmount");
document.getElementById("inputReset").addEventListener('click', function(){
	
	inputAmount.value = 0;
	
	
});




