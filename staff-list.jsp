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
    <title>Staff List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 20px;
        }
        .table {
            margin-top: 20px;
        }
        .table thead th {
            background-color: #007bff;
            color: #fff;
        }
        .btn {
            margin-right: 5px;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
        }
        .btn-info {
            background-color: #17a2b8;
            border: none;
        }
        .btn-danger {
            background-color: #dc3545;
            border: none;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center">Staff List</h2>
    <div class="mb-2">
        <a href="${pageContext.request.contextPath}/staff/new" class="btn btn-primary">Add New Staff</a>
        <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
        <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
    </div>
    <div class="table-responsive">
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Middle Name</th>
                    <th>Last Name</th>
                    <th>Date of Birth</th>
                    <th>Email</th>
                    <th>Phone Number</th>
                    <th>Gender</th>
                    <th>Religion</th>
                    <th>Title</th>
                    <th>Department</th>
                    <th>Date of Hire</th>
                    <th>Responsibilities</th>
                    <th>Education Level</th>
                    <th>Certification</th>
                    <th>Experience</th>
                    <th>Emergency Contact</th>
                    <th>Health Information</th>
                    <th>Special Accommodation</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="staff" items="${listStaff}">
                    <tr>
                        <td>${staff.staffId}</td>
                        <td>${staff.firstName}</td>
                        <td>${staff.middleName}</td>
                        <td>${staff.lastName}</td>
                        <td>${staff.dateOfBirth}</td>
                        <td>${staff.email}</td>
                        <td>${staff.phoneNumber}</td>
                        <td>${staff.gender}</td>
                        <td>${staff.religion}</td>
                        <td>${staff.title}</td>
                        <td>${staff.departmentId}</td>
                        <td>${staff.dateOfHire}</td>
                        <td>${staff.responsibilities}</td>
                        <td>${staff.educationLevel}</td>
                        <td>${staff.certification}</td>
                        <td>${staff.experience}</td>
                        <td>${staff.emergencyContact}</td>
                        <td>${staff.healthInformation}</td>
                        <td>${staff.specialAccommodation}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/staff/edit?id=${staff.staffId}" class="btn btn-info btn-sm">Edit</a>
                            <a href="${pageContext.request.contextPath}/staff/delete?id=${staff.staffId}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this staff member?');">Delete</a>
                            <a href="${pageContext.request.contextPath}/staff-attendance/new?staffId=${staff.staffId}" class="btn btn-success">Record Attendance</a>
                            <a href="${pageContext.request.contextPath}/staff-conduct/new?staffId=${staff.staffId}" class="btn btn-warning">Record Conduct</a>
                            <a href="${pageContext.request.contextPath}/staffreportservlet/generate?staffId=${staff.staffId}" class="btn btn-dark">Generate Report</a>
                
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</body>
</html>
