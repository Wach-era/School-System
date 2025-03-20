package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParentStudentDAO {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_PARENT_STUDENT_SQL = "INSERT INTO ParentStudent (parent_id, student_id) VALUES (?, ?)";
    private static final String DELETE_PARENT_STUDENT_SQL = "DELETE FROM ParentStudent WHERE parent_id = ? AND student_id = ?";
    private static final String CHECK_STUDENT_EXISTS_SQL = "SELECT COUNT(*) FROM Students WHERE student_id = ?";
    private static final String CHECK_PARENT_EXISTS_SQL = "SELECT COUNT(*) FROM Parents WHERE parent_id = ?";
    
    public ParentStudentDAO() {}

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


    public void insertParentStudent(int parentId, int studentId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PARENT_STUDENT_SQL)) {
            preparedStatement.setInt(1, parentId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteParentStudent(int parentId, int studentId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PARENT_STUDENT_SQL)) {
            preparedStatement.setInt(1, parentId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
        }
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

    public boolean parentExists(int parentId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_PARENT_EXISTS_SQL)) {
            preparedStatement.setInt(1, parentId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<ParentStudent> selectParentStudentByStudentId(int studentId) throws SQLException {
        List<ParentStudent> parentStudents = new ArrayList<>();
        String sql = "SELECT ps.parent_id, ps.student_id, p.name " + 
                     "FROM ParentStudent ps " +
                     "JOIN Parents p ON ps.parent_id = p.parent_id " +
                     "WHERE ps.student_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int parentId = rs.getInt("parent_id");
                String parentName = rs.getString("name");
                parentStudents.add(new ParentStudent(parentId, studentId, parentName));
            }
        }
        return parentStudents;
    }

}
