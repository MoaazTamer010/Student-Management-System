
package istudentmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentManagementSystem {
    private final JavaDb database;
    private final Scanner scanner;
    private String adminPassword = "admin123"; // Default password

    public StudentManagementSystem(JavaDb db) {
        this.database = db;
        this.scanner = new Scanner(System.in);
    }

    public void displayMainMenu() {
        System.out.println("------ Student Management System ------");
        System.out.println("1. Add Student");
        System.out.println("2. Update Grade");
        System.out.println("3. Display All Students");
        System.out.println("4. Change Admin Password");
        System.out.println("5. Exit");
    }

    public void start() throws SQLException {
        System.out.print("Enter admin password: ");
        String input = scanner.nextLine();
        if (!input.equals(adminPassword)) {
            System.out.println("Access Denied. Incorrect password.");
            return;
        }

        int choice;
        do {
            displayMainMenu();
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> updateGrade();
                case 3 -> displayAllStudents();
                case 4 -> changeAdminPassword();
                case 5 -> System.out.println("Exiting system.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }

    private void addStudent() throws SQLException {
        try {
            System.out.print("Enter student ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            double mark = 0;
            database.addStudent(id, name, mark);
            System.out.println("Student added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    private void updateGrade() {
        try {
            System.out.print("Enter student ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter grade (A-F): ");
            String grade = scanner.nextLine().toUpperCase();
            database.updateGrade(id, grade);
            System.out.println("Grade updated successfully!");
        } 
        
        catch (NumberFormatException e) {
            System.out.println("Error updating grade: " + e.getMessage());
        }
        
        catch (SQLException e ) {
            
        }
    }

    private void displayAllStudents() {
        try {
            Connection conn; // Add getter for connection in JavaDb
            conn = database.getConnection();
            String query = "SELECT * FROM students";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- Student Records ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Grade: %s\n",
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("grade"));
            }
            System.out.println("------------------------\n");
        } catch (SQLException e) {
            System.out.println("Error displaying students: " + e.getMessage());
        }
    }

    private void changeAdminPassword() {
        System.out.print("Enter current password: ");
        String current = scanner.nextLine();
        if (!current.equals(adminPassword)) {
            System.out.println("Incorrect password.");
            return;
        }
        System.out.print("Enter new password: ");
        adminPassword = scanner.nextLine();
        System.out.println("Password changed successfully!");
    }
}