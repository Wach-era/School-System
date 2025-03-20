package pages;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/staff/*")
public class StaffServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffDAO staffDAO;

    public void init() {
        staffDAO = new StaffDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertStaff(request, response);
                    break;
                case "/delete":
                    deleteStaff(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateStaff(request, response);
                    break;
                default:
                    listStaff(request, response);
                    break;
            }
        } catch (SQLException ex) {
            handleSQLException(ex, request, response);
        }
    }

    private void handleSQLException(SQLException ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorMessage", ex.getMessage());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
    }

    private void listStaff(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Staff> listStaff = staffDAO.selectAllStaff();
        request.setAttribute("listStaff", listStaff);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/staff-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = staffDAO.selectAllDepartments();
        request.setAttribute("departments", departments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/staff-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int staffId = Integer.parseInt(request.getParameter("id"));
        Staff existingStaff = staffDAO.selectStaff(staffId);
        List<Department> departments = staffDAO.selectAllDepartments();
        request.setAttribute("departments", departments);
        request.setAttribute("staff", existingStaff);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/staff-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertStaff(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        try {
            int staffId = Integer.parseInt(request.getParameter("staffId"));
            String firstName = request.getParameter("first_name");
            String middleName = request.getParameter("middle_name");
            String lastName = request.getParameter("last_name");
            String dateOfBirthString = request.getParameter("date_of_birth");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phone_number");
            String gender = request.getParameter("gender");
            String religion = request.getParameter("religion");
            String title = request.getParameter("title");
            String departmentId = request.getParameter("department_id");
            String dateOfHireString = request.getParameter("date_of_hire");
            String responsibilities = request.getParameter("responsibilities");
            String educationLevel = request.getParameter("education_level");
            String certification = request.getParameter("certification");
            String experience = request.getParameter("experience");
            String emergencyContact = request.getParameter("emergency_contact");
            String healthInformation = request.getParameter("health_information");
            String specialAccommodation = request.getParameter("special_accommodation");

            System.out.println("Received data:");
            System.out.println("staffId: " + staffId);
            System.out.println("firstName: " + firstName);
            System.out.println("middleName: " + middleName);
            System.out.println("lastName: " + lastName);
            System.out.println("dateOfBirthString: " + dateOfBirthString);
            System.out.println("email: " + email);
            System.out.println("phoneNumber: " + phoneNumber);
            System.out.println("gender: " + gender);
            System.out.println("religion: " + religion);
            System.out.println("title: " + title);
            System.out.println("departmentId: " + departmentId);
            System.out.println("dateOfHireString: " + dateOfHireString);
            System.out.println("responsibilities: " + responsibilities);
            System.out.println("educationLevel: " + educationLevel);
            System.out.println("certification: " + certification);
            System.out.println("experience: " + experience);
            System.out.println("emergencyContact: " + emergencyContact);
            System.out.println("healthInformation: " + healthInformation);
            System.out.println("specialAccommodation: " + specialAccommodation);

            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString);
            LocalDate dateOfHire = LocalDate.parse(dateOfHireString);

            Staff newStaff = new Staff(staffId, firstName, middleName, lastName, dateOfBirth, email, phoneNumber, gender, religion, title, departmentId, dateOfHire, responsibilities, educationLevel, certification, experience, emergencyContact, healthInformation, specialAccommodation);
            System.out.println("Inserting Staff: " + newStaff);
            staffDAO.insertStaff(newStaff);
            response.sendRedirect(request.getContextPath() + "/staff/list");
        } catch (DateTimeParseException | NumberFormatException e) {
            request.setAttribute("errorMessage", "Error parsing date or number: " + e.getMessage());
            e.printStackTrace();  // Print stack trace for debugging
            List<Department> departments = staffDAO.selectAllDepartments();
            request.setAttribute("departments", departments);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/staff-form.jsp");
            dispatcher.forward(request, response);
        }
    }


    private void updateStaff(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        String firstName = request.getParameter("first_name");
        String middleName = request.getParameter("middle_name");
        String lastName = request.getParameter("last_name");
        String dateOfBirth = request.getParameter("date_of_birth"); // Corrected parameter name
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone_number");
        String gender = request.getParameter("gender");
        String religion = request.getParameter("religion");
        String title = request.getParameter("title");
        String departmentId = request.getParameter("department_id");
        String dateOfHire = request.getParameter("date_of_hire");
        String responsibilities = request.getParameter("responsibilities");
        String educationLevel = request.getParameter("education_level");
        String certification = request.getParameter("certification");
        String experience = request.getParameter("experience");
        String emergencyContact = request.getParameter("emergency_contact");
        String healthInformation = request.getParameter("health_information");
        String specialAccommodation = request.getParameter("special_accommodation");

        try {
            Staff updatedStaff = new Staff(
                staffId, firstName, middleName, lastName,
                LocalDate.parse(dateOfBirth), email, phoneNumber,
                gender, religion, title, departmentId,
                LocalDate.parse(dateOfHire), responsibilities,
                educationLevel, certification, experience,
                emergencyContact, healthInformation, specialAccommodation
            );

            staffDAO.updateStaff(updatedStaff);
            response.sendRedirect(request.getContextPath() + "/staff/list");
        } catch (NullPointerException | DateTimeParseException e) {
            // Handle specific exceptions and provide feedback
            request.setAttribute("errorMessage", "Invalid date format or missing date value.");
            showEditForm(request, response); // Show the edit form again with error message
        }
    }
    private void deleteStaff(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int staffId = Integer.parseInt(request.getParameter("id"));
        staffDAO.deleteStaff(staffId);
        response.sendRedirect(request.getContextPath() + "/staff/list");
    }
}
