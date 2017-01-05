<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctx = request.getContextPath();    //콘텍스트명 얻어오기.
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SmartEditor</title>

<!-- SmartEditor를 사용하기 위해서 다음 js파일을 추가 (경로 확인) -->
<script type="text/javascript" src="<%=ctx %>/SE2/js/HuskyEZCreator.js" charset="utf-8"></script>
<!-- jQuery를 사용하기위해 jQuery라이브러리 추가 -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.min.js"></script>

<script type="text/javascript">
var oEditors = [];
$(function(){
      nhn.husky.EZCreator.createInIFrame({
          oAppRef: oEditors,
          elPlaceHolder: "ir1", //textarea에서 지정한 id와 일치해야 합니다. 
          //SmartEditor2Skin.html 파일이 존재하는 경로
          sSkinURI: "/SE2/SmartEditor2Skin.html",  
          htParams : {
              // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
              bUseToolbar : true,             
              // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
              bUseVerticalResizer : true,     
              // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
              bUseModeChanger : true,         
              fOnBeforeUnload : function(){
                   
              }
          }, 
          fOnAppLoad : function(){
              //기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
              oEditors.getById["ir1"].exec("PASTE_HTML", [""]);
          },
          fCreator: "createSEditor2"
      });
      
      //저장버튼 클릭시 form 전송
      $("#save").click(function(){
          oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
          $("#frm").submit();
      });
      
    //취소버튼 클릭시 form 전송
      $("#cancel").click(function(){
         location.href="reviewList.do"
      }); 
});
</script>

<link href="css/review.css" rel="stylesheet">
</head>
<body>

<form id="frm" action="reviewUpload.do" method="post" >
	<table class=tb6 align="center" width="800">
	        <tr>
	            <td align="center" width="100"><br>제목</td>
	            <td><br><input type="text" id="title" name="title" style="width:700px;" value="${list.title}"/></td>
	        </tr>
	        <tr>
	            <td align="center" width="100">내용</td>
	            <td>
	                <textarea rows="10" cols="30" id="ir1" name="content" style="width:700px; height:350px;">${list.content}</textarea>
	            </td>
	        </tr>
	        <tr>
	            <td align="right" colspan="2"><br>
	                <input type="hidden" name="seq" value="${param.seq}"/>
	                <input type="hidden" name="order" value="${param.order}"/>
	                <input type="hidden" name="depth" value="${param.depth}"/>
	                <input class=btn6 type="button" id="save" value="저장"/>
	                <input class=btn7 type="button" id="cancel" value="취소"/><br>
	            </td>
	        </tr>
	</table>
</form>
 
</body>
</html>