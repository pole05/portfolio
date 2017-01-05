<%@page import="com.jsp.dto.MenuCommentDto"%>
<%@page import="com.jsp.model.MenuCommentDao"%>
<%@page import="com.jsp.dto.MenuDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>HappyGwangju</title>
<script src="/js/jquery.min.js"></script>   
<link href="/css/MainCSS2.css" rel="stylesheet"/>
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
		
		//Boxes height
		
		var InnerBox = $('#InnerBox1').width();
		$('.EventContainer1').height(InnerBox-80);
		
		var sectionWidth = $('section').width();
		var headerheight = $('header').height();
		$('section').height(sectionWidth/2);
		
		if(sectionWidth<=1120){
			$('tr:not(:nth-child(1))').css('display','none');
			$('tr:nth-child(5)').css('display','inline-block');
		}else{
			$('tr:not(:nth-child(1))').css('display','inline-block');
		}
		
		$("footer").css("top",(sectionWidth/2+headerheight) + "px");
		
		$(window).resize(function(){
			var InnerBox = $('#InnerBox1').width();
			$('.EventContainer1').height(InnerBox-80);
			
			var sectionWidth = $('section').width();
			var headerheight = $('header').height();
			$('section').height(sectionWidth/2);
			
			if(sectionWidth<=1120){
				$('tr:not(:nth-child(1))').css('display','none');
				$('tr:nth-child(5)').css('display','inline-block');
			}else{
				$('tr:not(:nth-child(1))').css('display','inline-block');
			}
			
			$("footer").css("top",(sectionWidth/2+headerheight) + "px");
			  
		});
		
		if("${requestScope.logout}" != null && "${requestScope.logout}" != ""){
			alert("${requestScope.logout}");
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
		
		
			$(".share_icon").click(function() {
				var url = $(this).attr("url");
				var title = $(this).attr("title");
				var place = $(this).attr("place");
				var time = $(this).attr("time");
				
				if (title != "" && title != null) {
					location = "https://twitter.com/intent/tweet?text=" + title + "/ " + place + "/ " + time + "&url=http://localhost:8080/" + url;
				}
			});			
		
	});
	
	function deleteDB(obj) {
		var $img = $(obj).children();
		$.ajax({
			url : "heartDelete.jsp",
			type : "post",
			data : {
				"no" : $img.attr("h_no")
			},
			success : function(result) {
				$img.src = "/img/Like_b.png";
				location.reload();
			}
		});
	}

	function insertDB(obj) {
		var $img = $(obj).children();
		$.ajax({
			url : "heartInsert.jsp",
			type : "post",
			data : {
				"no" : $img.attr("h_no")
			},
			success : function(result) {
				$img.src = "/img/Like_a.png";
				alert('찜 목록에 추가되었습니다.');
				location.reload();
			}
		});
	}
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
		
		<section>
			<div id='MainBox1'>
				<img id='InnerBox1' src='/img/mainBox6.png'/>
				<div class='EventContainer1'>
					<img class='Events1_i' src='/img/innerBox1.png'/>
							<a href="menuDetail.do?no=${list[0].no}">
								<c:choose>
									<c:when test="${list[0].p_poster_s !='' && list[0].p_poster_s != null && fn:contains(list[0].p_poster_s, 'http')}">
										<img class='Events1' src="${list[0].p_poster_s}">
									</c:when>
									<c:otherwise>
										<img class='Events1' src="menuDownload.jsp?no=${list[0].no}&p_no=1">
									</c:otherwise>
								</c:choose>
								</a>
					<img class='Events2_i' src='/img/innerBox2.png'/>
					<img class='Events2_l' src='/img/balloon1.png'/>  
					<div class='Events2' id='e2'>
						<table>
							<tbody>
							<tr>
								<td colspan='2' class='Ltime'><a href='/menuDetail.do?no=${requestScope.list[0].no}'>${requestScope.list[0].name}</a></td>
							</tr>
							<tr>
								<td>기간</td><td>${requestScope.list[0].endtime}~${requestScope.list[0].starttime}</td>
							</tr>
							<tr>
								<td >시간</td><td>${requestScope.list[0].m_time}</td>
							</tr>
							<tr>
								<td>장소</td><td>${requestScope.list[0].place}</td>
							</tr>
							<tr >
								<td>댓글수</td><td>${requestScope.list[0].cnt}</td>
							</tr>
							</tbody>
							</table>
						<img class='share_icon' src='/img/twitter.png'
							url='menuDetail.do?no=${requestScope.list[0].no}' title='${requestScope.list[0].name}' 
							place='${requestScope.list[0].place}' time='${requestScope.list[0].starttime}~${requestScope.list[0].endtime}'/>
				<!-- 				찜 이미지 시작 -->
				<c:choose>
					<c:when test="${requestScope.list[0].heartYn == 1}">
						<a href="#" onclick="deleteDB(this)">
						<img src="/img/Like_a.png" width="25" height="25" 
							class="heart_a" h_no="${requestScope.list[0].no}">
						</a>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${sessionScope.userId eq null}">
								<img src="/img/Like_b.png" width="25" height="25"
									class="heart_b" h_no="${requestScope.list[0].no}">
							</c:when>
							<c:otherwise>
								<a href="#" onclick="insertDB(this)">
								<img src="/img/Like_b.png" width="25" height="25" class="heart_b"
									h_no="${requestScope.list[0].no}"></a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<!-- 				찜 이미지 끝 -->

					</div>
					<img class='Events3_i' src='/img/innerBox4.png'/>
					<img class='Events3_l' src='/img/balloon2.png'/>
					<div class='Events3' id='e3'>
						<table>
							<tbody>
							<tr>
								<td class='Ltime'><a href='/menuDetail.do?no=${requestScope.list[1].no}'>${requestScope.list[1].name}</a></td>
							</tr>
							<tr>
								<td>기간</td><td>${requestScope.list[1].endtime}~${requestScope.list[1].starttime}</td>
							</tr>
							<tr>
								<td>시간</td><td>${requestScope.list[1].m_time}</td>
							</tr>
							<tr>
								<td>장소</td><td>${requestScope.list[1].place}</td>
							</tr>
							<tr>
								<td>댓글수</td><td>${requestScope.list[1].cnt}</td>
							</tr>
							</tbody>
							</table>
						<img class='share_icon' src='/img/twitter.png'
							url='menuDetail.do?no=${requestScope.list[1].no}' title='${requestScope.list[1].name}' 
							place='${requestScope.list[1].place}' time='${requestScope.list[1].starttime}~${requestScope.list[1].endtime}'/>	
