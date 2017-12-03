<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <table class="table table-hover">
        <h1>Search Results</h1>
        <thead>
            <%--<th>ID</th>--%>
            <th>Nazwa użytkownika</th>
            <th>Imię</th>
            <th>Nazwisko</th>
            <th>Miejscowość</th>
            <%--<th>E-mail</th>--%>
            <%--<th>numer telefonu</th>--%>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <%--<td>${user.id}</td>--%>
                <td>${user.username}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.city}</td>
                <%--<td>${user.email}</td>--%>
                <%--<td>${user.tel}</td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</tag:Layout>