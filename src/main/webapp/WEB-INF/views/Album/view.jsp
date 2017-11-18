<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <h1>${album.name}</h1>
    <h3>Album użytkownika ${album.author.username}</h3>

    <%--<jsp:include page="Photo/list"/> --%>
    <%--<jsp:include page="Comment/list"/>--%>
    <%--<jsp:include page="Score/view"/>--%>

    <c:if test="${user == album.author.username}">
        <h3><a href="/${user}/${album.name}/edit">Edit</a></h3>
        <form method="post" action="/${user}/${album.name}/delete">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Delete</button>
        </form>
    </c:if>
</tag:Layout>