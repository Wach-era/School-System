<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Parent List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Parent List</h2>
    <a href="${pageContext.request.contextPath}/parents/new" class="btn btn-primary">Add New Parent</a>
    <a href="${pageContext.request.contextPath}/parentStudent/assign" class="btn btn-primary">Assign Students</a>
    <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
    <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
    
    <!-- Search and Filter Form -->
    <form method="get" action="${pageContext.request.contextPath}/parents/search">
        <div class="form-row">
            <div class="form-group col-md-6">
                <input type="text" class="form-control" name="query" placeholder="Search by name, phone, email, etc.">
            </div>
            <div class="form-group col-md-4">
                <select class="form-control" name="filter">
                    <option value="">All Categories</option>
                    <option value="name">Name</option>
                    <option value="phoneNumber">Phone Number</option>
                    <option value="email">Email</option>
                    <option value="gender">Gender</option>
                    <option value="religion">Religion</option>
                </select>
            </div>
            <div class="form-group col-md-2">
                <button type="submit" class="btn btn-primary">Search/Filter</button>
                <a href="${pageContext.request.contextPath}/parents/list" class="btn btn-secondary">Reset</a>
            </div>
        </div>
    </form>

    <table class="table table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Phone Number</th>
                <th>Email</th>
                <th>Gender</th>
                <th>Religion</th>
                <th>Assigned Students</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="parent" items="${listParent}">
                <tr>
                    <td>${parent.parentId}</td>
                    <td>${parent.name}</td>
                    <td>${parent.phoneNumber}</td>
                    <td>${parent.email}</td>
                    <td>${parent.gender}</td>
                    <td>${parent.religion}</td>
                    <td>
                        <c:forEach var="studentId" items="${parent.assignedStudents}">
                            ${studentId}<br>
                        </c:forEach>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/parents/edit?id=${parent.parentId}" class="btn btn-info">Edit</a>
                        <a href="${pageContext.request.contextPath}/parents/delete?id=${parent.parentId}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this parent?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
