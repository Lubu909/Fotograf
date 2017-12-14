<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <h1><spring:message code="view.photo.form.label.edit"/></h1>
    <form:form method="POST" modelAttribute="photoForm">
        <img src="/${photoForm.album.author.username}/${photoForm.album.id}/photo${photoForm.id}" alt="${photoForm.title}">
        <spring:bind path="title">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="title"><spring:message code="view.photo.form.name"/></form:label>
                <form:input type="text" path="title" class="form-control" placeholder="${photoForm.title}"
                            autofocus="true"/>
                <form:errors path="title"/>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.submit"/></button>
    </form:form>
</tag:Layout>