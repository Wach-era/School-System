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
    <title>${empty borrowedBook ? 'Add Borrowed Book' : 'Edit Borrowed Book'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: var(--background-color, #f0f0f0);
            color: var(--text-color, #333);
        }
        .container {
            width: 50%;
            margin: 50px auto;
            padding: 20px;
            background-color: var(--container-bg, #fff);
            border: 1px solid var(--border-color, #ccc);
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: var(--text-color, #333);
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: var(--text-color, #555);
        }
        .form-group input[type="text"],
        .form-group input[type="date"],
        .form-group select {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid var(--border-color, #ccc);
            border-radius: 5px;
        }
        .form-group input[type="submit"] {
            width: auto;
            padding: 10px 20px;
            background-color: var(--button-bg, #4CAF50);
            color: var(--button-text, white);
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .form-group input[type="submit"]:hover {
            background-color: var(--button-hover-bg, #45a049);
        }
        .form-group .btn-secondary {
            background-color: var(--button-bg-secondary, #ccc);
            color: var(--button-text-secondary, black);
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 5px;
            display: inline-block;
            margin-top: 10px;
        }
        .form-group .btn-secondary:hover {
            background-color: var(--button-hover-bg-secondary, #bbb);
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
        }
        .theme-toggle {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="theme-toggle">
        <label for="themeSwitch">Dark Mode:</label>
        <input type="checkbox" id="themeSwitch" onchange="toggleTheme()">
    </div>
    <h2>${empty borrowedBook ? 'Add Borrowed Book' : 'Edit Borrowed Book'}</h2>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>
    <form action="${empty borrowedBook ? '/SchoolSystem/library/insertBorrowedBook' : '/SchoolSystem/library/updateBorrowedBook'}" method="post" onsubmit="return validateDates()">
        <div class="form-group">
            <c:if test="${!empty borrowedBook}">
                <input type="hidden" name="id" value="${borrowedBook.borrowId}">
            </c:if>
        </div>

        <div class="form-group">
            <label for="borrowerRefId">Borrower Ref ID:</label>
            <input type="text" class="form-control" id="borrowerRefId" name="borrowerRefId" value="${empty borrowedBook ? '' : borrowedBook.borrowerRefId}" required>
        </div>

        <div class="form-group">
            <label for="bookId">Book ID:</label>
            <input type="text" class="form-control" id="bookId" name="bookId" value="${empty borrowedBook ? '' : borrowedBook.bookId}" required>
        </div>

        <div class="form-group">
            <label for="borrowDate">Borrow Date:</label>
            <input type="date" class="form-control" id="borrowDate" name="borrowDate" value="${empty borrowedBook ? '' : borrowedBook.borrowDate}" required>
        </div>

        <div class="form-group">
            <label for="dueDate">Due Date:</label>
            <input type="date" class="form-control" id="dueDate" name="dueDate" value="${empty borrowedBook ? '' : borrowedBook.dueDate}" required>
        </div>

        <div class="form-group">
            <label for="overdueFine">Overdue Fine:</label>
            <input type="text" name="overdueFine" value="${borrowedBook.overdueFine}" readonly><br>
        </div>

        <div class="form-group">
            <label for="lost">Lost:</label>
            <select id="lost" name="lost" class="form-control">
                <option value="false" ${borrowedBook.lost == false ? 'selected' : ''}>No</option>
                <option value="true" ${borrowedBook.lost == true ? 'selected' : ''}>Yes</option>
            </select>
        </div>

        <div class="form-group">
            <input type="submit" value="${empty borrowedBook ? 'Add' : 'Update'} Borrowed Book">
        </div>
        <div class="form-group">
            <a href="${pageContext.request.contextPath}/library/listBorrowedBooks" class="btn-secondary">View Borrowed Books List</a>
        </div>
    </form>
</div>

<script>
    function toggleTheme() {
        const isDarkMode = document.getElementById('themeSwitch').checked;
        if (isDarkMode) {
            document.documentElement.style.setProperty('--background-color', '#333');
            document.documentElement.style.setProperty('--text-color', '#f0f0f0');
            document.documentElement.style.setProperty('--container-bg', '#444');
            document.documentElement.style.setProperty('--border-color', '#666');
            document.documentElement.style.setProperty('--button-bg', '#555');
            document.documentElement.style.setProperty('--button-text', '#f0f0f0');
            document.documentElement.style.setProperty('--button-hover-bg', '#666');
            document.documentElement.style.setProperty('--button-bg-secondary', '#888');
            document.documentElement.style.setProperty('--button-text-secondary', '#f0f0f0');
            document.documentElement.style.setProperty('--button-hover-bg-secondary', '#999');
        } else {
            document.documentElement.style.setProperty('--background-color', '#f0f0f0');
            document.documentElement.style.setProperty('--text-color', '#333');
            document.documentElement.style.setProperty('--container-bg', '#fff');
            document.documentElement.style.setProperty('--border-color', '#ccc');
            document.documentElement.style.setProperty('--button-bg', '#4CAF50');
            document.documentElement.style.setProperty('--button-text', 'white');
            document.documentElement.style.setProperty('--button-hover-bg', '#45a049');
            document.documentElement.style.setProperty('--button-bg-secondary', '#ccc');
            document.documentElement.style.setProperty('--button-text-secondary', 'black');
            document.documentElement.style.setProperty('--button-hover-bg-secondary', '#bbb');
        }
        localStorage.setItem('darkMode', isDarkMode);
    }

    window.onload = function() {
        const darkMode = localStorage.getItem('darkMode') === 'true';
        document.getElementById('themeSwitch').checked = darkMode;
        toggleTheme();
    }

    function validateDates() {
        var borrowDate = new Date(document.getElementById("borrowDate").value);
        var dueDate = new Date(document.getElementById("dueDate").value);

        if (borrowDate > dueDate) {
            alert("Borrow Date cannot be after Due Date.");
            return false;
        }

        return true;
    }
</script>

</body>
</html>
