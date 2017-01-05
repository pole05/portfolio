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
	
	<table class=tb4 align="center" border=1 width="800">
		<tr class=title2>
			<td align=center width="100">제목</td> <td colspan=5>${Review.title}</td>	
		</tr>
		<tr>
			<td align=center width="100">작성자</td> <td width="200">${Review.id}</td>
			<td align=center width="100">작성일</td> <td width="200">${Review.date}</td>
			<td align=center width="100">조회수</td> <td width="100">${Review.hit}</td>
		</tr>
		<tr class=content>
			<td colspan=6>
				${Review.content}
			</td>
		</tr>
	</table>
	
	<table class=tb5 align="center" width="800">
		<tr>
			<c:if test="${Review.title != '글이 삭제되었습니다.'}">
				<c:if test="${sessionScope.userId eq Review.id}">	
					<td align="left">
						<input class=btn2 type=button id="button4" onclick="button4_click();" value=삭제>
						<input class=btn3 type=button value=수정
						onClick='location.href = "reviewEdit.do?seq=${Review.seq}"'>
					</td>
				</c:if>
			<td align="right">
				<c:if test='${sessionScope.userId != null && sessionScope.userId != ""}'>
					<input class=btn5 type=button value=답변
					onClick='location.href="write.jsp?seq=${Review.parent_seq}&order=${Review.order}&depth=${Review.depth}"'>
				</c:if>
				<input class=btn4 type=button value=목록
				onClick='location.href="reviewList.do"'>
			</td>
			</c:if>
		</tr>
	</table>

	
	<br>
	<form>
		<table class=ctb width=800 align=center>
			<tr>
				<td id='csize'>
					댓글 수 &nbsp; ${csize}
					<input type=hidden id=csize>
				</td>
			</tr>
			<tr>
				<td>
					<textarea class=comment name="comment" style="width:500px;"></textarea>
				</td>
				<td width=300>
					<input type="hidden" name="seqNo" value="${param.seq}">
					<!-- 	게시물 번호 값도 db에 넣어줘야 하는데 사용자한테 보이면 안되니까 hidden -->
					<c:if test="${sessionScope.userId != null}">	
					<input class="btn8" type="button" value="댓글 쓰기">
					</c:if>
				</td>
			</tr>
		</table>
	</form>
	
	<table width=800 align=center>
		<tr>
			<td align="left">
				<ul id='CommentList'>
					<!-- 	댓글 들어가는 부분 -->
				</ul>
			</td>
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
		function button4_click() {
			var seq = "${param.seq}";
			
			alert("글을 삭제하시겠습니까?");;
			location.href = "reviewDelete.do?seq=" + seq;	
		}	
	
		function getCommentList() {
			$.ajax({
						url : "reviewCommentRead.do",
						type : "post",
						data : {
							"seq" : "${param.seq}"
						},
						success : function(result) {
							// 					console.log(result);
							result = JSON.parse(result);
							var html = "";
							var userId = "${sessionScope.userId}";
							
							for (var i = 0; i < result.length; i++) {
								html += "<li class='li'>";
								html += result[i].content;
								html += "&nbsp&nbsp&nbsp";
								html += "<span class='idtime'>";
								html += result[i].id;
								html += "&nbsp&nbsp&nbsp";
								html += result[i].time;
								html += "&nbsp&nbsp&nbsp";
							if(userId == result[i].id) {
								html += "<input type=button c_no=" + result[i].c_no + " class=btn9 value=삭제>";							
							}		
								html += "</span>";								
								html += "</li>";
							}
							$("ul#CommentList").html(html);							
						}
					});
		}

		$("form input:button").click(				
				function() {
			$.ajax({
				url : "reviewCommentAdd.jsp",
				type : "post",
				data : {
					"comment" : $("textarea[name=comment]").val(),
					"seqNo" : $("input[name=seqNo]").val()
				},
				success : function(result) {
					
					getCommentList(); // 댓글 조회
				}
			});
			return false;
		});

		$("ul").on(
				"click",
				".btn9",
				function() {
					var flag = confirm("삭제하시겠습니까?"); // confirm: true, flase로만 대답
					if (flag) { // true라면 삭제 jsp 실행
						var seq = "${param.seq}";
						var c_no = $(this).attr("c_no");
						location.href = "reviewCommentDelete.do?seq=" + seq + "&c_no=" + c_no;
					}
					getCommentList();
				});

		getCommentList();
	</script>


</body>
</html>