<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Staff Management</title>
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
        .form-section {
            display: none; /* Hide all sections initially */
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }
        .form-section h2 {
            color: #007BFF;
        }
        .form-section label {
            display: block;
            margin-bottom: 5px;
        }
        .form-section input[type="text"], .form-section input[type="number"], .form-section input[type="email"], .form-section input[type="date"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-section button {
            background-color: #007BFF;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .form-section button:hover {
            background-color: #0056b3;
        }
        .filter-section {
            margin-bottom: 20px;
        }
        .filter-section label {
            margin-right: 10px;
        }
        .filter-section select, .filter-section input[type="text"] {
            padding: 5px;
            margin-right: 10px;
        }
        .radio-group {
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .radio-group label {
        display: flex;
        align-items: center;
    }
    </style>
</head>
<body>
    <div class="header">
        <h1>Staff Management</h1>
        <form action="Logoutservlet" method="post" style="display:inline;">
            <input type="submit" value="Logout" class="logout">
        </form>
    </div>
    <div class="sidebar">
        <a href="javascript:void(0);" onclick="showSection('add')">Add New Staff</a>
        <a href="javascript:void(0);" onclick="showSection('update')">Update/Delete Staff</a>
        <a href="javascript:void(0);" onclick="showSection('view')">View Staff</a>
    </div>
    <div class="main-content">
        <div id="add" class="form-section">
            <h2>Add New Staff</h2>
            <form action="AddstaffServlet" method="post">
                <h4>Personal Information</h4>
                <label for="ID">Staff ID:</label>
                <input type="number" id="ID" name="ID" required>
                
                <label for="fname">First Name:</label>
                <input type="text" id="fname" name="fname" required>
                
                <label for="mname">Middle Name:</label>
                <input type="text" id="mname" name="mname" required>
                
                <label for="lname">Last Name:</label>
                <input type="text" id="lname" name="lname" required>
                
                <label for="dob">Date of Birth:</label>
                <input type="date" id="dob" name="dateofbirth" required>
                
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
                
               <label for="phone">Phone Number:</label>
<input type="text" id="phonenumber" name="phonenumber" pattern="\+254\d{9}" title="Phone number must start with +254 and contain 9 digits after that" required>

                
                <div class="radio-group">
    <label>
        Gender:
    </label>
    <div style="display: flex;">
        <input type="radio" id="gender1" name="gender" value="Male">
        <label for="gender1"> Male</label>
        <input type="radio" id="gender2" name="gender" value="Female">
        <label for="gender2"> Female</label>
    </div>
</div>

<div class="radio-group">
    <label>
        Religion:
    </label>
    <div style="display: flex;">
        <input type="radio" id="religion1" name="religion" value="Christian">
        <label for="religion1"> Christian</label>
        <input type="radio" id="religion2" name="religion" value="Muslim">
        <label for="religion2"> Muslim</label>
        <input type="radio" id="religion3" name="religion" value="Hindi">
        <label for="religion3"> Hindi</label>
        <input type="radio" id="religion4" name="religion" value="Other">
        <label for="religion4"> Other</label>
    </div>
</div>
                
                <h4>ROLES</h4>
                <label for="tite">Title:</label>
                <input type="text" id="title" name="title" required>
                
                <label for="department">Department:</label>
    <select id="department" name="department_id" required>
        <option value="Administration-Department">Administration</option>
        <option value="Cleaning-Department">Cleaning</option>
        <option value="Security-Department">Security</option>
        <option value="Cafeteria-Department">Cafeteria</option>
        <option value="Library-Department">Library</option>
    </select>
                <label for="doh">Date of Hire:</label>
                <input type="date" id="doh" name="dateofhire" required>
                
                <label for="respo">Resposibilities:</label>
                <input type="text" id="respo" name="respo" required>
                
                <h4>QUALIFICATIONS</h4>
                <label for="education">Education:</label>
                <input type="text" id="education" name="education" required>
                
                <label for="cert">Certification:</label>
                <input type="text" id="cert" name="cert" required>
                
                <label for="exp">Experience:</label>
                <input type="text" id="exp" name="exp" required>
                
                <h4>ADDITIONAL INFORMATION</h4>
                <label for="emerg">Emergency Contact:</label>
                <input type="number" id="emerg" name="name= phonenumber" pattern="\+254\d{9}" title="Phone number must start with +254 and contain 9 digits after that">
>
                
                <label for="health">Health Information:</label>
                <input type="text" id="health" name="health" required>
                
                <label for="special">Special Accomodation:</label>
                <input type="text" id="special" name="special" required>
                <button type="submit">Add Staff</button>
            </form>
        </div>
        
          
            

          <div id="update" class="form-section">
            <h2>Update/Delete Staff</h2>
            <div class="filter-section">
                <label for="staff-id">Enter Staff ID:</label>
                <input type="number" id="staff-id" name="staff-id" required>
                <button type="button" onclick="fetchStaffDetails()">Fetch Details</button>
            </div>
            <div id="update-form" style="display: none;">
                <h3>Update Staff Information</h3>
                <form action="UpdateStaffServlet" method="post">
                    <input type="hidden" id="update-staff-id" name="staff_id">
                    <!-- Personal Information -->
                    <h4>Personal Information</h4>
                    <label for="update-fname">First Name:</label>
                    <input type="text" id="update-fname" name="fname">
                    <label for="update-mname">Middle Name:</label>
                    <input type="text" id="update-mname" name="mname">
                    <label for="update-lname">Last Name:</label>
                    <input type="text" id="update-lname" name="lname">
                    <label for="update-dob">Date of Birth:</label>
                    <input type="date" id="update-dob" name="dateofbirth">
                    <label for="update-email">Email:</label>
                    <input type="email" id="update-email" name="email">
                    <label for="update-phone">Phone Number:</label>
                    <input type="text" id="update-phone" name="phonenumber" pattern="\+254\d{9}" title="Phone number must start with +254 and contain 9 digits after that">
                    <div class="radio-group">
                        <label>Gender:</label>
                        <div style="display: flex;">
                            <input type="radio" id="update-gender1" name="gender" value="Male">
                            <label for="update-gender1"> Male</label>
                            <input type="radio" id="update-gender2" name="gender" value="Female">
                            <label for="update-gender2"> Female</label>
                        </div>
                    </div>
                    <div class="radio-group">
                        <label>Religion:</label>
                        <div style="display: flex;">
                            <input type="radio" id="update-religion1" name="religion" value="Christian">
                            <label for="update-religion1"> Christian</label>
                            <input type="radio" id="update-religion2" name="religion" value="Muslim">
                            <label for="update-religion2"> Muslim</label>
                            <input type="radio" id="update-religion3" name="religion" value="Hindi">
                            <label for="update-religion3"> Hindi</label>
                            <input type="radio" id="update-religion4" name="religion" value="Other">
                            <label for="update-religion4"> Other</label>
                        </div>
                    </div>
                    <!-- Roles -->
                    <h4>Roles</h4>
                    <label for="update-title">Title:</label>
                    <input type="text" id="update-title" name="title">
                    <label for="update-department">Department:</label>
                    <select id="update-department" name="department_id">
                        <option value="Administration-Department">Administration</option>
                        <option value="Cleaning-Department">Cleaning</option>
                        <option value="Security-Department">Security</option>
                        <option value="Cafeteria-Department">Cafeteria</option>
                        <option value="Library-Department">Library</option>
                    </select>
                    <label for="update-doh">Date of Hire:</label>
                    <input type="date" id="update-doh" name="dateofhire">
                    <label for="update-respo">Responsibilities:</label>
                    <input type="text" id="update-respo" name="respo">
                    <!-- Qualifications -->
                    <h4>Qualifications</h4>
                    <label for="update-education">Education:</label>
                    <input type="text" id="update-education" name="education">
                    <label for="update-cert">Certification:</label>
                    <input type="text" id="update-cert" name="certification">
                    <label for="update-exp">Experience:</label>
                    <input type="text" id="update-exp" name="experience">
                    <!-- Additional Information -->
                    <h4>Additional Information</h4>
                    <label for="update-emerg">Emergency Contact:</label>
                    <input type="number" id="update-emerg" name="emergency_contact" pattern="\+254\d{9}" title="Phone number must start with +254 and contain 9 digits after that">
                    <label for="update-health">Health Information:</label>
                    <input type="text" id="update-health" name="health">
                    <label for="update-special">Special Accommodation:</label>
                    <input type="text" id="update-special" name="special">
                    <h4>Additional Fields</h4>
                    <label for="update-reviews">Staff Reviews:</label>
                    <div>
                        <input type="radio" id="review-excellent" name="reviews" value="Excellent">
                        <label for="review-excellent">Excellent</label>
                        <input type="radio" id="review-good" name="reviews" value="Good">
                        <label for="review-good">Good</label>
                        <input type="radio" id="review-average" name="reviews" value="Average">
                        <label for="review-average">Average</label>
                        <input type="radio" id="review-bad" name="reviews" value="Bad">
                        <label for="review-bad">Bad</label>
                    </div>
                    <label for="review-date">Review Date:</label>
                    <input type="date" id="review-date" name="review_date">
                    <label for="update-attendance">Attendance:</label>
                    <div>
                        <input type="radio" id="attendance-present" name="attendance" value="Present">
                        <label for="attendance-present">Present</label>
                        <input type="radio" id="attendance-late" name="attendance" value="Late">
                        <label for="attendance-late">Late</label>
                        <input type="radio" id="attendance-absent" name="attendance" value="Absent">
                        <label for="attendance-absent">Absent</label>
                    </div>
                    <label for="attendance-date">Attendance Date:</label>
                    <input type="date" id="attendance-date" name="attendance_date">
                    <label for="update-conduct">Conduct:</label>
                    <div>
                        <input type="radio" id="conduct-excellent" name="conduct" value="Excellent">
                        <label for="conduct-excellent">Excellent</label>
                        <input type="radio" id="conduct-good" name="conduct" value="Good">
                        <label for="conduct-good">Good</label>
                        <input type="radio" id="conduct-average" name="conduct" value="Average">
                        <label for="conduct-average">Average</label>
                        <input type="radio" id="conduct-bad" name="conduct" value="Bad">
                        <label for="conduct-bad">Bad</label>
                    </div>
                    <label for="conduct-date">Conduct Date:</label>
                    <input type="date" id="conduct-date" name="conduct_date">
                    <button type="submit">Update Staff</button>
                </form>
                <form action="DeleteStaffServlet" method="post">
                    <input type="hidden" id="delete-staff-id" name="staff_id">
                    <button type="submit" style="background-color: red; color: white;">Delete Staff</button>
                </form>
            </div>
        </div>
       <div id="view" class="form-section">
    <h2>View Staff</h2>
    <form id="view-staff-form" method="post" action="ViewStaffServlet">
        <label for="staff-id">Enter Staff ID:</label>
        <input type="number" id="staff-id" name="staff_id" required>
        <button type="submit">View Details</button>
    </form>

    <div id="staff-details" style="display: none;">
        <h3>Staff Details</h3>
        <div id="details-content">
            <!-- Staff details will be displayed here -->
        </div>
        <h3>Filters</h3>
        <label for="filter-category">Filter By:</label>
        <select id="filter-category" onchange="filterDetails()">
            <option value="all">All</option>
            <option value="reviews">Reviews</option>
            <option value="attendance">Attendance</option>
            <option value="conduct">Conduct</option>
        </select>
    </div>
</div>
  </div>  
    <script>
        function showSection(sectionId) {
            var sections = document.getElementsByClassName('form-section');
            for (var i = 0; i < sections.length; i++) {
                sections[i].style.display = 'none';
            }
            document.getElementById(sectionId).style.display = 'block';
        }
        // Show the add section by default
        showSection('add');
     // Get the input element
        var phoneNumberInput = document.getElementById("phonenumber");

        // Add event listener for input
        phoneNumberInput.addEventListener("input", function() {
            // Check if the input starts with "+254"
            if (!this.value.startsWith("+254")) {
                // If it doesn't, prepend "+254"
                this.value = "+254" + this.value;
            }
        });
        function fetchStaffDetails() {
            const staffId = document.getElementById('staff-id').value;
            if (!staffId) {
                alert('Please enter a Staff ID');
                return;
            }
            fetch(`GetStaffDetailServlet?staff_id=${staffId}`)
                .then(response => response.json())
                .then(data => {
                    if (data.error) {
                        alert(data.message);
                    } else {
                        document.getElementById('update-form').style.display = 'block';
                        document.getElementById('update-staff-id').value = data.staff_id;
                        document.getElementById('delete-staff-id').value = data.staff_id;
                        document.getElementById('update-fname').value = data.fname;
                        document.getElementById('update-mname').value = data.mname;
                        document.getElementById('update-lname').value = data.lname;
                        document.getElementById('update-dob').value = data.dateofbirth;
                        document.getElementById('update-email').value = data.email;
                        document.getElementById('update-phone').value = data.phonenumber;
                        document.querySelector(`input[name="gender"][value="${data.gender}"]`).checked = true;
                        document.querySelector(`input[name="religion"][value="${data.religion}"]`).checked = true;
                        document.getElementById('update-title').value = data.title;
                        document.getElementById('update-department').value = data.department_id;
                        document.getElementById('update-doh').value = data.dateofhire;
                        document.getElementById('update-respo').value = data.respo;
                        document.getElementById('update-education').value = data.education_level;
                        document.getElementById('update-cert').value = data.certification;
                        document.getElementById('update-exp').value = data.experience;
                        document.getElementById('update-emerg').value = data.emergency_contact;
                        document.getElementById('update-health').value = data.health;
                        document.getElementById('update-special').value = data.special;
                        document.querySelector(`input[name="reviews"][value="${data.reviews}"]`).checked = true;
                        document.getElementById('review-date').value = data.review_date;
                        document.querySelector(`input[name="attendance"][value="${data.attendance}"]`).checked = true;
                        document.getElementById('attendance-date').value = data.attendance_date;
                        document.querySelector(`input[name="conduct"][value="${data.conduct}"]`).checked = true;
                        document.getElementById('conduct-date').value = data.conduct_date;
                    }
                })
                .catch(error => {
                    console.error('Error fetching staff details:', error);
                    alert('Error fetching staff details');
                });
        }
            function displayStaffDetail(staff) {
                const detailsContent = document.getElementById('details-content');
                detailsContent.innerHTML = `
                    <div><strong>Staff ID:</strong> ${staff.staff_id}</div>
                    <div><strong>Name:</strong> ${staff.first_name} ${staff.middle_name} ${staff.last_name}</div>
                    <div><strong>Date of Birth:</strong> ${staff.date_of_birth}</div>
                    <div><strong>Email:</strong> ${staff.email}</div>
                    <div><strong>Phone Number:</strong> ${staff.phone_number}</div>
                    <div><strong>Gender:</strong> ${staff.gender}</div>
                    <div><strong>Religion:</strong> ${staff.religion}</div>
                    <div><strong>Title:</strong> ${staff.title}</div>
                    <div><strong>Department:</strong> ${staff.department_id}</div>
                    <div><strong>Date of Hire:</strong> ${staff.date_of_hire}</div>
                    <div><strong>Responsibilities:</strong> ${staff.responsibilities}</div>
                    <div><strong>Education:</strong> ${staff.education}</div>
                    <div><strong>Certification:</strong> ${staff.certification}</div>
                    <div><strong>Experience:</strong> ${staff.experience}</div>
                    <div><strong>Emergency Contact:</strong> ${staff.emergency_contact}</div>
                    <div><strong>Health Information:</strong> ${staff.health_information}</div>
                    <div><strong>Special Accommodation:</strong> ${staff.special_accommodation}</div>
                    <div class="reviews"><strong>Review:</strong> ${staff.review} on ${staff.review_date}</div>
                    <div class="attendance"><strong>Attendance:</strong> ${staff.attendance} on ${staff.attendance_date}</div>
                    <div class="conduct"><strong>Conduct:</strong> ${staff.conduct} on ${staff.conduct_date}</div>
                `;
                document.getElementById('staff-details').style.display = 'block';
            }
            </script>
</body>
</html>
