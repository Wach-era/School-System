<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>Book List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
        <%@ include file="/WEB-INF/includes/header.jsp" %>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            color: #333;
        }
        .container {
            width: 80%;
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            border: 1px solid #ddd;
            background-color: #f9f9f9;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #333;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .btn {
            display: inline-block;
            padding: 8px 16px;
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
        .btn-delete {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn-delete:hover {
            background-color: #c82333;
            border-color: #bd2130;
        }
        .mb-2 {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Book List</h2>
    <a href="${pageContext.request.contextPath}/library/newBook" class="btn btn-primary mb-2">Add New Book</a>
    <a href="${pageContext.request.contextPath}/library-menu.jsp" class="btn btn-info">Go Back to Library Menu</a>
    <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
    <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Genre</th>
                <th>ISBN</th>
                <th>Publication Year</th>
                <th>Publisher</th>
                <th>Language</th>
                <th>Number of Copies</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${listBook}">
                <tr>
                    <td>${book.bookId}</td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.genre}</td>
                    <td>${book.isbn}</td>
                    <td>${book.publicationYear}</td>
                    <td>${book.publisher}</td>
                    <td>${book.language}</td>
                    <td>${book.numCopies}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/library/editBook?id=${book.bookId}" class="btn btn-info">Edit</a>
                        <a href="${pageContext.request.contextPath}/library/deleteBook?id=${book.bookId}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this book?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
