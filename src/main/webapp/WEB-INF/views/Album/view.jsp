<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <div class="alert alert-info row">
        <div class="col-sm-4">
            <h1>${album.name}</h1>
            <h3><spring:message code="view.album.userAlbum" arguments="${album.author.username}"/></h3>
            <p class="text-justify">${album.description}</p>
        </div>
        <div class="col-sm-4"></div>
        <div class="col-sm-4 edit-delete-buttons">
            <c:if test="${user == album.author.username}">
                <div class="btn-group text-nowrap">
                    <a href="/${user}/${album.id}/edit" class="btn btn-lg btn-primary"><spring:message code="label.edit"/> <spring:message code="word.album.lowercase"/></a>
                    <button type="button" class="btn btn-lg btn-primary" data-toggle="modal" data-target="#deleteModal" data-link="/${user}/${album.id}/delete"><spring:message code="label.delete"/> <spring:message code="word.album.lowercase"/></button>
                </div>
            </c:if>
        </div>
    </div>

    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3><spring:message code="view.score.global"/> ${globalScore}</h3>
        </div>
        <div class="panel-body">
            <c:if test="${user != null}">
                <c:if test="${userScore != null}">
                    <h5><spring:message code="view.score.user"/> ${userScore}</h5>
                </c:if>
                <%-- Zamienić na zawartość urla --%>
                <form:form method="post" modelAttribute="scoreForm" action="/${album.author.username}/${album.id}/rateAlbum">
                    <spring:bind path="value">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="range" path="value" class="form-control" min="0" max="10" step="0.5"
                                        autofocus="true"/>
                            <form:errors path="value"/>
                        </div>
                    </spring:bind>
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.submit"/></button>
                </form:form>
            </c:if>
        </div>
    </div>

    <h3>
    <c:if test="${user == album.author.username}">
        <a href="/${user}/${album.id}/add" class="btn btn-lg btn-primary"><spring:message code="label.add"/> <spring:message code="word.photo.lowercase"/></a>
    </c:if>
    </h3>

    <div id="photoList" class="row">
        <c:forEach var="photo" items="${album.pictures}">
            <div class="col-md-4">
                <div class="thumbnail">
                    <img id="photo" src="/${album.author.username}/${album.id}/photo${photo.id}.jpg" alt="${photo.title}" height="250">
                    <h3>${photo.title}</h3>
                    <c:if test="${user == album.author.username}">
                        <div class="btn-group text-nowrap">
                            <a href="/${user}/${album.id}/${photo.id}/edit" class="btn btn-primary"><spring:message code="label.edit"/></a>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#deleteModal" data-link="/${user}/${album.id}/${photo.id}/delete"><spring:message code="label.delete"/></button>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>

    <div id="commentList">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h2><spring:message code="label.comments"/></h2>
            </div>
            <div class="panel-body">
                <table class="table table-striped media">
                    <tbody>
                    <c:forEach var="comment" items="${album.comments}">
                        <tr>
                            <td class="col-sm-2 media-left">
                                <c:choose>
                                    <c:when test="${empty comment.author.photo}">
                                        <img src="<c:url value='/resources/images/user.png'/>" width="100" height="100"
                                             class="img-circle media-object"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="/${comment.author.username}/avatar" width="100" height="100"
                                             class="img-circle media-object"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="col-sm-8 media-body">
                                <b class="media-heading">${comment.author.name} ${comment.author.surname}</b>
                                <p class="multiline-text">${comment.description}</p>
                            </td>
                            <td class="btn-group text-nowrap col-sm-2">
                            <c:if test="${user == comment.author.username}">
                                <a href="/${comment.album.author.username}/${comment.album.id}/comment/${comment.id}" class="btn btn-primary"><spring:message code="label.edit"/></a>
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#deleteModal" data-link="/${comment.album.author.username}/${comment.album.id}/commentList/${comment.id}"><spring:message code="label.delete"/></button>
                            </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%--Add comment--%>
    <c:if test="${user != null}">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h2><spring:message code="view.comment.form.label"/></h2>
            </div>
            <div class="panel-body">
                <form:form method="post" modelAttribute="commentForm" action="/${album.author.username}/${album.id}/comment">
                    <spring:bind path="description">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <spring:message code="view.comment.form.placeholder" var="commentPlaceholder"/>
                            <form:input type="text" path="description" class="form-control" placeholder="${commentPlaceholder}"
                                        autofocus="true"/>
                            <form:errors path="description"/>
                        </div>
                    </spring:bind>
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.submit"/></button>
                </form:form>
            </div>
        </div>
    </c:if>
</tag:Layout>