/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Student;

import java.util.Objects;

public class Student {
    private final String studentId;
    private String name;
    private double averageMark;
    private String grade;

    public Student(String studentId, String name) {
        this.studentId = Objects.requireNonNull(studentId, "Student ID cannot be null");
        this.name = validateName(name);
        this.averageMark = 0.0;
        this.grade = "N/A";
    }

    // Set the student's overall average mark
    public void setAverageMark(double averageMark) {
        validateMark(averageMark);
        this.averageMark = averageMark;
        calculateGrade();
    }

    // Calculate grade based on average mark
    private void calculateGrade() {
        if (averageMark >= 90) {
            grade = "A";
        } else if (averageMark >= 80) {
            grade = "B";
        } else if (averageMark >= 70) {
            grade = "C";
        } else if (averageMark >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }
    }

    // Validation methods
    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        return name.trim();
    }

    private void validateMark(double mark) {
        if (mark < 0 || mark > 100) {
            throw new IllegalArgumentException("Mark must be between 0 and 100");
        }
    }

    // Getters
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public double getAverageMark() { return averageMark; }
    public String getGrade() { return grade; }

    // Setter
    public void setName(String name) {
        this.name = validateName(name);
    }

    @Override
    public String toString() {
        return String.format(
            "Student ID: %s\nName: %s\nAverage Mark: %.2f\nGrade: %s",
            studentId, name, averageMark, grade
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId.equals(student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}