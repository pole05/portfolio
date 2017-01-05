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
<title>menuList</title>
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
		<section>
	<p class='tag'>
	<c:if test="${category eq null || category ==''}">
	Whole Events
	</c:if>
	<c:if test="${requestScope.category == 1}">
	Concerts
	</c:if>
	<c:if test="${requestScope.category == 2}">
	Exhibits
	</c:if>
	<c:if test="${requestScope.category == 3}">
	Festivals
	</c:if>
	<c:if test="${requestScope.category == 4}">
	Movies
	</c:if>
	<c:if test="${requestScope.category == 5}">
	Other Events
	</c:if>
	</p>
	<div id='MenuList'>
	<table class=tb2 align="center" width="800">	
		<tr>
			<td colspan="2" align="right">
			<a href=menuList.do?category=${requestScope.category}&order=starttime>최신순</a> / &nbsp;
			<a href=menuList.do?category=${requestScope.category}&order=heart_cnt>인기순</a> / &nbsp;
			<a href=menuList.do?category=${requestScope.category}&order=comment_cnt>댓글순</a></td>
		</tr>
	</table>

	<table class=tb3 align="center" border="1" width="800">

		<c:set var="i" value="0" />
		<c:set var="j" value="2" />

		<c:forEach items="${list}" var="list">
			<c:if test="{i%j == 0}">
				<tr>
			</c:if>

			<td>
				<table width=400>
					<tr>
						<td width=200 rowspan=2 class=poster>
							<div class="postercell">
								<a href="menuDetail.do?no=${list.no}">
								<c:choose>
									<c:when test="${list.p_poster_s != '' && list.p_poster_s != null && fn:contains(list.p_poster_s, 'http')}">
										<img src="${list.p_poster_s}" width=200 height=250 style="max-width:200px; vertical-align: middle;">
									</c:when>
									<c:otherwise>
										<img src="menuDownload.jsp?no=${list.no}&p_no=1" width=200 height=250 style="vertical-align: middle;">
									</c:otherwise>
								</c:choose>
								</a>
							</div>
						</td>
						
						<td><a href="menuDetail.do?no=${list.no}">
						
						<c:if test="${category eq null}">
						[${list.category_s}]
						</c:if>
						
								${list.name}</a> <br> <br> ${list.place} <br>
							${list.starttime} - ${list.endtime}<br></td>
					</tr>
					<tr>
						<td>

							<table align="right" width="200">
								<tr>
									<td>댓글 ${list.cnt}<br>
									</td>


									<td align="right">
										<img class="share" src="/img/twitter.png" width=25 height=25 
										url="menuDetail.do?no=${list.no}" title="${list.name}" 
										place="${list.place}" time="${list.starttime}~${list.endtime}">
										
										<% //TODO 찜 이미지 %>										
										<c:choose>
											<c:when test="${list.heartYn == 1}">
												<a href="#" onclick="deleteDB(this)">
												<img src="/img/Like_a.png" width="25" height="25" class="heart_a" h_no="${list.no}"></a>
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
										
										<div id="heart_c" style="display: inline"> ${list.heartCnt} </div></td>
								</tr>
							</table>
						</td>
					</tr>
				</table> <c:if test="${i%j == 1}">
					</tr>
				</c:if> <c:set var="i" value="${i+1}" />
		</c:forEach>

	</table>

	<!-- 여기까지 -->



	<c:set var="startPage" value="${startPage}" />
	<c:set var="endPage" value="${endPage}" />
	<c:set var="pageNum" value="${pageNum}" />
	<c:set var="totalPage" value="${totalPage}" />
	<c:set var="seqNo" value="${seqNo}" />

	<!-- 	카테고리 없고, order 없을 때 페이징 -->
	<c:if test="${(category == null) and (order == null)}">
	<table align="center" width='85%'>
		<tr>
			<td><c:if test="${pageNum > 10}">
					<c:set var="pre" value="${startPage- 1}" />
					<a href="/menuList.do?nowPage=${pre}">◀</a>
				</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
					<c:choose>
						<c:when test="${i != pageNum}">
       				[<a href="/menuList.do?nowPage=${i}">${i}</a>]
    					</c:when>
						<c:otherwise>
				    	 [<font color=red>${i}</font>]
    					</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${endPage < totalPage}">
					<c:set var="next" value="${endPage + 1}" />
					<a href="/menuList.do?nowPage=${next}">▶</a>
				</c:if>
				</td>
		</tr>
	</table>
	</c:if>
	
	<!-- 	카테고리 없고, order 있을 때 페이징 -->
	<c:if test="${(category == null) and (order != null)}">
	<table align="center" width='85%'>
		<tr>
			<td><c:if test="${pageNum > 10}">
					<c:set var="pre" value="${startPage- 1}" />
					<a href="/menuList.do?order=${order}&nowPage=${pre}">◀</a>
				</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
					<c:choose>
						<c:when test="${i != pageNum}">
       				[<a href="/menuList.do?order=${order}&nowPage=${i}">${i}</a>]
    					</c:when>
						<c:otherwise>
				    	 [<font color=red>${i}</font>]
    					</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${endPage < totalPage}">
					<c:set var="next" value="${endPage + 1}" />
					<a href="/menuList.do?order=${order}&nowPage=${next}">▶</a>
				</c:if>
				</td>
		</tr>
	</table>
	</c:if>
	
	<!-- 	페이징 카테고리 있고, order 없을 때 페이징 -->
	<c:if test="${(category != null) and (order == null)}">
	<table align="center" width='85%'>
		<tr>
			<td><c:if test="${pageNum > 10}">
					<c:set var="pre" value="${startPage- 1}" />
					<a href="/menuList.do?category=${category}&nowPage=${pre}">◀</a>
				</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
					<c:choose>
						<c:when test="${i != pageNum}">
       				[<a href="/menuList.do?category=${category}&nowPage=${i}">${i}</a>]
    					</c:when>
						<c:otherwise>
				    	 [<font color=red>${i}</font>]
    					</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${endPage < totalPage}">
					<c:set var="next" value="${endPage + 1}" />
					<a href="/menuList.do?category=${category}&nowPage=${next}">▶</a>
				</c:if>
				</td>
		</tr>
	</table>	
	</c:if>
	
	<!-- 	페이징 카테고리 있고, order 있을 때 페이징 -->
	<c:if test="${(category != null) and (order != null)}">
	<table align="center" width='85%'>
		<tr>
			<td><c:if test="${pageNum > 10}">
					<c:set var="pre" value="${startPage- 1}" />
					<a href="/menuList.do?category=${category}&order=${order}&nowPage=${pre}">◀</a>
				</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
					<c:choose>
						<c:when test="${i != pageNum}">
       				[<a href="/menuList.do?category=${category}&order=${order}&nowPage=${i}">${i}</a>]
    					</c:when>
						<c:otherwise>
				    	 [<font color=red>${i}</font>]
    					</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${endPage < totalPage}">
					<c:set var="next" value="${endPage + 1}" />
					<a href="/menuList.do?category=${category}&order=${order}&nowPage=${next}">▶</a>
				</c:if>
				</td>
		</tr>
	</table>	
	</c:if>
	
	<table width="800">
		<tr>
			<td align=center>
			<input id=box2 type=text name=text onkeypress="if(event.keyCode==13) {find(); return false;}"> 
			<input id=box3 type=button name=btn10 value='검색' onclick='find()'>
			</td>
			
			<td align="right">
				<c:if test="${sessionScope.userId eq 'admin'}">
					<input class=btn2 type=button value=글쓰기
					onClick='location.href="menuWrite.jsp"'>
				</c:if>
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
	<script type="text/javascript">
	
	function deleteDB(obj) {
		var $img = $(obj).children();
		$.ajax({
			url : "heartDelete.jsp",
			type : "post",
			data : {
				"no" : $img.attr("h_no")
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
				"no" : $img.attr("h_no")
			},
			success : function(result) {
				$img.src="/img/Like_a.png";
				alert('찜 목록에 추가되었습니다.');
				location.reload();
			}
		});
	}	
	
		$(".share").click(function() {
			var url = $(this).attr("url");
			var title = $(this).attr("title");
			var place = $(this).attr("place");
			var time = $(this).attr("time");
			
			location = "https://twitter.com/intent/tweet?text=" + title + "/ " + place + "/ " + time + "&url=http://localhost:8080/" + url;
		});

		
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
			$("footer").css("top",(sectionHeight*1.02+headerheight) + "px");
			
			$(window).resize(function(){
				var sectionHeight = $('section').height();
				var sectionHeight = $('section').height();
				$('.tag').css("top", (sectionHeight/2 - 341/2 +155)+"px");
				$("footer").css("top",(sectionHeight*1.02+headerheight) + "px");
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
		
		function find(){
			var text = $("#box2").val();
			var category = "${requestScope.category}";
			if(category != null && category != "") {
				location = "menuList.do?category=" + category + "&text=" + text;
			} else {
				location = "menuList.do?text=" + text;
			}
		}

	</script>

</body>
</html>