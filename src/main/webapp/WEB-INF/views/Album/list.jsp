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
                <td><a href="/${user}/${album.name}">View</a> | <a href="/${user}/${album.name}/edit">Edit</a> | <a href="/${user}/${album.name}" data-confirm="Are you sure?" data-method="delete" rel="nofollow">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</tag:Layout>