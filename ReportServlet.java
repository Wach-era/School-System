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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/reportservlet/*")
public class ReportServlet extends HttpServlet {
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
            action = "/generate"; 
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
        String studentIdStr = request.getParameter("studentId");

        if (studentIdStr == null || studentIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Student ID is missing.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        int studentId = Integer.parseInt(studentIdStr);
        List<Trimester> trimesters = trimesterDAO.getAllTrimesters();
        request.setAttribute("studentId", studentId);
        request.setAttribute("trimesters", trimesters);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/report.jsp");
        dispatcher.forward(request, response);
    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String studentIdStr = request.getParameter("studentId");

        if (studentIdStr == null || studentIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Student ID is missing.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int studentId;
        try {
            studentId = Integer.parseInt(studentIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Student ID format.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String selectedTrimester = request.getParameter("trimester");
        String[] categories = request.getParameterValues("categories");

        StudentDAO studentDAO = new StudentDAO();
        StudentConductDAO conductDAO = new StudentConductDAO();
        StudentAttendanceDAO attendanceDAO = new StudentAttendanceDAO();
        StudentFeePaymentDAO feePaymentDAO = new StudentFeePaymentDAO();
        StudentClubDAO clubDAO = new StudentClubDAO();
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

        Student student = null;
        List<StudentConduct> conductList = new ArrayList<>();
        List<StudentAttendance> attendanceList = new ArrayList<>();
        List<StudentFeePayment> feePayments = new ArrayList<>();
        List<StudentClub> clubs = new ArrayList<>();
        List<BorrowedBook> borrowedBooks = new ArrayList<>();

        if (categories != null) {
            for (String category : categories) {
                switch (category) {
                    case "generalInfo":
                        student = studentDAO.getStudentById2(studentId);
                        break;
                    case "conduct":
                        conductList = conductDAO.getConductByStudentIdAndDateRange(studentId, startDate, endDate);
                        break;
                    case "attendance":
                        attendanceList = attendanceDAO.getAttendanceByStudentIdAndDateRange(studentId, startDate, endDate);
                        break;
                    case "finance":
                        feePayments = feePaymentDAO.getPaymentsByStudentIdAndDateRange(studentId, startDate, endDate);
                        break;
                    case "clubs":
                        clubs = clubDAO.getClubsByStudentIdAndDateRange(studentId, startDate, endDate);
                        break;
                    case "library":
                        borrowedBooks = borrowedBookDAO.selectBorrowedBooksByStudentAndDateRange(studentId, startDate, endDate);
                        break;
                }
            }
        }
        
        if (categories != null) {
            Map<String, Object> summaryStats = new HashMap<>();
            
            if (Arrays.asList(categories).contains("attendance")) {
                long presentDays = attendanceList.stream().filter(a -> a.getStatus().equals("Present")).count();
                summaryStats.put("attendanceRate", (presentDays * 100) / attendanceList.size());
            }
            
            if (Arrays.asList(categories).contains("finance")) {
                double totalPaid = feePayments.stream().mapToDouble(StudentFeePayment::getAmountPaid).sum();
                summaryStats.put("totalPaid", totalPaid);
            }
            
            request.setAttribute("summaryStats", summaryStats);
        }

        request.setAttribute("student", student);
        request.setAttribute("conductList", conductList);
        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("feePayments", feePayments);
        request.setAttribute("clubs", clubs);
        request.setAttribute("borrowedBooks", borrowedBooks);

        request.getRequestDispatcher("/report.jsp").forward(request, response);
    }

    private void handleSQLException(SQLException ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorMessage", ex.getMessage());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
    }
}
