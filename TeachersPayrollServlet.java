package pages;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;

@WebServlet("/teachers-payroll/*")
public class TeachersPayrollServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TeachersPayrollDAO payrollDAO;
    private TeacherDAO teacherDAO;

    @Override
    public void init() {
        payrollDAO = new TeachersPayrollDAO();
        teacherDAO = new TeacherDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/delete":
                    deletePayroll(request, response);
                    break;
                case "/list":
                default:
                    listPayrolls(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/insert":
                    addPayroll(request, response);
                    break;
                case "/update":
                    updatePayroll(request, response);
                    break;
                default:
                    listPayrolls(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listPayrolls(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	 List<Teacher> listTeachers = payrollDAO.getTeachersWithPayrollRecords();
    	 request.setAttribute("listPayroll", listTeachers);
        request.getRequestDispatcher("/teachers-payroll-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String nationalIdStr = request.getParameter("nationalId");
        if (nationalIdStr != null) {
            int nationalId = Integer.parseInt(nationalIdStr);
            Teacher teacher = teacherDAO.getTeacherById(nationalId);
            List<TeachersPayroll> previousPayments = payrollDAO.getPreviousPayments(nationalId);
            request.setAttribute("teacher", teacher);
            request.setAttribute("listPayments", previousPayments);
        }
        request.getRequestDispatcher("/teachers-payroll-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String payrollIdStr = request.getParameter("payrollId");
        if (payrollIdStr == null || payrollIdStr.isEmpty()) {
            throw new ServletException("Payroll ID is missing or empty");
        }
        int payrollId = Integer.parseInt(payrollIdStr);
        TeachersPayroll existingPayroll = payrollDAO.getPayrollById(payrollId);

        // Retrieve the nationalId from the existing payroll
        int nationalId = existingPayroll.getNationalId();
        Teacher teacher = teacherDAO.getTeacherById(nationalId);
        List<TeachersPayroll> previousPayments = payrollDAO.getPreviousPayments(nationalId);

        request.setAttribute("teacher", teacher);
        request.setAttribute("payroll", existingPayroll);
        request.setAttribute("listPayments", previousPayments);
        request.getRequestDispatcher("/teachers-payroll-form.jsp").forward(request, response);
    }


    private void addPayroll(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            int nationalId = Integer.parseInt(request.getParameter("nationalId"));
            double salary = Double.parseDouble(request.getParameter("salary"));
            double bonus = Double.parseDouble(request.getParameter("bonus"));
            double deductions = Double.parseDouble(request.getParameter("deductions"));
            Date paymentDate = Date.valueOf(request.getParameter("paymentDate"));
            String status = request.getParameter("status");

            double netSalary = payrollDAO.calculateNetSalary(salary, deductions, bonus);
            TeachersPayroll newPayroll = new TeachersPayroll(0, nationalId, salary, bonus, deductions, netSalary, paymentDate, status);
            payrollDAO.addPayroll(newPayroll);
            response.sendRedirect(request.getContextPath() + "/teachers-payroll/list");
        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void updatePayroll(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int payrollId = Integer.parseInt(request.getParameter("payrollId"));
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
        double salary = Double.parseDouble(request.getParameter("salary"));
        double bonus = Double.parseDouble(request.getParameter("bonus"));
        double deductions = Double.parseDouble(request.getParameter("deductions"));
        Date paymentDate = Date.valueOf(request.getParameter("paymentDate"));
        String status = request.getParameter("status");

        double netSalary = payrollDAO.calculateNetSalary(salary, deductions, bonus);
        TeachersPayroll updatedPayroll = new TeachersPayroll(payrollId, nationalId, salary, bonus, deductions, netSalary, paymentDate, status);
        payrollDAO.updatePayroll(updatedPayroll);
        response.sendRedirect(request.getContextPath() + "/teachers-payroll/list");
    }

    private void deletePayroll(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int payrollId = Integer.parseInt(request.getParameter("payrollId"));
        payrollDAO.deletePayroll(payrollId);
        response.sendRedirect(request.getContextPath() + "/teachers-payroll/list");
    }
}
