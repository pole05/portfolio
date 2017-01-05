<%@page import="com.jsp.dto.MenuCommentDto"%>
<%@page import="com.jsp.model.MenuCommentDao"%>
<%@page import="com.jsp.dto.MenuDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>heartList</title>
<link href="/css/heartList.css" rel="stylesheet" />
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
	<p class='tag'>My Heart List</p>
	<div id='heartList'>
		<div class='Box'>
			<c:forEach items="${list}" var="list">
				<c:if test="${list.p_poster_s != 'no_cnt.gif'}">
					<a href="menuDetail.do?no=${list.no}">
				</c:if>
				<c:choose>
						<c:when test="${list.p_poster_s != '' && list.p_poster_s != null && fn:contains(list.p_poster_s, 'http')}">
							<img class='posters' src="${list.p_poster_s}" width=18%>
						</c:when>
						<c:otherwise>
							<img class='posters' src="menuDownload.jsp?no=${list.no}&p_no=1" width=18%>
						</c:otherwise>
						</c:choose>
				<c:if test="${list.p_poster_s != 'no_cnt.gif'}">
					</a>
				</c:if>
				<img class='posterbox' src='/img/heartbox.png' width=19%/>
			</c:forEach>
				<table id='posternames'>
				<tbody>
					<tr>
						<c:forEach items="${list}" var="list">
							<td width='30%'>${list.name}<br></td>
						</c:forEach>
					</tr>
				</tbody>
				</table>
		</div>




	<c:set var="startPage" value="${startPage}" />
	<c:set var="endPage" value="${endPage}" />
	<c:set var="pageNum" value="${pageNum}" />
	<c:set var="totalPage" value="${totalPage}" />
	<c:set var="seqNo" value="${seqNo}" />

	<br>
	<table align="center" width="80%" id='pagingNums'>
		<tr>
			<td><c:if test="${pageNum > 10}">
					<c:set var="pre" value="${startPage- 1}" />
					<a href="/heartList.do?nowPage=${pre}">◀</a>
				</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
					<c:choose>
						<c:when test="${i != pageNum}">
       				[<a href="/heartList.do?nowPage=${i}">${i}</a>]
    			</c:when>
						<c:otherwise>
				     [${i}]
    			</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${endPage < totalPage}">
					<c:set var="next" value="${endPage + 1}" />
					<a href="/heartList.do?nowPage=${next}">▶</a>
				</c:if>
				</td>
		</tr>
	</table>
	</div>
	</section>
	<footer>
					<img id='team1' src='/img/jenny.png'/>
					<span id='team1_i'>Jenny</span>
					<img id='team2' src='/img/puppy.png'/>
					<span id='team2_i'>Soo</span>
					<img id='team3' src='/img/nok.png'/>
					<span id='team3_i'>Woon</span>
		</footer>
	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
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
		
		//section header boxes size
		var headerheight = $('header').height();
		var sectionHeight = $('section').height();
		var postersBottom = $('.posters').css("bottom");
		$('.posterbox').css("bottom",postersBottom);
		
		var postersHeight1 = $('.Box>a:nth-child(1)>.posters').height();
		var postersBoxHeight = $('.posterbox').height();
		var postersHeight2 = $('.Box>a:nth-child(3)>.posters').height();
		var postersHeight3 = $('.Box>a:nth-child(5)>.posters').height();
		
		console.log("postersHeight1"+postersHeight1);
		console.log("postersHeight2"+postersHeight2);
		console.log("postersHeight3"+postersHeight3);
		console.log("postersBoxHeight"+postersBoxHeight);
		console.log("postersBottom"+postersBottom);
		if(postersHeight1 < postersBoxHeight){
			console.log("poster1의 height"+postersHeight1);
			console.log("posterbox의 height"+postersBoxHeight);
			var sum = postersBoxHeight-(postersHeight1)/2;
			var prebottom = parseInt(postersBottom);
			$('.Box>a:nth-child(1)>.posters').css("bottom", prebottom + sum);
		}
		if(postersHeight2 < postersBoxHeight){
			console.log("poster2의 height"+postersHeight2);
			console.log("posterbox의 height"+postersBoxHeight);
			var sum = postersBoxHeight-(postersHeight2)/2;
			var prebottom = parseInt(postersBottom);
			$('.Box>a:nth-child(3)>.posters').css("bottom", prebottom + sum);
		}
		if(postersHeight3 < postersBoxHeight){
			console.log("poster3의 height"+postersHeight3);
			console.log("posterbox의 height"+postersBoxHeight);
			var sum = postersBoxHeight-(postersHeight3)/2;
			var prebottom = parseInt(postersBottom);
			$('.Box>a:nth-child(5)>.posters').css("bottom", prebottom + sum);
		}
		$('.tag').css("top", (sectionHeight/2 - 341/2 +155)+"px");
		$("footer").css("top",(sectionHeight*1.02+headerheight) + "px");
		
		$(window).resize(function(){
			var sectionHeight = $('section').height();
			var sectionHeight = $('section').height();
			var postersBottom = $('.posters').css("bottom");
			$('.posterbox').css("bottom",postersBottom);
			var postersHeight = $('.posters').height();
			var postersBoxHeight = $('.posterbox').height();
			console.log("poster의 bottom"+postersBottom);
			console.log("poster의 height"+postersHeight);
			console.log("posterbox의 height"+postersBoxHeight);
			if(postersHeight < postersBoxHeight){
				$('.posters').css("bottom", postersBottom + postersBoxHeight-postersHeight/2);
			}
			$('.tag').css("top", (sectionHeight/2 - 341/2 +155)+"px");
			$("footer").css("top",(sectionHeight*1.02+headerheight) + "px");
		});
		
		if("${fn:length(requestScope.list)}"<3){
			$("#posternames td:last").css("width","30*${fn:length(requestScope.list)}");
		}
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
	</script>
</body>
</html>