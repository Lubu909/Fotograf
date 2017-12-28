<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <table class="table table-hover">
        <h1><spring:message code="view.order.list.label"/></h1>
        <thead>
            <th><spring:message code="view.order.table.photographer"/></th>
            <th><spring:message code="view.order.table.client"/></th>
            <th><spring:message code="view.order.table.orderCompletionDate"/></th>
            <th><spring:message code="view.order.table.status"/></th>
            <th><spring:message code="view.order.table.options"/></th>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td><a href="/${order.fotograf.username}/albums">${order.fotograf.username}</a></td>
                <td>${order.klient.username}</td>
                <td>${order.terminWykonania}</td>
                <td>${order.status}</td>
                <td class="btn-group">
                    <c:choose>
                        <c:when test="${order.fotograf.username == user}">
                            Opcje fotografa
                            <!-- IF PO STATUSIE -->
                            <!-- CREATED/MODIFIED? - ACCEPT/REJECT -->
                        </c:when>
                        <c:otherwise>
                            Opcje klienta
                            <!-- IF PO STATUSIE -->
                                <!-- MODIFIED - ACCEPT/REJECT -->
                                <!-- CREATED/ACCEPTED - DELETE -->
                        </c:otherwise>
                    </c:choose>
                    <a href="/${user}/order/${order.id}" class="btn btn-primary"><spring:message code="label.view"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</tag:Layout>