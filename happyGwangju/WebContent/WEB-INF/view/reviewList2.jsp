<%@page import="java.util.List"%>
<%@page import="com.jsp.dto.ReviewDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix ="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/css/review.css" rel="stylesheet">
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
		
		<section>
	<p class='tag'>Reviews</p>
	<div id='ReviewList'>
	
	<p class=review align=left>후기게시판</p>
		
	<br>
	<table class=tb1 align="center" border="1" width="800">
		<tr class=title1>
			<td align=center>번호</td>
			<td align=center>제목</td>
			<td align=center>작성자</td>
			<td align=center>작성일</td>
			<td align=center>조회수</td>
		</tr>

		<c:forEach var="item" items="${list}">
			<tr class=color>
				<td align=center width="50">${item.seq}</td>
				<td align="left" width="500">
					<c:forEach begin="0" end="${item.depth}">&nbsp;&nbsp;&nbsp;</c:forEach>
					<c:if test="${item.depth > 0}">&nbsp;┗[re]:</c:if>
					<a href=reviewDetail.do?seq=${item.seq}>${item.title}</a>
				</td>
				<td align=center width="100">${item.id}</td>
				<td align=center width="100">${item.date}</td>
				<td align=center width="50">${item.hit}</td>
			</tr>
		</c:forEach>
	</table>

	<c:set var="startPage" value="${startPage}" />
	<c:set var="endPage" value="${endPage}" />
	<c:set var="pageNum" value="${pageNum}" />
	<c:set var="totalPage" value="${totalPage}" />
	<c:set var="totalCount" value="${totalCount}" />
		
	<table class=tb2 align="center" width="800">
		<tr align="center">
			<td>						
			<c:if test="${pageNum > 10}">
					<c:set var="pre" value="${startPage - 1}" />
					<a href="/reviewList.do?nowPage=${pre}">◀</a>
			</c:if> 
			
			<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">			
				<c:choose>
					<c:when test="${i != pageNum}">
	       				[<a href="/reviewList.do?nowPage=${i}">${i}</a>]
	    			</c:when>
					<c:otherwise>
					     [${i}]
	    			</c:otherwise>
				</c:choose>
			</c:forEach> 
			
			<c:if test="${endPage < totalPage}">
					<c:set var="next" value="${endPage + 1}" />
					<a href="/reviewList.do?nowPage=${next}">▶</a>
			</c:if> 			
			</td>
		</tr>
	</table>

	
	<table class=tb3 align="center" width="800">
		<tr>
			<td align="right">
				<select name=search id=box1 style="height:25px;">
					<option value='title' >제목</option>
					<option value='content'>내용</option>
					<option value='id'>아이디</option>
				</select>
			</td>	
			<td align="left">	
				<input id=box2 type=text name=text onkeypress='if(event.keyCode==13) {find(); return false;}'>
				<input id=box3 type=button name=btn10 value='검색' onclick='find()'>
			</td>
			
	<c:if test="${sessionScope.userId != null}">		
			<td align="right">
				<input class=btn1 type=button value=글쓰기
				onClick='location.href="write.jsp"'>
			</td>
	</c:if>
		</tr>
	</table>
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

	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
	
	function find(){	// 검색
		var searchtype = $("#box1").val();
		var text = $("#box2").val();		
		location = "reviewList.do?searchtype=" + searchtype + "&text=" + text;
	}
	
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
		
		//section header boxes size
		var headerheight = $('header').height();
		var sectionHeight = $('section').height();
		$('.tag').css("top", (sectionHeight/2 - 341/2 +155)+"px");
		$("footer").css("top",(sectionHeight*1.03+headerheight) + "px");
		
		$(window).resize(function(){
			var sectionHeight = $('section').height();
			var sectionHeight = $('section').height();
			$('.tag').css("top", (sectionHeight/2 - 341/2 +155)+"px");
			$("footer").css("top",(sectionHeight*1.03+headerheight) + "px");
		});
		
		
		if("${requestScope.result}" != null && "${requestScope.result}" != ""){
			alert("${requestScope.result}");
		}
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
// 		$("#box3").click(function() {
// 			var searchtype = $("#box1").val();
// 			var text = $("#box2").val();
			
// 			location = "reviewList.do?searchtype=" + searchtype + "&text=" + text;
// 		});
	</script>

</body>
</html>