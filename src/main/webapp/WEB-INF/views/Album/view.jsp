<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <h1>${album.name}</h1>
    <h3><spring:message code="view.album.userAlbum" arguments="${album.author.username}"/></h3>
    <h5>${album.description}</h5>

    <div id="scoreView">
        <%--<h3><spring:message code="view.score.global"/> ${globalScore}</h3>--%>
        <c:if test="${user != null}">
            <%--<c:if test="${userScore != null}">--%>
                <%--<h5><spring:message code="view.score.user"/> ${userScore}</h5>--%>
            <%--</c:if>--%>
            <%-- Zamienić na zawartość urla --%>
            <a href="/${album.author.username}/${album.id}/rateAlbum"><spring:message code="view.album.rateAlbum"/></a>
        </c:if>
    </div>

    <c:if test="${user == album.author.username}">
        <h3><a href="/${user}/${album.id}/add"><spring:message code="label.add"/> <spring:message code="word.photo.lowercase"/></a></h3>
    </c:if>

    <div id="photoList" class="row">
        <c:forEach var="photo" items="${album.pictures}">
            <div class="col-md-4">
                <div class="thumbnail">
                    <img src="/${album.author.username}/${album.id}/photo${photo.id}" alt="${photo.title}">
                    <h3>${photo.title}</h3>
                    <c:if test="${user == album.author.username}">
                        <div class="btn-group text-nowrap">
                            <a href="/${user}/${album.id}/${photo.id}/edit" class="btn btn-primary"><spring:message code="label.edit"/></a>
                            <form method="post" action="/${user}/${album.id}/${photo.id}/delete">
                                <button class="btn btn-primary" type="submit"><spring:message code="label.delete"/></button>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${user == album.author.username}">
        <div class="btn-group text-nowrap">
            <a href="/${user}/${album.id}/edit" class="btn btn-lg btn-primary"><spring:message code="label.edit"/> <spring:message code="word.album.lowercase"/></a>
            <form method="post" action="/${user}/${album.id}/delete">
                <button class="btn btn-lg btn-primary" type="submit"><spring:message code="label.delete"/> <spring:message code="word.album.lowercase"/></button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </c:if>



    <div id="commentList">
        <table class="table table-striped">
        <h2><spring:message code="label.comments"/></h2>
            <tbody>
            <c:forEach var="comment" items="${album.comments}">
                <tr>
                    <td><b>${comment.author.name} ${comment.author.surname}</b></td>
                    <td>${comment.description}</td>
                    <c:if test="${user == comment.author.username}">
                        <td class="btn-group text-nowrap">
                            <a href="/${comment.author.username}/${comment.album.id}/comment" class="btn btn-primary"><spring:message code="label.edit"/></a>
                            <form method="post" action="/${comment.author.username}/${comment.album.id}/commentList/${comment.id}">
                                <button class="btn btn-primary" type="submit"><spring:message code="label.delete"/></button>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <%--Add comment--%>
</tag:Layout>