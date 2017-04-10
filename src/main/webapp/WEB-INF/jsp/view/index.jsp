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
        </p>
        <p><a href="<c:url value="./forum/lecture/" />">Lecture Forum</a> | 
            <a href="<c:url value="./forum/lab/" />">Lab Forum</a> | 
            <a href="<c:url value="./forum/other/" />">Other Forum</a></p>
        <br /><br />
        <c:choose>
            <c:when test="${poll.question!=null}">
                Question: ${poll.question}<br/>
                Number of Users voted: ${poll.noA+poll.noB+poll.noC+poll.noD}<br/>
                
                <c:if test="${poll.choiceA!=''}">
                      A:${poll.choiceA} have ${poll.noA} votes<security:authorize access="hasAnyRole('ADMIN','USER')">[<a href="<c:url value="./vote/A" />">vote A</a>]</security:authorize><br />
                </c:if>
                <c:if test="${poll.choiceB!=''}">
                      B:${poll.choiceB} have ${poll.noB} votes<security:authorize access="hasAnyRole('ADMIN','USER')">[<a href="<c:url value="./vote/B" />">vote B</a>]</security:authorize><br />
                </c:if>
                <c:if test="${poll.choiceC!=''}">
                      C:${poll.choiceC} have ${poll.noC} votes<security:authorize access="hasAnyRole('ADMIN','USER')">[<a href="<c:url value="./vote/C" />">vote C</a>]</security:authorize><br />
                </c:if>
                <c:if test="${poll.choiceD!=''}">
                      D:${poll.choiceD} have ${poll.noD} votes<security:authorize access="hasAnyRole('ADMIN','USER')">[<a href="<c:url value="./vote/D" />">vote D</a>]</security:authorize><br />
                </c:if>
                <security:authorize access="hasRole('ADMIN')">[<a href="<c:url value="./deletePoll/" />">Delete Poll</a>]</security:authorize> <br /><br/>
            </c:when>
            <c:otherwise>
                There are no poll now.<br/><br/>
            </c:otherwise>
        </c:choose>
        <security:authorize access="hasRole('ADMIN')">            
             admin option:
             [<a href="<c:url value="./user/" />">User List</a>] 
             [<a href="<c:url value="./addPoll/" />">Create poll</a>]
        </security:authorize>
    </body>
</html>
