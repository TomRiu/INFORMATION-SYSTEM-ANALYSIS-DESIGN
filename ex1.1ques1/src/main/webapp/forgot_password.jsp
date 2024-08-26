<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forgot Password</title>
</head>
<body>
	<h2>Forgot Password</h2>
	<form action="forgotPassword" method="post">
		<label for="emailOrPhone">Enter Email or Phone:</label> <input
			type="text" id="emailOrPhone" name="emailOrPhone" required><br>
		<br>
		<button type="submit">Reset Password</button>
	</form>
	<br>
	<a href="login.jsp">Login</a>
	<p style="color: red;">${errorMessage}</p>
	<c:if test="${tempPassword != null}">
		<p style="color: green;">
			Here is your new password: ${tempPassword} <br>
			${successMessage}
		</p>
	</c:if>
</body>
</html>
