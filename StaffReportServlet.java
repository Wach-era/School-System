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

@WebServlet("/staffreportservlet/*")
public class StaffReportServlet extends HttpServlet {
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
        String staffIdStr = request.getParameter("staffId");

        if (staffIdStr == null || staffIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Staff ID is missing.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        int staffId = Integer.parseInt(staffIdStr);
        List<Trimester> trimesters = trimesterDAO.getAllTrimesters();
        request.setAttribute("staffId", staffId);
        request.setAttribute("trimesters", trimesters);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/staff-report.jsp");
        dispatcher.forward(request, response);
    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String staffIdStr = request.getParameter("staffId");

        if (staffIdStr == null || staffIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Staff ID is missing.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int staffId;
        try {
        	staffId = Integer.parseInt(staffIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Staff ID format.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String selectedTrimester = request.getParameter("trimester");
        String[] categories = request.getParameterValues("categories");

        StaffDAO staffDAO = new StaffDAO();
        StaffConductDAO conductDAO = new StaffConductDAO();
        StaffAttendanceDAO attendanceDAO = new StaffAttendanceDAO();
        StaffPayrollDAO payrollDAO = new StaffPayrollDAO();
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

        Staff staff = null;
        List<StaffConduct> conductList = new ArrayList<>();
        List<StaffAttendance> attendanceList = new ArrayList<>();
        List<StaffPayroll> payroll = new ArrayList<>();
        List<BorrowedBook> borrowedBooks = new ArrayList<>();

        if (categories != null) {
            for (String category : categories) {
                switch (category) {
                    case "generalInfo":
                        staff = staffDAO.getStaffById2(staffId);
                        break;
                    case "conduct":
                        conductList = conductDAO.getConductByStaffIdAndDateRange(staffId, startDate, endDate);
                        break;
                    case "attendance":
                        attendanceList = attendanceDAO.getAttendanceByStaffIdAndDateRange(staffId, startDate, endDate);
                        break;
                    case "finance":
                        payroll = payrollDAO.getPaymentsByStaffIdAndDateRange(staffId, startDate, endDate);
                        break;
                    case "library":
                        borrowedBooks = borrowedBookDAO.selectBorrowedBooksByStaffAndDateRange(staffId, startDate, endDate);
                        break;
                }
            }
        }

        request.setAttribute("staff", staff);
        request.setAttribute("conductList", conductList);
        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("feePayments", payroll);
        request.setAttribute("borrowedBooks", borrowedBooks);

        request.getRequestDispatcher("/staff-report.jsp").forward(request, response);
    }

    private void handleSQLException(SQLException ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorMessage", ex.getMessage());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
    }
}
