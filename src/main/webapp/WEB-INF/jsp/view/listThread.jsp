<!DOCTYPE html>
<html>
    <head>
        <title>Forum</title>
    </head>
    <body>
        <security:authorize access="isAuthenticated()">
            <p>Hello! <security:authentication property="principal.username" />!
                <c:url var="logoutUrl" value="/logout"/>
                <form action="${logoutUrl}" method="post">
                    <input type="submit" value="Log out" />
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>    
            </security:authorize>
        <security:authorize access="isAnonymous()"><p>
                <a href="<c:url value="./register" />">Register</a> | 
                <a href="<c:url value="/login" />">Login</a>
        </security:authorize>

        <h2>Threads</h2>
        <a href="<c:url value="./addThread/" />">Create a Thread</a><br /><br />
        <c:choose>
            <c:when test="${fn:length(entries) == 0}">
                <i>There are no threads in the system.</i>
            </c:when>
            <c:otherwise>
                There are ${fn:length(entries)} threads in this forum.<br /><br />
                <c:forEach items="${entries}" var="entry">
                    <c:choose>
                        <c:when test="${entry.banning == 'Y'}">
                            Unavailable: banned user - ${entry.username}<br/><br/>
                        </c:when>
                        <c:otherwise>
                            Topic: <a href="<c:url value="./viewThread/${entry.tid}" />">
                                <c:out value="${entry.title}" /></a>
                                by ${entry.username}
                                <security:authorize access="hasRole('ADMIN')">            
                                [<a href="<c:url value="./deleteThread/${entry.tid}" />">Delete</a>]
                                </security:authorize>
                            <br /><br />
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <a href="<c:url value="../../" />">Return to Home</a>
    </body>
</html>
