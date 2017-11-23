<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <h3>Lista komentarzy do albumu ${album.name}</h3>
    <table>
        <tr>
            <th>Autor</th>
            <th>Treść</th>
            <th>Opcje</th>
        </tr>
        <c:forEach var="comment" items="${comments}">
            <tr>
                <td><b>${comment.author.name}</b></td>
                <td>${comment.description}</td>
                <td><a href="/${comment.author.username}/${comment.album.id}/comment">Edit</a> |
                    <form method="post" action="/${comment.author.username}/${comment.album.id}/commentList/${comment.id}">
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Delete</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</tag:Layout>