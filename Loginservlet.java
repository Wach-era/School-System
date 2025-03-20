package pages;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@WebServlet("/Loginservlet")
public class Loginservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DatabaseConnection.getConnection()) { // Use the fully qualified method name
            String sql = "SELECT u.user_id, r.role_name FROM users u JOIN roles r ON u.role_id = r.role_id WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role_name");
                
                // Create session and set attributes
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", role);
                
                // Create cookies for username and role
                Cookie usernameCookie = new Cookie("username", username);
                Cookie roleCookie = new Cookie("role", role);
                
                // Set cookie max age (optional, e.g., 1 day = 86400 seconds)
                usernameCookie.setMaxAge(86400);
                roleCookie.setMaxAge(86400);

                // Add cookies to the response
                response.addCookie(usernameCookie);
                response.addCookie(roleCookie);

                response.sendRedirect("Dashboard.jsp");
            } else {
                response.sendRedirect("LogIn.jsp?error=invalid");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}

//i had a jsp file already created, integrate yours to mine with the scripts, to keep your response short you can minimize the unimportant sections for now <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
//<%
//if (session == null || session.getAttribute("username") == null) {
//    response.sendRedirect("LogIn.jsp");
//    return;
//}
//%>
//<!DOCTYPE html>
//<html>
//<head>
//<meta charset="UTF-8">
//<title>Student Management</title>
//<style>
//    body {
//        font-family: Arial, sans-serif;
//        margin: 0;
//        background-color: #f4f4f4;
//    }
//    .header {
//        background-color: #007BFF;
//        color: white;
//        padding: 15px;
//        text-align: center;
//    }
//    .sidebar {
//        background-color: #343a40;
//        color: white;
//        width: 200px;
//        height: 100vh;
//        position: fixed;
//        top: 0;
//        left: 0;
//        padding-top: 60px;
//    }
//    .sidebar a {
//        padding: 10px 15px;
//        text-decoration: none;
//        color: white;
//        display: block;
//    }
//    .sidebar a:hover {
//        background-color: #007BFF;
//    }
//    .main-content {
//        margin-left: 200px;
//        padding: 20px;
//    }
//    .form-section {
//        display: none; /* Hide all sections initially */
//        background-color: white;
//        padding: 20px;
//        border-radius: 5px;
//        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
//        margin-bottom: 20px;
//    }
//    .form-section h2 {
//        color: #007BFF;
//    }
//    .form-section label {
//        display: block;
//        margin-bottom: 5px;
//    }
//    .form-section input[type="text"], .form-section input[type="number"], .form-section input[type="email"], .form-section input[type="date"] {
//        width: 100%;
//        padding: 10px;
//        margin-bottom: 10px;
//        border: 1px solid #ccc;
//        border-radius: 5px;
//    }
//    .form-section button {
//        background-color: #007BFF;
//        color: white;
//        padding: 10px 15px;
//        border: none;
//        border-radius: 5px;
//        cursor: pointer;
//    }
//    .form-section button:hover {
//        background-color: #0056b3;
//    }
//    .filter-section {
//        margin-bottom: 20px;
//    }
//    .filter-section label {
//        margin-right: 10px;
//    }
//    .filter-section select, .filter-section input[type="text"] {
//        padding: 5px;
//        margin-right: 10px;
//    }
//    .radio-group {
//        display: flex;
//        flex-direction: column;
//        gap: 10px;
//    }
//    .radio-group label {
//        display: flex;
//        align-items: center;
//    }
//    .attendance-table {
//        width: 100%;
//        border-collapse: collapse;
//        margin-top: 20px;
//    }
//    .attendance-table th, .attendance-table td {
//        border: 1px solid #ddd;
//        padding: 8px;
//        text-align: center;
//    }
//    .attendance-table th {
//        background-color: #007BFF;
//        color: white;
//    }
//</style>
//</head>
//<body>
//<div class="header">
//    <h1>Student Management</h1>
//    <form action="Logoutservlet" method="post" style="display:inline;">
//        <input type="submit" value="Logout" class="logout">
//    </form>
//</div>
//<div class="sidebar">
//    <a href="javascript:void(0);" onclick="showSection('enroll')">Enroll New Student</a>
//    <a href="javascript:void(0);" onclick="showSection('update')">Update/Delete Student</a>
//    <a href="javascript:void(0);" onclick="showSection('view')">View Students</a>
//    <a href="javascript:void(0);" onclick="showSection('clearance')">Clearance</a>
//    <a href="javascript:void(0);" onclick="showSection('attendance')">Attendance Management</a>
//</div>
//<div class="main-content">
//    <div id="enroll" class="form-section">
//        <h2>Enroll New Student</h2>
//        <form action="EnrollStudentServlet" method="post">
//            <h4>Personal Information</h4>
//            <label for="ID">Student ID:</label>
//            <input type="number" id="ID" name="ID" required>
//
//            <label for="fname">First Name:</label>
//            <input type="text" id="fname" name="fname" required>
//
//            <label for="mname">Middle Name:</label>
//            <input type="text" id="mname" name="mname" required>
//
//            <label for="lname">Last Name:</label>
//            <input type="text" id="lname" name="lname" required>
//
//            <label for="dob">Date of Birth:</label>
//            <input type="date" id="dob" name="dateofbirth" required>
//
//            <label for="email">Email:</label>
//            <input type="email" id="email" name="email" required>
//
//            <label for="phone">Phone Number:</label>
//            <input type="text" id="phonenumber" name="phonenumber" pattern="\+254\d{9}" title="Phone number must start with +254 and contain 9 digits after that" required>
//
//            <div class="radio-group">
//                <label>Gender:</label>
//                <div style="display: flex;">
//                    <input type="radio" id="gender1" name="gender" value="Male">
//                    <label for="gender1"> Male</label>
//                    <input type="radio" id="gender2" name="gender" value="Female">
//                    <label for="gender2"> Female</label>
//                </div>
//            </div>
//
//            <div class="radio-group">
//                <label>Religion:</label>
//                <div style="display: flex;">
//                    <input type="radio" id="religion1" name="religion" value="Christian">
//                    <label for="religion1"> Christian</label>
//                    <input type="radio" id="religion2" name="religion" value="Muslim">
//                    <label for="religion2"> Muslim</label>
//                    <input type="radio" id="religion3" name="religion" value="Hindu">
//                    <label for="religion3"> Hindu</label>
//                    <input type="radio" id="religion4" name="religion" value="Other">
//                    <label for="religion4"> Other</label>
//                </div>
//            </div>
//            
//            <label for="doe">Date of Enrollment:</label>
//            <input type="date" id="doe" name="doe" required>
//
//            <h4>Parent/Guardian Information</h4>
//            <label for="pname">Parent/Guardian Full Name:</label>
//            <input type="text" id="pname" name="pname" required>
//
//            <label for="relation">Relationship to the Student:</label>
//            <input type="text" id="relation" name="relation" required>
//
//            <h4>Health/Medical Information</h4>
//            <label for="medic">Medical History:</label>
//            <textarea id="medic" name="medic" required></textarea>
//
//           <label for="phone">Emergency Contact:</label>
//<input type="text" id="phonenumber2" name="phonenumber2" pattern="\+254\d{9}" title="Phone number must start with +254 and contain 9 digits after that" required>
//
//            <h4>Special Needs/Accommodation</h4>
//            <label for="learning_disability">Learning Disabilities:</label>
//            <textarea id="learning_disability" name="learning_disability" required></textarea>
//
//            <label for="disability">Details of Disability:</label>
//            <textarea id="disability" name="disability" required></textarea>
//
//            <button type="submit">Enroll Student</button>
//        </form>
//    </div>
//
//    <div id="update" class="form-section">
//        <h2>Update/Delete Student</h2>
//        <div class="filter-section">
//            <label for="student-filter">Search Student:</label>
//            <input type="text" id="student-filter" name="student-filter" placeholder="Enter student name or ID">
//            <button type="button" onclick="searchStudent()">Search</button>
//        </div>
//        <div id="student-list">
//            <!-- List of students will be displayed here -->
//        </div>
//
//        <!-- This section will be populated when a student is selected -->
//        <div id="update-form" style="display: none;">
//            <h3>Update Student Information</h3>
//            <form action="UpdateStudentServlet" method="post">
//                <input type="hidden" id="update-student-id" name="student_id">
//
//                <h4>Conduct Information</h4>
//                <label for="conduct-date">Date:</label>
//                <input type="date" id="conduct-date" name="conduct_date">
//
//                <label for="conduct-description">Description:</label>
//                <textarea id="conduct-description" name="conduct_description"></textarea>
//
//                <h4>Club Membership</h4>
//                <label for="club-id">Select Club:</label>
//                <select id="club-id" name="club_id">
//                    <!-- Populate this with club options from the database -->
//                </select>
//
//                <label for="join-date">Join Date:</label>
//                <input type="date" id="join-date" name="join_date">
//
//                <h4>Additional Information</h4>
//                <label for="updated-medical-history">Medical History:</label>
//                <textarea id="updated-medical-history" name="medical_history"></textarea>
//
//                <label for="updated-learning-disabilities">Learning Disabilities:</label>
//                <textarea id="updated-learning-disabilities" name="learning_disabilities"></textarea>
//
//                <label for="updated-disability-details">Disability Details:</label>
//                <textarea id="updated-disability-details" name="disability_details"></textarea>
//
//                <button type="submit">Update Student</button>
//            </form>
//        </div>
//    </div>
//
//    <div id="view" class="form-section">
//        <h2>View Students</h2>
//        <div class="filter-section">
//            <label for="class-filter">Class:</label>
//            <select id="class-filter" name="class-filter">
//                <option value="">All Classes</option>
//                <!-- Add class options -->
//            </select>
//            <label for="name-filter">Name:</label>
//            <input type="text" id="name-filter" name="name-filter" placeholder="Enter student name">
//            <button type="button" onclick="filterStudents()">Filter</button>
//        </div>
//        <div id="student-view-list">
//            <!-- Filtered list of students will be displayed here -->
//        </div>
//    </div>
//
//    <div id="clearance" class="form-section">
//        <h2>Student Clearance</h2>
//        <form action="ClearanceServlet" method="post">
//            <label for="student-id">Student ID:</label>
//            <input type="text" id="student-id" name="student-id" required>
//            
//            <label for="fees-status">Fees Status:</label>
//            <select id="fees-status" name="fees-status" required>
//                <option value="paid">Paid</option>
//                <option value="unpaid">Unpaid</option>
//            </select>
//            
//            <!-- Add more fields as necessary -->
//
//            <button type="submit">Clear Student</button>
//        </form>
//    </div>
//
//    <div id="attendance" class="form-section">
//        <h2>Attendance Management</h2>
//        <div class="filter-section">
//            <label for="class-attendance-filter">Class:</label>
//            <select id="class-attendance-filter" name="class-attendance-filter">
//                <option value="">All Classes</option>
//                <!-- Add class options -->
//            </select>
//            <label for="date-filter">Date:</label>
//            <input type="date" id="date-filter" name="date-filter">
//            <button type="button" onclick="filterAttendance()">Filter</button>
//        </div>
//        <table class="attendance-table">
//            <thead>
//                <tr>
//                    <th>Student ID</th>
//                    <th>Student Name</th>
//                    <th>Status</th>
//                </tr>
//            </thead>
//            <tbody id="attendance-list">
//                <!-- Attendance records will be displayed here -->
//            </tbody>
//        </table>
//    </div>
//</div>
//<script>
//    function showSection(sectionId) {
//        var sections = document.getElementsByClassName('form-section');
//        for (var i = 0; i < sections.length; i++) {
//            sections[i].style.display = 'none';
//        }
//        document.getElementById(sectionId).style.display = 'block';
//    }
//    // Show the enroll section by default
//    showSection('enroll');
//
//    // Get the input element
//    var phoneNumberInput = document.getElementById("phonenumber");
//
//    // Add event listener for input
//    phoneNumberInput.addEventListener("input", function() {
//        // Check if the input starts with "+254"
//        if (!this.value.startsWith("+254")) {
//            // If it doesn't, prepend "+254"
//            this.value = "+254" + this.value;
//        }
//    });
//    phoneNumberInput2.addEventListener("input", function() {
//        // Check if the input starts with "+254"
//        if (!this.value.startsWith("+254")) {
//            // If it doesn't, prepend "+254"
//            this.value = "+254" + this.value;
//        }
//    });
//
//    function searchStudent() {
//        // Implement AJAX call to search and display the student list
//        // When a student is selected from the list, populate the form fields and show the update form
//        document.getElementById('update-form').style.display = 'block';
//        // Populate the form with the selected student's data (for demonstration purposes, this part is left as an exercise)
//    }
//
//    function filterAttendance() {
//        // Implement AJAX call to filter and display the attendance records
//        // Example: Populate the attendance list with the filtered records
//        var attendanceList = document.getElementById('attendance-list');
//        attendanceList.innerHTML = `
//            <tr>
//                <td>1</td>
//                <td>John Doe</td>
//                <td>
//                    <select name="attendance-status">
//                        <option value="present">Present</option>
//                        <option value="absent">Absent</option>
//                    </select>
//                </td>
//            </tr>
//            <!-- Add more rows as necessary -->
//        `;
//    }
//</script>
//</body>
//</html>