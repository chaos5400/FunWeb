<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body onunload="return myFunction()">

<p>Close this window, press F5 or click on the link below to invoke the onbeforeunload event.</p>

<a href="https://www.w3schools.com">Click here to go to w3schools.com</a>

<textarea value=""></textarea>

<script>
window.onbeforeunload = function(e) {
    return "Write something clever here.sss..";
}
</script>

</body>
</html>
