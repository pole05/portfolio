<%@page import="com.jsp.dto.MenuPosterDto"%>
<%@page import="com.jsp.model.MenuPosterDao"%>
<%@page import="com.jsp.dto.MenuDto"%>
<%@page import="com.jsp.model.MenuDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/css/menu.css" rel="stylesheet">
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
		
		<section><p class='tag'>${list.category_s}</p>

	<div id='MenuList'>
	<table class=tb1 width=800 align=center border=1>
		<tr>
			<td width=100>${list.category_s}명</td>
			<td width=300>${list.name}</td>
			
			<td width=300 colspan=2>
				<table align=right>
					<tr>
						<td>						
							<img class="share" src="/img/twitter.png" width=25 height=25 
							url="menuDetail.do?no=${list.no}" title="${list.name}" 
							place="${list.place}" time="${list.starttime}~${list.endtime}">
							
							<c:choose>
								<c:when test="${list.heartYn == 1}">
									<a href="#" onclick="deleteDB(this)">
									<img src="/img/Like_a.png" width="25" height="25" class="heart_a" h_no="${list.no}">
									</a>
								</c:when>											
								<c:otherwise>
									<c:choose>
										<c:when test="${sessionScope.userId eq null}">														
											<img src="/img/Like_b.png" width="25" height="25" class="heart_b" h_no="${list.no}">
										</c:when>
										<c:otherwise>
											<a href="#" onclick="insertDB(this)">
											<img src="/img/Like_b.png" width="25" height="25" class="heart_b" h_no="${list.no}"></a>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose> 						
						</td>
					</tr>
				</table>
			</td>			
		</tr>

		<tr>
			<td width=100>기간</td>
			<td width=300>${list.starttime}~${list.endtime}</td>
			
			<td width=100>시간</td>
			<td width=300>${list.m_time}</td>
		</tr>

		<tr>
			<td width=100>장소</td>
			<td width=300>${list.place}</td>
			<td width=100>주최</td>
			<td width=300>${list.host}</td>
		</tr>

		<tr>
			<td width=100>연락처</td>
			<td width=300>${list.call}</td>
			<td width=100>입장료</td>
			<td width=300>${list.money}</td>
		</tr>

		<tr>
			<td width=100>홈페이지</td>
			<td width=300>
					<c:set var="homepage" value="${list.homepage}"/>
					<c:choose>
						<c:when test="${fn:contains(homepage, 'http')}">
							<a href="${list.homepage}" target="_blank">${list.homepage}</a>
						</c:when>
						<c:when test="${list.homepage eq null}">
							<!-- 				아무것도 안 보임 -->
						</c:when>
						<c:otherwise>
							<a href="'http://' + ${list.homepage}" target="_blank">
							${list.homepage}</a>
						</c:otherwise>
					</c:choose>
			</td>
			<td colspan=2></td>
		</tr>

		<tr>
			<td colspan=4>${list.content}<br><br>
			<!--		첨부 이미지 넣기 --> 
			<c:if test="${fn:length(fList) != 0}">
					<c:forEach begin="1" end="${fn:length(fList)}" var="i">						
						<img src="menuDownload.jsp?no=${list.no}&p_no=${i}" style="max-width:799px;"><br>
						<br>
					</c:forEach>
			</c:if>

			</td>
		</tr>
	</table>

	<table class=tb2 width=800 align=center>
		<tr>
			<td align=right>
				
				<c:if test="${sessionScope.userId eq 'admin'}">
				<a href="menuEdit.do?no=${list.no}">수정</a> / 
				<a id=del> 삭제</a> / 
				</c:if>
				
				<a href=menuList.do> 목록</a>
			</td>
		</tr>
	</table>

	<br>
	<c:if test="${sessionScope.userId != null}">
	<form method="post" action="addComment.jsp">
		<table class=ctb width=800 align=center>
			<tr>
			<!-- 댓글수 -->
				<td>
					<div id="csize">댓글</div>
				</td>
			</tr>
			
			<tr>
				<td width=500>
					<!-- 댓글창 -->
					<textarea class=comment name="comment" id="comment" style="width:500px;"></textarea> <br>
				</td>
				<td width=300 align="left">
					<input type="hidden" name="seqNo" value="${list.no}">
					<!-- 	게시물 번호 값도 db에 넣어줘야 하는데 사용한테 보이면 안되니까 hidden --> 
					<input class="btn" id="btn" type="button" value="댓글 쓰기">
				</td>
			</tr>
		</table>
	</form>
	</c:if>

	<br>
