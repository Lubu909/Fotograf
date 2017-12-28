<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>

<tag:Layout>
    <table class="table">
        <tr>
            <td><strong><spring:message code="view.order.table.photographer"/></strong></td>
            <td><a href="/${order.fotograf.username}/albums">${order.fotograf.name} ${order.fotograf.surname}</a></td>
        </tr>
        <tr>
            <td><strong><spring:message code="view.order.table.client"/></strong></td>
            <td><a href="/${order.klient.username}/albums">${order.klient.name} ${order.klient.surname}</a></td>
        </tr>
        <tr>
            <td><strong><spring:message code="view.order.table.orderCompletionDate"/></strong></td>
            <td>${order.terminWykonania} <!-- - order.terminWykonania + order.hours --></td>
        </tr>
        <tr>
            <td><strong><spring:message code="view.order.table.orderDate"/></strong></td>
            <td>${order.dataZamowienia}</td>
        </tr>
        <tr>
            <td><strong><spring:message code="view.order.form.description.placeholder"/></strong></td>
            <td>${order.description}</td>
        </tr>
        <tr>
            <td><strong><spring:message code="view.order.table.status"/></strong></td>
            <td>${order.status}</td>
        </tr>
        <tr>
            <td><strong><spring:message code="view.order.table.options"/></strong></td>
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
            </td>
        </tr>
    </table>
</tag:Layout>