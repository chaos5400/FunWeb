/**
 * 회원가입 유효성 검사와 필수 입력 태그에 대한 에러메시지 출력을 구현하는 메소드를 포함하고 있다.
 */

/*********************************************
 * Retype할 태그를 연결한다.
 * Retype이벤트가 적용된 태그에서 onblur 효과가 발생하면
 * 두 개의 태그 값이 같은지 비교한다.
 * 첫 번째 태그의 값이 변경되면 두 번째 태그의 내용은 비워진다.
 *********************************************/

// 2개의 태그를 연결하여 Retype하게 만든다.
// 2개의 태그를 연결할 2차원 배열
// 하나의 행에는 Retype하게 연결한 2개의 태그 아이디가 저장된다.
var arrayOfRetype = [];

/**
 * Retype으로 연결할 2개의 태그를 입력받는다.
 * 
 * @param array Retype으로 연결할 태그의 배열 
 */
function bindRetypeTag(array, msg) {
	var index = arrayOfRetype.length;
	arrayOfRetype[index] = array;
	notEqualErrorMessage[index] = msg;
	registerOnKeyDownEvent(array);
}





/**
 * Retype태그의 경우 첫 번째 태그에 Key Down 이벤트가 발생하면
 * 연결되어 있는 태그의 값을 공백으로 바꾼다.
 * 
 * @param array Retype 태그 배열의 1행
 */
function registerOnKeyDownEvent(array) {
	var primary_id = array[0]; // 기본 태그의 id를 구한다.
	var retype_id = array[1];
	/* Retype의 기본태그에서 키 입력 이벤트가 일어나면 
	      두 번째 태그의 내용을 지우고 테두리 색상을 원래대로 하고 에러 메시지를 삭제한다. */
	document.getElementById(primary_id).onkeydown = function() {
		var primary_id = array[0]; // 기본 태그의 id를 구한다.
		var retype_id = array[1];
		
		document.getElementById(retype_id).value = ""; // 두 번째 태그의 내용을 지우고
		
		var errorMsgId = getErrorMsgId(retype_id);
		toOrigin(retype_id, errorMsgId); // 테두리 색상을 원래대로하고 에러메시지를 삭제한다.
	}
}





/**
 * <p>태그의 ID값으로 에러메시지 태그의 ID값을 반환한다.
 * 
 * @param id 태그의 ID값
 * @return 해당하는 에러메시지 태그의 ID값
 */
function getErrorMsgId(id) {
	var index = getIndex(id);
	return listOfErrorMsgId[index];
}





/**
 * <p>id값의 태그가 Retype 태그인지 확인하여
 * Retype태그의 두 번째 태그이면 2차원 배열의 행의 인덱스를 반환한다.
 * Retype태그가 아니라면 -1를 반환한다.
 * 
 * @param id Retype 태그인지 확인할 태그의 id값(배열에서 2번째 열)
 * @return Retype태그이면 2차원 배열의 행의 인덱스를, 아니라면 -1
 */
function isRetypeTag(id) {
	for(var i = 0; i < arrayOfRetype.length; i++) {
		if(id == arrayOfRetype[i][1]) {
			return i;
		}
	}
	return -1;
}





/**
 * Retype 태그의 첫 번째 태그에 값이 존재하는지를 확인하여 리턴한다.
 * 
 * @param index
 * @return {Boolean}
 */
function getReTypeValue(index) {
	var id = arrayOfRetype[index][0];
	return document.getElementById(id).value;
}





/**
 * Retype태그에서 두 태그의 값이 일치하지 않았을 때 출력할
 * 에러메시지를 반환한다.
 */
function getNotEqualErrorMsg(id) {
	for(var i = 0; i < arrayOfRetype.length; i++) {
		if(id == arrayOfRetype[i][1]) {
			// 만약 설정되어 있는 에러메시지가 있다면 그 에러 메시지를 반환한다.
			return notEqualErrorMessage[i];
		}
	}
	// 설정한 에러 메시지가 없다면 기본 에러메시지를 반환한다.
	return "두 값이 일치하지 않습니다.";
}






/* 여러개의 태그를 바인딩하여 하나처럼 동작하도록 한다. */
// 바인딩된 태그들을 저장할 2차원 배열
var arrayOfBinding = [];
// 바인딩된 태그가 포커스 이벤트가 발생된 적이 있는지 확인할 2차원 배열
// 포커스 이벤트가 발생된 적이 있다면 true, 없다면 false
var hasFocusedFlag = [];
// arrayOfBinding 배열의 행에 해당하는 인덱스를 저장할 변수
var indexOfBinding = -1;

