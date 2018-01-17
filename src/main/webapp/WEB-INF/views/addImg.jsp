<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link href="${contextPath}/resources/images/user.png">


    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Manage Profile</title>
</head>
<body>

<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">

        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <form:form method="GET" action="${contextPath}/addImag" modelAttribute="userImage" class="form-signin">

            ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a>
            <c:choose>
                <c:when test="${empty userImage}">
                    <img src="<c:url value='${contextPath}resources/images/user.png'/>" width="100" height="100"
                         class="img-rounded"/>
                </c:when>
                <c:otherwise>
                    <img src="data:image/jpeg;base64,${userImage}" width="100" height="100" class="img-circle"/>
                </c:otherwise>

            </c:choose>
        </form:form>


        <form:form method="POST" action="${contextPath}/addImg" modelAttribute="user" enctype="multipart/form-data"
                   class="form-signin">

            <spring:bind path="photoFile">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="file" path="photoFile" class="form-control"/>
                    <form:errors path="photoFile"/>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>

        </form:form>

    </c:if>
</div>
</body>
</html>
