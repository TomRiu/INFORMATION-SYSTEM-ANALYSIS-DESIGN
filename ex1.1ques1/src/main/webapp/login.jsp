<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <form action="login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <button type="submit">Login</button>
    </form>
    <p style="color:red;">
        ${errorMessage}
    </p>
    <p style="color:green;">
        ${successMessage}
    </p>
    <p>
        <a href="forgot_password.jsp">Forgot Password?</a>
    </p>
    <p>Debug info: your current session ID is ${pageContext.session.id}</p>
</body>
</html>
