/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Student;

import java.util.HashMap; 
import java.util.Map;

public class Student {                //(Class of Moaaz)
    private final String studentId;  // Made final since ID shouldn't change
    private String name;
    private final Map<String, Double> marks; // Subject -> Marks
    private String grade;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.marks = new HashMap<>();
        this.grade = "N/A";  // Initialize as "N/A" instead of empty string
    }

    // Adding marks for a subject with validation
    public void addMarks(String subject, double mark) {
        if (mark < 0 || mark > 100) {
            throw new IllegalArgumentException("Mark must be between 0 and 100");
        }
        marks.put(subject, mark);
        calculateGrade();
    }

    // More accurate grade calculation
    public void calculateGrade() {
        if (marks.isEmpty()) {
            grade = "N/A";
            return;
        }

        double average = marks.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        if (average >= 90) grade = "A";
        else if (average >= 80) grade = "B";
        else if (average >= 70) grade = "C";
        else if (average >= 60) grade = "D";
        else grade = "F";
    }

    // Getters
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public Map<String, Double> getMarks() { return new HashMap<>(marks); } // Return a copy
    public String getGrade() { return grade; }

    // Setter only for name (studentId shouldn't be changeable)
    public void setName(String name) { 
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name; 
    }

    @Override
    public String toString() {
        return String.format(
            "Student ID: %s\nName: %s\nMarks: %s\nGrade: %s",
            studentId, name, marks, grade
        );
    }
}