/**
 * <p>태그들을 Bind하여 하나처럼 동작하도록 하기위해
 * array를 2차원 배열의 형태로 내부 변수에 저장한다.
 * 
 * <p>array변수에 있는 id값들을 listOfId 배열에 저장한다.
 * 
 * @param array 바인딩할 태그의 id를 저장한 2차원 배열
 */
function bindTags(array) {
	for(var i = 0; i < array.length; i++) {
		arrayOfBinding[++indexOfBinding] = array[i];
		hasFocusedFlag[indexOfBinding] = [false, false];
	}
}





/******* 상황에 따른 에러메시지를 저장할 배열 ******/
// 1. 아무것도 입력되지 않았을 때 출력되는 에러메시지
var emptyErrorMessage = [];
// 2. 값은 입력되었지만 정규표현식을 이용한 유효성 검사를 통과하지 못했을 대 출력되는 에러메시지
var invalidErrorMessage = [];
// 3. 아이디가 중복되었을 때 출력되는 에러메시지
var duplicateErrorMessage = [];
// 4. 아이디 중복체크를 하는 도중 에러가 발생했을 때 출력되는 에러메시지
var idCheckErrorMessage = [];
// 5. Retype의 두 값이 일치하지 않을 때 출력되는 에러메시지
var notEqualErrorMessage = [];

/* ID 중복체크를 하는 도중 나타날 수 있는 값 */
const VALID_ID = 100;		// 유효한 아이디(중복되지 않음)
const DUPLICATE_ID = 101;	// 중복된 아이디
const IDCHECK_ERROR = 102;	// ID 중복체크 도중 에러가 발생하였음





/*****************************************************
 * 유효성검사를 구분하기 위한 상수값 선언
 * isValid() 함수에서는 여기에 있는 상수값을 반환하여
 * 유효성 검사 에러상태를 확인할 수 있게 한다.
 * 
 * 에러메시지는 id값과 매칭하여 사용한다.
 * 각각의 태그 id값마다 다른 에러메시지를 가지고 있다.
 *****************************************************/
const EMPTY_ERROR = 1;				// input 태그가 비어있음
const INVALIDATION_ERROR = 2;		// 정규표현식에 부합되지 않음
const DUPLICATE_VALUE_ERROR = 3;	// DB에 동일한 데이터가 존재함(중복)
const RETYPE_NOT_EQUAL_ERROR = 4;	// Retype 태그의 두 값이 일치하지 않음.

/* 유효성 검사가 성공했을 때 isValid() 함수에서 반환하는 값 */
const SUCCESS_VALIDATION = 10;





/* 이벤트를 등록한 태그의 id를 저장할 배열 */
var listOfId = [];
/* errorMsgId를 저장할 배열 */
var listOfErrorMsgId = [];
/* 각각의 id마다 유효성 검사를 수행할 정규표현식을 저장할 배열 */
var listOfValidator = [];
/* listOfId 배열에 접근하기 위한 인덱스 */
var indexOfListOfId = -1;





/* input 태그의 원래 테두리 색상 */
var inputTagOriginBorderColor = "gray";
/* input 태그의 에러 발생시 테두리 색상 */
var inputTagFailBorderColor = "red";





/* DB 중복 검사를 시행할 태그의 id값들을 저장할 배열 */
var idForValidDuplicate = [];
/* idFOrValidDuplicate 배열에 접근하기 위한 인덱스 */
var indexOfIFVD = -1;

/* URL FOR ID CHECK */
var urlVal = [];

/*
 * 
 * @param id
 * @param url
 * @param dpErrorMsg
 * @param ckerrorMsg
 */
function registerDuplicateCheckTag(id, url, dpErrorMsg, ckerrorMsg) {
	idForValidDuplicate[++indexOfIFVD] = id;			// 중복체크할 id 저장
	duplicateErrorMessage[indexOfIFVD] = dpErrorMsg;	// 중복발생시 출력할 에러메시지 저장
	urlVal[indexOfIFVD] = url;							// db접속시 사용할 url 저장
	idCheckErrorMessage[indexOfIFVD] = ckerrorMsg;		// 중복체크도중 발생한 예상치 못한 에러 발생시 출력할 에러메시지 저장
}





