<<<<<<< HEAD
<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <h3><spring:message code="view.comment.commentList" arguments="${album.name}"/></h3>
    <table>
        <tr>
            <th><spring:message code="label.author"/></th>
            <th><spring:message code="label.content"/></th>
            <th><spring:message code="label.options"/></th>
        </tr>
        <c:forEach var="comment" items="${comments}">
            <tr>
                <td><b>${comment.author.name}</b></td>
                <td>${comment.description}</td>
                <td><a href="/${comment.author.username}/${comment.album.id}/comment"><spring:message code="label.edit"/></a> |
                    <form method="post" action="/${comment.author.username}/${comment.album.id}/commentList/${comment.id}">
                        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.delete"/></button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
=======
<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <h3><spring:message code="view.comment.commentList" arguments="${album.name}"/></h3>
    <table>
        <tr>
            <th><spring:message code="label.author"/></th>
            <th><spring:message code="label.content"/></th>
            <th><spring:message code="label.options"/></th>
        </tr>
        <c:forEach var="comment" items="${comments}">
            <tr>
                <td><b>${comment.author.name}</b></td>
                <td>${comment.description}</td>
                <td><a href="/${comment.author.username}/${comment.album.id}/comment"><spring:message code="label.edit"/></a> |
                    <form method="post" action="/${comment.author.username}/${comment.album.id}/commentList/${comment.id}">
                        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.delete"/></button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
>>>>>>> f3364fb7cf661ead67597d3c62992013c4728ac3
</tag:Layout>