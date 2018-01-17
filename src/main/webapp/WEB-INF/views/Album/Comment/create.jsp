<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tag:Layout>
    <h2><spring:message code="view.comment.form.label"/></h2>
    <form:form method="post" modelAttribute="commentForm">
        <spring:bind path="description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:message code="view.comment.form.placeholder" var="commentPlaceholder"/>
                <form:input type="text" path="description" class="form-control" placeholder="${commentPlaceholder}"
                            autofocus="true"/>
                <form:errors path="description"/>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.submit"/></button>
    </form:form>
</tag:Layout>