/*
 * <p>idForValidDuplicate 배열에서 id값과 일치하는 인덱스를 반환한다.
 * 
 * <p>
 * id : 인덱스를 구할 id값
 */
function getIFVDIndex(id) {
	for(var i = 0; i < idForValidDuplicate.length; i++) {
		if(id == idForValidDuplicate[i]) return i;
	}
}





/*
 * 등록된 id개수만큼 사이즈의 에러메시지 저장공간을 확보한다.
 */
function setErrorMsgSize() {
	var size = listOfId.length;	// 등록된 id 개수를 사이즈로 지정한다.
	emptyErrorMessage = new Array(size);
	invalidErrorMessage = new Array(size);
}




/*
 * <p>이벤트 등록된 전체 태그 id 배열(listOfId)에서 
 * 매개변수 id값과 일치하는 인덱스를 반환한다. 
 * 
 * <p>일치하는 id가 없으면 -1을 리턴한다.
 * 
 * <p>
 * id :	인덱스를 구할 id
 * 
 * <p>return {Number} : 인덱스 or -1(일치하는 id 없음)
 */
function getIndex(id) {
	for(var i = 0; i < listOfId.length; i++) {
		if(listOfId[i] == id) return i;
	}
	return -1;
}





function getEmptyErrorMsg(id) {
	var index = getIndex(id);
	return emptyErrorMessage[index];
}





function getInvalidErrorMsg(id) {
	var index = getIndex(id);
	return invalidErrorMessage[index];
}





function getDuplicateErrorMsg(id) {
	for(var i = 0; i < idForValidDuplicate.length; i++) {
		if(idForValidDuplicate[i] == id) return duplicateErrorMessage[i];
	}
	return null;
}





function getIdCheckErrorMsg(id) {
	for(var i = 0; i < idForValidDuplicate.length; i++) {
		if(idForValidDuplicate[i] == id) return idCheckErrorMessage[i];
	}
	return null;
}





function setEmptyErrorMsg(id, msg) {
	var index = getIndex(id);
	emptyErrorMessage[index] = msg;
}





function setInvalidErrorMsg(id, msg) {
	var index = getIndex(id);
	invalidErrorMessage[index] = msg;
}





function setInvalidErrorMsgByArray(array) {
	for(var i = 0; i < array.length; i++) {
		setInvalidErrorMsg(array[i][0], array[i][1]);
	}
}





function setEmptyErrorMsgByArray(array) {
	for(var i = 0; i < array.length; i++) {
		setEmptyErrorMsg(array[i][0], array[i][1]);
	}
}






function setEmptyErrorMsgAll(msg) {
	for(var i = 0; i < listOfId.length; i++) {
		setEmptyErrorMsg(listOfId[i], msg);
	}
}





function setInvalidErrorMsgAll(msg) {
	for(var i = 0; i < listOfId.length; i++) {
		setInvalidErrorMsg(listOfId[i], msg);
	}
}





function setErrorMsgColorByClassName(className, color) {
	var list = document.getElementsByClassName(className);
	for (var i = 0; i < list.length; i++) {
		list[i].style.color = color;
	}
}





function setInputTagOriginBorderColor(color) {
	inputTagOriginBorderColor = color;
}





function setInputTagFailBorderColor(color) {
	inputTagFailBorderColor = color;
}





/*
 * 하나의 회원가입 폼 태그에 대한 모든 이벤트를 일괄적으로 등록시킨다.
 */
function registerAllEventToJoinFormSubTag(id, errorMsgId, validator) {
	/* onblur */
	registerOnBlurEvent(id, errorMsgId);
	/* onfocus */
	registerOnFocusEvent(id, errorMsgId);
	
	/* id를 배열에 저장한다. */
	listOfId[++indexOfListOfId] = id;
	
	/* errorMsgId를 배열에 저장한다 */
	listOfErrorMsgId[indexOfListOfId] = errorMsgId;
	
	/* 유효성 검사를 수행할 정규표현식을 저장한다. */
	if(validator == null) { listOfValidator[indexOfListOfId] = /.+$/; }
	else { listOfValidator[indexOfListOfId] = validator; }
	
	/* 에러메시지의 기본 색상을 red로 지정한다. */
	document.getElementById(errorMsgId).style.color = "red";
}	




/**
 * 등록할 이벤트 정보를 2차원 배열로 받아서 이벤트를 등록시킨다.
 * 
 * @param arrayForEvent 이벤트 정보가 포함된 2차원 배열
 */
