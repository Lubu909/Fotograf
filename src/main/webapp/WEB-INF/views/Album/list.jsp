<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <c:choose>
        <c:when test="${albums.size() > 0}">
        <h3><spring:message code="view.album.userAlbums"/></h3>
        <table class="table">
            <thead>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.description"/></th>
                <c:if test="${albums.get(0).author.username == user}">
                    <th><spring:message code="label.options"/></th>
                </c:if>
            </thead>
            <tbody>
                <c:forEach var="album" items="${albums}">
                    <tr>
                        <td><a href="/${album.author.username}/${album.id}"><b>${album.name}</b></a></td>
                        <td><p class="text-justify">${album.description}</p></td>
                        <c:if test="${album.author.username == user}">
                            <td class="btn-group text-nowrap">
                                <a href="/${album.author.username}/${album.id}/edit" class="btn btn-lg btn-primary"><spring:message code="label.edit"/></a>
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#deleteModal" data-link="/${album.author.username}/${album.id}/delete"><spring:message code="label.delete"/></button>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </c:when>
        <c:otherwise>
            <h2><spring:message code="view.album.zeroAlbums"/></h2>
        </c:otherwise>
    </c:choose>
</tag:Layout>