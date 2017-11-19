<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tag:Layout>
    <h2>Oceń album</h2>
    <form:form method="post" modelAttribute="scoreForm">
        <spring:bind path="value">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="range" path="value" class="form-control" min="0" max="10" step="0.5"
                            autofocus="true"/>
                <form:errors path="value"/>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</tag:Layout>