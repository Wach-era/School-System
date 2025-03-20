package pages;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pages.StudentClassEnrollmentDAO.ClassFullException;

@WebServlet("/enrollments/*")
public class EnrollmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentClassEnrollmentDAO enrollmentDAO;
    private AcademicYearDAO yearDAO;

    public void init() {
        enrollmentDAO = new StudentClassEnrollmentDAO();
        yearDAO = new AcademicYearDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertEnrollment(request, response);
                    break;
                case "/delete":
                    deleteEnrollment(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateEnrollment(request, response);
                    break;
                default:
                    listEnrollments(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ClassFullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void listEnrollments(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<StudentClassEnrollment> listEnrollment = enrollmentDAO.selectAllEnrollments();
        request.setAttribute("listEnrollment", listEnrollment);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/enrollment-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<AcademicYear> listYear = yearDAO.selectAllYears();
        request.setAttribute("listYear", listYear);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/enrollment-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int enrollmentId = Integer.parseInt(request.getParameter("enrollment_id"));
        StudentClassEnrollment existingEnrollment = enrollmentDAO.selectEnrollmentById(enrollmentId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/enrollment-form.jsp");
        request.setAttribute("enrollment", existingEnrollment);
        dispatcher.forward(request, response);
    }

    private void insertEnrollment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ClassFullException {
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        String classId = request.getParameter("class_id");
        int yearId = Integer.parseInt(request.getParameter("year_id"));
        String status = request.getParameter("status");
        boolean retake = Boolean.parseBoolean(request.getParameter("retake"));


        if (enrollmentDAO.checkClassExists(classId) && enrollmentDAO.checkStudentExists(studentId) && enrollmentDAO.checkYearExists(yearId)) {
            StudentClassEnrollment newEnrollment = new StudentClassEnrollment(0, studentId, classId, yearId, status,retake);
            enrollmentDAO.insertEnrollment(newEnrollment);
            response.sendRedirect("list");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private void updateEnrollment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int enrollmentId = Integer.parseInt(request.getParameter("enrollment_id"));
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        String classId = request.getParameter("class_id");
        int yearId = Integer.parseInt(request.getParameter("year_id"));
        String status = request.getParameter("status");
        boolean retake = Boolean.parseBoolean(request.getParameter("retake"));


        if (enrollmentDAO.checkClassExists(classId) && enrollmentDAO.checkStudentExists(studentId) && enrollmentDAO.checkYearExists(yearId)) {
            StudentClassEnrollment enrollment = new StudentClassEnrollment(enrollmentId, studentId, classId, yearId, status,retake);
            enrollmentDAO.updateEnrollment(enrollment);
            response.sendRedirect("list");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private void deleteEnrollment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int enrollmentId = Integer.parseInt(request.getParameter("enrollment_id"));
        enrollmentDAO.deleteEnrollment(enrollmentId);
        response.sendRedirect("list");
    }
}
