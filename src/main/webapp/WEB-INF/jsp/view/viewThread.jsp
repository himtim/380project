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
        <h2>Topic: <c:out value="${thread.title}" /></h2>
        <security:authorize access="hasRole('ADMIN')">            
            [<a href="<c:url value="../deleteThread/${thread.tid}" />">Delete this thread</a>]
        </security:authorize>
        Category: <c:out value="${thread.category}" /><br /><br />
        Content: <c:out value="${thread.content}" /><br /><br />
        <c:if test="${fn:length(attachments) > 0 }">
            Attachments:
            <c:forEach items="${attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="<c:url value="./${thread.tid}/attachment/${attachment.key}" />">
                    <c:out value="${attachment.key}" /></a>
            </c:forEach><br /><br />
        </c:if>
        <c:choose>
            <c:when test="${fn:length(replies) == 0}">
                <i>There are no replies in this thread.</i>
            </c:when>
            <c:otherwise>
                There are ${fn:length(replies)} reply in this thread.<br /><br />
                <c:forEach items="${replies}" var="reply">
                    <c:choose>
                        <c:when test="${reply.banning == 'Y'}">
                            Unavailable: show banned user - ${reply.username}<br/><br/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${reply.content}" />
                            by ${reply.username}
                            [<a href="<c:url value="../replyAttachment/${reply.rid}" />">view attachment</a>]
                            <security:authorize access="hasRole('ADMIN')">            
                                [<a href="<c:url value="../deleteReply/${reply.rid}" />">Delete</a>]
                            </security:authorize>
                            <br /><br />
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <br /><br />
        <a href="<c:url value="../addReply/${thread.tid}" />">Add a reply to this thread</a><br /><br />
        <a href="<c:url value="../listThread" />">Return to thread list</a>
    </body>
</html>