<!-- 				찜 이미지 시작 -->
<!-- 						<img class='like_icon' src='/img/Like.png'/> -->
				<c:choose>
					<c:when test="${requestScope.list[1].heartYn == 1}">
						<a href="#" onclick="deleteDB(this)">
						<img src="/img/Like_a.png" width="25" height="25" class="heart_a" h_no="${requestScope.list[1].no}"></a>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${sessionScope.userId eq null}">
								<img src="/img/Like_b.png" width="25" height="25"
									class="heart_b" h_no="${requestScope.list[1].no}">
							</c:when>
							<c:otherwise>
								<a href="#" onclick="insertDB(this)"> <img
									src="/img/Like_b.png" width="25" height="25" class="heart_b"
									h_no="${requestScope.list[1].no}"></a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
<!-- 				찜 이미지 끝 -->

			</div>
					<img class='Events4_i' src='/img/innerBox3.png'/>
						<a href="menuDetail.do?no=${list[1].no}">
								<c:choose>
									<c:when test="${list[1].p_poster_s !='' && list[1].p_poster_s != null && fn:contains(list[1].p_poster_s, 'http')}">
										<img class='Events4' src="${list[1].p_poster_s}">
									</c:when>
									<c:otherwise>
										<img class='Events4' src="menuDownload.jsp?no=${list[1].no}&p_no=1">
									</c:otherwise>
								</c:choose>
						</a>
				</div>
			</div>  
			<div id='MainBox2'>
				<img id='InnerBox2' src='/img/mainBox5.png'/>
					<div class='EventContainer1'>
						<img class='Events1_i' src='/img/innerBox1.png'/>
							<a href="menuDetail.do?no=${list[2].no}">
								<c:choose>
									<c:when test="${list[2].p_poster_s !='' && list[2].p_poster_s != null && fn:contains(list[2].p_poster_s, 'http')}">
										<img class='Events1' src="${list[2].p_poster_s}" ">
									</c:when>
									<c:otherwise>
										<img class='Events1' src="menuDownload.jsp?no=${list[2].no}&p_no=1">
									</c:otherwise>
								</c:choose>
							</a>
						<img class='Events2_i' src='/img/innerBox2.png'/>
						<img class='Events2_l' src='/img/balloon1.png'/>
						<div class='Events2' id='e6'>
							<table>
							<tbody>
							<tr>
								<td class='Ltime'><a href='/menuDetail.do?no=${requestScope.list[2].no}'>${requestScope.list[2].name}</a></td>
							</tr>
							<tr>
								<td>기간</td><td>${requestScope.list[2].endtime}~${requestScope.list[2].starttime}</td>
							</tr>
							<tr>
								<td>시간</td><td>${requestScope.list[2].m_time}</td>
							</tr>
							<tr>
								<td>장소</td><td>${requestScope.list[2].place}</td>
							</tr>
							<tr>
								<td>댓글수</td><td>${requestScope.list[2].cnt}</td>
							</tr>
							</tbody>
							</table>
							<img class='share_icon' src='/img/twitter.png'
							url='menuDetail.do?no=${requestScope.list[2].no}' title='${requestScope.list[2].name}' 
							place='${requestScope.list[2].place}' time='${requestScope.list[2].starttime}~${requestScope.list[2].endtime}'/>
							
				<!-- 				찜 이미지 시작 -->
				<c:choose>
					<c:when test="${requestScope.list[2].heartYn == 1}">
						<a href="#" onclick="deleteDB(this)">
						<img src="/img/Like_a.png" width="25" height="25" 
							class="heart_a" h_no="${requestScope.list[2].no}">
						</a>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${sessionScope.userId eq null}">
								<img src="/img/Like_b.png" width="25" height="25"
									class="heart_b" h_no="${requestScope.list[2].no}">
							</c:when>
							<c:otherwise>
								<a href="#" onclick="insertDB(this)">
								<img src="/img/Like_b.png" width="25" height="25" class="heart_b"
									h_no="${requestScope.list[2].no}"></a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<!-- 				찜 이미지 끝 -->
						</div>
						<img class='Events3_i' src='/img/innerBox4.png'/>
						<img class='Events3_l' src='/img/balloon2.png'/>
						<div class='Events3' id='e7'>  
							<table>
							<tbody>
							<tr>
								<td class='Ltime'><a href='/menuDetail.do?no=${requestScope.list[3].no}'>${requestScope.list[3].name}</a></td>
							</tr>
							<tr>
								<td>기간</td><td>${requestScope.list[3].endtime}~${requestScope.list[3].starttime}</td>
							</tr>
							<tr>
								<td>시간</td><td>${requestScope.list[3].m_time}</td>
							</tr>
							<tr>
								<td>장소</td><td>${requestScope.list[3].place}</td>
							</tr>
							<tr>
								<td>댓글수</td><td>${requestScope.list[3].cnt}</td>
							</tr>
							</tbody>
							</table>
							<img class='share_icon' src='/img/twitter.png'
							url='menuDetail.do?no=${requestScope.list[3].no}' title='${requestScope.list[3].name}' 
							place='${requestScope.list[3].place}' time='${requestScope.list[3].starttime}~${requestScope.list[3].endtime}'/>
				<!-- 				찜 이미지 시작 -->
				<c:choose>
					<c:when test="${requestScope.list[3].heartYn == 1}">
						<a href="#" onclick="deleteDB(this)">
						<img src="/img/Like_a.png" width="25" height="25" 
							class="heart_a" h_no="${requestScope.list[3].no}">
						</a>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${sessionScope.userId eq null}">
								<img src="/img/Like_b.png" width="25" height="25"
									class="heart_b" h_no="${requestScope.list[3].no}">
							</c:when>
							<c:otherwise>
								<a href="#" onclick="insertDB(this)">
								<img src="/img/Like_b.png" width="25" height="25" class="heart_b"
									h_no="${requestScope.list[3].no}"></a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<!-- 				찜 이미지 끝 -->
						</div>
						<img class='Events4_i' src='/img/innerBox3.png'/>
						<a href="menuDetail.do?no=${list[3].no}">
								<c:choose>
									<c:when test="${list[3].p_poster_s !='' && list[3].p_poster_s != null && fn:contains(list[3].p_poster_s, 'http')}">
										<img class='Events4' src="${list[3].p_poster_s}">
									</c:when>
									<c:otherwise>
										<img class='Events4' src="menuDownload.jsp?no=${list[3].no}&p_no=1">
									</c:otherwise>
								</c:choose>
						</a>
					</div>
			</div>
			</section>
			
		<footer>
		
			<div id='app'>
				<img id='appb' src='/img/app.jpg' style="left:100px; bottom: 5px; position:absolute;"/>
			</div>
		
			<div id='teamBox'>
					<img id='team1' src='/img/jenny.png'/>
					<span id='team1_i'><b>Jenny</b></span>
					<img id='team2' src='/img/puppy.png'/>
					<span id='team2_i'><b>Soo</b></span>
					<img id='team3' src='/img/nok.png'/>
					<span id='team3_i'><b>Woon</b></span>
			</div>
		</footer>
</body>
</html>