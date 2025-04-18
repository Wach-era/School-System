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
    <title>${empty book ? 'Add New Book' : 'Edit Book'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
        <%@ include file="/WEB-INF/includes/header.jsp" %>
    
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
        .form-group input[type="number"],
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
    <h2>${empty book ? 'Add New Book' : 'Edit Book'}</h2>
    <form action="${book == null ? '/SchoolSystem/library/insertBook' : '/SchoolSystem/library/updateBook'}" method="post">
        <div class="form-group">
            <label for="bookId">Book ID</label>
            <input type="text" class="form-control" id="bookId" name="bookId" value="${empty book ? '' : book.bookId}" required>
        </div>
        <div class="form-group">
            <label for="title">Title</label>
            <input type="text" class="form-control" id="title" name="title" value="${empty book ? '' : book.title}" required>
        </div>
        <div class="form-group">
            <label for="author">Author</label>
            <input type="text" class="form-control" id="author" name="author" value="${empty book ? '' : book.author}" required>
        </div>
        <div class="form-group">
            <label for="genre">Genre</label>
            <input type="text" class="form-control" id="genre" name="genre" value="${empty book ? '' : book.genre}" required>
        </div>
        <div class="form-group">
            <label for="isbn">ISBN</label>
            <input type="text" class="form-control" id="isbn" name="isbn" value="${empty book ? '' : book.isbn}" required>
        </div>
        <div class="form-group">
            <label for="publicationYear">Publication Year</label>
            <input type="number" class="form-control" id="publicationYear" name="publicationYear" value="${empty book ? '' : book.publicationYear}" required>
        </div>
        <div class="form-group">
            <label for="publisher">Publisher</label>
            <input type="text" class="form-control" id="publisher" name="publisher" value="${empty book ? '' : book.publisher}" required>
        </div>
        <div class="form-group">
            <label for="language">Language</label>
            <input type="text" class="form-control" id="language" name="language" value="${empty book ? '' : book.language}" required>
        </div>
        <div class="form-group">
            <label for="numCopies">Number of Copies</label>
            <input type="number" class="form-control" id="numCopies" name="numCopies" value="${empty book ? '' : book.numCopies}" required>
        </div>
        <div class="form-group">
            <label for="shelfLocation">Shelf Location</label>
            <input type="text" class="form-control" id="shelfLocation" name="shelfLocation" value="${empty book ? '' : book.shelfLocation}" required>
        </div>
        <div class="form-group">
            <label for="bookCoverUrl">Book Cover URL</label>
            <input type="text" class="form-control" id="bookCoverUrl" name="bookCoverUrl" value="${empty book ? '' : book.bookCoverUrl}">
        </div>
        <div class="form-group">
            <input type="submit" value="${book != null ? 'Update' : 'Add'} Book">
        </div>
        <div class="form-group">
            <a href="${pageContext.request.contextPath}/library/listBooks" class="btn-secondary">View Book List</a>
        </div>
    </form>
</div>
</body>
</html>
