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
    <title>Staff Form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 50px;
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px 0px #000;
        }
        .btn-primary, .btn-secondary {
            width: 100%;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>${staff == null ? 'New Staff' : 'Edit Staff'}</h2>
    <form action="${staff == null ? '/SchoolSystem/staff/insert' : '/SchoolSystem/staff/update'}" method="post">
<h4>Personal Information</h4>
        <div class="form-group">
            <label for="staffId">Staff ID:</label>
            <input type="number" id="staffId" name="staffId" value="${staff != null ? staff.staffId : ''}" ${staff == null ? 'required' : 'readonly'}>
        </div>
        <div class="form-group">
            <label for="first_name">First Name:</label>
            <input type="text" id="first_name" name="first_name" value="${staff != null ? staff.firstName : ''}" required>
        </div>
        <div class="form-group">
            <label for="middle_name">Middle Name:</label>
            <input type="text" id="middle_name" name="middle_name" value="${staff != null ? staff.middleName : ''}" required>
        </div>
        <div class="form-group">
            <label for="last_name">Last Name:</label>
            <input type="text" id="last_name" name="last_name" value="${staff != null ? staff.lastName : ''}" required>
        </div>
        <div class="form-group">
            <label for="date_of_birth">Date of Birth:</label>
            <input type="date" id="date_of_birth" name="date_of_birth" value="${staff != null ? staff.dateOfBirth : ''}" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${staff != null ? staff.email : ''}" required>
        </div>
        <div class="form-group">
            <label for="phone_number">Phone Number:</label>
            <input type="text" id="phone_number" name="phone_number" value="${staff != null ? staff.phoneNumber : ''}" pattern="\+254\d{9}" title="Phone number must start with +254 and contain 9 digits after that" required>
        </div>
        <div class="radio-group">
            <label for="gender">Gender:</label>
            <div style="display: flex;">
                <input type="radio" id="gender1" name="gender" value="Male" ${staff != null && staff.gender == 'Male' ? 'checked' : ''}>
                <label for="gender1"> Male</label>
                <input type="radio" id="gender2" name="gender" value="Female" ${staff != null && staff.gender == 'Female' ? 'checked' : ''}>
                <label for="gender2"> Female</label>
            </div>
        </div>
        <div class="radio-group">
            <label for="religion">Religion:</label>
            <div style="display: flex;">
                <input type="radio" id="religion1" name="religion" value="Christian" ${staff != null && staff.religion == 'Christian' ? 'checked' : ''}>
                <label for="religion1"> Christian</label>
                <input type="radio" id="religion2" name="religion" value="Muslim" ${staff != null && staff.religion == 'Muslim' ? 'checked' : ''}>
                <label for="religion2"> Muslim</label>
                <input type="radio" id="religion3" name="religion" value="Hindi" ${staff != null && staff.religion == 'Hindi' ? 'checked' : ''}>
                <label for="religion3"> Hindi</label>
                <input type="radio" id="religion4" name="religion" value="Other" ${staff != null && staff.religion == 'Other' ? 'checked' : ''}>
                <label for="religion4"> Other</label>
            </div>
        </div>

        <h4>ROLES</h4>
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="${staff != null ? staff.title : ''}" required>
        </div>
        <div class="form-group">
    <label for="department">Department:</label>
    <select id="department" name="department_id" required>
        <c:forEach var="department" items="${departments}">
            <option value="${department.id}" ${staff != null && staff.departmentId == department.id ? 'selected' : ''}>
                ${department.name}
            </option>
        </c:forEach>
    </select>
</div>
        <div class="form-group">
            <label for="date_of_hire">Date of Hire:</label>
            <input type="date" id="date_of_hire" name="date_of_hire" value="${staff != null ? staff.dateOfHire : ''}" required>
        </div>
        <div class="form-group">
            <label for="responsibilities">Responsibilities:</label>
            <input type="text" id="responsibilities" name="responsibilities" value="${staff != null ? staff.responsibilities : ''}" required>
        </div>

        <h4>QUALIFICATIONS</h4>
        <div class="form-group">
            <label for="education_level">Education:</label>
            <input type="text" id="education_level" name="education_level" value="${staff != null ? staff.educationLevel : ''}" required>
        </div>
        <div class="form-group">
            <label for="certification">Certification:</label>
            <input type="text" id="certification" name="certification" value="${staff != null ? staff.certification : ''}" required>
        </div>
        <div class="form-group">
            <label for="experience">Experience:</label>
            <input type="text" id="experience" name="experience" value="${staff != null ? staff.experience : ''}" required>
        </div>

        <h4>ADDITIONAL INFORMATION</h4>
        <div class="form-group">
            <label for="emergency_contact">Emergency Contact:</label>
            <input type="text" id="emergency_contact" name="emergency_contact" value="${staff != null ? staff.emergencyContact : ''}" pattern="\+254\d{9}" title="Phone number must start with +254 and contain 9 digits after that" required>
        </div>
        <div class="form-group">
            <label for="health_information">Health Information:</label>
            <input type="text" id="health_information" name="health_information" value="${staff != null ? staff.healthInformation : ''}" required>
        </div>
        <div class="form-group">
            <label for="special_accommodation">Special Accommodation:</label>
            <input type="text" id="special_accommodation" name="special_accommodation" value="${staff != null ? staff.specialAccommodation : ''}" required>
        </div>
        <button type="submit" class="btn btn-primary">${staff == null ? 'Save' : 'Update'}</button>
        <a href="staff/list" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>
