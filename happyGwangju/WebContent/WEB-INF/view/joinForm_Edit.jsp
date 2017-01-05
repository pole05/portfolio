<%@page import="javax.xml.ws.RequestWrapper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/css/joinFormEdit.css" rel="stylesheet" />
</head>
<body>
	<header>
	<div id='upperBox'>
		<ul id='MemberBox'>
			<li class='MemberBoxMenu' id='login'><a href='#'>로그인</a>&nbsp;I&nbsp;</li>
			<li class='MemberBoxMenu' id='logout'>I&nbsp;<a href='#'>로그아웃</a>&nbsp;I&nbsp;
			</li>
			<li class='MemberBoxMenu' id='joinMember'><a href='#'>회원가입</a></li>
			<li class='MemberBoxMenu' id='mypage'><a href='#'>마이페이지</a></li>
		</ul>
	</div>
	<img class='logo' src='/img/happyLogo2.png' />
	<ul id='nav'>
		<div class='navBox'>
			<li class='navs' id='whole'><a href='#'>전체</a></li>
		</div>
		<div class='navBox'>
			<li class='navs' id='concert'><a href='#'>공연</a></li>
		</div>
		<div class='navBox'>
			<li class='navs' id='exhibit'><a href='#'>전시</a></li>
		</div>
		<div class='navBox'>
			<li class='navs' id='festival'><a href='#'>축제행사</a></li>
		</div>
		<div class='navBox'>
			<li class='navs' id='movie'><a href='#'>영화</a></li>
		</div>
		<div class='navBox'>
			<li class='navs' id='other'><a href='#'>기타</a></li>
		</div>
		<div class='navBox'>
			<li class='navs' id='review'><a href='#'>후기게시판</a></li>
		</div>
	</ul>
	</header>

	<section> <p class='tag'>Edit My Info</p> 
	<img id='joinIcon' src='/img/icon_edit.png' />
	<div id='joinpage'>
		<!-- 	<form name="frm" action="/joinEdit.do" method="post"> -->

		<table>
			<tbody>
				<tr>
					<td colspan='4'></td>
				</tr>
				<tr>
					<td>아이디 :</td>
					<td>${list.id}</td>
					<td>별명 :</td>
					<td><input type="text" name="nickname" id="nickname"
						value="${list.nickname}"></td>
				</tr>
				<tr>
					<td>이름 :</td>
					<td>${list.name}</td>
					<td colspan='2'></td>
				</tr>
				<tr>
					<td>현재 비밀번호:</td>
					<td><input type="password" name="pw" id="pw"></td>
					<td colspan='2'><div id="checkPw">동일한 암호를 입력하세요.</div></td>
				</tr>
				<tr>
					<td>새 비밀번호:</td>
					<td><input type="password" name="npw" id="npw"></td>
					<td>새 비밀번호 확인:</td>
					<td><input type="password" name="npw2" id="npw2"
						onkeyup="checkPwd()"></td>
				</tr>   
				<tr>
					<td>이메일 :</td>
					<td><input type="text" name="email" id="email"
						value="${list.email}"></td>
					<td colspan='2'></td>
				</tr>
			</tbody>
		</table>
		<span id='usernickname'></span><br>

		<!-- 	</form> -->
	<input type="submit" id="edit" value="수정">&nbsp;&nbsp;
	<input type="button" onClick="history.back();" value="취 소">&nbsp;&nbsp;
	<input
		type="button" value="회원 탈퇴" id="del">
	</div>

	<!-- 	<form action="deleteMember.jsp" method="post"> -->