<!-- 	여기부터 -->
	<table width=800 id='CommentList'> <%//TODO %>
			<!-- 		댓글 들어가는 부분 -->
	</table>
<!-- 	여기까지 -->
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
		
		getCommentList();
		countComment();
		
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
		
	function getCommentList() {		
		$.ajax({
					url : "menuCommentRead.do",
					type : "post",
					data : {
						"no" : "${list.no}"
					},
					success : function(result) {						
						result = JSON.parse(result);
						var html = "";
						var userId = "${id}";
						
						for (var i = 0; i < result.length; i++) {
							html += "<tr><td>";
							html += "<table width=600><tr>";
							html += "<td width=380 align=left>"; 
							html += result[i].content;
							if(userId == result[i].id || userId == "admin") {
								html += "&nbsp;<a href=# onclick=co_del(this) c_no=" + result[i].c_no + "><font color=red>x</font></a>";
							}
							html += "</td>";
							html += "<td align=right>";
							html += "<font color=green>" + result[i].id + "</font> &nbsp;&nbsp;&nbsp;";
							html += result[i].time;							
							html += "</td></tr>";
							html += "</table>";
							html += "</td></tr>";
						}
						$("table#CommentList").html(html);							
					}
				});
	}
	
	
	function co_del(obj) {
		var flag = confirm("삭제하시겠습니까?");
		if (flag) {
			$.ajax({
				url : "menuCommentRemove.jsp",
				type : "post",
				data : {
					"no" : "${list.no}",
					"c_no" : $(obj).attr("c_no")
				},
				success : function(result) {					
					getCommentList(); // 댓글 조회
					countComment();
				}		
			});
		}
	}
	
	function countComment() {
		$.ajax({
			url : "countComment.jsp",
			type : "post",
			data : {
				"no" : "${list.no}"
			},
			success : function(result) {
				var html = "댓글 " + result.size;
				$("#csize").html(html);
			}
		});					
	}
	
	// 찜하트
	function deleteDB(obj) {
		var $img = $(obj).children();
		$.ajax({
			url : "heartDelete.jsp",
			type : "post",
			data : {
				"no" : "${list.no}"
			},
			success : function(result) {
				$img.src="/img/Like_b.png";
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
				"no" : "${list.no}"
			},
			success : function(result) {
				$img.src="/img/Like_a.png";
				alert('찜 목록에 추가되었습니다.');
				location.reload();
			}
		});
	}
	
	$("#btn").click(		// 댓글 쓰기 버튼 눌렀을 때	
			function() {
				var comment = $("#comment").val();				
				if (comment == "") {	// 댓글 내용 없을 때				
					alert('댓글 내용을 써 주세요. ^^');
					return false;
				}				
		$.ajax({
			url : "menuCommentAdd.jsp",
			type : "post",
			data : {
				"comment" : $("textarea[name=comment]").val(),
				"seqNo" : $("input[name=seqNo]").val(),
				"category" : "${list.category}",
				"csize" : "${csize}"
			},
			success : function(result) {
				getCommentList(); // 댓글 조회
				countComment();
				$('form').each(function(){	// 댓글 등록되고 난 후에 폼 지우기
					    this.reset();
				});
			}		
			
		});
		return false;
	});
	
	$(".share").click(function() {
		var url = $(this).attr("url");
		var title = $(this).attr("title");
		var place = $(this).attr("place");
		var time = $(this).attr("time");
		
		location = "https://twitter.com/intent/tweet?text=" + title + "/ " + place + "/ " + time + "&url=http://localhost:8080/" + url;
	});

	$("#del").click(	// 글 삭제
			function() {
				var flag = confirm("삭제하시겠습니까?"); // confirm: true, flase로만 대답
				if (flag) { // true라면 삭제 jsp 실행
					var no = "${list.no}";
					location.href = "menuDelete.jsp?no=" + no;						
				}
				getCommentList();
				countComment();
			});
	
	</script>
</body>
</html>