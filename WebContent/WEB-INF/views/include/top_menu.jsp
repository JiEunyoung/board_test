<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath }"/>   <!-- 첫 경로(?) -->
<!-- 상단 메뉴 부분 -->
	<nav
		class="navbar navbar-expand-md bg-dark navbar-dark fixed-top shadow-lg">
		<a class="navbar-brand" href="${root }/main">SoftSoldesk</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navMenu">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navMenu">
			<ul class="navbar-nav">
				<%-- <li class="nav-item">
					<a href="${root }/board/main" class="nav-link">자유게시판</a>
					<!-- root 경로에서 보낸 것 처럼 root를 기준으로 둠(자유게시판 누른 후 이동한 페이지에서 자유게시판 눌렀을 경우 /board/board/main 방지 -->
				</li>
				<li class="nav-item">
					<a href="${root }/board/main" class="nav-link">유머게시판</a>
				</li>
				<li class="nav-item">
					<a href="${root }/board/main" class="nav-link">정치게시판</a>
				</li>
				<li class="nav-item">
					<a href="${root }/board/main" class="nav-link">스포츠게시판</a>
				</li> --%>
				
				<c:forEach var='obj' items="${topMenuList }">
					<li class="nav-item">
						<a class="nav-link" href="${root }/board/main?board_info_idx=${obj.board_info_idx}">${obj.board_info_name }</a>
					</li>
				</c:forEach>
			</ul>

			<ul class="navbar-nav ml-auto">
			
				<c:choose>
					<c:when test="${loginUserBean.userLogin == false }">
						<li class="nav-item">
							<a href="${root }/user/login" class="nav-link">로그인</a>
						</li>
						<li class="nav-item">
							<a href="${root }/user/join" class="nav-link">회원가입</a>
						</li>
					</c:when>
					
					<c:otherwise>
						<li class="nav-item">
							<a href="${root }/user/modify" class="nav-link">정보수정</a>
						</li>
						<li class="nav-item">
							<a href="${root }/user/logout" class="nav-link">로그아웃</a>
						</li>
					</c:otherwise>
				
				</c:choose>
			</ul>
		</div>
	</nav>