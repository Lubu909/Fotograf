<<<<<<< HEAD
<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>


<tag:Layout>
    <h1><spring:message code="view.order.list.label"/></h1>
    <div class="panel panel-default">
        <table class="table table-hover">
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
                    <td>${order.formattedTerminWykonania}</td>
                    <td>
                        <c:choose>
                            <c:when test="${order.status == 1}"><spring:message code="status.created"/></c:when>
                            <c:when test="${order.status == 2}"><spring:message code="status.modified"/></c:when>
                            <c:when test="${order.status == 3}"><spring:message code="status.accepted"/></c:when>
                            <c:when test="${order.status == 4}"><spring:message code="status.rejected"/></c:when>
                            <c:otherwise><spring:message code="status.unknown"/></c:otherwise>
                        </c:choose>
                    </td>
                    <td class="btn-group text-nowrap">
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
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                        <a href="/${user}/order/${order.id}" class="btn btn-primary"><spring:message code="label.view"/></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
=======
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
                <td>${order.formattedTerminWykonania}</td>
                <td>
                    <c:choose>
                        <c:when test="${order.status == 1}"><spring:message code="status.created"/></c:when>
                        <c:when test="${order.status == 2}"><spring:message code="status.modified"/></c:when>
                        <c:when test="${order.status == 3}"><spring:message code="status.accepted"/></c:when>
                        <c:when test="${order.status == 4}"><spring:message code="status.rejected"/></c:when>
                        <c:otherwise><spring:message code="status.unknown"/></c:otherwise>
                    </c:choose>
                </td>
                <td class="btn-group text-nowrap">
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
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <a href="/${user}/order/${order.id}" class="btn btn-primary"><spring:message code="label.view"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
>>>>>>> f3364fb7cf661ead67597d3c62992013c4728ac3
</tag:Layout>