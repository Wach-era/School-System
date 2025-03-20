package pages;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BillingService {
    private StudentFeePaymentDAO paymentDAO;

    public BillingService() {
        paymentDAO = new StudentFeePaymentDAO();
    }

    public void generateBillsForTrimester(String trimesterId) {
        try {
            List<Integer> studentIds = getEligibleStudents(trimesterId);

            for (int studentId : studentIds) {
                double trimesterFee = paymentDAO.getFeeAmountForTrimester(trimesterId);
                double overpayment = getOverpayment(studentId);
                double amountDue = Math.max(trimesterFee - overpayment, 0);

                // Create a new payment record
                StudentFeePayment payment = new StudentFeePayment(studentId, trimesterId, amountDue, new java.sql.Date(System.currentTimeMillis()), overpayment, amountDue);
                paymentDAO.addPayment(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getEligibleStudents(String trimesterId) throws SQLException {
        List<Integer> studentIds = new ArrayList<>();
        // Logic to fetch eligible students from the database
        // Example: Select students with enrollment date within 4 years and have not repeated a year
        String sql = "SELECT student_id FROM Students WHERE date_of_enrollment >= DATE_SUB(CURDATE(), INTERVAL 4 YEAR) AND (SELECT COUNT(*) FROM StudentClassEnrollment WHERE student_id = Students.student_id AND status = 'active') < 4";
        try (Connection connection = paymentDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                studentIds.add(rs.getInt("student_id"));
            }
        }
        return studentIds;
    }

    private double getOverpayment(int studentId) throws SQLException {
        // Fetch total overpayment for the student
        String sql = "SELECT SUM(amount_paid) - SUM(amount_due) AS overpayment FROM StudentFeePayment WHERE student_id = ?";
        try (Connection connection = paymentDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("overpayment");
            }
        }
        return 0.0;
    }

    public void removeBillIfDroppedOut(int studentId) {
        try {
            boolean isDroppedOut = checkIfDroppedOut(studentId);
            if (isDroppedOut) {
                paymentDAO.deletePaymentsByStudentId(studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfDroppedOut(int studentId) throws SQLException {
        // Logic to check if the student has dropped out
        String sql = "SELECT status FROM StudentClassEnrollment WHERE student_id = ? ORDER BY year_id DESC LIMIT 1";
        try (Connection connection = paymentDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                return "dropped".equalsIgnoreCase(status);
            }
        }
        return false;
    }
    
    public double getTrimesterFee(String trimesterId) throws SQLException {
        // Fetch trimester fee from the FeesStructure table
        return paymentDAO.getFeeAmountForTrimester(trimesterId);
    }

    public String getCurrentTrimesterId() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based

        if (month >= 1 && month <= 4) {
            return "T1"; // January to April
        } else if (month >= 5 && month <= 8) {
            return "T2"; // May to August
        } else {
            return "T3"; // September to December
        }
    }
}
