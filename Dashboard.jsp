<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<%
   
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }

    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");

    // Check if cookies exist and update session attributes if necessary
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("username".equals(cookie.getName())) {
                username = cookie.getValue();
                session.setAttribute("username", username);
            } else if ("role".equals(cookie.getName())) {
                role = cookie.getValue();
                session.setAttribute("role", role);
            }
        }
    }%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
        <%@ include file="/WEB-INF/includes/header.jsp" %>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            background-color: #9e2c34;
        }
        .container {
            text-align: center;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
        }
        a {
            display: block;
            margin: 10px 0;
            padding: 10px 20px;
            background-color: #007BFF;
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
        }
        a:hover {
            background-color: #0056b3;
        }
        .logout {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #dc3545;
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
        }
        .logout:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome, <%= username %>!</h1>
        <c:choose>
            <c:when test="${role == 'Admin'}">
                <a href="${pageContext.request.contextPath}/students/list">Student Management</a>
                <a href="${pageContext.request.contextPath}/teachers/list">Teacher Management</a>
                <a href="${pageContext.request.contextPath}/parents/list">Parent Management</a>
                <a href="${pageContext.request.contextPath}/staff/list">Staff Management</a>
                <a href="${pageContext.request.contextPath}/classes/list">Class Management</a>
                <a href="${pageContext.request.contextPath}/finance.jsp">Finance Management</a>
                 <a href="${pageContext.request.contextPath}/subjects/list">Subject Management</a>
                 <a href="${pageContext.request.contextPath}/exams/list" >Exam Management</a>
               <a href="${pageContext.request.contextPath}/clubs/listClub">Clubs and Societies Management</a>
                <a href="${pageContext.request.contextPath}/library-menu.jsp">Library Management</a>
            </c:when>
            <c:when test="${role == 'StudentManager'}">
                <a href="${pageContext.request.contextPath}/students/list">Manage Students</a>
            </c:when>
            <c:when test="${role == 'TeacherManager'}">
                <a href="${pageContext.request.contextPath}/teachers/list">Manage Teachers</a>
            </c:when>
            <c:when test="${role == 'StaffManager'}">
            <a href="${pageContext.request.contextPath}/staff/list">Manage Staff</a>
            </c:when>
            <c:when test="${role == 'ParentManager'}">
               <a href="${pageContext.request.contextPath}/parents/list">Manage Parents</a>
            </c:when>
            <c:when test="${role == 'ClassManager'}">
                <a href="${pageContext.request.contextPath}/classes/list">Manage Classes</a>
            </c:when>
            <c:when test="${role == 'FeesManager'}">
                <a href="${pageContext.request.contextPath}/finance.jsp">Manage Finance</a>
            </c:when>
            <c:when test="${role == 'SubjectManager'}">
                <a href="${pageContext.request.contextPath}/subjects/list">Manage Subjects</a>
            </c:when>
            <c:when test="${role == 'ExamManager'}">
                <a href="${pageContext.request.contextPath}/exams/list" >>Manage Exams</a>
            </c:when>
            <c:when test="${role == 'ClubSocietyManager'}">
                <a href="${pageContext.request.contextPath}/clubs/listClub">Manage Clubs and Societies</a>
            </c:when>
            <c:when test="${role == 'LibraryManager'}">
                <a href="${pageContext.request.contextPath}/library-menu.jsp">Manage Library</a>
            </c:when>
        </c:choose>
        <a href="Logoutservlet" class="logout">Logout</a>
    </div>
</body>
</html>


<%-- page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
<%
    
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
%>--%>
<!--  DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #f4f4f4;
        }
        .header {
            background-color: #007BFF;
            color: white;
            padding: 15px;
            text-align: center;
        }
        .sidebar {
            background-color: #343a40;
            color: white;
            width: 200px;
            height: 100vh;
            position: fixed;
            top: 0;
            left: 0;
            padding-top: 60px;
        }
        .sidebar a {
            padding: 10px 15px;
            text-decoration: none;
            color: white;
            display: block;
        }
        .sidebar a:hover {
            background-color: #007BFF;
        }
        .main-content {
            margin-left: 200px;
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>School Management Dashboard</h1>
        <form action="Logoutservlet" method="post" style="display:inline;">
            <input type="submit" value="Logout" class="logout">
        </form>
    </div>
    <div class="sidebar">
        <a href="studentManagement.jsp">Student Management</a>
        <a href="teacherManagement.jsp">Teacher Management</a>
        <a href="parentManagement.jsp">Parent Management</a>
        <a href="staffManagement.jsp">Staff Management</a>
        <a href="classManagement.jsp">Class Management</a>
        <a href="feeManagement.jsp">Fee Management</a>
        <a href="subjectManagement.jsp">Subject Management</a>
        <a href="examManagement.jsp">Exam Management</a>
        <a href="resultManagement.jsp">Result Management</a>
        <a href="notificationManagement.jsp">Notification Management</a>
        <a href="reportManagement.jsp">Report Management</a>
        <a href="clubManagement.jsp">Clubs & Societies Management</a>
        <a href="libraryManagement.jsp">Library Management</a>
    </div>
    <div class="main-content">
        <h2>Welcome, Admin</h2>
        <p>Select an option from the sidebar to get started.</p>
    </div>
</body>
</html>-->
