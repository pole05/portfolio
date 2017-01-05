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

	<section> <p class='tag'>Join Member</p>
	<img id='joinIcon' src='/img/icon_Memberjoin.png' />
	<div id='joinpage'>
	<form name="join" method="post" action="/join.do">
	<table>
			<tbody>
				<tr>
					<td colspan='4'><div id="checkMsg">아이디는 영문 소문자와 숫자 5자~15자 이하</div></td>
				</tr>
				<tr>
					<td>아이디*</td>
					<td><input type="text" name="id" id="id"></td>
					<td colspan='2'><div id="checkPw">동일한 암호를 입력하세요.</div></td>
				</tr>
				<tr>
					<td>비밀번호*</td>
					<td><input type="password" name="pw" id="pw"></td>
					<td>비밀번호 확인*</td>
					<td><input	type="password" id="pw_check" onkeyup="checkPwd()"></td>
				</tr>
				<tr>
					<td>이름*</td>
					<td><input type="text" name="name" id="pw"></td>
					<td colspan='2'></td>
				</tr>
				<tr>
					<td>이메일*</td>
					<td><input type="text" name="email" id="email"></td>
					<td colspan='2'></td>
				</tr>   
			</tbody>
		</table>
		<input type="submit" id="joinSubmit" value="가입">&nbsp;
<!-- 		<input type="button" onClick="history.back();" value="취 소"> -->
	</form>
	</div>
	</section>

	<footer>
	<div id='searchBox'>
	</div>
	<div id='teamBox'>
		<img id='team1' src='/img/jenny.png' /> <span id='team1_i'>Jenny</span>
		<img id='team2' src='/img/puppy.png' /> <span id='team2_i'>Soo</span>
		<img id='team3' src='/img/nok.png' /> <span id='team3_i'>Woon</span>
	</div>
	</footer>

	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>
	$(document).ready(function() {
		
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
		$('table').width(joinpageWidth*0.8);
		$('table').height(joinpageHeight*0.70);
		$('td').width(joinpageWidth*0.25);
// 		$('td').height(joinpageHeight*0.14);
		
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
							$('td').width(joinpageWidth*0.25);
// 							$('td').height(joinpageHeight*0.14);
							var headerheight = $('header').height();
							$('section').height(sectionWidth / 2);

							$('.tag').css("top",(sectionWidth / 4 - 341 / 2 +155)+ "px");
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
		//
		
		$("#id").keyup(function() {
//			var regex = /^[a-zA-Z0-9]*$/;
			var regex = /^[a-z0-9]*$/;
			var id = $(this).val();
			
			if(!regex.test(id)) {
				$("#checkMsg").html("<font color=red>영문 소문자와 숫자만 가능합니다</font>");
				return false;
			}			
			
			if(id == "" || id == null) {
				$("#checkMsg").html("아이디는 영문 소문자와 숫자 5자~15자 이하");				
			} else {
			$.ajax({
				type : "post",
				url : "id_check.jsp",
				data : {
					"id" : id
				},
				success : function(result) {
					var trans = JSON.parse(result);
					if (trans.re == 1) {
						$("#checkMsg").html("사용 불가 ㅠㅠ");
					} else {
						$("#checkMsg").html("사용 가능 ^^");
					}
				}
			});	
			}			
		});
		
		
		
		$("#pw_check").keyup(function() {
			var pw = $("#pw").val();
			var pw2 = $("#pw_check").val();
			if(pw == pw2) {
				$("#checkPw").html("");
			} else {
				$("#checkPw").html("동일한 암호를 입력하세요");
			}
		});
		
	
	$("#joinSubmit").click(function() {	// 보내기 버튼 눌렀을 때
		var id = $("#id").val();
		var pw = $("#pw").val();
		var pw_check = $("#pw_check").val();
		var name = $("#name").val();		
		var email = $("#email").val();
		
		if (id == "" || pw == "" || pw_check == "" || name == "" || email == "") {	
		// 필수값 안 썼을 때
				alert('필수값(*)을 입력해주세요');
				return false;
			} 
		});
	
	});
	</script>


</body>
</html>