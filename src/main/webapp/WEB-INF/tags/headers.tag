<<<<<<< HEAD
<%@ attribute name="replaceMap" rtexprvalue="true" type="java.util.HashMap" %>
<c:forEach items="${replaceMap}" var="rm">
    <br/>:${rm}:
</c:forEach>
<c:forEach items="${replaceMap}" var="rm1">
    <br/>Item:${rm1.key}:${rm1.value}:${rm1}
=======
<%@ attribute name="replaceMap" rtexprvalue="true" type="java.util.HashMap" %>
<c:forEach items="${replaceMap}" var="rm">
    <br/>:${rm}:
</c:forEach>
<c:forEach items="${replaceMap}" var="rm1">
    <br/>Item:${rm1.key}:${rm1.value}:${rm1}
>>>>>>> f3364fb7cf661ead67597d3c62992013c4728ac3
</c:forEach>