function registerEventByArray(arrayForEvent) {
	for(var i = 0; i < arrayForEvent.length; i++) {
		registerAllEventToJoinFormSubTag(arrayForEvent[i][0], arrayForEvent[i][1], arrayForEvent[i][2]);
	}
	setErrorMsgSize();	// 등록된 id 개수만큼 에러메시지 배열 크기 지정
}





/*
 * 
 * @param id
 * @param errorMsgId
 */
function registerOnBlurEvent(id, errorMsgId) {
	document.getElementById(id).onblur = function() {
		var validation = isValid(id);
		
		if(validation == SUCCESS_VALIDATION) { // 유효성 검사에 성공하면
			toOrigin(id, errorMsgId);
		} else { // 유효성 검사에 실패하면
			var errorMessage = getErrorMsg(id, validation);	// 적절한 에러메시지를 얻어서
			toFail(id, errorMsgId, errorMessage);			// 에러메시지를 출력하고 input태그의 테두리 색상을 변경한다.
		}
	}
}





/*
 * <p>id에 해당하는 태그에 onfocus 이벤트를 등록한다.
 * 
 * <p>이벤트: 해당 id값의 태그에 포커스가 맞춰졌을 시 borderColor를 원래대로 하고
 * 에러메시지를 없앤다.
 * 
 * <p>이 이벤트는 input태그를 위한 것이다.
 * 
 * <p>에러메시지는 span태그를 위한 것이다.
 * 
 * <p>
 * id : 이벤트를 등록할 태그의 id
 * errorMsgId : 에러메시지를 출력할 태그의 id
 */ 
function registerOnFocusEvent(id, errorMsgId) {
	document.getElementById(id).onfocus = function() {
		/* 바인딩된 태그라면 포커스 flag값을 true로 준다. */
		turnBindingTagTrue(id);
		
		/* 태그 테두리 색상을 원래대로 하고 에러메시지를 삭제한다. */
		toOrigin(id, errorMsgId);
	}
}






/**
 * id가 바인딩된 태그라면 바인딩된 태그 배열의
 * 인덱스를 얻어와 그 태그의 포커스 Flag 를 true로 바꾸어준다.
 * 
 * @param id 바인딩된 태그의 id
 */
function turnBindingTagTrue(id) {
	var index = getBindingTagIndex(id);
	if(index != -1) {
		hasFocusedFlag[index[0]][index[1]] = true;
	}
}





/**
 * <p>index를 이용하여 바인딩된 태그중 실패한 태그가 있는지 검사하여
 * 실패한 태그가 있을 경우 true를 리턴한다.
 * 
 * <p>index가 범위를 벗어나면 바인딩할 태그가 없는 것으로 간주하여 false를 리턴한다.
 * 
 * @param index arrayOfBinding의 행의 인덱스값
 * @return {Boolean} 바인딩된 태그중 유효성 검사에 실패한 태그가 있다면 true 그외에는 false
 */
function isBindingTagFailed(id) {
	/* id와 바인딩된 태그가 있는지 검색한다. */
	var index = getBindingTagIndex(id);
	
	/* index가 -1이라면 바인딩할 태그가 없는 것이기 때문에 false 리턴. */
	if(index == -1) {
		return false;
	}
	
	/* index가 범위를 벗어나면 바인딩할 태그가 없는 것으로 간주하여 false 리턴. */
	if(index[0] < 0 || index[0] > indexOfBinding) {
		return false;
	}
	
	/* 바인딩된 태그에 유효성 검사를 실시한다. */
	for(var i = 0; i < 2; i++) {
		
		if(i == index[1]) { continue; }
		
		if(hasFocusedFlag[index[0]][i] == true && // 포커스를 준 적이 있고
				isValid(arrayOfBinding[index[0]][i]) != SUCCESS_VALIDATION) { // 유효성 검사에 실패하였다면
			/* 해당 태그에 강제로  onblur 효과를 준다. */
			var failedId = arrayOfBinding[index[0]][i];
			document.getElementById(failedId).onblur();
			return true;
		}
		
	}
	
	/* 바인딩된 태그가 유효성 검사에 통과하였다면 false 리턴, 실패하였다면 true 리턴 */
	return false;
}





