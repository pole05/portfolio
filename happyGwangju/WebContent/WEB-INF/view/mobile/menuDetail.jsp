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
<meta name="viewport" content="user-scalable=yew, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width, height=device-height">
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<title>HAPPY GWANGJU</title>
<link href="/css/mobile.css" rel="stylesheet">
</head>
<body>
	
	<table width="100%">
	<tr><td colspan="2" style="background: #d4e893; padding: 8px; margin: 8px; text-align: right;">
			<a href="http://168.131.22.123:8080/Main.do" target="_blank" title="웹버전 홈페이지로 이동합니다.">
			<img src="/img/home.png" width=22 height=22></a>
	</td></tr>
	
	<tr>
		<td colspan="2" height="145" style="background: #d4e893; padding: 0px; margin: 0px;">
			<center><img src="/img/mobile_top.png" height=50></center>
		</td>
	</tr>
	<tr>
		<td colspan="2" height="50" style="background: #e1e7e7; padding: 0px; margin: 0px;">
			<center><b>[${list.category_s}] ${list.name}</b></center>		
		</td>
	</tr>
	<%-- 	${list.no} --%>
	
	<tr><td colspan="2" height="10px"></td></tr>
	
	<tr>
		<td class="td1">추최</td>
		<td>${list.place}
		<c:set var="homepage" value="${list.homepage}" /> <c:choose>
					<c:when test="${fn:contains(homepage, 'http')}">
						<a href="${list.homepage}" target="_blank"> <img
							src="/img/home.gif" width="15" height="15"
							title="클릭하면 홈페이지로 이동합니다."></a>
					</c:when>
					<c:when test="${list.homepage eq null}">
						<!-- 				아무것도 안 보임 -->
					</c:when>
					<c:otherwise>
						<a href="'http://' + ${list.homepage}" target="_blank"> <img
							src="/img/home.gif" width="15" height="15"
							title="클릭하면 홈페이지로 이동합니다."></a>
					</c:otherwise>
				</c:choose>
			</td>
	</tr>
	
	<tr>
		<td class="td1">기간</td>
		<td>${list.starttime}~${list.endtime}</td>
	</tr>
	
	<tr>
		<td class="td1">시간</td>
		<td>${list.m_time}</td>
	</tr>
	
	<tr>
		<td class="td1">연락처</td>
		<td>${list.call}</td>
	</tr>

	<c:if test="${list.money != ''}">
		<tr>
			<td class="td1">입장료</td>
			<td>${list.money}</td>
		</tr>	
	</c:if>
	
	<c:if test="${list.content != ''}">	
	<tr><td colspan="2" align="center">	
	<br> ${list.content}
	<br>
	</td></tr>
	</c:if>
	
	<tr><td colspan="2" align="center">
	<br>
	<!-- 	첨부 이미지 소스 시작 -->
	<c:choose>
		<c:when test="${fn:contains(fList[0].p_poster_s, 'http')}">
			<c:forEach items="${fList}" var="fList">
				<img src="${fList.p_poster_s}"
					style="max-width: 80%; vertical-align: middle;">					
				<br> <br>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:forEach begin="1" end="${fn:length(fList)}" var="i">
				<img src="/menuDownload.jsp?no=${list.no}&p_no=${i}"
					style="max-width: 80%; vertical-align: middle;">
				<br> <br>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	</td></tr>
</table>

<!-- <br> -->
<%-- <center> --%>
<!-- <input type="button" onClick="history.back();" value="닫 기"> -->
<%-- </center> --%>
<!-- <br> -->


</body>
</html>