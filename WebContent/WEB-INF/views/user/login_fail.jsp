<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var='root' value='${pageContext.request.contextPath}/'/>
<script>
   alert('로그인에 실패하였습니다')
   location.href = '${root}user/login?fail=true'
		            //로그인 컨트롤러로 요청 , ?는 fail 파라미터에 true값 담아서 보냄
</script>