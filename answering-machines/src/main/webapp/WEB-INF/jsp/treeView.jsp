<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<table>
   <c:forEach items="${node.prompts}" var="prompt">
       <tr><td>
       <c:out value="${prompt.name}"/>
       </td></tr>
   </c:forEach>
</table>
</body>
</html>