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

var radioButtons = document.querySelectorAll('.applyFieldset input[type="radio"]');

radioButtons.forEach(function(radio) {
  radio.addEventListener('change', function() {
    changeParentBorderStyle(radio);
  });
});
