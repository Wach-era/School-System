package pages;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_STUDENT_SQL = "INSERT INTO Students (student_id, first_name, middle_name, last_name, date_of_birth, email, phone_number, gender, religion, medical_history, emergency_contact, learning_disabilities, date_of_enrollment, disability_details) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM Students WHERE student_id = ?";
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM Students";
    private static final String DELETE_STUDENT_SQL = "DELETE FROM Students WHERE student_id = ?";
    private static final String UPDATE_STUDENT_SQL = "UPDATE Students SET first_name = ?, middle_name = ?, last_name = ?, date_of_birth = ?, email = ?, phone_number = ?, gender = ?, religion = ?, medical_history = ?, emergency_contact = ?, learning_disabilities = ?, date_of_enrollment = ?, disability_details = ? WHERE student_id = ?";
    
    private static final String CHECK_STUDENT_EXISTS_SQL = "SELECT COUNT(*) FROM Students WHERE student_id = ?";

    public StudentDAO() {}

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

    public void insertStudent(Student student) throws SQLException {
        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL)) {
            preparedStatement.setInt(1, student.getStudentId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getMiddleName());
            preparedStatement.setString(4, student.getLastName());
            preparedStatement.setDate(5, Date.valueOf(student.getDateOfBirth()));
            preparedStatement.setString(6, student.getEmail());
            preparedStatement.setString(7, student.getPhoneNumber());
            preparedStatement.setString(8, student.getGender());
            preparedStatement.setString(9, student.getReligion());
            preparedStatement.setString(12, student.getMedicalHistory());
            preparedStatement.setString(13, student.getEmergencyContact());
            preparedStatement.setString(14, student.getLearningDisabilities());
            preparedStatement.setDate(15, student.getDateOfEnrollment() != null ? Date.valueOf(student.getDateOfEnrollment()) : null);
            preparedStatement.setString(16, student.getDisabilityDetails());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Student selectStudent(int id) {
        Student student = null;
        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String gender = rs.getString("gender");
                String religion = rs.getString("religion");
                String medicalHistory = rs.getString("medical_history");
                String emergencyContact = rs.getString("emergency_contact");
                String learningDisabilities = rs.getString("learning_disabilities");
                LocalDate dateOfEnrollment = rs.getDate("date_of_enrollment").toLocalDate();
                String disabilityDetails = rs.getString("disability_details");
                student = new Student(id, firstName, middleName, lastName, dateOfBirth, email, phoneNumber, gender, religion, medicalHistory, emergencyContact, learningDisabilities, dateOfEnrollment, disabilityDetails);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return student;
    }

    public List<Student> selectAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String gender = rs.getString("gender");
                String religion = rs.getString("religion");
                String medicalHistory = rs.getString("medical_history");
                String emergencyContact = rs.getString("emergency_contact");
                String learningDisabilities = rs.getString("learning_disabilities");
                LocalDate dateOfEnrollment = rs.getDate("date_of_enrollment").toLocalDate();
                String disabilityDetails = rs.getString("disability_details");
                students.add(new Student(id, firstName, middleName, lastName, dateOfBirth, email, phoneNumber, gender, religion, medicalHistory, emergencyContact, learningDisabilities, dateOfEnrollment, disabilityDetails));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return students;
    }

    public boolean deleteStudent(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); 
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateStudent(Student student) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); 
             PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT_SQL)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getMiddleName());
            statement.setString(3, student.getLastName());
            statement.setDate(4, Date.valueOf(student.getDateOfBirth()));
            statement.setString(5, student.getEmail());
            statement.setString(6, student.getPhoneNumber());
            statement.setString(7, student.getGender());
            statement.setString(8, student.getReligion());
            statement.setString(11, student.getMedicalHistory());
            statement.setString(12, student.getEmergencyContact());
            statement.setString(13, student.getLearningDisabilities());
            statement.setDate(14, student.getDateOfEnrollment() != null ? Date.valueOf(student.getDateOfEnrollment()) : null);
            statement.setString(15, student.getDisabilityDetails());
            statement.setInt(16, student.getStudentId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    public boolean studentExists(int studentId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_STUDENT_EXISTS_SQL)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
    private static final String SEARCH_STUDENTS_SQL = "SELECT * FROM Students WHERE student_id LIKE ? OR first_name LIKE ? OR middle_name LIKE ? OR last_name LIKE ? OR email LIKE ?";

    public List<Student> searchStudents(String keyword) {
        List<Student> students = new ArrayList<>();
        String searchKeyword = "%" + keyword + "%";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_STUDENTS_SQL)) {
            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);
            preparedStatement.setString(3, searchKeyword);
            preparedStatement.setString(4, searchKeyword);
            preparedStatement.setString(5, searchKeyword); // Add this line to set the email placeholder
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String gender = rs.getString("gender");
                String religion = rs.getString("religion");
                String medicalHistory = rs.getString("medical_history");
                String emergencyContact = rs.getString("emergency_contact");
                String learningDisabilities = rs.getString("learning_disabilities");
                LocalDate dateOfEnrollment = rs.getDate("date_of_enrollment").toLocalDate();
                String disabilityDetails = rs.getString("disability_details");
                students.add(new Student(id, firstName, middleName, lastName, dateOfBirth, email, phoneNumber, gender, religion, medicalHistory, emergencyContact, learningDisabilities, dateOfEnrollment, disabilityDetails));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return students;
    }
    
    public List<Student> getStudentsByExamId(String examId) throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.student_id, s.first_name, s.last_name " +
                       "FROM Students s " +
                       "JOIN StudentSubjects ss ON s.student_id = ss.student_id " +
                       "JOIN Exams e ON ss.subject_name = e.subject_name " +
                       "WHERE e.exam_id = ? AND e.class_id = s.class_id";
        
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, examId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Student student = new Student(rs.getInt("student_id"), rs.getString("first_name"), rs.getString("last_name"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return students;
    }
    
    private static final String SELECT_ALL_STUDENTS_WITH_FEES_SQL = "SELECT s.student_id, s.first_name, s.last_name, s.date_of_enrollment, " +
            "SUM(p.amount_due) as totalFeesDue, SUM(p.overpayment) as overpayment " +
            "FROM Students s LEFT JOIN StudentFeePayment p ON s.student_id = p.student_id " +
            "GROUP BY s.student_id, s.first_name, s.last_name, s.date_of_enrollment";
    
    public List<Student> getAllStudentsWithFeeDetails() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS_WITH_FEES_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                LocalDate dateOfEnrollment = rs.getDate("date_of_enrollment").toLocalDate();
                double totalFeesDue = rs.getDouble("totalFeesDue");
                double overpayment = rs.getDouble("overpayment");
                Student student = new Student(studentId, firstName, lastName, dateOfEnrollment, totalFeesDue, overpayment);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    
    private static final String GET_STUDENT_BY_ID_SQL = "SELECT student_id, first_name, last_name, date_of_enrollment FROM Students WHERE student_id = ?";

    
    public Student getStudentById(int studentId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_STUDENT_BY_ID_SQL)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                LocalDate dateOfEnrollment = rs.getDate("date_of_enrollment").toLocalDate();
                return new Student(id, firstName, lastName, dateOfEnrollment);
            }
        }
        return null;
    }
    
    public double calculateTotalFeesDue(int studentId) throws SQLException {
        String getStudentQuery = "SELECT date_of_enrollment FROM Students WHERE student_id = ?";
        String getTrimesterFeesQuery = "SELECT SUM(amount) AS total_amount FROM FeesStructure WHERE trimester_id = ?";
        String getTotalPaymentsQuery = "SELECT SUM(amount_paid) AS total_payments FROM StudentFeePayment WHERE student_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement getStudentStmt = connection.prepareStatement(getStudentQuery);
             PreparedStatement getTrimesterFeesStmt = connection.prepareStatement(getTrimesterFeesQuery);
             PreparedStatement getTotalPaymentsStmt = connection.prepareStatement(getTotalPaymentsQuery)) {

            // Get the student's enrollment date
            getStudentStmt.setInt(1, studentId);
            ResultSet studentRs = getStudentStmt.executeQuery();
            if (!studentRs.next()) {
                throw new SQLException("Student not found");
            }
            Date enrollmentDate = studentRs.getDate("date_of_enrollment");

            // Determine the starting trimester based on the enrollment date
            String startingTrimesterId = getTrimesterByDate(enrollmentDate);

            // Determine the current trimester
            Date currentDate = new Date(System.currentTimeMillis());
            String currentTrimesterId = getTrimesterByDate(currentDate);

            // Calculate total fees from the starting trimester to the current trimester
            double totalFeesDue = 0.0;
            for (String trimesterId = startingTrimesterId; !trimesterId.equals(currentTrimesterId); trimesterId = getNextTrimester(trimesterId)) {
                getTrimesterFeesStmt.setString(1, trimesterId);
                ResultSet feesRs = getTrimesterFeesStmt.executeQuery();
                if (feesRs.next()) {
                    totalFeesDue += feesRs.getDouble("total_amount");
                }
            }
            // Add the current trimester fees
            getTrimesterFeesStmt.setString(1, currentTrimesterId);
            ResultSet feesRs = getTrimesterFeesStmt.executeQuery();
            if (feesRs.next()) {
                totalFeesDue += feesRs.getDouble("total_amount");
            }

            // Get the total payments made by the student
            getTotalPaymentsStmt.setInt(1, studentId);
            ResultSet paymentsRs = getTotalPaymentsStmt.executeQuery();
            double totalPayments = 0.0;
            if (paymentsRs.next()) {
                totalPayments = paymentsRs.getDouble("total_payments");
            }

            // Calculate the total fees due by subtracting total payments from total fees
            totalFeesDue -= totalPayments;

            return totalFeesDue;
        }
    }
    
    public void updateTotalFeesDue(int studentId, double totalFeesDue) throws SQLException {
        String sql = "UPDATE StudentFeePayment SET amount_due = ? WHERE student_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, totalFeesDue);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
        }
    }


    private String getTrimesterByDate(Date date) throws SQLException {
        String getTrimesterQuery = "SELECT trimester_id FROM Trimesters WHERE ? BETWEEN start_date AND end_date";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(getTrimesterQuery)) {
            stmt.setDate(1, date);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("trimester_id");
            } else {
                throw new SQLException("Trimester not found for the given date");
            }
        }
    }

    private String getNextTrimester(String currentTrimesterId) throws SQLException {
        String[] parts = currentTrimesterId.split("-");
        int trimesterNumber = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);
        if (trimesterNumber == 3) {
            trimesterNumber = 1;
            year += 1;
        } else {
            trimesterNumber += 1;
        }
        return trimesterNumber + "-" + year;
    }
    
    public Student getStudentById2(int studentId) {
        Student student = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Students WHERE student_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                student = new Student(
                    resultSet.getInt("student_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("middle_name"),
                    resultSet.getString("last_name"),
                    resultSet.getDate("date_of_birth").toLocalDate(),
                    resultSet.getString("email"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("gender"),
                    resultSet.getString("religion"),
                    resultSet.getString("medical_history"),
                    resultSet.getString("emergency_contact"),
                    resultSet.getString("learning_disabilities"),
                    resultSet.getDate("date_of_enrollment").toLocalDate(),
                    resultSet.getString("disability_details")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
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

}
