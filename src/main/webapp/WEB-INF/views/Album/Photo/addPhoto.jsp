<<<<<<< HEAD
<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tag:Layout>
    <div class="panel panel-primary add-photo-form">
        <div class="panel-heading">
            <h2><spring:message code="view.photo.form.label.create"/></h2>
        </div>
        <div class="panel-body">
            <form:form method="post" enctype="multipart/form-data" modelAttribute="photoForm">
                <spring:bind path="title">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:label path="title"><spring:message code="view.photo.form.name"/></form:label>
                        <form:input type="text" path="title" class="form-control" placeholder="${photoForm.title}"
                                    autofocus="true"/>
                        <form:errors path="title"/>
                    </div>
                </spring:bind>
                <spring:bind path="photoFile">
                    <div class="add-photo form-group ${status.error ? 'has-error' : ''}">
                        <form:label path="photoFile"><spring:message code="view.photo.form.file"/></form:label>
                        <form:input type="file" path="photoFile"/>
                        <form:errors path="photoFile"/>
                    </div>
                </spring:bind>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.submit"/></button>
            </form:form>
        </div>
    </div>
=======
<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tag:Layout>
    <h2><spring:message code="view.photo.form.label.create"/></h2>
    <form:form method="post" enctype="multipart/form-data" modelAttribute="photoForm">
        <spring:bind path="title">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="title"><spring:message code="view.photo.form.name"/></form:label>
                <form:input type="text" path="title" class="form-control" placeholder="${photoForm.title}"
                            autofocus="true"/>
                <form:errors path="title"/>
            </div>
        </spring:bind>
        <spring:bind path="photoFile">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="photoFile"><spring:message code="view.photo.form.file"/></form:label>
                <form:input type="file" path="photoFile"/>
                <form:errors path="photoFile"/>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.submit"/></button>
    </form:form>
>>>>>>> f3364fb7cf661ead67597d3c62992013c4728ac3
</tag:Layout>