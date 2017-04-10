<!DOCTYPE html>
<html>
    <head>
        <title>Poll</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Create a new Poll</h2>
        <form:form method="POST">
            <form:label path="question">Question</form:label><br/>
            <form:input type="text" path="question" /><br/><br/>
            <form:label path="choiceA">choice A</form:label><br/>
            <form:input type="text" path="choiceA" /><br/><br/>
            <form:label path="choiceB">choice B</form:label><br/>
            <form:input type="text" path="choiceB" /><br/><br/>
            <form:label path="choiceC">choice C</form:label><br/>
            <form:input type="text" path="choiceC" /><br/><br/>
            <form:label path="choiceD">choice D</form:label><br/>
            <form:input type="text" path="choiceD" /><br/><br/>
            <input type="submit" name="addPoll" value="Submit"/>
        </form:form>
        <a href="<c:url value="../" />">Return to Home</a>
    </body>
</html>
