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

    <h2><spring:message code="view.main.bestAlbums.label"/></h2>
    <c:choose>
        <c:when test="${not empty topAlbums}">
            <table class="table">
                <thead>
                    <th><spring:message code="label.name"/></th>
                    <!-- <th><spring:message code="label.description"/></th> -->
                    <th><spring:message code="label.author"/></th>
                </thead>
                <tbody>
                <c:forEach items="${topAlbums}" var="album">
                    <tr>
                        <td><a href="/${album.author.username}/${album.id}"><b>${album.name}</b></a></td>
                        <!-- <td><p class="text-justify">${album.description}</p></td> -->
                        <td><a href="/${album.author.username}/albums"><b>${album.author.fullName}</b></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>EMPTY</p>
        </c:otherwise>
    </c:choose>
</tag:Layout>


