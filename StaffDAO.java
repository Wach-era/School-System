package pages;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_STAFF_SQL = "INSERT INTO Staff (staff_id, first_name, middle_name, last_name, date_of_birth, email, phone_number, gender, religion, title, department_id, date_of_hire, responsibilities, education_level, certification, experience, emergency_contact, health_information, special_accommodation) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_STAFF_BY_ID = "SELECT * FROM Staff WHERE staff_id = ?";
    private static final String SELECT_ALL_STAFF = "SELECT * FROM Staff";
    private static final String DELETE_STAFF_SQL = "DELETE FROM Staff WHERE staff_id = ?";
    private static final String UPDATE_STAFF_SQL = "UPDATE Staff SET first_name = ?, middle_name = ?, last_name = ?, date_of_birth = ?, email = ?, phone_number = ?, gender = ?, religion = ?, title = ?, department_id = ?, date_of_hire = ?, responsibilities = ?, education_level = ?, certification = ?, experience = ?, emergency_contact = ?, health_information = ?, special_accommodation = ? WHERE staff_id = ?";

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

    public void insertStaff(Staff staff) throws SQLException {
        System.out.println("Attempting to insert staff: " + staff); // Debugging line
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STAFF_SQL)) {
            preparedStatement.setInt(1, staff.getStaffId());
            preparedStatement.setString(2, staff.getFirstName());
            preparedStatement.setString(3, staff.getMiddleName());
            preparedStatement.setString(4, staff.getLastName());
            preparedStatement.setDate(5, Date.valueOf(staff.getDateOfBirth()));
            preparedStatement.setString(6, staff.getEmail());
            preparedStatement.setString(7, staff.getPhoneNumber());
            preparedStatement.setString(8, staff.getGender());
            preparedStatement.setString(9, staff.getReligion());
            preparedStatement.setString(10, staff.getTitle());
            preparedStatement.setString(11, staff.getDepartmentId());
            preparedStatement.setDate(12, Date.valueOf(staff.getDateOfHire()));
            preparedStatement.setString(13, staff.getResponsibilities());
            preparedStatement.setString(14, staff.getEducationLevel());
            preparedStatement.setString(15, staff.getCertification());
            preparedStatement.setString(16, staff.getExperience());
            preparedStatement.setString(17, staff.getEmergencyContact());
            preparedStatement.setString(18, staff.getHealthInformation());
            preparedStatement.setString(19, staff.getSpecialAccommodation());

            int result = preparedStatement.executeUpdate();
            System.out.println("Insert result: " + result);  // Debugging line
        } catch (SQLException e) {
            printSQLException(e);
            throw new SQLException("Error inserting staff", e); // Rethrow exception for servlet to catch
        }
    }


    public Staff selectStaff(int staffId) {
        Staff staff = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STAFF_BY_ID)) {
            preparedStatement.setInt(1, staffId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String gender = rs.getString("gender");
                String religion = rs.getString("religion");
                String title = rs.getString("title");
                String departmentId = rs.getString("department_id");
                LocalDate dateOfHire = rs.getDate("date_of_hire").toLocalDate();
                String responsibilities = rs.getString("responsibilities");
                String educationLevel = rs.getString("education_level");
                String certification = rs.getString("certification");
                String experience = rs.getString("experience");
                String emergencyContact = rs.getString("emergency_contact");
                String healthInformation = rs.getString("health_information");
                String specialAccommodation = rs.getString("special_accommodation");
                staff = new Staff(staffId, firstName, middleName, lastName, dateOfBirth, email, phoneNumber, gender, religion, title, departmentId, dateOfHire, responsibilities, educationLevel, certification, experience, emergencyContact, healthInformation, specialAccommodation);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return staff;
    }

    public List<Staff> selectAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STAFF)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int staffId = rs.getInt("staff_id");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String gender = rs.getString("gender");
                String religion = rs.getString("religion");
                String title = rs.getString("title");
                String departmentId = rs.getString("department_id");
                LocalDate dateOfHire = rs.getDate("date_of_hire").toLocalDate();
                String responsibilities = rs.getString("responsibilities");
                String educationLevel = rs.getString("education_level");
                String certification = rs.getString("certification");
                String experience = rs.getString("experience");
                String emergencyContact = rs.getString("emergency_contact");
                String healthInformation = rs.getString("health_information");
                String specialAccommodation = rs.getString("special_accommodation");
                staffList.add(new Staff(staffId, firstName, middleName, lastName, dateOfBirth, email, phoneNumber, gender, religion, title, departmentId, dateOfHire, responsibilities, educationLevel, certification, experience, emergencyContact, healthInformation, specialAccommodation));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return staffList;
    }

    public boolean deleteStaff(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STAFF_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateStaff(Staff staff) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STAFF_SQL)) {
            statement.setString(1, staff.getFirstName());
            statement.setString(2, staff.getMiddleName());
            statement.setString(3, staff.getLastName());
            statement.setDate(4, Date.valueOf(staff.getDateOfBirth()));
            statement.setString(5, staff.getEmail());
            statement.setString(6, staff.getPhoneNumber());
            statement.setString(7, staff.getGender());
            statement.setString(8, staff.getReligion());
            statement.setString(9, staff.getTitle());
            statement.setString(10, staff.getDepartmentId());
            statement.setDate(11, Date.valueOf(staff.getDateOfHire()));
            statement.setString(12, staff.getResponsibilities());
            statement.setString(13, staff.getEducationLevel());
            statement.setString(14, staff.getCertification());
            statement.setString(15, staff.getExperience());
            statement.setString(16, staff.getEmergencyContact());
            statement.setString(17, staff.getHealthInformation());
            statement.setString(18, staff.getSpecialAccommodation());
            statement.setInt(19, staff.getStaffId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    
    public List<Department> selectAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT department_id, department_name, department_type FROM Departments";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String id = rs.getString("department_id");
                String name = rs.getString("department_name");
                String type = rs.getString("department_type");
                departments.add(new Department(id, name, type));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return departments;
    }
    
private static final String GET_STAFF_BY_ID_SQL = "SELECT staff_id, first_name, middle_name, last_name, title, department_id, date_of_hire FROM Staff WHERE staff_id = ?";

    
    public Staff getStaffById(int staffId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_STAFF_BY_ID_SQL)) {
            preparedStatement.setInt(1, staffId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("staff_id");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                String title = rs.getString("title");
                String departmentId = rs.getString("department_id");
                LocalDate dateOfHire = rs.getDate("date_of_hire").toLocalDate();

                return new Staff(id, firstName, middleName, lastName, title, departmentId, dateOfHire);
            }
        }
        return null;
    }
    
    public Staff getStaffById2(int staffId) {
        Staff staff = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Staff WHERE staff_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                staff = new Staff(
                		 rs.getInt("staff_id"),
                         rs.getString("first_name"),
                         rs.getString("middle_name"),
                         rs.getString("last_name"),
                         rs.getDate("date_of_birth").toLocalDate(),
                         rs.getString("email"),
                         rs.getString("phone_number"),
                         rs.getString("gender"),
                         rs.getString("religion"),
                         rs.getString("title"),
                         rs.getString("department_id"),
                         rs.getDate("date_of_hire").toLocalDate(),
                         rs.getString("responsibilities"),
                         rs.getString("education_level"),
                         rs.getString("certification"),
                         rs.getString("experience"),
                         rs.getString("emergency_contact"),
                         rs.getString("health_information"),
                         rs.getString("special_accommodation")
                		);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }
}
