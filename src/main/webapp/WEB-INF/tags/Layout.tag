<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="url" value="${pageContext.request.requestURL}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="pl-PL">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Welcome</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>

<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="modal.delete.header"/></h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p><spring:message code="modal.delete.content"/></p>
            </div>
            <div class="modal-footer">
                <form id="deleteForm" method="post" action="/">
                    <button class="btn btn-primary" type="submit"><spring:message code="modal.delete.option.yes"/></button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="modal.delete.option.no"/></button>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="/">Fotograf</a>
            </div>
        <c:choose>
            <c:when test="${pageContext.request.userPrincipal.name != null}">
                <form id="logoutForm" method="POST" action="${contextPath}/logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>

                <ul class="nav navbar-nav">
                    <form class="navbar-form navbar-left search-bar" action="/search" method="get">
                        <div class="input-group">
                            <spring:message code="layout.navbar.searchBar" var="searchPlaceholder"/>
                            <input name="query" id="searchBar" type="text" class="form-control" placeholder="${searchPlaceholder}">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="submit">
                                    <i class="glyphicon glyphicon-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                    <li><a href="/${pageContext.request.userPrincipal.name}/albums"><spring:message code="layout.navbar.myAlbums"/></a></li>
                    <li><a href="/createAlbum"><spring:message code="layout.navbar.createAlbum"/></a></li>
                    <li><a href="/${pageContext.request.userPrincipal.name}/orders"><spring:message code="layout.navbar.myOrders"/></a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/${pageContext.request.userPrincipal.name}/profile"><spring:message code="layout.navbar.welcomeUser" arguments="${pageContext.request.userPrincipal.name}"/></a></li>
                    <li><a onclick="document.forms['logoutForm'].submit()"><spring:message code="layout.navbar.logout"/></a></li>
                    <li class="languages"><a href="/?lang=pl">PL</a></li>
                    <li><a href="/?lang=en">ENG</a></li>
                </ul>
            </c:when>
            <c:otherwise>
                <ul class="nav navbar-nav">
                    <form class="navbar-form navbar-left search-bar" action="/search" method="get">
                        <div class="input-group">
                            <spring:message code="layout.navbar.searchBar" var="searchPlaceholder"/>
                            <input name="query" id="searchBar" type="text" class="form-control" placeholder="${searchPlaceholder}">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="submit">
                                    <i class="glyphicon glyphicon-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/login"><spring:message code="layout.navbar.login"/></a></li>
                    <li><a href="/registration"><spring:message code="layout.navbar.register"/></a></li>
                    <li class="languages"><a href="/?lang=pl">PL</a></li>
                    <li><a href="/?lang=en">ENG</a></li>
                </ul>
            </c:otherwise>
        </c:choose>
        </div>
    </div>

    <c:if test="${error != null}">
        <div class="alert alert-danger">
            <strong><spring:message code="flash.error"/></strong> ${error}
        </div>
    </c:if>
    <c:if test="${message != null}">
        <div class="alert alert-info">
            <strong><spring:message code="flash.info"/></strong> ${message}
        </div>
    </c:if>
    <c:if test="${success != null}">
        <div class="alert alert-success">
            <strong><spring:message code="flash.success"/></strong> ${success}
        </div>
    </c:if>

    <jsp:doBody/>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<script>
    $('#deleteModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var link = button.data('link'); // Extract info from data-deleteLink
        var modal = $(this);
        modal.find('#deleteForm').attr('action', link);
    });
</script>
</body>
</html>