<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="currentUser" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <div class="row">
        <div class="col-sm-4">
            <h2>${user.fullName}</h2>
            <c:choose>
                <c:when test="${empty user.photo}">
                    <img src="<c:url value='/resources/images/user.png'/>" width="200" height="200"
                         class="img-circle media-object"/>
                </c:when>
                <c:otherwise>
                    <img src="/${user.username}/avatar" width="200" height="200"
                         class="img-circle media-object"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${user.username == currentUser}">
                    <h3><spring:message code="label.options"/></h3>
                    <!-- TODO: Opcje użytkownika -->
                    <div class="btn-group">
                        <a href="/${user.username}/editAccountDetails" class="btn btn-primary btn-profile"><spring:message code="label.accountDetails.edit"/></a>
                        <a href="/${user.username}/changePasswd" class="btn btn-primary btn-profile"><spring:message code="label.changePassword"/></a>
                        <a href="/addImg" class="btn btn-primary btn-profile"><spring:message code="label.setAvatar"/></a>
                    </div>
                    <!-- Koniec opcji -->
                </c:when>
                <c:otherwise>
                    <c:if test="${isPhotographer}">
                        <a href="/${user.username}/order" class="btn btn-primary"><spring:message code="label.order"/></a>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-sm-8">
            <c:choose>
                <c:when test="${isPhotographer}">
                    <c:choose>
                        <c:when test="${user.albums.size() > 0}">
                            <div class="panel panel-primary photographer-view">
                                <div class="panel-heading">
                                    <h3><spring:message code="view.album.userAlbums"/></h3>
                                </div>
                                <div class="panel-body">
                                    <table class="table">
                                        <thead>
                                        <th><spring:message code="label.name"/></th>
                                        <th><spring:message code="label.description"/></th>
                                        <c:if test="${user.username == currentUser}">
                                            <th><spring:message code="label.options"/></th>
                                        </c:if>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="album" items="${user.albums}">
                                            <tr>
                                                <td><a href="/${album.author.username}/${album.id}"><b>${album.name}</b></a></td>
                                                <td><p class="text-justify">${album.description}</p></td>
                                                <c:if test="${user.username == currentUser}">
                                                    <td class="btn-group text-nowrap">
                                                        <a href="/${album.author.username}/${album.id}/edit" class="btn btn-lg btn-primary"><spring:message code="label.edit"/></a>
                                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#deleteModal" data-link="/${album.author.username}/${album.id}/delete"><spring:message code="label.delete"/></button>
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <h2><spring:message code="view.album.zeroAlbums"/></h2>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <!-- TODO: Co wyswietlać u zwykłych użytkowników? -->
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</tag:Layout>