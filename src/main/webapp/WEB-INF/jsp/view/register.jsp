<!DOCTYPE html>
<html>
    <head>
        <title>Registration</title>
    </head>
    <body>
        <h1>Register to forum</h1>
        <form:form method="post">
            <form:label path="username">Username:</form:label> 
            <form:input type="text" path="username" /> <br />
            <form:label path="password">Password:</form:label> 
            <form:input type="password" path="password" /> <br />
            <input type="submit" name="register" value="Register" />
        </form:form>
        <a href="<c:url value="./" />">Return to Home</a>
    </body>
</html>
