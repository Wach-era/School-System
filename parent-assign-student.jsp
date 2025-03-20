<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Assign Student to Parent</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Assign Student to Parent</h2>
        <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
    <form action="${pageContext.request.contextPath}/parentStudent/insert" method="post">
        <div class="form-group">
            <label for="parentId">Parent ID</label>
            <input type="text" class="form-control" id="parentId" name="parentId" required>
        </div>
        <div class="form-group">
            <label for="studentId">Student ID</label>
            <input type="text" class="form-control" id="studentId" name="studentId" required>
        </div>
        <button type="submit" class="btn btn-primary">Assign Student</button>
        <a href="${pageContext.request.contextPath}/parents/list" class="btn btn-secondary">Cancel</a>
    </form>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger mt-2">${errorMessage}</div>
    </c:if>
</div>
</body>
</html>
