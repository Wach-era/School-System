package pages;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public TeacherDAO() {
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

    // Insert a new teacher
    public void insertTeacher(Teacher teacher) throws SQLException {
        String sql = "INSERT INTO Teachers (teacher_id, national_id, first_name, middle_name, last_name, date_of_birth, email, phone_number, gender, religion, degrees, majors, institution, date_of_graduation, position, date_of_hire, salary, emergency_contact, health_information, special_accommodation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teacher.getTeacherId());
            statement.setInt(2, teacher.getnationalId());
            statement.setString(3, teacher.getFirstName());
            statement.setString(4, teacher.getMiddleName());
            statement.setString(5, teacher.getLastName());
            statement.setDate(6, Date.valueOf(teacher.getDateOfBirth()));
            statement.setString(7, teacher.getEmail());
            statement.setString(8, teacher.getPhoneNumber());
            statement.setString(9, teacher.getGender());
            statement.setString(10, teacher.getReligion());
            statement.setString(11, teacher.getDegrees());
            statement.setString(12, teacher.getMajors());
            statement.setString(13, teacher.getInstitution());
            statement.setDate(14, Date.valueOf(teacher.getDateOfGraduation()));
            statement.setString(15, teacher.getPosition());
            statement.setDate(16, Date.valueOf(teacher.getDateOfHire()));
            statement.setDouble(17, teacher.getSalary());
            statement.setString(18, teacher.getEmergencyContact());
            statement.setString(19, teacher.getHealthInformation());
            statement.setString(20, teacher.getSpecialAccommodation());
            statement.executeUpdate();
        }
    }

    // Update an existing teacher
    public void updateTeacher(Teacher teacher) throws SQLException {
        String sql = "UPDATE Teachers SET first_name = ?, middle_name = ?, last_name = ?, date_of_birth = ?, email = ?, phone_number = ?, gender = ?, religion = ?, degrees = ?, majors = ?, institution = ?, date_of_graduation = ?, position = ?, date_of_hire = ?, salary = ?, emergency_contact = ?, health_information = ?, special_accommodation = ? WHERE national_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getMiddleName());
            statement.setString(3, teacher.getLastName());
            statement.setDate(4, Date.valueOf(teacher.getDateOfBirth()));
            statement.setString(5, teacher.getEmail());
            statement.setString(6, teacher.getPhoneNumber());
            statement.setString(7, teacher.getGender());
            statement.setString(8, teacher.getReligion());
            statement.setString(9, teacher.getDegrees());
            statement.setString(10, teacher.getMajors());
            statement.setString(11, teacher.getInstitution());
            statement.setDate(12, Date.valueOf(teacher.getDateOfGraduation()));
            statement.setString(13, teacher.getPosition());
            statement.setDate(14, Date.valueOf(teacher.getDateOfHire()));
            statement.setDouble(15, teacher.getSalary());
            statement.setString(16, teacher.getEmergencyContact());
            statement.setString(17, teacher.getHealthInformation());
            statement.setString(18, teacher.getSpecialAccommodation());
            statement.setInt(19, teacher.getnationalId());
            statement.executeUpdate();
        }
    }

    // Delete a teacher by teacher ID
    public void deleteTeacher(int teacherId) throws SQLException {
        String sql = "DELETE FROM Teachers WHERE teacher_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teacherId);
            statement.executeUpdate();
        }
    }

    // Select a teacher by teacher ID
    public Teacher selectTeacher(int nationalId) throws SQLException {
        String sql = "SELECT * FROM Teachers WHERE national_id = ?";
        Teacher teacher = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, nationalId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int teacherId = resultSet.getInt("teacher_id");
                String firstName = resultSet.getString("first_name");
                String middleName = resultSet.getString("middle_name");
                String lastName = resultSet.getString("last_name");
                LocalDate dateOfBirth = resultSet.getDate("date_of_birth").toLocalDate();
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String gender = resultSet.getString("gender");
                String religion = resultSet.getString("religion");
                String degrees = resultSet.getString("degrees");
                String majors = resultSet.getString("majors");
                String institution = resultSet.getString("institution");
                LocalDate dateOfGraduation = resultSet.getDate("date_of_graduation").toLocalDate();
                String position = resultSet.getString("position");
                LocalDate dateOfHire = resultSet.getDate("date_of_hire").toLocalDate();
                double salary = resultSet.getDouble("salary");
                String emergencyContact = resultSet.getString("emergency_contact");
                String healthInformation = resultSet.getString("health_information");
                String specialAccommodation = resultSet.getString("special_accommodation");
                teacher = new Teacher(teacherId, nationalId, firstName, middleName, lastName, dateOfBirth, email, phoneNumber, gender, religion, degrees, majors, institution, dateOfGraduation, position, dateOfHire, salary, emergencyContact, healthInformation, specialAccommodation);
            }
        }
        return teacher;
    }

    // Select all teachers
    public List<Teacher> selectAllTeachers() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM Teachers";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int teacherId = resultSet.getInt("teacher_id");
                int nationalId = resultSet.getInt("national_id");
                String firstName = resultSet.getString("first_name");
                String middleName = resultSet.getString("middle_name");
                String lastName = resultSet.getString("last_name");
                LocalDate dateOfBirth = resultSet.getDate("date_of_birth").toLocalDate();
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String gender = resultSet.getString("gender");
                String religion = resultSet.getString("religion");
                String degrees = resultSet.getString("degrees");
                String majors = resultSet.getString("majors");
                String institution = resultSet.getString("institution");
                LocalDate dateOfGraduation = resultSet.getDate("date_of_graduation").toLocalDate();
                String position = resultSet.getString("position");
                LocalDate dateOfHire = resultSet.getDate("date_of_hire").toLocalDate();
                double salary = resultSet.getDouble("salary");
                String emergencyContact = resultSet.getString("emergency_contact");
                String healthInformation = resultSet.getString("health_information");
                String specialAccommodation = resultSet.getString("special_accommodation");
                Teacher teacher = new Teacher(teacherId, nationalId, firstName, middleName, lastName, dateOfBirth, email, phoneNumber, gender, religion, degrees, majors, institution, dateOfGraduation, position, dateOfHire, salary, emergencyContact, healthInformation, specialAccommodation);
                teachers.add(teacher);
            }
        }
        return teachers;
    }
    private static final String CHECK_TEACHER_EXISTS_SQL = "SELECT COUNT(*) FROM Teachers WHERE national_id = ?;";

    public boolean checkTeacherExists(int nationalId) {
        boolean exists = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_TEACHER_EXISTS_SQL)) {
            preparedStatement.setInt(1, nationalId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return exists;
    }
    
private static final String GET_TEACHER_BY_ID_SQL = "SELECT national_id, first_name, middle_name, last_name FROM Teachers WHERE national_id = ?";

    
    public Teacher getTeacherById(int nationalId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TEACHER_BY_ID_SQL)) {
            preparedStatement.setInt(1, nationalId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("national_id");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                return new Teacher(id, firstName, middleName, lastName);
            }
        }
        return null;
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
    
    public Teacher getTeacherById2(int nationalId) {
        Teacher teacher = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Teachers WHERE national_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, nationalId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                teacher = new Teacher(
                		resultSet.getInt("national_id"),
                		resultSet.getInt("teacher_id"),
                        resultSet.getString("first_name"),
                       resultSet.getString("middle_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDate("date_of_birth").toLocalDate(),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("gender"),
                        resultSet.getString("religion"),
                        resultSet.getString("degrees"),
                        resultSet.getString("majors"),
                        resultSet.getString("institution"),
                        resultSet.getDate("date_of_graduation").toLocalDate(),
                        resultSet.getString("position"),
                        resultSet.getDate("date_of_hire").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("emergency_contact"),
                        resultSet.getString("health_information"),
                        resultSet.getString("special_accommodation")
                		);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacher;
    }
}
