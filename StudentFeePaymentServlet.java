package pages;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/student-fee-payment/*")
public class StudentFeePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentFeePaymentDAO paymentDAO;
    private BillingService billingService;
    private StudentDAO studentDAO;
    private TrimesterDAO trimesterDAO;

    @Override
    public void init() {
        paymentDAO = new StudentFeePaymentDAO();
        billingService = new BillingService();
        studentDAO = new StudentDAO();
        trimesterDAO = new TrimesterDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/list":
                    listPayments(request, response);
                    break;
                case "/generateBills":
                    generateBills(request, response);
                    break;
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/listByStudent":
                    listPaymentsByStudent(request, response);
                    break;
                default:
                    listPayments(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listPayments(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Student> listStudents = studentDAO.getAllStudentsWithFeeDetails();
        for (Student student : listStudents) {
            double totalFeesDue = studentDAO.calculateTotalFeesDue(student.getStudentId());
            student.setTotalFeesDue(totalFeesDue);
        }
        request.setAttribute("listStudents", listStudents);
        request.getRequestDispatcher("/student-fee-payment-list.jsp").forward(request, response);
    }


    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));
        StudentFeePayment payment = paymentDAO.getPaymentById(paymentId);
        List<Trimester> trimesters = trimesterDAO.getAllTrimesters();
        int studentId = payment.getStudentId();
        Student student = studentDAO.getStudentById(studentId);
        
        // Calculate total fees due and overpayment for the student
        double totalFeesDue = studentDAO.calculateTotalFeesDue(studentId);
        double overpayment = paymentDAO.calculateTotalOverpayment(studentId);
        
        request.setAttribute("trimesters", trimesters);
        request.setAttribute("student", student);
        request.setAttribute("payment", payment);
        request.setAttribute("totalFeesDue", totalFeesDue);
        request.setAttribute("overpayment", overpayment);
        
        // Retrieve previous payments
        List<StudentFeePayment> previousPayments = paymentDAO.getPaymentsByStudentId(studentId);
        request.setAttribute("previousPayments", previousPayments);
        
        request.getRequestDispatcher("/student-fee-payment-form.jsp").forward(request, response);
    }

    private void generateBills(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String trimesterId = request.getParameter("trimesterId");
        billingService.generateBillsForTrimester(trimesterId);
        response.sendRedirect(request.getContextPath() + "/student-fee-payment/list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Trimester> trimesters = trimesterDAO.getAllTrimesters();
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        Student student = studentDAO.getStudentById(studentId);
        
        // Calculate total fees due and overpayment for the student
        double totalFeesDue = studentDAO.calculateTotalFeesDue(studentId);
        double overpayment = paymentDAO.calculateTotalOverpayment(studentId);
        
        request.setAttribute("trimesters", trimesters);
        request.setAttribute("student", student);
        request.setAttribute("totalFeesDue", totalFeesDue);
        request.setAttribute("overpayment", overpayment);
        
        // Retrieve previous payments
        List<StudentFeePayment> previousPayments = paymentDAO.getPaymentsByStudentId(studentId);
        request.setAttribute("previousPayments", previousPayments);
        
        request.getRequestDispatcher("/student-fee-payment-form.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/add":
                    addPayment(request, response);
                    break;
                case "/update":
                    updatePayment(request, response);
                    break;

                case "/delete":
                    deletePayment(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void addPayment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String trimesterId = request.getParameter("trimesterId");
        double amountPaid = Double.parseDouble(request.getParameter("amountPaid"));
        Date dateOfPayment = Date.valueOf(request.getParameter("dateOfPayment"));
        String paymentMode = request.getParameter("paymentMode");

        if (studentDAO.studentExists(studentId)) {
            StudentFeePayment newPayment = new StudentFeePayment(0,studentId, trimesterId, amountPaid, dateOfPayment, paymentMode);
            paymentDAO.addPayment(newPayment);
            
            // Recalculate total fees due
            double totalFeesDue = studentDAO.calculateTotalFeesDue(studentId);
            studentDAO.updateTotalFeesDue(studentId, totalFeesDue);

            response.sendRedirect(request.getContextPath() + "/student-fee-payment/list");
        } else {
            request.setAttribute("errorMessage", "Student ID does not exist.");
            showNewForm(request, response);
        }
    }
    
    
    private void updatePayment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String trimesterId = request.getParameter("trimesterId");
        double amountPaid = Double.parseDouble(request.getParameter("amountPaid"));
        Date dateOfPayment = Date.valueOf(request.getParameter("dateOfPayment"));
        String paymentMode = request.getParameter("paymentMode");

        StudentFeePayment existingPayment = paymentDAO.getPaymentById(paymentId);
        double oldAmountPaid = existingPayment.getAmountPaid();

        StudentFeePayment payment = new StudentFeePayment(paymentId, studentId, trimesterId, amountPaid, dateOfPayment, paymentMode);
        paymentDAO.updatePayment(payment);

        // Adjust the total fees due
        studentDAO.updateTotalFeesDue(studentId, oldAmountPaid - amountPaid);

        response.sendRedirect(request.getContextPath() + "/student-fee-payment/list");
    }
    
    private void deletePayment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        
        // Retrieve the payment amount before deleting
        StudentFeePayment payment = paymentDAO.getPaymentById(paymentId);
        double amountPaid = payment.getAmountPaid();
        
        // Delete the payment
        paymentDAO.deletePayment(paymentId);
        
        // Adjust the total fees due
        studentDAO.updateTotalFeesDue(studentId, amountPaid);
        
        response.sendRedirect(request.getContextPath() + "/student-fee-payment/list");
    }
    private void listPaymentsByStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        List<StudentFeePayment> payments = paymentDAO.getPaymentsByStudentId(studentId);
        double totalFeesDue = studentDAO.calculateTotalFeesDue(studentId);
        double overpayment = paymentDAO.calculateTotalOverpayment(studentId);

        // Generate HTML for the fee payments
        StringBuilder html = new StringBuilder();
        html.append("<h4>Fee Payments</h4>");
        html.append("<p><strong>Total Fees Due:</strong> $").append(totalFeesDue).append("</p>");
        html.append("<p><strong>Overpayment:</strong> $").append(overpayment).append("</p>");
        if (payments.isEmpty()) {
            html.append("<p>No payments found.</p>");
        } else {
            html.append("<table class='table table-bordered'>");
            html.append("<thead><tr><th>Payment ID</th><th>Trimester</th><th>Amount Paid</th><th>Date of Payment</th><th>Payment Mode</th></tr></thead>");
            html.append("<tbody>");
            for (StudentFeePayment payment : payments) {
                html.append("<tr>");
                html.append("<td>").append(payment.getPaymentId()).append("</td>");
                html.append("<td>").append(payment.getTrimesterId()).append("</td>");
                html.append("<td>").append(payment.getAmountPaid()).append("</td>");
                html.append("<td>").append(payment.getDateOfPayment()).append("</td>");
                html.append("<td>").append(payment.getPaymentMode()).append("</td>");
                html.append("</tr>");
            }
            html.append("</tbody></table>");
        }

        // Set the response content type to HTML
        response.setContentType("text/html");
        response.getWriter().write(html.toString());
    } 

}
