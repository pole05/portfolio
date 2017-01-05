<%@page import="javax.xml.ws.RequestWrapper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	-회원 정보 수정-
	<form name="frm" action="/joinEdit.do" method="post">
	
		아이디 : ${list.id} <input type="hidden" name="id" value="${list.id}"><br>
		이름 : ${list.name} <br> 
		별명 : <input type="text" name="nickname" value="${list.nickname}"><br> 
		현재 비밀번호: <input type="password" name="pw" id="pw"><br>
		새 비밀번호: <input type="password" name="npw" id="npw"><br>
		새 비밀번호 확인: <input type="password" name="npw2" id="npw2" onkeyup="checkPwd()"><br>
		<div id="checkPw">동일한 암호를 입력하세요.</div>
		이메일 : <input type="text" name="email" value="${list.email}"><br>
		<input type="submit" id="edit" value="수정">&nbsp;
		<input type="button" onClick="history.back();" value="취 소"> <br><br>
		
	</form>

	<br>

<!-- 	<form action="deleteMember.jsp" method="post"> -->
		<input type="hidden" name="id" value="${list.id}"> 
		<input type="button" value="회원 탈퇴" id="del">
<!-- 	</form> -->

	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script>

		$(document).ready(function() {

			$("#npw2").keyup(function() {
				var pw = $("#npw").val();
				var pw2 = $("#npw2").val();
				if (pw == pw2) {
					$("#checkPw").html("");
				} else {
					$("#checkPw").html("동일한 암호를 입력하세요");
				}
			});

			
		//TODO
		$("#edit").click(function() {
 			var result = <%=request.getAttribute("result")%>;
//			var result = "${result}";
			if (result == 1) {			// 비밀번호 일치할 때
				alert('수정 성공!');
			} else {
				alert('비밀번호가 일치하지 않습니다.');
//				return false;
			}
		});

			
			$("form input:button:last").click(function() {
				var flag = confirm("삭제하시겠습니까?"); // confirm: true, flase로만 대답
				if (flag) { // 확인 눌렀을 때
					$.ajax({
						url : "deleteMember.jsp",
						type : "post",
						data : {
							"id" : "${list.id}"
						},
						success : function(result) {

						}
					});
				}
			});

		});
	</script>

</body>
</html>