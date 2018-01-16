<%@ attribute name="replaceMap" rtexprvalue="true" type="java.util.HashMap" %>
<c:forEach items="${replaceMap}" var="rm">
    <br/>:${rm}:
</c:forEach>
<c:forEach items="${replaceMap}" var="rm1">
    <br/>Item:${rm1.key}:${rm1.value}:${rm1}
</c:forEach>