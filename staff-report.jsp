<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Staff Report Generator</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
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
            margin: 50px auto;
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
    </style>

    <script>
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
    </script>
</head>
<body>
    <div class="container">

    <h1>Report for ID: ${param.staffId}</h1>
    <a href="${pageContext.request.contextPath}/staff/list" class="btn btn-secondary mb-3">Go Back</a>
                        <a href="${pageContext.request.contextPath}/staffreportservlet/generate?staffId=${param.staffId}" class="btn btn-secondary mb-3">Refresh</a>
            
        <form action="${pageContext.request.contextPath}/staffreportservlet/create" method="post" onsubmit="return validateForm()">
            <input type="hidden" name="staffId" value="${param.staffId}">
            
            
            <div class="form-group">
                <label>Start Date:</label>
                <input type="date" id="startDate" name="startDate" class="form-control">
            </div>
            <div class="form-group">
                <label>End Date:</label>
                <input type="date" id="endDate" name="endDate" class="form-control">
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
                <input type="checkbox" id="generalInfo" name="categories" value="generalInfo">
                <label for="generalInfo">General Info</label><br>
                
                <input type="checkbox" id="finance" name="categories" value="finance">
                <label for="finance">Finance</label><br>
                
                <input type="checkbox" id="conduct" name="categories" value="conduct">
                <label for="conduct">Conduct</label><br>
                
                <input type="checkbox" id="attendance" name="categories" value="attendance">
                <label for="attendance">Attendance</label><br>
            
                <input type="checkbox" id="library" name="categories" value="library">
                <label for="library">Library</label><br>
            </div>
            <input type="submit" value="Generate Report" class="btn btn-primary">
        </form>

   <div class="table-container">
            <h2>Report Details</h2>
            <c:if test="${not empty staff}">
                <h3> General Info</h3>
                <p><strong>Name:</strong> ${staff.firstName} ${staff.middleName} ${staff.lastName}</p>
                <p><strong>Staff ID:</strong> ${staff.staffId}</p>
                <p><strong>Date of Birth:</strong> ${staff.dateOfBirth}</p>
                <p><strong>Hire Date:</strong> ${staff.dateOfHire}</p>
            </c:if>
            
            <c:if test="${not empty payroll}">
                <h3> Finance</h3>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Payment Date</th>
                            <th>Account Number</th>
                            <th>Salary</th>
                            <th>Bonus</th>
                            <th>Deductions</th>
                            <th>Net Salary</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="payment" items="${payroll}">
                            <tr>
                                <td>${payroll.paymentDate}</td>
                                <td>${payroll.accountNumber}</td>
                                <td>${payroll.salary}</td>
                                <td>${payroll.bonus}</td>
                                <td>${payroll.deductions}</td>
                                <td>${payroll.netSalary}</td>
                                <td>${payroll.status}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
    
    <c:if test="${not empty conductList}">
        <h3>Conduct</h3>
        <table class="table table-bordered">
            <tr>
                <th>Date</th>
                <th>Conduct Description</th>
                <th>Action Taken</th>
            </tr>
            <c:forEach var="conduct" items="${conductList}">
                <tr>
                    <td>${conduct.date}</td>
                    <td>${conduct.conductDescription}</td>
                    <td>${conduct.actionTaken}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    
    <c:if test="${not empty attendanceList}">
        <h3>Attendance</h3>
        <table class="table table-bordered">
            <tr>
                <th>Date</th>
                <th>Status</th>
            </tr>
            <c:forEach var="attendance" items="${attendanceList}">
                <tr>
                    <td>${attendance.date}</td>
                    <td>${attendance.status}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    
    
    <c:if test="${not empty borrowedBooks}">
        <h3>Library</h3>
        <table class="table table-bordered">
            <tr>
                <th>Borrow Date</th>
                <th>Due Date</th>
                <th>Return Date</th>
                <th>Book ID</th>
                <th>Overdue Fine</th>
                <th>Lost</th>
            </tr>
            <c:forEach var="borrowedBook" items="${borrowedBooks}">
                <tr>
                    <td>${borrowedBook.borrowDate}</td>
                    <td>${borrowedBook.dueDate}</td>
                    <td>${borrowedBook.returnDate}</td>
                    <td>${borrowedBook.bookId}</td>
                    <td>${borrowedBook.overdueFine}</td>
                    <td>${borrowedBook.lost ? "Yes" : "No"}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    
    </div>
   </div> 
</body>
</html>
