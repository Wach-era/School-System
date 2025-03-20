<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Student Subjects</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
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
    </style>
</head>
<body>
<div class="container">
    <h2>Subjects for Student ID: ${studentId}</h2>
    <a href="${pageContext.request.contextPath}/students/list" class="btn btn-info mb-2">Back to Student List</a>
        <a href="${pageContext.request.contextPath}/student-subject/list" class="btn btn-primary mb-2">Add Another Student to Subject</a>
    <a href="${pageContext.request.contextPath}/student-subjects?action=deleteAll&studentId=${studentId}" class="btn btn-danger mb-2" onclick="return confirm('Are you sure you want to delete all subjects for this student?');">Delete All Subjects</a>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Subject Name</th>
                <th>Section</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="subject" items="${subjects}">
                <tr>
                    <td>${subject.subjectName}</td>
                    <td>${subject.section}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
