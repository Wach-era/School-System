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
    <title>Record Attendance</title>
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
            text-align: center;
        }
        .table tbody td {
            text-align: center;
            vertical-align: middle;
        }
        .table tbody .btn {
            display: inline-block;
            margin: 0 5px;
        }
        /* Added for chart */
        .chart-container {
            margin: 25px 0;
            height: 300px;
        }
        .chart-filter {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Record Attendance for ID: ${param.studentId} </h2>

    <form action="${pageContext.request.contextPath}/student-attendance/${attendance != null ? 'update' : 'insert'}" method="post">
    <input type="hidden" name="attendanceId" value="${attendance != null ? attendance.attendanceId : ''}" />
    <input type="hidden" name="studentId" value="${param.studentId}" />
    <div class="form-group">
        <label for="date">Date</label>
        <input type="date" id="date" name="date" class="form-control" required value="${attendance != null ? attendance.date : ''}">
    </div>
    <div class="form-group">
        <label for="status">Status</label>
        <select id="status" name="status" class="form-control">
            <option value="Present" ${attendance != null && attendance.status == 'Present' ? 'selected' : ''}>Present</option>
            <option value="Absent" ${attendance != null && attendance.status == 'Absent' ? 'selected' : ''}>Absent</option>
            <option value="Late" ${attendance != null && attendance.status == 'Late' ? 'selected' : ''}>Late</option>
        </select>
    </div>
    <button type="submit" class="btn btn-primary">${attendance != null ? 'Update Attendance' : 'Record Attendance'}</button>
    </form><br>

    <div class="chart-filter">
        <h4>Attendance Chart</h4>
        <form id="chartFilter" class="row">
            <div class="col-md-5">
                <label class="form-label">From Date</label>
                <input type="date" name="startDate" class="form-control">
            </div>
            <div class="col-md-5">
                <label class="form-label">To Date</label>
                <input type="date" name="endDate" class="form-control">
            </div>
            <div class="col-md-2" style="display: flex; align-items: flex-end;">
                <button type="submit" class="btn btn-primary">Update</button>
            </div>
            <input type="hidden" name="studentId" value="${param.studentId}">
        </form>
    </div>
    
    <div class="chart-container">
        <canvas id="attendanceChart"></canvas>
    </div>

    <a href="${pageContext.request.contextPath}/students/list" class="btn btn-primary">Go Back</a>

    <div class="table-container">
        <h2>Previous Attendance Records</h2>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="attendance" items="${attendanceList}">
                    <tr>
                        <td><fmt:formatDate value="${attendance.date}" pattern="yyyy-MM-dd" /></td>
                        <td>${attendance.status}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/student-attendance/edit?attendanceId=${attendance.attendanceId}&studentId=${param.studentId}" class="btn btn-primary">Edit</a>
                            <a href="${pageContext.request.contextPath}/student-attendance/delete?attendanceId=${attendance.attendanceId}&studentId=${param.studentId}" class="btn btn-danger">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    const ctx = document.getElementById('attendanceChart').getContext('2d');
    let attendanceChart;
    
    function loadChart(params = '') {
        fetch('${pageContext.request.contextPath}/student-attendance/data?' + params)
            .then(response => response.json())
            .then(data => {
                if (attendanceChart) {
                    attendanceChart.destroy();
                }
                
                attendanceChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: data.labels,
                        datasets: [
                            {
                                label: 'Present',
                                data: data.present,
                                backgroundColor: '#28a745'
                            },
                            {
                                label: 'Absent',
                                data: data.absent,
                                backgroundColor: '#dc3545'
                            },
                            {
                                label: 'Late',
                                data: data.late,
                                backgroundColor: '#ffc107'
                            }
                        ]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    stepSize: 1
                                }
                            }
                        }
                    }
                });
            });
    }
    
    // Initial load with current studentId
    loadChart('studentId=${param.studentId}');
    
    // Filter form handler
    document.getElementById('chartFilter').addEventListener('submit', function(e) {
        e.preventDefault();
        const params = new URLSearchParams(new FormData(this));
        loadChart(params);
    });
});
</script>
</body>
</html>