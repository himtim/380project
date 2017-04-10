<!DOCTYPE html>
<html>
    <head>
        <title>User of Forum</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>User List</h2>
        <a href="<c:url value="./add/" />">Add a new user</a><br /><br />
        There are ${fn:length(entries)} users in this forum.<br /><br />
        <c:forEach items="${entries}" var="entry">
            Uid: ${entry.uid} Username: ${entry.username}
            [<a href="<c:url value="./edit/${entry.uid}" />">Edit</a>] [<a href="<c:url value="./delete/${entry.uid}" />">Delete</a>]<br/>
            Banned: ${entry.banning} role: ${entry.role}
                    <br /><br />
        </c:forEach>
        <a href="<c:url value="../" />">Return to Home</a>
    </body>
</html>
