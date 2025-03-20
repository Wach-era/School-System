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
    <title>Teacher List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            font-size: 12px; /* Reduce overall font size */
        }

        .container {
            max-width: 100%; /* Allow full-width container */
            margin-top: 30px;
            overflow-x: auto; /* Add horizontal scrolling for large tables */
        }

        table {
            table-layout: auto;
            font-size: 12px; /* Reduce font size inside the table */
            width: 100%; /* Ensure table takes full width */
        }

        th, td {
            padding: 8px 10px; /* Adjust padding to save space */
        }

        th {
            white-space: nowrap; /* Prevent header text wrapping */
        }

        .action-links {
            display: grid;
            grid-template-columns: repeat(2, auto);
            grid-gap: 5px;
        }

        .action-links a {
            display: inline-block;
            padding: 6px;
            font-size: 12px;
            text-align: center;
            border-radius: 4px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .action-links a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Teacher List</h2>
    <div class="btn-group mb-4">
        <a href="${pageContext.request.contextPath}/teachers/new" class="btn btn-primary">Add New Teacher</a>
        <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
        <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
    </div>
    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
            <tr>
                <th>National ID</th>
                <th>Name</th>
                <th>Date of Birth</th>
                <th>Email</th>
                <th>Phone Number</th>
                <th>Gender</th>
                <th>Religion</th>
                <th>Degrees</th>
                <th>Majors</th>
                <th>Institution</th>
                <th>Date of Graduation</th>
                <th>Position</th>
                <th>Date of Hire</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="teacher" items="${listTeachers}">
                <tr>
                    <td>${teacher.nationalId}</td>
                    <td>${teacher.firstName} ${teacher.middleName} ${teacher.lastName}</td>
                    <td>${teacher.dateOfBirth}</td>
                    <td>${teacher.email}</td>
                    <td>${teacher.phoneNumber}</td>
                    <td>${teacher.gender}</td>
                    <td>${teacher.religion}</td>
                    <td>${teacher.degrees}</td>
                    <td>${teacher.majors}</td>
                    <td>${teacher.institution}</td>
                    <td>${teacher.dateOfGraduation}</td>
                    <td>${teacher.position}</td>
                    <td>${teacher.dateOfHire}</td>
                    <td>
                        <div class="action-links">
                            <a href="${pageContext.request.contextPath}/teachers/edit?nationalId=${teacher.nationalId}">Edit</a>
                            <a href="${pageContext.request.contextPath}/teachers/delete?teacherId=${teacher.nationalId}" onclick="return confirm('Are you sure you want to delete this teacher?');">Delete</a>
                            <a href="${pageContext.request.contextPath}/teacher-subject/view?nationalId=${teacher.nationalId}">View Subjects</a>
                            <a href="${pageContext.request.contextPath}/teacher-attendance/new?nationalId=${teacher.nationalId}">Record Attendance</a>
                            <a href="${pageContext.request.contextPath}/teacher-conduct/new?nationalId=${teacher.nationalId}">Record Conduct</a>
                            <a href="${pageContext.request.contextPath}/teacherreportservlet/generate?nationalId=${teacher.nationalId}">Generate Report</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
