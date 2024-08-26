<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
</head>
<body>
    <c:choose>
        <c:when test="${sessionScope.customer != null}">
            <h2>Welcome, ${sessionScope.customer.username}!</h2>
            <p>
                <a href="change_password.jsp">Change Password</a> | 
                <a href="logout">Logout</a>
            </p>
        </c:when>
        <c:otherwise>
            <p>Please <a href="login.jsp">login</a> first.</p>
        </c:otherwise>
    </c:choose>
    <p>Debug info: your current session ID is ${pageContext.session.id}</p>
</body>
</html>
