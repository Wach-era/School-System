<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Form</title>
        <%@ include file="/WEB-INF/includes/header.jsp" %>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            color: #333;
        }
        .container {
            width: 50%;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #333;
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }
        .form-group input[type="text"],
        .form-group input[type="date"],
        .form-group input[type="email"],
        .form-group select {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-group input[type="submit"] {
            width: auto;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .form-group input[type="submit"]:hover {
            background-color: #45a049;
        }
        .form-group .btn-secondary {
            background-color: #ccc;
            color: black;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 5px;
            display: inline-block;
            margin-top: 10px;
        }
        .form-group .btn-secondary:hover {
            background-color: #bbb;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Student Form</h2>
        <form action="${student != null ? 'update' : 'insert'}" method="post">
            <div class="form-group">
                <label for="studentId">Student ID:</label>
                <input type="text" id="studentId" name="id" value="${student != null ? student.studentId : ''}" ${student != null ? 'readonly' : ''}>
            </div>
            <div class="form-group">
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName" value="${student != null ? student.firstName : ''}" required>
            </div>
            <div class="form-group">
                <label for="middleName">Middle Name:</label>
                <input type="text" id="middleName" name="middleName" value="${student != null ? student.middleName : ''}">
            </div>
            <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName" value="${student != null ? student.lastName : ''}" required>
            </div>
            <div class="form-group">
                <label for="dateOfBirth">Date of Birth:</label>
                <input type="date" id="dateOfBirth" name="dateOfBirth" value="${student != null ? student.dateOfBirth : ''}" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${student != null ? student.email : ''}" required>
            </div>
            <div class="form-group">
                <label for="phoneNumber">Phone Number:</label>
                <input type="text" id="phoneNumber" name="phoneNumber" value="${student != null ? student.phoneNumber : ''}" required>
            </div>
            <div class="form-group">
                <label for="gender">Gender:</label>
                <select id="gender" name="gender" required>
                    <option value="Male" ${student != null && student.gender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${student != null && student.gender == 'Female' ? 'selected' : ''}>Female</option>
                    <option value="Other" ${student != null && student.gender == 'Other' ? 'selected' : ''}>Other</option>
                </select>
            </div>
            <div class="form-group">
                <label for="religion">Religion:</label>
                <input type="text" id="religion" name="religion" value="${student != null ? student.religion : ''}">
            </div>
            
            <div class="form-group">
                <label for="medicalHistory">Medical History:</label>
                <input type="text" id="medicalHistory" name="medicalHistory" value="${student != null ? student.medicalHistory : ''}">
            </div>
            <div class="form-group">
                <label for="emergencyContact">Emergency Contact:</label>
                <input type="text" id="emergencyContact" name="emergencyContact" value="${student != null ? student.emergencyContact : ''}" required>
            </div>
            <div class="form-group">
                <label for="learningDisabilities">Learning Disabilities:</label>
                <input type="text" id="learningDisabilities" name="learningDisabilities" value="${student != null ? student.learningDisabilities : ''}">
            </div>
            <div class="form-group">
                <label for="dateOfEnrollment">Date of Enrollment:</label>
                <input type="date" id="dateOfEnrollment" name="dateOfEnrollment" value="${student != null ? student.dateOfEnrollment : ''}">
            </div>
            <div class="form-group">
                <label for="disabilityDetails">Disability Details:</label>
                <input type="text" id="disabilityDetails" name="disabilityDetails" value="${student != null ? student.disabilityDetails : ''}">
            </div>
            <div class="form-group">
                <input type="submit" value="${student != null ? 'Update' : 'Add'} Student">
            </div>
        </form>
        <div class="form-group">
            <a href="students/list" class="btn-secondary">View Student List</a>
        </div>
    </div>
</body>
</html>