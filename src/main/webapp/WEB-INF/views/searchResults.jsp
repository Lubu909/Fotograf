<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <h1><spring:message code="view.search.advanced.title"/></h1>
    <form:form method="post" modelAttribute="searchForm" action="/advSearch">
        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="name"><spring:message code="view.search.table.name" var="firstName"/></form:label>
                <form:input type="text" path="name" class="form-control" placeholder="${firstName}"
                            autofocus="true"/>
                <form:errors path="name"/>
            </div>
        </spring:bind>
        <spring:bind path="surname">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="surname"><spring:message code="view.search.table.surname" var="surname"/></form:label>
                <form:input type="text" path="surname" class="form-control" placeholder="${surname}"
                            autofocus="true"/>
                <form:errors path="surname"/>
            </div>
        </spring:bind>
        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="username"><spring:message code="view.search.table.username" var="username"/></form:label>
                <form:input type="text" path="username" class="form-control" placeholder="${username}"
                            autofocus="true"/>
                <form:errors path="username"/>
            </div>
        </spring:bind>
        <spring:bind path="city">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="city"><spring:message code="view.search.table.city" var="city"/></form:label>
                <form:input type="text" path="city" class="form-control" placeholder="${city}"
                            autofocus="true"/>
                <form:errors path="city"/>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="layout.navbar.searchBar"/></button>
    </form:form>
    <table class="table table-hover">
        <h1><spring:message code="view.search.title"/></h1>
        <thead>
            <%--<th>ID</th>--%>
            <th><spring:message code="view.search.table.username"/></th>
            <th><spring:message code="view.search.table.name"/></th>
            <th><spring:message code="view.search.table.surname"/></th>
            <th><spring:message code="view.search.table.city"/></th>
            <%--<th>E-mail</th>--%>
            <%--<th>numer telefonu</th>--%>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <%--<td>${user.id}</td>--%>
                <td><a href="/${user.username}/albums">${user.username}</a></td>
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