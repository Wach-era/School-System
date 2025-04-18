<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Student Academic Transcript</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <%@ include file="/WEB-INF/includes/header.jsp" %>
    
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            line-height: 1.6;
        }
        .container {
            width: 80%;
            margin: 50px auto;
            background-color: #fff;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 30px;
        }
        h2 {
            color: #343a40;
            text-align: center;
            margin-bottom: 30px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 40px; /* Reduced spacing */
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #dee2e6;
        }
        th {
            background-color: #f2f2f2;
            font-weight: 600;
        }
        .gpa-card {
            background: linear-gradient(135deg, #4e73df 0%, #224abe 100%);
            color: white;
            border-radius: 10px;
            padding: 25px;
            margin-bottom: 40px; /* Reduced spacing */
        }
        .badge {
            font-size: 0.9rem;
            margin-right: 8px;
            padding: 6px 10px;
        }
        .chart-container {
            height: 300px;
            position: relative;
            margin-bottom: 40px; /* Reduced spacing */
        }
        .subject-row:hover {
            background-color: #f8f9fa;
        }
        .performance-indicator {
            width: 15px;
            height: 15px;
            border-radius: 50%;
            display: inline-block;
            margin-right: 5px;
        }
        .improving { background-color: #28a745; }
        .declining { background-color: #dc3545; }
        .stable { background-color: #ffc107; }
        .alert-info {
            background-color: #d1ecf1;
            border-color: #bee5eb;
            color: #0c5460;
            padding: 15px;
            border-radius: 5px;
        }
        .alert-info h5 {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Student Academic Transcript</h2>

    <a href="${pageContext.request.contextPath}/students/list" class="btn btn-secondary mb-3"><i class="fas fa-arrow-left"></i> Back to Student List</a>

    <form action="transcripts" method="get" class="mb-4">
        <input type="hidden" name="studentId" value="${studentId}">
        <div class="form-group">
            <label for="trimesterId">Select Trimester:</label>
            <select name="trimesterId" id="trimesterId" class="form-control" onchange="this.form.submit()">
                <c:forEach items="${trimesters}" var="trimester">
                    <option value="${trimester.trimesterId}" ${trimester.trimesterId eq currentTrimester ? 'selected' : ''}>
                        ${trimester.trimesterId}
                    </option>
                </c:forEach>
            </select>
        </div>
    </form>

    <div class="mb-4">
        <h4>Student: ${studentFullName}</h4>
    </div>

    <c:if test="${not empty resultsBySubject}">
        <table class="table table-bordered">
            <thead class="thead-light">
                <tr>
                    <th>Subject</th>
                    <th>Average Score (%)</th>
                    <th>Grade</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${resultsBySubject}" var="subject">
                    <tr>
                        <td>${subject.key}</td>
                        <td><fmt:formatNumber value="${subject.value}" maxFractionDigits="2"/></td>
                        <td>${studentGrades[studentId][subject.key]}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div class="gpa-card">
        <div class="row">
            <div class="col-md-4 text-center">
                <h5 class="text-white">Current GPA</h5>
                <h1 class="display-4 text-white"><fmt:formatNumber value="${gpa}" maxFractionDigits="2"/></h1>
            </div>
            <div class="col-md-8">
                <div class="chart-container">
                    <canvas id="gpaChart"></canvas>
                </div>
            </div>
        </div>
        <p class="mt-3">
            <span class="performance-indicator ${performanceTrend.toLowerCase()}"></span>
            <strong>Trend:</strong> ${performanceTrend}
        </p>
    </div>

    <c:if test="${not empty badges}">
        <div class="mb-4">
            <h5><i class="fas fa-trophy"></i> Achievements</h5>
            <c:forEach items="${badges}" var="badge">
                <span class="badge bg-warning text-dark">
                    <i class="fas fa-award"></i> ${badge}
                </span>
            </c:forEach>
        </div>
    </c:if>

    <table class="table table-bordered">
        <thead class="thead-light">
            <tr>
                <th>Subject</th>
                <th>Your Score</th>
                <th>Class Average</th>
                <th>Grade</th>
                <th>Comparison</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${resultsBySubject}" var="subject">
                <tr class="subject-row">
                    <td>${subject.key}</td>
                    <td><fmt:formatNumber value="${subject.value}" maxFractionDigits="1"/>%</td>
                    <td><fmt:formatNumber value="${classAverages[subject.key]}" maxFractionDigits="1"/>%</td>
                    <td>${studentGrades[studentId][subject.key]}</td>
                    <td>
                        <c:choose>
                            <c:when test="${subject.value > classAverages[subject.key] + 5}">
                                <span class="text-success"><i class="fas fa-arrow-up"></i> Above average</span>
                            </c:when>
                            <c:when test="${subject.value < classAverages[subject.key] - 5}">
                                <span class="text-danger"><i class="fas fa-arrow-down"></i> Below average</span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-muted"><i class="fas fa-equals"></i> Average</span>
                            </c:otherwise>
                        </c:choose>
                        <small class="text-muted"> (${classAverages[subject.key] - subject.value > 0 ? '+' : ''}<fmt:formatNumber value="${classAverages[subject.key] - subject.value}" maxFractionDigits="1"/>%)</small>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="alert alert-info">
        <h5><i class="fas fa-info-circle"></i> GPA Calculation</h5>
        <p>
            Your GPA of <strong><fmt:formatNumber value="${gpa}" maxFractionDigits="2"/></strong> is based on the following scale:
            A (90-100%)=4.0, B (80-89%)=3.5, C (70-79%)=3.0, D (60-69%)=2.5, E (50-59%)=2.0, F (below 50%)=1.0
        </p>
    </div>
    
    <div class="alert alert-info">
    <h5><i class="fas fa-info-circle"></i> Achievement Calculation</h5>
    <p>
        Achievements are determined based on your GPA and subject scores.
        <br/><br/>
        <strong>GPA Achievements:</strong>
        <ul>
            <li>Honor Roll: GPA of 3.5 or higher.</li>
            <li>High Honors: GPA of 3.8 or higher.</li>
        </ul>
        <br/>
        <strong>Subject Score Achievements:</strong>
        <ul>
            <li>Subject Expert: At least one subject score of 90 or higher.</li>
            <li>Multi-Subject Expert: At least three subject scores of 90 or higher.</li>
            <li>Consistent Performer: No subject score below 70.</li>
        </ul>
    </p>
</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const ctx = document.getElementById('gpaChart').getContext('2d');
    const gpaData = {
        labels: [<c:forEach items="${historicalGPA}" var="gpa">"${gpa.key}",</c:forEach>],
        datasets: [{
            label: 'GPA Trend',
            data: [<c:forEach items="${historicalGPA}" var="gpa">${gpa.value},</c:forEach>],
            borderColor: '#FF6B6B', // Vibrant coral red border
            backgroundColor: 'rgba(100, 210, 255, 0.6)', // Bright sky blue fill
            pointBackgroundColor: '#4ECDC4', // Turquoise points
            pointBorderColor: '#fff',
            pointHoverRadius: 8,
            borderWidth: 4,
            fill: true,
            tension: 0.3
        }]
    };
    
    new Chart(ctx, {
        type: 'line',
        data: gpaData,
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { 
                    display: true,
                    labels: {
                        color: '#333',
                        font: {
                            weight: 'bold',
                            size: 14
                        }
                    }
                },
                tooltip: {
                    backgroundColor: '#292F36',
                    titleColor: '#FF6B6B',
                    bodyColor: '#F7FFF7',
                    borderColor: '#4ECDC4',
                    borderWidth: 2,
                    callbacks: {
                        label: function(context) {
                            return 'GPA: ' + context.raw.toFixed(2);
                        }
                    }
                }
            },
            scales: {
                y: {
                    min: 0,
                    max: 4.0,
                    ticks: {
                        color: '#292F36', // Dark gray
                        font: {
                            weight: 'bold'
                        },
                        stepSize: 0.5
                    },
                    grid: {
                        color: 'rgba(41, 47, 54, 0.1)' // Subtle grid
                    },
                    border: {
                        color: '#292F36'
                    }
                },
                x: {
                    ticks: {
                        color: '#292F36', // Dark gray
                        font: {
                            weight: 'bold'
                        }
                    },
                    grid: {
                        color: 'rgba(41, 47, 54, 0.1)'
                    },
                    border: {
                        color: '#292F36'
                    }
                }
            },
            elements: {
                line: {
                    tension: 0.3
                },
                point: {
                    radius: 5,
                    hoverRadius: 8
                }
            }
        }
    });
});
</script>
</body>
</html>