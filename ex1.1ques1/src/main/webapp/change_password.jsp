<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Change Password</title>
</head>
<body>
    <h2>Change Password</h2>
    <form action="changePassword" method="post">
        <label for="newPassword">New Password:</label>
        <input type="password" id="newPassword" name="newPassword" required><br><br>
        <button type="submit">Change Password</button>
    </form>
    <p style="color:red;">
        ${errorMessage}
    </p>
    <p style="color:green;">
        ${successMessage}
    </p>
</body>
</html>
