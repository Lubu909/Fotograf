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
        <h3>Albumy użytkownika</h3>
        <table class="table">
            <thead>
                <th>Nazwa</th>
                <th>Opis</th>
                <c:if test="${albums.get(0).author.username == user}">
                    <th>Opcje</th>
                </c:if>
            </thead>
            <tbody>
                <c:forEach var="album" items="${albums}">
                    <tr>
                        <td><a href="/${album.author.username}/${album.id}"><b>${album.name}</b></a></td>
                        <td>${album.description}</td>
                        <c:if test="${album.author.username == user}">
                            <td class="btn-group text-nowrap">
                                <a href="/${album.author.username}/${album.id}/edit" class="btn btn-lg btn-primary">Edit</a>
                                <form method="post" action="/${album.author.username}/${album.id}/delete">
                                    <button class="btn btn-lg btn-primary" type="submit">Delete</button>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </c:when>
        <c:otherwise>
            <h2>Użytkownik nie utworzył jeszcze żadnego albumu</h2>
        </c:otherwise>
    </c:choose>
</tag:Layout>