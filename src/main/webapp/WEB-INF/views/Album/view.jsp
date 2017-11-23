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
    <h5>${album.description}</h5>

    <%--<jsp:include page="Photo/list.jsp"/> --%>
    <%--<jsp:include page="Comment/list.jsp"/>--%>
    <%--<jsp:include page="Score/view.jsp"/>--%>

    <a href="/${album.author.username}/${album.id}/rateAlbum">Rate this album</a>

    <c:if test="${user == album.author.username}">
        <h3><a href="/${user}/${album.id}/edit">Edit</a></h3>
        <form method="post" action="/${user}/${album.id}/delete">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Delete</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <h3><a href="/${user}/${album.id}/add">Add photo</a></h3>
    </c:if>
</tag:Layout>