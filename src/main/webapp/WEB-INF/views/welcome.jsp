<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tag:Layout>
    <h1><spring:message code="view.main.welcome"/></h1>
    <h2><spring:message code="view.main.chooseLanguage"/></h2>
    <a href="/?lang=pl"><h3>PL</h3></a>
    <a href="/?lang=en"><h3>ENG</h3></a>
</tag:Layout>


