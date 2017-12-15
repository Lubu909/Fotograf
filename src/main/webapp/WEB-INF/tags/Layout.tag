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

<%--<script type="text/javascript">--%>
<%--function inputFocus(i){--%>
<%--if(i.value==i.defaultValue){ i.value=""; i.style.color="#000"; }--%>
<%--}--%>
<%--function inputBlur(i){--%>
<%--if(i.value==""){ i.value=i.defaultValue; i.style.color="#848484"; }--%>
<%--}--%>
<%--</script>--%>

<%--<script>--%>
<%--$(document).ready(function() {--%>
<%--//attach autocomplete--%>
<%--$("#searchBar").autocomplete({--%>
<%--minLength: 1,--%>
<%--delay: 500,--%>
<%--//define callback to format results--%>
<%--source: function (request, response) {--%>
<%--$.getJSON("/searchJSON", request, function(result) {--%>
<%--response($.map(result, function(user) {--%>
<%--return {--%>
<%--// following property gets displayed in drop down--%>
<%--label: user.name + " " + user.surname + " - " + user.username,--%>
<%--// following property gets entered in the textbox--%>
<%--value: user.name,--%>
<%--// following property is added for our own use--%>
<%--tag_url: "http://" + window.location.host + "/" + user.username + "/albums"--%>
<%--}--%>
<%--}));--%>
<%--});--%>
<%--},--%>

<%--//define select handler--%>
<%--select : function(event, ui) {--%>
<%--if (ui.item) {--%>
<%--event.preventDefault();--%>
<%--$("#selected_tags span").append('<a href=" + ui.item.tag_url + " target="_blank">'+ ui.item.label +'</a>');--%>
<%--//$("#tagQuery").value = $("#tagQuery").defaultValue--%>
<%--var defValue = $("#searchBar").prop('defaultValue');--%>
<%--$("#searchBar").val(defValue);--%>
<%--$("#searchBar").blur();--%>
<%--return false;--%>
<%--}--%>
<%--}--%>
<%--});--%>
<%--});--%>
<%--</script>--%>

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
                    <li><a href="/${pageContext.request.userPrincipal.name}/albums"><spring:message code="layout.navbar.myAlbums"/></a></li>
                    <li><a href="/createAlbum"><spring:message code="layout.navbar.createAlbum"/></a></li>
                    <form class="navbar-form navbar-left" action="/search" method="get">
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
                    <li><a href="/"><spring:message code="layout.navbar.welcomeUser" arguments="${pageContext.request.userPrincipal.name}"/></a></li>
                    <li><a onclick="document.forms['logoutForm'].submit()"><spring:message code="layout.navbar.logout"/></a></li>
                </ul>
            </c:when>
            <c:otherwise>
                <ul class="nav navbar-nav">
                    <form class="navbar-form navbar-left" action="/search" method="get">
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
                </ul>
            </c:otherwise>
        </c:choose>
        </div>
    </nav>

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
</body>
</html>