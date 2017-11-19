<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <h3>Albumy użytkownika</h3>
    <table>
        <tr>
            <th>Nazwa</th>
            <th>Opis</th>
            <th>Opcje</th>
        </tr>
        <c:forEach var="album" items="${albums}">
            <tr>
                <td><b>${album.name}</b></td>
                <td>${album.description}</td>
                <td><a href="/${user}/${album.id}">View</a> | <a href="/${user}/${album.id}/edit">Edit</a> | <form method="post" action="/${user}/${album.id}/delete">
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Delete</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form></td>
            </tr>
        </c:forEach>
    </table>
</tag:Layout>