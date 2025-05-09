<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Record Conduct</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
        <%@ include file="/WEB-INF/includes/header.jsp" %>
    
       <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            color: #333;
        }
        .container {
            width: 50%;
            margin: 50px auto;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-control {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            text-decoration: none;
            color: #fff;
            background-color: #007bff;
            border: 1px solid #007bff;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        .btn-success {
            background-color: #28a745;
            border-color: #28a745;
        }
        .btn-success:hover {
            background-color: #218838;
            border-color: #1e7e34;
        }
        .btn-secondary {
            background-color: #6c757d;
            border-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
            border-color: #545b62;
        }
       .table-container {
    margin-top: 30px;
}

.table {
    width: 100%;
    margin-bottom: 20px;
}

.table thead th {
    background-color: #f8f9fa;
    border-bottom: 2px solid #dee2e6;
    text-align: center; /* Center the header text */
}

.table tbody td {
    text-align: center; /* Center the body text */
    vertical-align: middle; /* Vertically center the body text */
}

.table tbody .btn {
    display: inline-block; /* Ensure buttons are inline */
    margin: 0 5px; /* Optional: Add some spacing between buttons */
}

    </style>
</head>
<body>
<div class="container">
    <h2>Record Conduct for ID: ${param.studentId}</h2>

   <form action="${pageContext.request.contextPath}/student-conduct/${conduct != null ? 'update' : 'insert'}" method="post">
    <input type="hidden" name="conductId" value="${conduct != null ? conduct.conductId : ''}" />
    <input type="hidden" name="studentId" value="${param.studentId}" />
    <div class="form-group">
        <label for="date">Date</label>
        <input type="date" id="date" name="date" class="form-control" required value="${conduct != null ? conduct.date : ''}">
    </div>
    <div class="form-group">
        <label for="conductDescription">Conduct Description</label>
        <textarea id="conductDescription" name="conductDescription" class="form-control" required>${conduct != null ? conduct.conductDescription : ''}</textarea>
    </div>
    <div class="form-group">
        <label for="actionTaken">Action Taken</label>
        <input type="text" id="actionTaken" name="actionTaken" class="form-control" required value="${conduct != null ? conduct.actionTaken : ''}">
    </div>
    <button type="submit" class="btn btn-primary">${conduct != null ? 'Update Conduct' : 'Record Conduct'}</button>
</form><br>

                <a href="${pageContext.request.contextPath}/students/list" class="btn btn-primary">Go Back</a>


    <div class="table-container">
        <h2>Previous Conduct Records</h2>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Conduct Description</th>
                    <th>Action Taken</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="conduct" items="${listConduct}">
                    <tr>
                        <td><fmt:formatDate value="${conduct.date}" pattern="yyyy-MM-dd" /></td>
                        <td>${conduct.conductDescription}</td>
                        <td>${conduct.actionTaken}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/student-conduct/edit?id=${conduct.conductId}&studentId=${param.studentId}" class="btn btn-primary">Edit</a>
                            <a href="${pageContext.request.contextPath}/student-conduct/delete?id=${conduct.conductId}&studentId=${param.studentId}" class="btn btn-danger">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
