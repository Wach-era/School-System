package pages;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class StudentFeePaymentDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public StudentFeePaymentDAO() {
        this.connection = getConnection();
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<StudentFeePayment> getAllPayments() throws SQLException {
        List<StudentFeePayment> payments = new ArrayList<>();
        String query = "SELECT * FROM StudentFeePayment";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            StudentFeePayment payment = new StudentFeePayment(
                resultSet.getInt("payment_id"),
                resultSet.getInt("student_id"),
                resultSet.getString("trimester_id"),
                resultSet.getDouble("amount_paid"),
                resultSet.getDate("date_of_payment"),
                resultSet.getDouble("overpayment"),
                resultSet.getDouble("amount_due"),
                resultSet.getString("payment_mode")

            );
            payments.add(payment);
        }

        return payments;
    }

    public void addPayment(StudentFeePayment payment) throws SQLException {
        String sql = "INSERT INTO StudentFeePayment (student_id, trimester_id, amount_paid, date_of_payment, payment_mode) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getStudentId());
            stmt.setString(2, payment.getTrimesterId());
            stmt.setDouble(3, payment.getAmountPaid());
            stmt.setDate(4, payment.getDateOfPayment());
            stmt.setString(5, payment.getPaymentMode());
            stmt.executeUpdate();
        }
    }


    public void updatePayment(StudentFeePayment payment) throws SQLException {
        String query = "UPDATE StudentFeePayment SET student_id = ?, trimester_id = ?, amount_paid = ?, date_of_payment = ?, payment_mode = ? WHERE payment_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, payment.getStudentId());
        preparedStatement.setString(2, payment.getTrimesterId());
        preparedStatement.setDouble(3, payment.getAmountPaid());
        preparedStatement.setDate(4, new java.sql.Date(payment.getDateOfPayment().getTime()));
        preparedStatement.setString(5, payment.getPaymentMode());
        preparedStatement.setInt(6, payment.getPaymentId());
        preparedStatement.executeUpdate();
    }


    public void deletePayment(int paymentId) throws SQLException {
        String query = "DELETE FROM StudentFeePayment WHERE payment_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, paymentId);
        preparedStatement.executeUpdate();
    }
    
    public void deletePaymentsByStudentId(int studentId) throws SQLException {
        String sql = "DELETE FROM StudentFeePayment WHERE student_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.executeUpdate();
        }
    }

    public List<StudentFeePayment> getPaymentsByStudentId(int studentId) throws SQLException {
        List<StudentFeePayment> payments = new ArrayList<>();
        String sql = "SELECT * FROM StudentFeePayment WHERE student_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int paymentId = rs.getInt("payment_id");
                    int sId = rs.getInt("student_id");
                    String trimesterId = rs.getString("trimester_id");
                    double amountPaid = rs.getDouble("amount_paid");
                    Date dateOfPayment = rs.getDate("date_of_payment");
                    String paymentMode = rs.getString("payment_mode");
                    double overpayment = rs.getDouble("overpayment");
                    double amountDue = rs.getDouble("amount_due");
                    payments.add(new StudentFeePayment(paymentId, sId, trimesterId, amountPaid, dateOfPayment, overpayment, amountDue, paymentMode));
                }
            }
        }
        return payments;
    }


    public double getFeeAmountForTrimester(String trimesterId) throws SQLException {
        double totalAmount = 0;
        String sql = "SELECT SUM(amount) AS totalAmount FROM FeesStructure WHERE trimester_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, trimesterId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            totalAmount = rs.getDouble("totalAmount");
        }
        return totalAmount;
    }
    
    


    public double calculateTotalOverpayment(int studentId) throws SQLException {
        String query = "SELECT SUM(overpayment) AS total_overpayment FROM StudentFeePayment WHERE student_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_overpayment");
            } else {
                return 0;
            }
        }
    }
    
    public StudentFeePayment getPaymentById(int paymentId) throws SQLException {
        String sql = "SELECT * FROM StudentFeePayment WHERE payment_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int studentId = rs.getInt("student_id");
                String trimesterId = rs.getString("trimester_id");
                double amountPaid = rs.getDouble("amount_paid");
                Date dateOfPayment = rs.getDate("date_of_payment");
                String paymentMode = rs.getString("payment_mode");
                return new StudentFeePayment(paymentId, studentId, trimesterId, amountPaid, dateOfPayment, 0, 0, paymentMode);
            }
        }
        return null;
    }
    
    public void updateTotalFeesDue(int studentId, double amountPaid) throws SQLException {
        String sql = "UPDATE StudentFeePayment SET amount_due = amount_due + ? WHERE student_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amountPaid);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
        }
    }

    public List<StudentFeePayment> getPaymentsByStudentIdAndDateRange(int studentId, String startDate, String endDate) {
        List<StudentFeePayment> feePayments = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM StudentFeePayment WHERE student_id = ? AND date_of_payment BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StudentFeePayment payment = new StudentFeePayment(
                    resultSet.getInt("payment_id"),
                    resultSet.getInt("student_id"),
                    resultSet.getString("trimester_id"),
                    resultSet.getDouble("amount_paid"),
                    resultSet.getDate("date_of_payment"),
                    resultSet.getDouble("overpayment"),
                    resultSet.getDouble("amount_due"),
                    resultSet.getString("payment_mode")
                );
                feePayments.add(payment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feePayments;
    }

    
}

