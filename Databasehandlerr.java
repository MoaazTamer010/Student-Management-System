/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package databasehandlerr;
import java.sql.*;
/**
 *
 * @author Mohamed
 */
public class Databasehandlerr {

 public class JavaDB {


public class JavaDb {
    private final Connection connection;


    public JavaDb(Connection connection) throws SQLException {
        this.connection = connection;
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS students (" +
                       "student_id INTEGER PRIMARY KEY, " +
                       "name VARCHAR(100) NOT NULL, " +
                       "grade VARCHAR(1))");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS marks (" +
                       "record_id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                       "student_id INTEGER, " +
                       "subject VARCHAR(50) NOT NULL, " +
                       "mark DECIMAL(5,2) NOT NULL, " +
                       "FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE)");
        }
    }

    public void addStudent(int id, String name) throws SQLException {
        String sql = "INSERT INTO students (student_id, name) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        }
    }

    public void updateGrade(int studentId, String grade) throws SQLException {
        String sql = "UPDATE students SET grade = ? WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, grade);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
}
}