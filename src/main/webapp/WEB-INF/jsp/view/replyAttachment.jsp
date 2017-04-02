<!DOCTYPE html>
<html>
    <head>
        <title>Forum</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Attachment of reply: </h2>
        <br /><br />
        <c:choose>
            <c:when test="${fn:length(attachments) > 0 }">
            Attachments:
                <c:forEach items="${attachments}" var="attachment"
                           varStatus="status">
                    <c:if test="${!status.first}">, </c:if>
                    <a href="<c:url value="./${rid}/attachment/${attachment.key}" />">
                        <c:out value="${attachment.key}" /></a>
                </c:forEach><br /><br />
            </c:when>
            <c:otherwise>
                There are no attachment in this reply. <br /><br />
            </c:otherwise>
        </c:choose>
        <a href="<c:url value="../listThread" />">Return to thread list</a>
    </body>
</html>