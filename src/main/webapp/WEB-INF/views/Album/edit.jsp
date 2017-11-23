<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tag:Layout>
    <h2>Edytuj album</h2>
    <form:form method="POST" modelAttribute="albumForm">
        <spring:bind path="id">
            <form:hidden path="id"/>
        </spring:bind>
        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="name">Nazwa albumu</form:label>
                <form:input type="text" path="name" class="form-control" placeholder="${albumForm.name}"
                            autofocus="true"/>
                <form:errors path="name"/>
            </div>
        </spring:bind>
        <spring:bind path="description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="description">Opis albumu</form:label>
                <form:input type="text" path="description" class="form-control" placeholder="${albumForm.description}"
                            autofocus="true"/>
                <form:errors path="description"/>
            </div>
        </spring:bind>
        <spring:bind path="author">
            <form:hidden path="author.username" />
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>
</tag:Layout>