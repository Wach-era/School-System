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
    <title>Assign Subjects to Student</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
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
        h2, h3 {
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
        .form-group {
            margin-bottom: 15px;
        }
        .form-control {
            width: 100%;
            padding: 10px;
            margin: 5px 0 10px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .message {
            text-align: center;
            margin-bottom: 20px;
            color: green;
        }
        .error {
            text-align: center;
            margin-bottom: 20px;
            color: red;
        }
    </style>
    <script>
        function showAlert(message, isError) {
            let alertDiv = document.createElement('div');
            alertDiv.className = isError ? 'alert alert-danger' : 'alert alert-success';
            alertDiv.innerHTML = message;
            document.querySelector('.container').prepend(alertDiv);
            setTimeout(() => alertDiv.remove(), 3000);
        }

        document.addEventListener("DOMContentLoaded", function() {
            const urlParams = new URLSearchParams(window.location.search);
            const message = urlParams.get('message');
            const errorMessage = urlParams.get('errorMessage');

            if (message) {
                showAlert(message, false);
            } else if (errorMessage) {
                showAlert(errorMessage, true);
            }

            document.querySelector('form').addEventListener('submit', function(e) {
                // Ensure selected radio buttons are sent as hidden inputs
                document.querySelectorAll('input[type="radio"]:checked').forEach(radio => {
                    let hiddenInput = document.createElement('input');
                    hiddenInput.type = 'hidden';
                    hiddenInput.name = 'subjects';
                    hiddenInput.value = radio.value;
                    this.appendChild(hiddenInput);
                });
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h2>Assign Subjects to Student</h2>
    <form action="assign" method="post">
        <div class="form-group">
            <label for="studentId">Student ID:</label>
            <input type="text" name="studentId" id="studentId" class="form-control" required>
        </div>
        <h3>Subjects</h3>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Subject Name</th>
                    <th>Compulsory</th>
                    <th>Select</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="subject" items="${listSubjects}">
                    <tr>
                        <td>${subject.subjectName}</td>
                        <td>${subject.compulsory ? 'Yes' : 'No'}</td>
                        <td>
                            <c:if test="${subject.compulsory}">
                                <input type="checkbox" name="subjects" value="${subject.subjectName}" checked disabled>
                                <input type="hidden" name="subjects" value="${subject.subjectName}">
                            </c:if>
                            <c:if test="${!subject.compulsory}">
                                <input type="radio" name="subject_${subject.section}" value="${subject.subjectName}">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <input type="submit" value="Assign" class="btn btn-primary">
    </form>
    
    <a href="${pageContext.request.contextPath}/subjects/list" class="btn btn-info mt-3">Go Back to Subject List</a>
   <a href="${pageContext.request.contextPath}/students/list" class="btn btn-info mt-3">Confirm Subjects are Added</a>
    <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info mt-3">Go Back to Dashboard</a>
    <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger mt-3">Logout</a>
</div>
</body>
</html>
