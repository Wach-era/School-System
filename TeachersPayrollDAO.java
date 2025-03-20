package pages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeachersPayrollDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public TeachersPayrollDAO() {
        this.connection = getConnection();
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public TeachersPayroll getPayrollById(int payrollId) throws SQLException {
        TeachersPayroll payroll = null;
        String query = "SELECT * FROM TeachersPayroll WHERE payroll_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, payrollId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int nationalId = rs.getInt("national_id");
                double salary = rs.getDouble("salary");
                double bonus = rs.getDouble("bonus");
                double deductions = rs.getDouble("deductions");
                double netSalary = rs.getDouble("net_salary");
                Date paymentDate = rs.getDate("payment_date");
                String status = rs.getString("status");

                payroll = new TeachersPayroll(payrollId, nationalId, salary, bonus, deductions, netSalary, paymentDate, status);
            }
        }
        return payroll;
    }

    public List<TeachersPayroll> getAllTeachersWithPayrollDetails() throws SQLException {
        List<TeachersPayroll> teachers = new ArrayList<>();
        String query = "SELECT t.national_id, p.payroll_id, CONCAT(t.first_name, ' ', t.middle_name, ' ', t.last_name) AS full_name, " +
                       "IFNULL(p.salary, 0) AS salary, IFNULL(p.bonus, 0) AS bonus, IFNULL(p.deductions, 0) AS deductions, " +
                       "IFNULL(p.net_salary, 0) AS net_salary, p.payment_date, p.status " +
                       "FROM Teachers t " +
                       "LEFT JOIN TeachersPayroll p ON t.national_id = p.national_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int payrollId = rs.getInt("payroll_id");
                int nationalId = rs.getInt("national_id");
                String fullName = rs.getString("full_name");
                double salary = rs.getDouble("salary");
                double bonus = rs.getDouble("bonus");
                double deductions = rs.getDouble("deductions");
                double netSalary = rs.getDouble("net_salary");
                Date paymentDate = rs.getDate("payment_date");
                String status = rs.getString("status");

                TeachersPayroll payroll = new TeachersPayroll(payrollId, nationalId, fullName, salary, bonus, deductions, netSalary, paymentDate, status);
                payroll.setTeacherFullName(fullName);
                teachers.add(payroll);
            }
        }
        return teachers;
    }

    public void addPayroll(TeachersPayroll payroll) throws SQLException {
        String query = "INSERT INTO TeachersPayroll (national_id, salary, bonus, deductions, net_salary, payment_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, payroll.getNationalId());
            statement.setDouble(2, payroll.getSalary());
            statement.setDouble(3, payroll.getBonus());
            statement.setDouble(4, payroll.getDeductions());
            statement.setDouble(5, payroll.getNetSalary());
            statement.setDate(6, payroll.getPaymentDate());
            statement.setString(7, payroll.getStatus());
            statement.executeUpdate();
        }
    }

    public void updatePayroll(TeachersPayroll payroll) throws SQLException {
        String sql = "UPDATE TeachersPayroll SET salary = ?, bonus = ?, deductions = ?, net_salary = ?, payment_date = ?, status = ? WHERE payroll_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, payroll.getSalary());
            preparedStatement.setDouble(2, payroll.getBonus());
            preparedStatement.setDouble(3, payroll.getDeductions());
            preparedStatement.setDouble(4, payroll.getNetSalary());
            preparedStatement.setDate(5, payroll.getPaymentDate());
            preparedStatement.setString(6, payroll.getStatus());
            preparedStatement.setInt(7, payroll.getPayrollId());
            preparedStatement.executeUpdate();
        }
    }

    public void deletePayroll(int payrollId) throws SQLException {
        String query = "DELETE FROM TeachersPayroll WHERE payroll_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, payrollId);
            statement.executeUpdate();
        }
    }

    public double calculateNetSalary(double salary, double deductions, double bonus) {
        return salary - deductions + bonus;
    }

    public List<TeachersPayroll> getPendingPayrolls() throws SQLException {
        List<TeachersPayroll> payrolls = new ArrayList<>();
        String sql = "SELECT * FROM TeachersPayroll WHERE status = 'pending' AND payment_date <= CURDATE()";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int payrollId = rs.getInt("payroll_id");
                int nationalId = rs.getInt("national_id");
                double salary = rs.getDouble("salary");
                double bonus = rs.getDouble("bonus");
                double deductions = rs.getDouble("deductions");
                double netSalary = rs.getDouble("net_salary");
                Date paymentDate = rs.getDate("payment_date");
                String status = rs.getString("status");

                TeachersPayroll payroll = new TeachersPayroll(payrollId, nationalId, salary, bonus, deductions, netSalary, paymentDate, status);
                payrolls.add(payroll);
            }
        }
        return payrolls;
    }

    public boolean teacherExists(int nationalId) {
        String sql = "SELECT COUNT(*) FROM Teachers WHERE national_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, nationalId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<TeachersPayroll> getPreviousPayments(int nationalId) throws SQLException {
        List<TeachersPayroll> payments = new ArrayList<>();
        String query = "SELECT * FROM TeachersPayroll WHERE national_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, nationalId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int payrollId = rs.getInt("payroll_id");
                double salary = rs.getDouble("salary");
                double bonus = rs.getDouble("bonus");
                double deductions = rs.getDouble("deductions");
                double netSalary = rs.getDouble("net_salary");
                Date paymentDate = rs.getDate("payment_date");
                String status = rs.getString("status");

                TeachersPayroll payroll = new TeachersPayroll(payrollId, nationalId, salary, bonus, deductions, netSalary, paymentDate, status);
                payments.add(payroll);
            }
        }
        return payments;
    }
    
    private static final String SELECT_ALL_TEACHERS_WITH_PAYROLL_SQL = "SELECT t.national_id, t.first_name, t.middle_name, t.last_name " +
            "FROM Teachers t " +
            "JOIN TeachersPayroll tp ON t.national_id = tp.national_id";

    public List<Teacher> getTeachersWithPayrollRecords() {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TEACHERS_WITH_PAYROLL_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int nationalId = rs.getInt("national_id");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                Teacher teacher = new Teacher(nationalId, firstName, middleName, lastName);
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }
    
    public List<TeachersPayroll> getPaymentsByTeacherIdAndDateRange(int nationalId, String startDate, String endDate) {
        List<TeachersPayroll> payroll = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM TeachersPayroll WHERE national_id = ? AND payroll_id BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, nationalId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                TeachersPayroll payment = new TeachersPayroll(
                		 rs.getInt("payroll_id"),
                         rs.getInt("national_id"),
                         rs.getDouble("salary"),
                         rs.getDouble("bonus"),
                         rs.getDouble("deductions"),
                         rs.getDouble("net_salary"),
                         rs.getDate("payment_date"),
                         rs.getString("status")
                );
                payroll.add(payment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payroll;
    }


}