<%-- 	 <input	type="hidden" name="id" value="${list.id}"> --%>
	   <!-- 	</form> --> </section>

	<footer>
	</div>
	<div id='teamBox'>
		<img id='team1' src='/img/jenny.png' /> <span id='team1_i'>Jenny</span>
		<img id='team2' src='/img/puppy.png' /> <span id='team2_i'>Soo</span>
		<img id='team3' src='/img/nok.png' /> <span id='team3_i'>Woon</span>
	</div>
	</footer>
	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script>
		$(document).ready(
						function() {

							//login Info
							if ("${sessionScope.userId}" == null
									|| "${sessionScope.userId}" == "") {
								$('.MemberBoxMenu:nth-child(2n)').css(
										'display', 'none');
							} else {
								$('.MemberBoxMenu:nth-child(2n+1)').css(
										'display', 'none');
							}

							if ("${sessionScope.loginResult}" != null
									&& "${sessionScope.loginResult}" != "") {
								$(
										"<li class='MemberBoxMenu' style='display:inline-block;'>${sessionScope.loginResult}&nbsp;&nbsp;</li>")
										.insertBefore('.MemberBoxMenu:first');
							}

							//Boxes height

							var InnerBox = $('#InnerBox1').width();
							$('.EventContainer1').height(InnerBox - 80);
							
							var sectionWidth = $('section').width();
							var joinpageWidth = $('#joinpage').width();
							var joinpageHeight = $('#joinpage').height();
// 							console.log("joinheight"+joinpageHeight);
// 							console.log("joinwidth"+joinpageWidth);
// 							var width = $('table').width();
// 							var height = $('table').height();
// 							console.log("w"+width);
// 							console.log("h"+height);
							$('table').width(joinpageWidth*0.8);
							$('table').height(joinpageHeight*0.70);
// // 							$('tbody').width(joinpageWidth*0.5);
// // 							$('tbody').height(joinpageHeight*0.5);
							$('td').width(joinpageWidth*0.25);
// 							$('td').height(joinpageHeight*0.14);
// // 							$('tr').width(joinpageWidth*0.05);
// // 							$('tr').height(joinpageHeight*0.01);
							
							var headerheight = $('header').height();
							$('section').height(sectionWidth / 2);

							$('.tag').css("top",
									(sectionWidth / 4 - 341 / 2 + 155) + "px");
							$("footer").css("top",
									(sectionWidth / 2 + headerheight) + "px");

							$(window)
									.resize(
											function() {
												var sectionWidth = $('section').width();
												
												var joinpageWidth = $('#joinpage').width();
												var joinpageHeight = $('#joinpage').height();
												$('table').width(joinpageWidth*0.8);
												$('table').height(joinpageHeight*0.70);
// 												$('tbody').width(joinpageWidth*0.5);
// 												$('tbody').height(joinpageHeight*0.5);
												$('td').width(joinpageWidth*0.25);
												$('td').height(joinpageHeight*0.14);
// 												$('tr').width(joinpageWidth*0.05);
// 												$('tr').height(joinpageHeight*0.01);
												
// 												console.log("joinheight"+joinpageHeight);
// 												console.log("joinwidth"+joinpageWidth);
// 												var width = $('table').width();
// 												var height = $('table').height();
// 												console.log("w"+width);
// 												console.log("h"+height);
												
												var headerheight = $('header').height();
												$('section').height(sectionWidth / 2);

												$('.tag').css("top",(sectionWidth / 4 - 341 / 2 + 155)+ "px");
												$("footer").css("top",(sectionWidth / 2 + headerheight)	+ "px");

											});

							$('#MemberBox').on('click', '#logout', function() {
								location.href = "/logout.do";
							});

							$('#MemberBox').on('click', '#login', function() {
								location.href = "/login.do";
							});

							$('#MemberBox').on('click', '#joinMember',
									function() {
										location.href = "/join.do";
									});
							$('#MemberBox').on('click', '#mypage', function() {
								location.href = "/mypage.do";
							});

							//logo main과 연결
							$('header').on('click', '.logo', function() {
								location.href = "/Main.do";
							});

							// menu랑 연결하기		
							$("#whole").click(function() {
								location = "menuList.do?=";
							});

							$("#concert").click(function() {
								location = "menuList.do?category=1";
							});

							$("#exhibit").click(function() {
								location = "menuList.do?category=2";
								v
							});

							$("#festival").click(function() {
								location = "menuList.do?category=3";
							});

							$("#movie").click(function() {
								location = "menuList.do?category=4";
							});

							$("#other").click(function() {
								location = "menuList.do?category=5";
							});

							$("#review").click(function() {
								location = "reviewList.do?=";
							});

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
								$.ajax({
									url : "/joinEdit.do",
									type : "post",
									data : {
										"id" : "${list.id}",
										"pw" : $("#pw").val(),
										"pw2" : $("#npw2").val(),
										"nickname" : $("#nickname").val(),
										"email" : $("#email").val()
									},
									success : function(result) {
										if (result.result == 1) { // 비밀번호 일치할 때
											alert('수정 성공!');
										} else {
											alert('비밀번호가 일치하지 않습니다.');
											//				return false;
										}
									}
								});
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