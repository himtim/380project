<!DOCTYPE html>
<html>
    <head>
        <title>Admin: add a new user</title>
    </head>
    <body>
        <h1>Add new user to forum</h1>
        <form:form method="post">
            <form:label path="username">Username:</form:label> 
            <form:input type="text" path="username" /> <br />
            <form:label path="password">Password:</form:label> 
            <form:input type="password" path="password" /> <br />
            <form:select path="role">
                <form:option value="ROLE_USER">User</form:option>
                <form:option value="ROLE_ADMIN">Admin</form:option>
            </form:select> <br />
            <input type="submit" name="add" value="Add" />
        </form:form>
    </body>
</html>