/**
 * <p>하나의 태그 아이디를 매개변수로 전달받아
 * 그 태그 아이디와 바인딩된 태그가 있는지는 검색하여
 * 있다면 그 행의 인덱스를 리턴한다.
 * 
 * @param id 바인딩된 태그의 아이디
 * @returns {Number} 바인딩된 태그의 인덱스, 해당하는 아이디가 없을 시 -1
 */
function getBindingTagIndex(id) {
	var outer = arrayOfBinding.length;
	for(var i = 0; i < outer; i++) {
		for(var j = 0; j < 2; j++) {
			if(id == arrayOfBinding[i][j]) {
				/* 바인딩된 tag가 존재하면 그 행의 인덱스를 리턴한다. */
				return [i, j];
			}
		}
	}
	return -1;
}





/**
 * id와 validation을 이용하여 적절한 에러메시지를 반환한다.
 * 
 * @param id 			에러메시지를 구할 id
 * @param validation 	에러메시지를 구분할 변수
 * @returns 	id에 일치하고 validation의 구분에 따른 에러메시지
 */
function getErrorMsg(id, validation) {
	var errorMessage;
	
	if(validation == EMPTY_ERROR) { errorMessage = getEmptyErrorMsg(id); }
	else if(validation == INVALIDATION_ERROR) { errorMessage = getInvalidErrorMsg(id); }
	else if(validation == DUPLICATE_VALUE_ERROR) { errorMessage = getDuplicateErrorMsg(id); }
	else if(validation == IDCHECK_ERROR) { errorMessage = getIdCheckErrorMsg(id); }
	else if(validation == RETYPE_NOT_EQUAL_ERROR) { errorMessage = getNotEqualErrorMsg(id); }
	
	return errorMessage;
}





/**
 * <p>유효성 검사에 통과하면 input 태그의 색상을 원래대로 돌리고
 * 에러메시지를 삭제하는 함수
 * 
 * <p>바인딩된 태그가 유효성 검사에 실패하면 
 * 에러메시지는 삭제하지 않고그대로 둔다.
 * 
 * @param id 적용할 input 태그의 id값
 * @param errorMsgId 에러메시지를 출력할 id값
 */
function toOrigin(id, errorMsgId) {
	changeBorderColor(id, inputTagOriginBorderColor);
	
	if(isBindingTagFailed(id) == true) {
		return;
	}
	
	changeInnerHTML(errorMsgId, "");
}






/**
 * <p>유효성 검사에 실패하면 input 태그의 색상을 inputTagFailBorderColor로 바꾸고
 * 에러메시지를 출력하는 함수
 * 
 * @param id 적용할 input 태그의 id값
 * @param errorMsgId 에러메시지를 출력할 id값
 * @param errorMsg 출력할 에러메시지
 */
function toFail(id, errorMsgId, errorMsg) {
	changeBorderColor(id, inputTagFailBorderColor);
	changeInnerHTML(errorMsgId, errorMsg);
}





/**
 * id에 해당하는 태그의 borderColor를 color으로 변경한다. 
 * 
 * @param id	borderColor를 변경할 태그의 id값
 * @param color	변경할 색상의 문자열
 */
function changeBorderColor(id, color) {
	document.getElementById(id).style.borderColor = color;
}





/**
 * id에 해당하는 태그의 innerHTML을 string으로 변경한다.
 * 
 * @param id	 innerHTML을 변경할 태그의 id값
 * @param string 변경할 문자열
 */
function changeInnerHTML(id, string) {
	document.getElementById(id).innerHTML = string;
}
 




/**
 * <ul><strong>id값의 유효성 검사를 수행한다.</strong><BR><BR>
 * 
 * <li>태그 값이 비었을 때는 EMPTY_ERROR 를 반환한다.</li>
 * <li>정규표현식을 이용한 유효성 검사에 실패했을 때는 INVALIDATION_ERROR 를 반환한다.</li>
 * <li>DB에 중복된 값이 존재할 경우에는 DUPLICATE_VALUE_ERROR 를 반환한다.</li>
 * <li>DB중복검사 도중 에러가 발생할 경우에는 IDCHECK_ERROR 를 반환한다.</li>
 * <li>유효성 검사에 통과되었을 경우에는 SUCCESS_VALIDATION 을 반환한다.</li></ul>
 * 
 * @param id 유효성 검사를 수행할 태그의 id
 */
