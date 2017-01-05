<%@page import="com.jsp.dto.ReviewDto"%>
<%@page import="com.jsp.model.ReviewDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%    
    //제대로 utf-8환경이 아니라 한글 깨짐 그래서 임의로 추가                                                   
    request.setCharacterEncoding("utf-8");
    
	String seq2 = request.getParameter("seq");
	String order2 = request.getParameter("order");
	String depth2 = request.getParameter("depth");
	String id = (String) request.getSession().getAttribute("userId");
	
	int order = 1;
	int depth = 0;

	// 첫글쓰기
	if(seq2.equals("")) {
		seq2 = "0";
	} else {
		order = Integer.parseInt(order2) + 1;
		depth = Integer.parseInt(depth2) + 1;
	}
	
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    String content2 = content.replace("<", "&lt;").replace(">", "&gt;");
    
    //콘솔 출력
    System.out.println("제목:"+title);
    System.out.println("내용:"+content);
    
    ReviewDao dao = new ReviewDao();
    ReviewDto dto = new ReviewDto();
    dto.setTitle(title);
    dto.setId(id);
    dto.setContent(content);
    dto.setParent_seq(Integer.parseInt(seq2));
    dto.setOrder(order);
    dto.setDepth(depth);
    
    // 게시물 등록
    dao.insertReview(dto);
    
    // 현재 등록된 글의 번호
    int seq = dao.getMaxSeqNo();
    if(seq2.equals("0")) { // 첫글쓰기
	    // 게시물 그룹번호 수정
	    dao.updateReviewByParentSeq(seq);
    } else {
    	dao.updateReviewByOrderPlus(seq, Integer.parseInt(seq2), order);
    }

    %>

	<script type="text/javascript">
		alert("글이 저장되었습니다.");
		location.href="reviewList.do";
	</script>

    <%  
//     response.sendRedirect("reviewList.do");
	%>
 