<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Report Generator</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <%@ include file="/WEB-INF/includes/header.jsp" %>

    <style>
        body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #e9ecef;
        color: #333;
        margin: 0;
        padding: 0;
    }
    .container {
        width: 70%;
        margin: 100px auto;
        padding-top: 1000px; 
        background-color: #fff;
        border-radius: 10px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        padding: 40px;
    }
        h1 {
            text-align: center;
            color: #343a40;
            font-size: 2.5rem;
            margin-bottom: 40px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        .form-group {
            margin-bottom: 25px;
        }
        label {
            display: block;
            font-weight: 600;
            margin-bottom: 10px;
            color: #495057;
        }
        .form-control {
            width: 100%;
            padding: 12px;
            border: 2px solid #ced4da;
            border-radius: 8px;
            font-size: 1rem;
        }
        .btn {
            padding: 12px 24px;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            display: inline-block;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
            color: #fff;
            text-transform: uppercase;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
            border: none;
            color: #fff;
            text-transform: uppercase;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        .btn-success {
            background-color: #28a745;
            border: none;
            color: #fff;
            text-transform: uppercase;
        }
        .btn-success:hover {
            background-color: #218838;
        }
        .checkbox-group label {
            display: inline-block;
            margin-right: 15px;
            font-weight: normal;
        }
        .table-container {
            margin-top: 50px;
        }
        .table {
            width: 100%;
            margin-bottom: 30px;
            border-collapse: separate;
            border-spacing: 0;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        .table thead th {
            background-color: #007bff;
            color: #fff;
            padding: 12px;
            text-align: left;
            font-weight: 600;
        }
        .table tbody td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #dee2e6;
        }
        .table tbody tr:last-child td {
            border-bottom: none;
        }
        .table tbody tr:hover {
            background-color: #f8f9fa;
        }
        .icon {
            margin-right: 8px;
            vertical-align: middle;
        }
        /* New styles for enhancements */
        .summary-card {
            border-left: 4px solid #007bff;
            transition: all 0.3s;
        }
        .summary-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .report-section {
            margin-bottom: 30px;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            padding: 15px;
        }
        .report-section h3 {
            cursor: pointer;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
        .section-content {
            display: block;
        }
        .collapsed .section-content {
            display: none;
        }
        .date-presets {
            margin-bottom: 15px;
        }
        .date-presets .btn {
            padding: 5px 10px;
            margin-right: 5px;
            font-size: 0.8rem;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Report for ID: ${param.studentId}</h1>
    <form action="${pageContext.request.contextPath}/reportservlet/create" method="post" onsubmit="return validateForm()">
        <input type="hidden" name="studentId" value="${param.studentId}">
        <div class="d-flex justify-content-between mb-4">
            <div>
                <a href="${pageContext.request.contextPath}/students/list" class="btn btn-secondary">Go Back</a>
                <a href="${pageContext.request.contextPath}/reportservlet/generate?studentId=${param.studentId}" class="btn btn-secondary">Refresh</a>
            </div>
            <div>
                <input type="submit" value="Generate Report" class="btn btn-primary">
                <button type="button" onclick="generatePDF()" class="btn btn-success ml-2">
                    <i class="fas fa-file-pdf"></i> Export PDF
                </button>
            </div>
        </div>

        <div class="form-group">
            <label for="startDate">Start Date:</label>
            <input type="date" id="startDate" name="startDate" class="form-control">
        </div>
        <div class="form-group">
            <label for="endDate">End Date:</label>
            <input type="date" id="endDate" name="endDate" class="form-control">
        </div>

        <div class="date-presets">
            <small class="text-muted">Quick select: </small>
            <button type="button" class="btn btn-sm btn-outline-secondary" onclick="setDateRange('week')">This Week</button>
            <button type="button" class="btn btn-sm btn-outline-secondary" onclick="setDateRange('month')">This Month</button>
            <button type="button" class="btn btn-sm btn-outline-secondary" onclick="setDateRange('year')">This Year</button>
        </div>

        <div class="form-group">
            <label for="trimester">Or Select Trimester:</label>
            <select id="trimester" name="trimester" class="form-control">
                <option value="">Select a Trimester</option>
                <c:forEach var="trimester" items="${trimesters}">
                    <option value="${trimester.trimesterId}">Trimester ${trimester.trimesterId}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group checkbox-group">
            <label>Select Categories:</label><br>
            <input type="checkbox" id="generalInfo" name="categories" value="generalInfo" checked>
            <label for="generalInfo">General Info</label><br>
            <input type="checkbox" id="finance" name="categories" value="finance" checked>
            <label for="finance">Finance</label><br>
            <input type="checkbox" id="conduct" name="categories" value="conduct" checked>
            <label for="conduct">Conduct</label><br>
            <input type="checkbox" id="attendance" name="categories" value="attendance" checked>
            <label for="attendance">Attendance</label><br>
            <input type="checkbox" id="clubs" name="categories" value="clubs" checked>
            <label for="clubs">Clubs</label><br>
            <input type="checkbox" id="library" name="categories" value="library" checked>
            <label for="library">Library</label><br>
        </div>
    </form>

    <div class="table-container">
        <h2>Report Details</h2>
        
        <c:if test="${not empty summaryStats}">
        <div class="row mb-4">
            <c:if test="${not empty summaryStats.attendanceRate}">
            <div class="col-md-4 mb-3">
                <div class="card summary-card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><i class="fas fa-calendar-check"></i> Attendance</h5>
                        <div class="d-flex align-items-center">
                            <div class="me-3">
                                <h2 class="mb-0">${summaryStats.attendanceRate}%</h2>
                                <small class="text-muted">${presentDays} of ${attendanceList.size()} days</small>
                            </div>
                            <div class="ms-auto">
                                <div class="progress" style="height: 10px; width: 100px;">
                                    <div class="progress-bar bg-success" role="progressbar" 
                                         style="width: ${summaryStats.attendanceRate}%" 
                                         aria-valuenow="${summaryStats.attendanceRate}" 
                                         aria-valuemin="0" 
                                         aria-valuemax="100"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:if>
            
            <c:if test="${not empty summaryStats.totalPaid}">
            <div class="col-md-4 mb-3">
                <div class="card summary-card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><i class="fas fa-money-bill-wave"></i> Payments</h5>
                        <div class="d-flex align-items-center">
                            <div>
                                <h2 class="mb-0">$${summaryStats.totalPaid}</h2>
                                <small class="text-muted">${feePayments.size()} transactions</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:if>
            
            <c:if test="${not empty conductList}">
            <div class="col-md-4 mb-3">
                <div class="card summary-card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><i class="fas fa-user-graduate"></i> Conduct</h5>
                        <div class="d-flex align-items-center">
                            <div>
                                <h2 class="mb-0">${conductList.size()}</h2>
                                <small class="text-muted">conduct records</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:if>
        </div>
        </c:if>

        <c:if test="${not empty student}">
        <div class="report-section">
            <h3 onclick="toggleSection(this)">
                <i class="fas fa-chevron-down"></i> General Info
            </h3>
            <div class="section-content">
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>Name:</strong> ${student.firstName} ${student.middleName} ${student.lastName}</p>
                        <p><strong>Student ID:</strong> ${student.studentId}</p>
                    </div>
                    <div class="col-md-6">
                        <p><strong>Date of Birth:</strong> ${student.dateOfBirth}</p>
                        <p><strong>Enrollment Date:</strong> ${student.dateOfEnrollment}</p>
                    </div>
                </div>
            </div>
        </div>
        </c:if>

        <c:if test="${not empty feePayments}">
        <div class="report-section">
            <h3 onclick="toggleSection(this)">
                <i class="fas fa-chevron-down"></i> Finance
            </h3>
            <div class="section-content">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Payment Date</th>
                            <th>Amount</th>
                            <th>Payment Mode</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="payment" items="${feePayments}">
                        <tr>
                            <td>${payment.dateOfPayment}</td>
                            <td>$${payment.amountPaid}</td>
                            <td>${payment.paymentMode}</td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        </c:if>

        <c:if test="${not empty conductList}">
        <div class="report-section">
            <h3 onclick="toggleSection(this)">
                <i class="fas fa-chevron-down"></i> Conduct
            </h3>
            <div class="section-content">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Description</th>
                            <th>Action Taken</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="conduct" items="${conductList}">
                        <tr>
                            <td>${conduct.date}</td>
                            <td>${conduct.conductDescription}</td>
                            <td>
                                <span class="badge ${conduct.actionTaken.contains('Warning') ? 'bg-warning' : 'bg-danger'}">
                                    ${conduct.actionTaken}
                                </span>
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        </c:if>

        <c:if test="${not empty attendanceList}">
        <div class="report-section">
            <h3 onclick="toggleSection(this)">
                <i class="fas fa-chevron-down"></i> Attendance
            </h3>
            <div class="section-content">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="attendance" items="${attendanceList}">
                        <tr>
                            <td>${attendance.date}</td>
                            <td>
                                <span class="badge ${attendance.status == 'Present' ? 'bg-success' : 
                                                     attendance.status == 'Absent' ? 'bg-danger' : 'bg-warning'}">
                                    ${attendance.status}
                                </span>
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        </c:if>

        <c:if test="${not empty clubs}">
        <div class="report-section">
            <h3 onclick="toggleSection(this)">
                <i class="fas fa-chevron-down"></i> Clubs
            </h3>
            <div class="section-content">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Club Name</th>
                            <th>Join Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="club" items="${clubs}">
                        <tr>
                            <td>${club.clubName}</td>
                            <td>${club.joinDate}</td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        </c:if>

        <c:if test="${not empty borrowedBooks}">
        <div class="report-section">
            <h3 onclick="toggleSection(this)">
                <i class="fas fa-chevron-down"></i> Library
            </h3>
            <div class="section-content">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Book ID</th>
                            <th>Borrow Date</th>
                            <th>Due Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${borrowedBooks}">
                        <tr>
                            <td>${book.bookId}</td>
                            <td>${book.borrowDate}</td>
                            <td>${book.dueDate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${book.returnDate != null}">
                                        <span class="badge bg-success">Returned</span>
                                    </c:when>
                                    <c:when test="${book.lost}">
                                        <span class="badge bg-danger">Lost</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-warning">Borrowed</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        </c:if>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>

<script>
// Form validation
function validateForm() {
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;
    var trimester = document.getElementById("trimester").value;

    if ((startDate === "" || endDate === "") && trimester === "") {
        alert("Please select either a date range or a trimester.");
        return false;
    }
    return true;
}

// Date presets
function setDateRange(range) {
    const today = new Date();
    const startDate = document.getElementById('startDate');
    const endDate = document.getElementById('endDate');
    
    if (range === 'week') {
        const weekStart = new Date(today.setDate(today.getDate() - today.getDay()));
        startDate.value = weekStart.toISOString().split('T')[0];
        endDate.value = new Date().toISOString().split('T')[0];
    } else if (range === 'month') {
        const monthStart = new Date(today.getFullYear(), today.getMonth(), 1);
        startDate.value = monthStart.toISOString().split('T')[0];
        endDate.value = new Date().toISOString().split('T')[0];
    } else if (range === 'year') {
        const yearStart = new Date(today.getFullYear(), 0, 1);
        startDate.value = yearStart.toISOString().split('T')[0];
        endDate.value = new Date().toISOString().split('T')[0];
    }
}

function generatePDF() {
    // Hide elements
    const elementsToHide = document.querySelectorAll('.btn, .checkbox-group, form');
    elementsToHide.forEach(el => el.style.visibility = 'hidden');

    // Loading indicator
    const loadingIndicator = document.createElement('div');
    loadingIndicator.innerHTML = `
        <div style="position:fixed; top:50%; left:50%; transform:translate(-50%,-50%); 
                   background:white; padding:20px; border-radius:5px; z-index:9999">
            <h3>Generating PDF...</h3>
            <div class="spinner-border text-primary"></div>
        </div>
    `;
    document.body.appendChild(loadingIndicator);
    let loadingIndicatorAdded = true;

    // Capture report container
    const element = document.querySelector('.table-container');

    html2canvas(element, {
        scale: 2,
        logging: false,
        useCORS: true,
        allowTaint: true
    }).then(canvas => {
        // Remove loading indicator
        if(loadingIndicatorAdded && document.body.contains(loadingIndicator)){
            document.body.removeChild(loadingIndicator);
        }
        loadingIndicatorAdded = false;

        // Restore hidden elements
        elementsToHide.forEach(el => el.style.visibility = 'visible');

        // Generate PDF
        const imgData = canvas.toDataURL('image/png');
        const pdf = new jspdf.jsPDF('p', 'mm', 'a4');
        const imgWidth = pdf.internal.pageSize.getWidth();
        const imgHeight = (canvas.height * imgWidth) / canvas.width;

        pdf.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);

        // Get current date
        const today = new Date();
        const dateString = today.getFullYear() + '-' +
            String(today.getMonth() + 1).padStart(2, '0') + '-' +
            String(today.getDate()).padStart(2, '0');

        pdf.save(`SummitAcademy-Report-${dateString}.pdf`);
    }).catch(err => {
        console.error('PDF generation failed:', err);
        alert('PDF generation failed. Please try again.');
        if(loadingIndicatorAdded && document.body.contains(loadingIndicator)){
            document.body.removeChild(loadingIndicator);
        }
        loadingIndicatorAdded = false;
        elementsToHide.forEach(el => el.style.visibility = 'visible');
    });
}

// Collapsible sections
function toggleSection(header) {
    const section = header.parentElement;
    section.classList.toggle('collapsed');
    const icon = header.querySelector('i');
    icon.classList.toggle('fa-chevron-down');
    icon.classList.toggle('fa-chevron-right');
}

// Initialize all sections as expanded by default
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.report-section').forEach(section => {
        section.classList.remove('collapsed');
    });
});
</script>
</body>
</html>