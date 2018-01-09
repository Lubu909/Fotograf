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
            <td>${order.formattedTerminWykonania} - ${endTime}</td>
        </tr>
        <tr>
            <td><strong><spring:message code="view.order.table.orderDate"/></strong></td>
            <td>${order.formattedDataZamowienia}</td>
        </tr>
        <tr>
            <td><strong><spring:message code="view.order.form.description.placeholder"/></strong></td>
            <td>${order.description}</td>
        </tr>
        <tr>
            <td><strong><spring:message code="view.order.table.status"/></strong></td>
            <td>
                <c:choose>
                    <c:when test="${order.status == 1}"><spring:message code="status.created"/></c:when>
                    <c:when test="${order.status == 2}"><spring:message code="status.modified"/></c:when>
                    <c:when test="${order.status == 3}"><spring:message code="status.accepted"/></c:when>
                    <c:when test="${order.status == 4}"><spring:message code="status.rejected"/></c:when>
                    <c:otherwise><spring:message code="status.unknown"/></c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td><strong><spring:message code="view.order.table.options"/></strong></td>
            <td class="btn-group">
                <c:choose>
                    <c:when test="${order.fotograf.username == user}">
                        <!-- Opcje fotografa -->
                        <c:choose>
                            <c:when test="${order.status == 1}">
                                <!-- CREATED - ACCEPT/REJECT/MODIFY -->
                                <a href="/${user}/order/${order.id}/edit" class="btn btn-primary"><spring:message code="label.Modify"/></a>
                                <form method="post" action="/${user}/order/${order.id}/accept">
                                    <button class="btn btn-primary" type="submit"><spring:message code="label.accept"/></button>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <form method="post" action="/${user}/order/${order.id}/reject">
                                    <button class="btn btn-primary" type="submit"><spring:message code="label.reject"/></button>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                            </c:when>
                            <c:otherwise><spring:message code="view.order.wait.userApproval"/></c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <!-- Opcje klienta -->
                        <c:choose>
                            <c:when test="${order.status == 1 || order.status == 3}">
                                <!-- CREATED/ACCEPTED - DELETE -->
                                <!--
                                <form method="post" action="/${user}/order/${order.id}/delete">
                                    <button class="btn btn-primary" type="submit"><spring:message code="label.delete"/></button>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                -->
                            </c:when>
                            <c:when test="${order.status == 2}">
                                <!-- MODIFIED - ACCEPT/REJECT -->
                                <form method="post" action="/${user}/order/${order.id}/accept">
                                    <button class="btn btn-primary" type="submit"><spring:message code="label.accept"/></button>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <form method="post" action="/${user}/order/${order.id}/reject">
                                    <button class="btn btn-primary" type="submit"><spring:message code="label.reject"/></button>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                            </c:when>
                            <c:otherwise><spring:message code="view.order.wait.photographerApproval"/></c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</tag:Layout>