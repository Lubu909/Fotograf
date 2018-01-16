<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tag:Layout>
    <h2><spring:message code="view.album.form.label.create"/></h2>
    <form:form method="post" modelAttribute="albumForm">
        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="name"><spring:message code="view.album.form.name" var="albumName"/></form:label>
                <form:input type="text" path="name" class="form-control" placeholder="${albumName}"
                            autofocus="true"/>
                <form:errors path="name"/>
            </div>
        </spring:bind>
        <spring:bind path="description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="description"><spring:message code="view.album.form.description" var="albumDescription"/></form:label>
                <form:input type="text" path="description" class="form-control" placeholder="${albumDescription}"
                            autofocus="true"/>
                <form:errors path="description"/>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.submit"/></button>
    </form:form>

</tag:Layout>