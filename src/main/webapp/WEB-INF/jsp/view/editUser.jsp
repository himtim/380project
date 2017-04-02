<!DOCTYPE html>
<html>
    <head>
        <title>User Edit</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>User #${entry.uid}</h2>
        <form:form method="post" modelAttribute="userForm">
            <form:label path="username">Username:</form:label> 
            <form:input type="text" path="username" value="${entry.username}" /> <br />
            <form:label path="password">Password:</form:label> 
            <form:input type="password" path="password"  value="${entry.password}" /> <br />
            current role: <c:out value="${entry.role}" /> <br />
            <form:select path="role"> 
                <form:option value="ROLE_USER">User</form:option>
                <form:option value="ROLE_ADMIN">Admin</form:option>
            </form:select> <br />
            banning: <c:out value="${entry.banning}" /> <br />
            <form:select path="banning"> 
                <form:option value="N">No</form:option>
                <form:option value="Y">Yes</form:option>
            </form:select> <br />
            <input type="submit" value="Save" />
        </form:form>
        <a href="<c:url value="../" />">Return to list users</a>
    </body>
</html>