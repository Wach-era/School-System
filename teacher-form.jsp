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
    <title>${teacher == null ? 'New Teacher' : 'Edit Teacher'}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        .container {
            max-width: 600px;
            margin-top: 50px;
        }
        .btn-secondary {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>${teacher == null ? 'New Teacher' : 'Edit Teacher'}</h2>
    <form action="${teacher == null ? '/SchoolSystem/teachers/insert' : '/SchoolSystem/teachers/update'}" method="post">
        <div class="form-group">
            <label for="nationalId">National ID</label>
            <input type="number" class="form-control" id="nationalId" name="nationalId" value="${teacher != null ? teacher.nationalId : ''}" required>
        </div>
        <div class="form-group">
            <label for="firstName">First Name</label>
            <input type="text" class="form-control" id="firstName" name="firstName" value="${teacher != null ? teacher.firstName : ''}" required>
        </div>
        <div class="form-group">
            <label for="middleName">Middle Name</label>
            <input type="text" class="form-control" id="middleName" name="middleName" value="${teacher != null ? teacher.middleName : ''}" required>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name</label>
            <input type="text" class="form-control" id="lastName" name="lastName" value="${teacher != null ? teacher.lastName : ''}" required>
        </div>
        <div class="form-group">
            <label for="dateOfBirth">Date of Birth</label>
            <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" value="${teacher != null ? teacher.dateOfBirth : ''}" required>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" value="${teacher != null ? teacher.email : ''}" required>
        </div>
        <div class="form-group">
            <label for="phoneNumber">Phone Number</label>
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${teacher != null ? teacher.phoneNumber : ''}" required>
        </div>
        <div class="form-group">
            <label for="gender">Gender</label>
            <select class="form-control" id="gender" name="gender" required>
                <option value="Male" ${teacher != null && teacher.gender == 'Male' ? 'selected' : ''}>Male</option>
                <option value="Female" ${teacher != null && teacher.gender == 'Female' ? 'selected' : ''}>Female</option>
            </select>
        </div>
        <div class="form-group">
            <label for="religion">Religion</label>
            <select class="form-control" id="religion" name="religion" required>
                <option value="Christian" ${teacher != null && teacher.religion == 'Christian' ? 'selected' : ''}>Christian</option>
                <option value="Muslim" ${teacher != null && teacher.religion == 'Muslim' ? 'selected' : ''}>Muslim</option>
                <option value="Hindi" ${teacher != null && teacher.religion == 'Hindi' ? 'selected' : ''}>Hindi</option>
                <option value="Other" ${teacher != null && teacher.religion == 'Other' ? 'selected' : ''}>Other</option>
            </select>
        </div>
        <div class="form-group">
            <label for="degrees">Degrees</label>
            <input type="text" class="form-control" id="degrees" name="degrees" value="${teacher != null ? teacher.degrees : ''}" required>
        </div>
        <div class="form-group">
            <label for="majors">Majors</label>
            <input type="text" class="form-control" id="majors" name="majors" value="${teacher != null ? teacher.majors : ''}" required>
        </div>
        <div class="form-group">
            <label for="institution">Institution</label>
            <input type="text" class="form-control" id="institution" name="institution" value="${teacher != null ? teacher.institution : ''}" required>
        </div>
        <div class="form-group">
            <label for="dateOfGraduation">Date of Graduation</label>
            <input type="date" class="form-control" id="dateOfGraduation" name="dateOfGraduation" value="${teacher != null ? teacher.dateOfGraduation : ''}" required>
        </div>
        <div class="form-group">
            <label for="position">Position</label>
            <input type="text" class="form-control" id="position" name="position" value="${teacher != null ? teacher.position : ''}" required>
        </div>
        <div class="form-group">
            <label for="dateOfHire">Date of Hire</label>
            <input type="date" class="form-control" id="dateOfHire" name="dateOfHire" value="${teacher != null ? teacher.dateOfHire : ''}" required>
        </div>
        <div class="form-group">
            <label for="salary">Salary</label>
            <input type="number" step="0.01" class="form-control" id="salary" name="salary" value="${teacher != null ? teacher.salary : ''}" required>
        </div>
        <div class="form-group">
            <label for="emergencyContact">Emergency Contact</label>
            <input type="text" class="form-control" id="emergencyContact" name="emergencyContact" value="${teacher != null ? teacher.emergencyContact : ''}" required>
        </div>
        <div class="form-group">
            <label for="healthInformation">Health Information</label>
            <textarea class="form-control" id="healthInformation" name="healthInformation" required>${teacher != null ? teacher.healthInformation : ''}</textarea>
        </div>
        <div class="form-group">
            <label for="specialAccommodation">Special Accommodation</label>
            <textarea class="form-control" id="specialAccommodation" name="specialAccommodation" required>${teacher != null ? teacher.specialAccommodation : ''}</textarea>
        </div>
        <c:if test="${teacher != null}">
            <input type="hidden" name="teacherId" value="${teacher.teacherId}">
        </c:if>
        <button type="submit" class="btn btn-primary">${teacher == null ? 'Add' : 'Update'}</button>
        <a href="${pageContext.request.contextPath}/teachers/list" class="btn btn-secondary">View Teachers List</a>
    </form>
</div>
</body>
</html>
