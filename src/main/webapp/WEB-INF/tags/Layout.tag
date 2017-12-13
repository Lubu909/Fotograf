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
</head>
<body>

<div class="container">

    <nav class="navbar navbar-default">
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
                    <li><a href="/${pageContext.request.userPrincipal.name}/albums">My albums</a></li>
                    <li><a href="/createAlbum">Create album</a></li>
                    <form class="navbar-form navbar-left">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Search">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="submit">
                                    <i class="glyphicon glyphicon-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/">Welcome ${pageContext.request.userPrincipal.name}</a></li>
                    <li><a onclick="document.forms['logoutForm'].submit()">Logout</a></li>
                </ul>
            </c:when>
            <c:otherwise>
                <ul class="nav navbar-nav">
                    <form class="navbar-form navbar-left">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Search">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="submit">
                                    <i class="glyphicon glyphicon-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/login">Login</a></li>
                    <li><a href="/registration">Register</a></li>
                </ul>
            </c:otherwise>
        </c:choose>
        </div>
    </nav>

    <c:if test="${error != null}">
        <div class="alert alert-danger">
            <strong>Error!</strong> ${error}
        </div>
    </c:if>
    <c:if test="${message != null}">
        <div class="alert alert-info">
            <strong>Info!</strong> ${message}
        </div>
    </c:if>
    <c:if test="${success != null}">
        <div class="alert alert-success">
            <strong>Success!</strong> ${success}
        </div>
    </c:if>

    <jsp:doBody/>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>