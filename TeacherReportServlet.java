package pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/teacherreportservlet/*")
public class TeacherReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TrimesterDAO trimesterDAO;

    public void init() {
        trimesterDAO = new TrimesterDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) {
            action = "/generate"; // Default action
        }

        try {
            switch (action) {
                case "/generate":
                    showReportForm(request, response);
                    break;
                case "/create":
                    generateReport(request, response);
                    break;
                default:
                    showReportForm(request, response);
                    break;
            }
        } catch (SQLException ex) {
            handleSQLException(ex, request, response);
        }
    }

    private void showReportForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String nationalIdStr = request.getParameter("nationalId");

        if (nationalIdStr == null || nationalIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "National ID is missing.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        int nationalId = Integer.parseInt(nationalIdStr);
        List<Trimester> trimesters = trimesterDAO.getAllTrimesters();
        request.setAttribute("nationalId", nationalId);
        request.setAttribute("trimesters", trimesters);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-report.jsp");
        dispatcher.forward(request, response);
    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String nationalIdStr = request.getParameter("nationalId");

        if (nationalIdStr == null || nationalIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "National ID is missing.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int nationalId;
        try {
        	nationalId = Integer.parseInt(nationalIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid National ID format.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String selectedTrimester = request.getParameter("trimester");
        String[] categories = request.getParameterValues("categories");

        TeacherDAO teacherDAO = new TeacherDAO();
        TeacherConductDAO conductDAO = new TeacherConductDAO();
        TeacherAttendanceDAO attendanceDAO = new TeacherAttendanceDAO();
        TeachersPayrollDAO payrollDAO = new TeachersPayrollDAO();
        PatronDAO clubDAO = new PatronDAO();
        LibraryDAO borrowedBookDAO = new LibraryDAO();
        TrimesterDAO trimesterDAO = new TrimesterDAO();

        if (selectedTrimester != null && !selectedTrimester.isEmpty()) {
            try {
                Trimester trimester = trimesterDAO.getTrimesterById(selectedTrimester);
                startDate = trimester.getStartDate().toString();
                endDate = trimester.getEndDate().toString();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Teacher teacher = null;
        List<TeacherConduct> conductList = new ArrayList<>();
        List<TeacherAttendance> attendanceList = new ArrayList<>();
        List<TeachersPayroll> payroll = new ArrayList<>();
        List<Patron> clubs = new ArrayList<>();
        List<BorrowedBook> borrowedBooks = new ArrayList<>();

        if (categories != null) {
            for (String category : categories) {
                switch (category) {
                    case "generalInfo":
                        teacher = teacherDAO.getTeacherById2(nationalId);
                        break;
                    case "conduct":
                        conductList = conductDAO.getConductByTeacherIdAndDateRange(nationalId, startDate, endDate);
                        break;
                    case "attendance":
                        attendanceList = attendanceDAO.getAttendanceByTeacherIdAndDateRange(nationalId, startDate, endDate);
                        break;
                    case "finance":
                        payroll = payrollDAO.getPaymentsByTeacherIdAndDateRange(nationalId, startDate, endDate);
                        break;
                    case "clubs":
                        clubs = clubDAO.getClubsByTeacherIdAndDateRange(nationalId, startDate, endDate);
                        break;
                    case "library":
                        borrowedBooks = borrowedBookDAO.selectBorrowedBooksByTeacherAndDateRange(nationalId, startDate, endDate);
                        break;
                }
            }
        }

        request.setAttribute("teacher", teacher);
        request.setAttribute("conductList", conductList);
        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("feePayments", payroll);
        request.setAttribute("clubs", clubs);
        request.setAttribute("borrowedBooks", borrowedBooks);

        request.getRequestDispatcher("/teacher-report.jsp").forward(request, response);
    }

    private void handleSQLException(SQLException ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorMessage", ex.getMessage());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
    }
}
