package pages;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaffPayrollDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public StaffPayrollDAO() {
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

    public StaffPayroll getPayrollById(int payrollId) throws SQLException {
        StaffPayroll payroll = null;
        String query = "SELECT * FROM StaffPayroll WHERE payroll_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, payrollId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int staffId = rs.getInt("staff_id");
                String accountNumber = rs.getString("account_number");
                double salary = rs.getDouble("salary");
                double bonus = rs.getDouble("bonus");
                double deductions = rs.getDouble("deductions");
                double netSalary = rs.getDouble("net_salary");
                Date paymentDate = rs.getDate("payment_date");
                String status = rs.getString("status");

                payroll = new StaffPayroll(payrollId, staffId, accountNumber, salary, bonus, deductions, netSalary, paymentDate, status);
            }
        }
        return payroll;
    }

    public List<StaffPayroll> getAllStaffWithPayrollDetails() throws SQLException {
        List<StaffPayroll> staffList = new ArrayList<>();
        String query = "SELECT s.staff_id, p.payroll_id, CONCAT(s.first_name, ' ', s.middle_name, ' ', s.last_name) AS full_name, " +
                       "IFNULL(p.salary, 0) AS salary, IFNULL(p.bonus, 0) AS bonus, IFNULL(p.deductions, 0) AS deductions, " +
                       "IFNULL(p.net_salary, 0) AS net_salary, p.payment_date, p.status, p.account_number " +
                       "FROM Staff s " +
                       "LEFT JOIN StaffPayroll p ON s.staff_id = p.staff_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int payrollId = rs.getInt("payroll_id");
                int staffId = rs.getInt("staff_id");
                String fullName = rs.getString("full_name");
                double salary = rs.getDouble("salary");
                double bonus = rs.getDouble("bonus");
                double deductions = rs.getDouble("deductions");
                double netSalary = rs.getDouble("net_salary");
                Date paymentDate = rs.getDate("payment_date");
                String status = rs.getString("status");
                String accountNumber = rs.getString("account_number");

                StaffPayroll payroll = new StaffPayroll(payrollId, staffId, accountNumber, salary, bonus, deductions, netSalary, paymentDate, status);
                payroll.setFullName(fullName);
                staffList.add(payroll);
            }
        }
        return staffList;
    }

    public void addPayroll(StaffPayroll payroll) throws SQLException {
        String query = "INSERT INTO StaffPayroll (staff_id, account_number, salary, bonus, deductions, net_salary, payment_date, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, payroll.getStaffId());
            statement.setString(2, payroll.getAccountNumber());
            statement.setDouble(3, payroll.getSalary());
            statement.setDouble(4, payroll.getBonus());
            statement.setDouble(5, payroll.getDeductions());
            statement.setDouble(6, payroll.getNetSalary());
            statement.setDate(7, payroll.getPaymentDate());
            statement.setString(8, payroll.getStatus());
            statement.executeUpdate();
        }
    }

    public void updatePayroll(StaffPayroll payroll) throws SQLException {
        String sql = "UPDATE StaffPayroll SET account_number = ?, salary = ?, bonus = ?, deductions = ?, net_salary = ?, payment_date = ?, status = ? WHERE payroll_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	preparedStatement.setString(1, payroll.getAccountNumber());
            preparedStatement.setDouble(2, payroll.getSalary());
            preparedStatement.setDouble(3, payroll.getBonus());
            preparedStatement.setDouble(4, payroll.getDeductions());
            preparedStatement.setDouble(5, payroll.getNetSalary());
            preparedStatement.setDate(6, payroll.getPaymentDate());
            preparedStatement.setString(7, payroll.getStatus());
            preparedStatement.setInt(8, payroll.getPayrollId());
            preparedStatement.executeUpdate();
        }
    }

    public void deletePayroll(int payrollId) throws SQLException {
        String query = "DELETE FROM StaffPayroll WHERE payroll_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, payrollId);
            statement.executeUpdate();
        }
    }

    public double calculateNetSalary(double salary, double deductions, double bonus) {
        return salary - deductions + bonus;
    }

    public List<StaffPayroll> getPendingPayrolls() throws SQLException {
        List<StaffPayroll> payrolls = new ArrayList<>();
        String sql = "SELECT * FROM StaffPayroll WHERE status = 'pending' AND payment_date <= CURDATE()";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int payrollId = rs.getInt("payroll_id");
                int staffId = rs.getInt("staff_id");
                double salary = rs.getDouble("salary");
                double bonus = rs.getDouble("bonus");
                double deductions = rs.getDouble("deductions");
                double netSalary = rs.getDouble("net_salary");
                Date paymentDate = rs.getDate("payment_date");
                String status = rs.getString("status");
                String accountNumber = rs.getString("account_number");


                StaffPayroll payroll = new StaffPayroll(payrollId, staffId, accountNumber, salary, bonus, deductions, netSalary, paymentDate, status);
                payrolls.add(payroll);
            }
        }
        return payrolls;
    }

    public boolean staffExists(int staffId) {
        String sql = "SELECT COUNT(*) FROM Staff WHERE staff_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, staffId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<StaffPayroll> getPreviousPayments(int staffId) throws SQLException {
        List<StaffPayroll> payments = new ArrayList<>();
        String query = "SELECT * FROM StaffPayroll WHERE staff_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, staffId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int payrollId = rs.getInt("payroll_id");
                double salary = rs.getDouble("salary");
                double bonus = rs.getDouble("bonus");
                double deductions = rs.getDouble("deductions");
                double netSalary = rs.getDouble("net_salary");
                Date paymentDate = rs.getDate("payment_date");
                String status = rs.getString("status");
                String accountNumber = rs.getString("account_number");


                StaffPayroll payroll = new StaffPayroll(payrollId, staffId, accountNumber, salary, bonus, deductions, netSalary, paymentDate, status);
                payments.add(payroll);
            }
        }
        return payments;
    }



    private static final String SELECT_ALL_STAFF_WITH_PAYROLL_SQL = "SELECT s.staff_id, s.first_name, s.middle_name, s.last_name, s.title, s.department_id, s.date_of_hire " +
            "FROM Staff s " +
            "LEFT JOIN StaffPayroll sp ON s.staff_id = sp.staff_id";

    public List<Staff> getStaffWithPayrollRecords() {
        List<Staff> staffList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STAFF_WITH_PAYROLL_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int staffId = rs.getInt("staff_id");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                String title = rs.getString("title");
                String departmentId = rs.getString("department_id");
                LocalDate dateOfHire = rs.getDate("date_of_hire").toLocalDate();

                Staff staff = new Staff(staffId, firstName, middleName, lastName, title, departmentId, dateOfHire);
                staffList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }
    
    public List<StaffPayroll> getPaymentsByStaffIdAndDateRange(int staffId, String startDate, String endDate) {
        List<StaffPayroll> payroll = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM StaffPayroll WHERE staff_id = ? AND payroll_id BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                StaffPayroll payment = new StaffPayroll(
                		rs.getInt("payroll_id"),
                        rs.getInt("staff_id"),
                        rs.getString("account_number"),
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