function isValid(id) {
	var value = document.getElementById(id).value;
	
	/* Retype태그이면 기본이 되는 태그의 값과 일치하는지 검사한다. */
	var index;
	if((index = isRetypeTag(id)) != -1) {
		// Retype태그이면 기본 태그에 값이 존재하는지 확인한다.
		var typeValue = getReTypeValue(index);
			if(value != typeValue) {  // 두 값이 일치하지 않는 경우
				return RETYPE_NOT_EQUAL_ERROR; // 에러를 반환
			}
	}
	
	/* EMPTY Check */
	if(value.length == 0) {
		return EMPTY_ERROR;
	}
	
	var re;
	
	/* get validator */
	for(var i = 0; i < listOfId.length; i++) {
		if(listOfId[i] == id) {
			re = listOfValidator[i];
			break;
		}
	}
	
	/* INVALID Check */
	if(re != null && !re.test(value)) {
		return INVALIDATION_ERROR;
	}
	
	/* DUPLICATE Check */
	for(var i = 0; i < idForValidDuplicate.length; i++) {
		if(id == idForValidDuplicate[i]) {
			var error_code = isDuplicate(id);
			
			if(error_code == IDCHECK_ERROR) {		// 중복체크 도중 에러 발생!!!
				return IDCHECK_ERROR;
			}
			
			if(error_code == DUPLICATE_ID) {		// 중복되었다면
				return DUPLICATE_VALUE_ERROR;
			}
			
			break;
		}
	}
	
	/* SUCCESS */
	return SUCCESS_VALIDATION;
}





/**
 * <p>ID 중복 체크를 페이지 리로딩 없이
 * 실시간으로 진행할 수 있게한다.
 *  
 * <p>성공시 화면에서 데이터를 읽어 중복 여부를 결정한다.
 * 중복되지 않았다면 0이 쓰여질 것이다.
 *  
 * @param id 중복체크를 해야할 input 태그의 id
 */
function isDuplicate(id) {
	var error_code = IDCHECK_ERROR;
	var index = getIFVDIndex(id);
	var _url = urlVal[index];
	$.ajax({
		type: "POST",
		url: _url,
		async : false,
		data: {
			"id" : $("#" + id).val()
		},
		success: function(data) {
			if($.trim(data) == 0) {
				error_code = VALID_ID;
			} else {
				error_code = DUPLICATE_ID;
			}
		},
		error: function() {
			error_code = IDCHECK_ERROR;
		}
	});
	return error_code;
};





/**
 * <p>회원가입 폼의 Submit 에 해당하는 버튼의 이벤트를 등록한다.
 * Submit 버튼을 클릭했을 때 유효성 검사에 모두 통과하였다면 submit() 한다.
 * 유효성 검사에 실패하였다면 실패한 input 태그 모두에 onblur 효과를 주어
 * 실패한 태그들을 표시한다.
 * 
 * @param buttonId 이벤트를 발생시킬 버튼 id
 * @param formId submit 이벤트를 발생시킬 form 태그 id 	
 */
function registerSubmitEvent(buttonId, formId, msg) {
	
	document.getElementById(buttonId).onclick = function() {
		
		var countFailed = 0;
		
		// 모든 필수 태그의 유효성 검사를 실시하고
		// 유효성 검사에 실패한 태그의 id 값들을 저장한다.
		for(var indexOfList = indexOfListOfId; indexOfList > -1; indexOfList--) {
			if(isValid(listOfId[indexOfList]) != SUCCESS_VALIDATION) {
				/* 실패한 태그의 id값 저장 */
				document.getElementById(listOfId[indexOfList]).onblur();
				countFailed++;
			}
		}
		
		// 유효성 검사에 실패한 항목이 있으면 리턴한다.
		if(countFailed > 0) return;
		
		if(msg != null && msg != "") { if(!confirm(msg)) return; }
		
		// 모든 유효성 검사가 통과되었으면 submit한다.
		document.getElementById(formId).submit();
	}
	
}





/**
 * <p>buttonId 버튼을 클릭했을 때 회원가입 취소 확인 창을 띄어주어
 * 확인 버튼을 눌렀을 때는 forward에 지정한 페이지로 이동한다. 
 * 
 * @param buttonId 이벤트를 발생시킬 버튼 id<br>
 * @param forward 이동할 페이지 주소<br>
 * @param msg 회원가입 취소 확인 메시지
 */
function registerCancelEvent(buttonId, forward, msg) {
	
	document.getElementById(buttonId).onclick = function() {
		if(msg != null && msg != "") {
			if(confirm(msg)) {
				location.href = forward;
			}
		}
	}
	
}


