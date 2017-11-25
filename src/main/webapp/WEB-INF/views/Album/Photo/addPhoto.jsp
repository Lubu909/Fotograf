<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tag:Layout>
    <h2>Dodaj zdjęcie</h2>
    <form:form method="post" enctype="multipart/form-data" modelAttribute="photoForm">
        <spring:bind path="title">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="title">Nazwa zdjęcia: </form:label>
                <form:input type="text" path="title" class="form-control" placeholder="${photoForm.title}"
                            autofocus="true"/>
                <form:errors path="title"/>
            </div>
        </spring:bind>
        <spring:bind path="photoFile">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="photoFile">Plik: </form:label>
                <form:input type="file" path="photoFile"/>
                <form:errors path="photoFile"/>
            </div>
        </spring:bind>
        <%--<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>--%>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>
</tag:Layout>