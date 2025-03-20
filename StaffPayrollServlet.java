package pages;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/staff-payroll/*")
public class StaffPayrollServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffPayrollDAO payrollDAO;
    private StaffDAO staffDAO;

    @Override
    public void init() {
        payrollDAO = new StaffPayrollDAO();
        staffDAO = new StaffDAO();
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
            throws  ServletException, IOException, SQLException {
        List<Staff> listStaff = payrollDAO.getStaffWithPayrollRecords();
      System.out.println("List of Staff with Payroll Records: " + listStaff); // Debug statement
      request.setAttribute("listPayroll", listStaff);
      request.getRequestDispatcher("/staff-payroll-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String staffIdStr = request.getParameter("staffId");
        if (staffIdStr != null) {
            int staffId = Integer.parseInt(staffIdStr);
            Staff staff = staffDAO.getStaffById(staffId);
            List<StaffPayroll> previousPayments = payrollDAO.getPreviousPayments(staffId);
            request.setAttribute("staff", staff);
            request.setAttribute("listPayments", previousPayments);
        }
        request.getRequestDispatcher("/staff-payroll-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String payrollIdStr = request.getParameter("payrollId");
        if (payrollIdStr == null || payrollIdStr.isEmpty()) {
            throw new ServletException("Payroll ID is missing or empty");
        }
        int payrollId = Integer.parseInt(payrollIdStr);
        StaffPayroll existingPayroll = payrollDAO.getPayrollById(payrollId);

        // Retrieve the staffId from the existing payroll
        int staffId = existingPayroll.getStaffId();
        Staff staff = staffDAO.getStaffById(staffId);
        List<StaffPayroll> previousPayments = payrollDAO.getPreviousPayments(staffId);

        request.setAttribute("staff", staff);
        request.setAttribute("payroll", existingPayroll);
        request.setAttribute("listPayments", previousPayments);
        request.getRequestDispatcher("/staff-payroll-form.jsp").forward(request, response);
    }

    private void addPayroll(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            int staffId = Integer.parseInt(request.getParameter("staffId"));
            double salary = Double.parseDouble(request.getParameter("salary"));
            double bonus = Double.parseDouble(request.getParameter("bonus"));
            double deductions = Double.parseDouble(request.getParameter("deductions"));
            Date paymentDate = Date.valueOf(request.getParameter("paymentDate"));
            String status = request.getParameter("status");
            String accountNumber = request.getParameter("account_number");

            double netSalary = payrollDAO.calculateNetSalary(salary, deductions, bonus);
            StaffPayroll newPayroll = new StaffPayroll(0, staffId, accountNumber, salary, bonus, deductions, netSalary, paymentDate, status);
            payrollDAO.addPayroll(newPayroll);
            response.sendRedirect(request.getContextPath() + "/staff-payroll/list");
        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void updatePayroll(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int payrollId = Integer.parseInt(request.getParameter("payrollId"));
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        double salary = Double.parseDouble(request.getParameter("salary"));
        double bonus = Double.parseDouble(request.getParameter("bonus"));
        double deductions = Double.parseDouble(request.getParameter("deductions"));
        Date paymentDate = Date.valueOf(request.getParameter("paymentDate"));
        String status = request.getParameter("status");
        String accountNumber = request.getParameter("account_number");


        double netSalary = payrollDAO.calculateNetSalary(salary, deductions, bonus);
        StaffPayroll updatedPayroll = new StaffPayroll(payrollId, staffId,accountNumber, salary, bonus, deductions, netSalary, paymentDate, status);
        payrollDAO.updatePayroll(updatedPayroll);
        response.sendRedirect(request.getContextPath() + "/staff-payroll/list");
    }

    private void deletePayroll(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int payrollId = Integer.parseInt(request.getParameter("payrollId"));
        payrollDAO.deletePayroll(payrollId);
        response.sendRedirect(request.getContextPath() + "/staff-payroll/list");
    }
}
