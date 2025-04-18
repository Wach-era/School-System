<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>Subjects</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
            <%@ include file="/WEB-INF/includes/header.jsp" %>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            color: #333;
        }
        .container {
            width: 80%;
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            border: 1px solid #ddd;
            background-color: #f9f9f9;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #333;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .btn {
            display: inline-block;
            padding: 8px 16px;
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
        .btn-danger {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn-danger:hover {
            background-color: #c82333;
            border-color: #bd2130;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Subjects</h2>
    <a href="${pageContext.request.contextPath}/teacher-subject/list" class="btn btn-primary mb-2">Add Teacher to Subject</a>
    <a href="${pageContext.request.contextPath}/student-subject/list" class="btn btn-primary mb-2">Add Student to Subject</a>
    <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
    <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Subject Name</th>
                <th>Subject Code</th>
                <th>Department</th>
                <th>Compulsory</th>
                <th>Section</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="subject" items="${listSubject}">
                <tr>
                    <td>${subject.subjectName}</td>
                    <td>${subject.subjectCode}</td>
                    <td>${subject.departmentId}</td>
                    <td>${subject.compulsory ? 'Yes' : 'No'}</td>
                    <td>${subject.section}</td>
                    <td>
                        <c:if test="${!subject.compulsory}">
                            <form action="${pageContext.request.contextPath}/subject-students" method="get">
                                <input type="hidden" name="subjectName" value="${subject.subjectName}">
                                <button type="submit" class="btn btn-primary">View Students Doing this Subject</button>
                            </form>
                        </c:if>
                         <form action="${pageContext.request.contextPath}/teacher-subject/subject-teachers" method="get">
                            <input type="hidden" name="subjectName" value="${subject.subjectName}">
                            <button type="submit" class="btn btn-primary">View Teachers Teaching this Subject</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
