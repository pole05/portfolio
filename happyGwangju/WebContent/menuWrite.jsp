<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/css/menuWrite.css" rel="stylesheet"/>
</head>
<body>
	<script src='http://jquery-multifile-plugin.googlecode.com/svn/trunk/jquery.form.js' type="text/javascript" language="javascript"></script>
	<script src='http://jquery-multifile-plugin.googlecode.com/svn/trunk/jquery.MetaData.js' type="text/javascript" language="javascript"></script>
	<script src='http://jquery-multifile-plugin.googlecode.com/svn/trunk/jquery.MultiFile.js' type="text/javascript" language="javascript"></script>
	<script src='http://jquery-multifile-plugin.googlecode.com/svn/trunk/jquery.blockUI.js' type="text/javascript" language="javascript"></script> 
	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
	
	<form action="upload.jsp" method="post" enctype="multipart/form-data">
	<!-- 	<form name="gForm" action="upload.do" enctype="multipart/form-data"	method="post"> -->
	<br>
		<table width=800 align=center class=main>
			<tr>
				<td width=100>제목</td>
				<td width=300><input type="text" name="name" class="input1"></td>
				<td width=100>분류</td>
				<td width=300><select name=category class="input1">
						<option value=1>공연</option>
						<option value=2>전시</option>
						<option value=3>축제행사</option>
						<option value=4>영화</option>
						<option value=5>기타</option>
				</select></td>
			</tr>

			<tr>
				<td>기간</td>
				<td><input type="text" name="starttime" style="width: 100px;" class="input1">-
				<input type="text" name="endtime" style="width: 100px;" class="input1"></td>
				<td>시간</td>
				<td><input type="text" name="time" class="input1"></td>
			</tr>

			<tr>
				<td>장소</td>
				<td><input type="text" name="place" class="input1"></td>
				<td>주최</td>
				<td><input type="text" name="host" class="input1"></td>
			</tr>

			<tr>
				<td>연락처</td>
				<td><input type="text" name="call" class="input1"></td>
				<td>입장료</td>
				<td><input type="text" name="money" class="input1"></td>
			</tr>

			<tr>
				<td>홈페이지</td>
				<td><input type="text" name="homepage" class="input1"></td>
				<td></td>
				<td></td>
			</tr>

			<tr>
				<td colspan=4>
				<br>
				<textarea name=content rows=10 style="width: 99%;" class="input1"></textarea></td>
			</tr>

			<tr>
				<td colspan=4>
<!-- 										<input type="file" name="file1"> <br>  -->
<!-- 										<input type="file" name="file2"> <br>  -->
<!-- 										<input type="file" name="file3"> <br>   -->
<!-- 										<input type="file" name="file4"> <br> -->
<!-- 										<input type="file" name="file5"> <br>  -->
					<br>
					<input type="button" value="파일 추가" onclick="javascript:addInputBox();" class="btn1">
					&nbsp; <br> <br> <input type="hidden" name="count">

					<table cellpadding=0 cellspacing=0 id="" border="0">
						<tr>
							<!-- 							 id="dynamic_table" -->
							<table cellpadding=0 cellspacing=0 border="0">
								<tr>
									<td width=140>
								<input type=file name=file1 size=40>
<!-- 								<input type="file" id="afile" name="afile" class="multi" maxlength="5"> -->
									</td>
								</tr>
							</table>
							<table cellpadding=0 cellspacing=0 id="dynamic_table" border="0">
							</table>
							<center>
								<input type="submit" value="글쓰기"
									onclick="javascript:submitbutton();" class="btn1">
							</center>
						</tr>
					</table>

				</td>
			</tr>

			<tr>
				<td colspan=4 align=center>
				</td>
			</tr>
		</table>
	
	
	
	<script language="javascript">
		var count = 2;
		var addCount = 2;

		// 파일칸 추가
		function addInputBox() {
			for (var i = 1; i <= count; i++) {
				if (!document.getElementsByName("test" + i)[0]) {
					addCount = i;
					break;
				} else
					addCount = count;
			}
			
			if(count <= 5){
			var addStr = "<tr><td width=140>";
			addStr += "<input type=file name=file" + count + " size=40 style=border:0>";
			addStr += "<a href=# onclick=subtractInputBox(this) no=" + addCount + ">";
			addStr += "<img src=/img/x.png width=10 height=10>";
			addStr += "</a></td></tr>";
			
			var table = document.getElementById("dynamic_table");
			var newRow = table.insertRow();
			var newCell = newRow.insertCell();
			newCell.innerHTML = addStr;
			count++;
			} else {
				alert('5개 이상 첨부할 수 없습니다. ㅜㅜ');
			}
		}

		// 파일칸 삭제
		function subtractInputBox(obj) {
			$(obj).parent().parent().remove();
			count--;
		}

// 		function submitbutton() {
// 			var gform = document.gForm;
// 			gform.count.value = eval(count);
// 			//alert(count);
// 			gForm.submit();
// 			return;
// 		}
		
	</script>
	
	</form>
</body>
</html>