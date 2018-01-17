<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tag:Layout>
    <div class="panel panel-primary add-photo-form">
        <div class="panel-heading">
            <h2><spring:message code="view.album.form.label.edit"/></h2>
        </div>
        <div class="panel-body">
            <form:form method="POST" modelAttribute="albumForm">
                <spring:bind path="id">
                    <form:hidden path="id"/>
                </spring:bind>
                <spring:bind path="name">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:label path="name"><spring:message code="view.album.form.name"/></form:label>
                        <form:input type="text" path="name" class="form-control" placeholder="${albumForm.name}"
                                    autofocus="true"/>
                        <form:errors path="name"/>
                    </div>
                </spring:bind>
                <spring:bind path="description">
                    <div class="album-description form-group ${status.error ? 'has-error' : ''}">
                        <form:label path="description"><spring:message code="view.album.form.description"/></form:label>
                        <form:input type="text" path="description" class="form-control" placeholder="${albumForm.description}"
                                    autofocus="true"/>
                        <form:errors path="description"/>
                    </div>
                </spring:bind>
                <spring:bind path="author">
                    <form:hidden path="author.username" />
                </spring:bind>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.submit"/></button>
            </form:form>
        </div>
    </div>
</tag:Layout>