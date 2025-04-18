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
    <title>${parent == null ? 'Add Parent' : 'Edit Parent'}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
            <%@ include file="/WEB-INF/includes/header.jsp" %>
    
</head>
<body>
<div class="container">
    <h2>${parent == null ? 'Add Parent' : 'Edit Parent'}</h2>
    <form action="${parent == null ? '/SchoolSystem/parents/insert' : '/SchoolSystem/parents/update'}" method="post">
        <div class="form-group">
            <label for="parentId">Parent ID</label>
            <input type="number" class="form-control" id="parentId" name="parentId" value="${parent != null ? parent.parentId : ''}" required>
        </div>
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name" value="${parent != null ? parent.name : ''}" required>
        </div>
        <div class="form-group">
            <label for="phoneNumber">Phone Number</label>
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${parent != null ? parent.phoneNumber : ''}" required>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" value="${parent != null ? parent.email : ''}" required>
        </div>
        <div class="form-group">
            <label for="gender">Gender</label>
            <select class="form-control" id="gender" name="gender" required>
                <option value="Male" ${parent != null && parent.gender == 'Male' ? 'selected' : ''}>Male</option>
                <option value="Female" ${parent != null && parent.gender == 'Female' ? 'selected' : ''}>Female</option>
            </select>
        </div>
        <div class="form-group">
            <label for="religion">Religion</label>
            <select class="form-control" id="religion" name="religion" required>
                <option value="Christian" ${parent != null && parent.religion == 'Christian' ? 'selected' : ''}>Christian</option>
                <option value="Muslim" ${parent != null && parent.religion == 'Muslim' ? 'selected' : ''}>Muslim</option>
                <option value="Hindi" ${parent != null && parent.religion == 'Hindi' ? 'selected' : ''}>Hindi</option>
                <option value="Other" ${parent != null && parent.religion == 'Other' ? 'selected' : ''}>Other</option>
            </select>
        </div>
        <c:if test="${parent != null}">
            <input type="hidden" name="id" value="${parent.parentId}">
        </c:if>
        <button type="submit" class="btn btn-primary">${parent == null ? 'Add' : 'Update'}</button>
        <a href="${pageContext.request.contextPath}/parents/list" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>
