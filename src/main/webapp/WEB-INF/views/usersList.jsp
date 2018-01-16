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


    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>
<body>


<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
    <form id="logoutForm" method="POST" action="${contextPath}/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <form:form method="POST" action="/usersList" modelAttribute="user" class="form-signin">
        <h2 class="form-signin-heading">Create your account</h2>

        <spring:bind path="id">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="id" class="form-control" placeholder="Id"/>
                <form:errors path="id"/>
            </div>
        </spring:bind>

        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="username" class="form-control" placeholder="Username"
                            autofocus="true"/>
                <form:errors path="username"/>
            </div>
        </spring:bind>

        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="name" class="form-control"
                            placeholder="Enter your name"/>
                <form:errors path="name"/>
            </div>
        </spring:bind>

        <spring:bind path="surname">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="surname" class="form-control"
                            placeholder="Enter your surname"/>
                <form:errors path="name"/>
            </div>
        </spring:bind>

        <spring:bind path="city">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="city" class="form-control"
                            placeholder="Enter your city"/>
                <form:errors path="name"/>
            </div>
        </spring:bind>

        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="email" class="form-control"
                            placeholder="Enter your e-mail"/>
                <form:errors path="email"/>
            </div>
        </spring:bind>

        <spring:bind path="tel">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="tel" class="form-control"
                            placeholder="Enter your phone number"/>
                <form:errors path="tel"/>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

    <h3>Users List</h3>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <form method="GET" action="${contextPath}/usersList">
            <c:if test="${!empty usersList}">
                <table border="1">
                    <tr>
                        <th width="80">USER ID</th>
                        <th width="120">USERNAME</th>
                        <th width="120">NAME</th>
                        <th width="120">SURNAME</th>
                        <th width="120">CITY</th>
                        <th width="120">EMAIL</th>
                        <th width="120">PHONE NUMBER</th>
                        <th width="60">EDIT</th>
                        <th width="60">DELETE</th>
                    </tr>
                    <c:forEach items="${usersList}" var="user">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <td>${user.city}</td>
                            <td>${user.email}</td>
                            <td>${user.tel}</td>
                            <td><a href="<c:url value='/edit/${user.id}'/>">EDIT</a></td>
                            <td><a href="<c:url value='/remove/${user.id}'/>">DELETE</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </form>
    </c:if>
</div>


</c:if>


<%--</div>--%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>