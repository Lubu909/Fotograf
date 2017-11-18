<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tag:Layout>
    <h2>Stwórz album</h2>
    <form:form method="post" modelAttribute="albumForm">
        <h3>Nowy album</h3>
        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="name" class="form-control" placeholder="Nazwa albumu"
                            autofocus="true"/>
                <form:errors path="name"/>
            </div>
        </spring:bind>
        <spring:bind path="description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="description" class="form-control" placeholder="Opis albumu"
                            autofocus="true"/>
                <form:errors path="description"/>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</tag:Layout>