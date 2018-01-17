<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tag:Layout>
    <h1><spring:message code="view.order.form.label"/></h1>
    <form:form method="post" modelAttribute="orderForm">
        <!-- TERMIN ZAMÓWIENIA - date picker -->
        <spring:bind path="dateForm">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type='date' path="dateForm" class="form-control"
                            autofocus="true"/>
                <form:errors path="dateForm"/>
            </div>
        </spring:bind>
        <spring:bind path="timeForm">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type='time' path="timeForm" class="form-control"
                            autofocus="true"/>
                <form:errors path="timeForm"/>
            </div>
        </spring:bind>
        <spring:bind path="hours">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="number" path="hours" class="form-control" placeholder="1"
                            autofocus="true"/>
                <form:errors path="hours"/>
            </div>
        </spring:bind>
        <spring:bind path="description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:message code="view.order.form.description.placeholder" var="commentPlaceholder"/>
                <form:input type="text" path="description" class="form-control" placeholder="${commentPlaceholder}"
                            autofocus="true"/>
                <form:errors path="description"/>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.submit"/></button>
    </form:form>
</tag:Layout>