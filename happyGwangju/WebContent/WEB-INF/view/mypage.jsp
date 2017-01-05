<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/js/jquery.min.js"></script>   
<link href="/css/mypage.css" rel="stylesheet"/>
<script>
	$(document).ready(function(){
		
		//login Info
		if("${sessionScope.userId}" == null || "${sessionScope.userId}"==""){
			$('.MemberBoxMenu:nth-child(2n)').css('display','none');
		}else{
			$('.MemberBoxMenu:nth-child(2n+1)').css('display','none');
		}
		
		if("${sessionScope.loginResult}" != null && "${sessionScope.loginResult}" != ""){
		$("<li class='MemberBoxMenu' style='display:inline-block;'>${sessionScope.loginResult}&nbsp;&nbsp;</li>").insertBefore('.MemberBoxMenu:first');
		}
		
		$('section').on('mouseover','.mypageDiv',function(){
			$(this).children('.Spans').css('display','inline');
			$(this).children('.bigIcon').css('opacity','0.3');
		});
		$('section').on('mouseout','.mypageDiv',function(){
			$(this).children('.Spans').css('display','none');
			$(this).children('.bigIcon').css('opacity','1');
		});
		  
		//sectionbox
		var sectionWidth = $('section').width();
		var headerheight = $('header').height();
		
		$('section').height(sectionWidth/2);
		
		$('.tag').css("top", (sectionWidth/4 - 341/2 + 155)+"px");
		
		$("footer").css("top",(sectionWidth/2+headerheight) + "px");
		
		$(window).resize(function(){
			var sectionWidth = $('section').width();
			var headerheight = $('header').height();
			$('section').height(sectionWidth/2);
			
			$('.tag').css("top", (sectionWidth/4 - 341/2 +155)+"px");
			
			$("footer").css("top",(sectionWidth/2+headerheight) + "px");
		});
		
		$('#MemberBox').on('click','#logout',function(){
			location.href="/logout.do";
		});
		
		$('#MemberBox').on('click','#login',function(){
			location.href="/login.do";
		});
		
		$('#MemberBox').on('click','#joinMember',function(){
			location.href="/join.do";
		});
		$('#MemberBox').on('click','#mypage',function(){
			location.href="/mypage.do";
		});
		
		//logo main과 연결
		$('header').on('click','.logo',function(){
			location.href="/Main.do";
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
		
	});
</script>
</head>
<body>
<header>
		<div id='upperBox'>
			<ul id='MemberBox'>
				<li class='MemberBoxMenu' id='login'><a href='#'>로그인</a>&nbsp;I&nbsp;</li>
				<li class='MemberBoxMenu' id='logout'>I&nbsp;<a href='#'>로그아웃</a>&nbsp;I&nbsp;</li>
				<li class='MemberBoxMenu' id='joinMember'><a href='#'>회원가입</a></li>
				<li class='MemberBoxMenu' id='mypage'><a href='#'>마이페이지</a></li>
			</ul>
		</div>
			<img class='logo' src='/img/happyLogo2.png'/>
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
		
		<section><p class='tag'>My page</p>
	
	<img class='icon' src='/img/icon_edit.png'/>
	<img class='icon' src='/img/icon_like.png'/>
	<img class='icon' src='/img/icon_document.png'/>
	
	<div id='editMe' class='mypageDiv'
	onClick='location.href="/joinEdit.do"'>   
		<span class='Spans'>내 정보수정 하기</span>
		<img class='bigIcon' src='/img/editMe.png'/>
	</div>
	
	<div id='myLike' class='mypageDiv'
	onClick='location.href="/heartList.do"'>   
		<span class='Spans'>내 찜목록 보기</span>
		<img class='bigIcon' src='/img/likes.png'/>
	</div>
	
	<div id='myWrite' class='mypageDiv'
	onClick='location.href="/myboard.do"'>
		<span class='Spans'>내 게시글 보기</span>
		<img class='bigIcon' src='/img/mywrite.png'/>
	</div>
	
	</section>
		
		<footer>
			<div id='teamBox'>
					<img id='team1' src='/img/jenny.png'/>
					<span id='team1_i'>Jenny</span>
					<img id='team2' src='/img/puppy.png'/>
					<span id='team2_i'>Soo</span>
					<img id='team3' src='/img/nok.png'/>
					<span id='team3_i'>Woon</span>
			</div>
		</footer>
	
</body>
</html>








