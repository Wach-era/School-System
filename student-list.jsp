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
    <title>Student List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            color: #333;
        }
        .container {
            width: 90%;
            margin: 50px auto;
        }
        .student-card {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            padding: 20px;
        }
        .student-card h3 {
            margin-top: 0;
        }
        .btn-group {
            margin-top: 10px;
        }
        .btn-group .btn {
            margin-right: 5px;
        }
        .collapse {
            margin-top: 10px;
        }
        .system-strip {
            display: flex;
            gap: 10px;
            margin-top: 10px;
        }
        .btn-system {
            background-color: #6c757d;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-system:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="mb-4 text-center">Student List</h2>
    <form action="${pageContext.request.contextPath}/students/search" method="get" class="form-inline mb-4">
        <input type="hidden" name="action" value="search">
        <input type="text" name="keyword" class="form-control mr-2" placeholder="Search by id, name or email" required>
        <button type="submit" class="btn btn-primary">Search</button>
        <a href="${pageContext.request.contextPath}/students/list" class="btn btn-secondary ml-2">Reset</a>
    </form>
    <div class="mb-4">
        <a href="${pageContext.request.contextPath}/students/new" class="btn btn-primary">Add New Student</a>
        <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
        <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
    </div>
    <c:forEach var="student" items="${listStudent}">
        <div class="student-card">
            <h3>${student.firstName} ${student.middleName} ${student.lastName}</h3>
            <p><strong>ID:</strong> ${student.studentId}</p>
            <p><strong>Date of Birth:</strong> ${student.dateOfBirth}</p>
            <p><strong>Email:</strong> ${student.email}</p>
            <p><strong>Phone Number:</strong> ${student.phoneNumber}</p>
            <p><strong>Gender:</strong> ${student.gender}</p>
            <p><strong>Religion:</strong> ${student.religion}</p>
            
            <button class="btn btn-secondary" type="button" data-toggle="collapse" data-target="#details-${student.studentId}" aria-expanded="false" aria-controls="details-${student.studentId}">
                More Details
            </button>
            <div class="collapse" id="details-${student.studentId}">
                <div class="mt-3">
                    <p><strong>Medical History:</strong> ${student.medicalHistory}</p>
                    <p><strong>Emergency Contact:</strong> ${student.emergencyContact}</p>
                    <p><strong>Learning Disabilities:</strong> ${student.learningDisabilities}</p>
                    <p><strong>Date of Enrollment:</strong> ${student.dateOfEnrollment}</p>
                    <p><strong>Disability Details:</strong> ${student.disabilityDetails}</p>
                </div>
            </div>

            <!-- System Strip -->
            <div class="system-strip">

                <button class="btn btn-system btn-library" data-student-id="${student.studentId}">Library</button>
                <button class="btn btn-system btn-subjects" data-student-id="${student.studentId}">Subjects</button>
                <button class="btn btn-system btn-finance" data-student-id="${student.studentId}">Finance</button>
                <button class="btn btn-system btn-clubs" data-student-id="${student.studentId}">Clubs</button>
                <button class="btn btn-system btn-parents" data-student-id="${student.studentId}">Parents</button>
            </div>

            <!-- Actions -->
            <div class="btn-group">
                <a href="${pageContext.request.contextPath}/students/edit?id=${student.studentId}" class="btn btn-info">Edit</a>
                <a href="${pageContext.request.contextPath}/students/delete?id=${student.studentId}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this student?');">Delete</a>
                <a href="${pageContext.request.contextPath}/transcripts?studentId=${student.studentId}" class="btn btn-secondary">View Transcript</a>
                <a href="${pageContext.request.contextPath}/student-attendance/new?studentId=${student.studentId}" class="btn btn-success">Record Attendance</a>
                <a href="${pageContext.request.contextPath}/student-conduct/new?studentId=${student.studentId}" class="btn btn-warning">Record Conduct</a>
                <a href="${pageContext.request.contextPath}/reportservlet/generate?studentId=${student.studentId}" class="btn btn-dark">Generate Report</a>
            </div>
        </div>

        <!-- Modals for Each System -->
        <!-- Library Modal -->
<div class="modal fade" id="libraryModal-${student.studentId}" tabindex="-1" role="dialog" aria-labelledby="libraryModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="libraryModalLabel">Library Details for ${student.firstName} ${student.lastName}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="libraryModalBody-${student.studentId}">
                <!-- Data will be loaded here dynamically -->
                <p>Loading library data...</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
       <!-- Subjects Modal -->
<div class="modal fade" id="subjectsModal-${student.studentId}" tabindex="-1" role="dialog" aria-labelledby="subjectsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="subjectsModalLabel">Subjects for ${student.firstName} ${student.lastName}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="subjectsModalBody-${student.studentId}">
                <!-- Data will be loaded here dynamically -->
                <p>Loading subjects data...</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

        <!-- Finance Modal -->
<div class="modal fade" id="financeModal-${student.studentId}" tabindex="-1" role="dialog" aria-labelledby="financeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="financeModalLabel">Finance Details for ${student.firstName} ${student.lastName}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="financeModalBody-${student.studentId}">
                <!-- Data will be loaded here dynamically -->
                <p>Loading finance data...</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

        <!-- Clubs Modal -->
<div class="modal fade" id="clubsModal-${student.studentId}" tabindex="-1" role="dialog" aria-labelledby="clubsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="clubsModalLabel">Clubs for ${student.firstName} ${student.lastName}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="clubsModalBody-${student.studentId}">
                <!-- Data will be loaded here dynamically -->
                <p>Loading clubs data...</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<!-- Parents Modal -->
<div class="modal fade" id="parentsModal-${student.studentId}" tabindex="-1" role="dialog" aria-labelledby="parentsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="parentsModalLabel">Parents for ${student.firstName} ${student.lastName}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="parentsModalBody-${student.studentId}">
                <!-- Data will be loaded here dynamically -->
                <p>Loading parents data...</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
    </c:forEach>
</div>
<!-- Include jQuery first -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

<!-- Include Bootstrap JS (if needed) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- Your custom script -->
<script>
    $(document).ready(function() {
        $(".btn-library").click(function() {
            var studentId = $(this).data("student-id");
            console.log("Button clicked! Student ID:", studentId); // Debugging log

            $.ajax({
                url: "/SchoolSystem/library/listBorrowedBooksByStudent?studentId=" + studentId,
                method: "GET",
                success: function(data) {
                    $("#libraryModalBody-" + studentId).html(data);
                    $("#libraryModal-" + studentId).modal("show");
                },
                error: function() {
                    alert("Failed to fetch library data.");
                }
            });
        });
    });
</script>

<script>
    $(document).ready(function() {
        $(".btn-finance").click(function() {
            var studentId = $(this).data("student-id");
            $.ajax({
                url: "${pageContext.request.contextPath}/student-fee-payment/listByStudent?studentId=" + studentId,
                method: "GET",
                success: function(data) {
                    // Insert the fetched HTML into the modal body
                    $("#financeModalBody-" + studentId).html(data);
                    // Show the modal
                    $("#financeModal-" + studentId).modal("show");
                },
                error: function() {
                    alert("Failed to fetch finance data.");
                }
            });
        });
    });
</script>

<script>
    $(document).ready(function() {
        $(".btn-subjects").click(function() {
            var studentId = $(this).data("student-id");
            $.ajax({
                url: "${pageContext.request.contextPath}/subjects/listByStudent?studentId=" + studentId,
                method: "GET",
                success: function(data) {
                    // Insert the fetched HTML into the modal body
                    $("#subjectsModalBody-" + studentId).html(data);
                    // Show the modal
                    $("#subjectsModal-" + studentId).modal("show");
                },
                error: function() {
                    alert("Failed to fetch subjects data.");
                }
            });
        });
    });
</script>

<script>
    $(document).ready(function() {
        $(".btn-clubs").click(function() {
            var studentId = $(this).data("student-id");
            $.ajax({
                url: "${pageContext.request.contextPath}/clubs/listByStudent?studentId=" + studentId,
                method: "GET",
                success: function(data) {
                    // Insert the fetched HTML into the modal body
                    $("#clubsModalBody-" + studentId).html(data);
                    // Show the modal
                    $("#clubsModal-" + studentId).modal("show");
                },
                error: function() {
                    alert("Failed to fetch clubs data.");
                }
            });
        });
    });
</script>

<script>
    $(document).ready(function() {
        $(".btn-parents").click(function() {
            var studentId = $(this).data("student-id");
            $.ajax({
                url: "${pageContext.request.contextPath}/parentStudent/listByStudent?studentId=" + studentId,
                method: "GET",
                success: function(data) {
                    // Insert the fetched HTML into the modal body
                    $("#parentsModalBody-" + studentId).html(data);
                    // Show the modal
                    $("#parentsModal-" + studentId).modal("show");
                },
                error: function() {
                    alert("Failed to fetch parents data.");
                }
            });
        });
    });
</script>
</body>
</html>