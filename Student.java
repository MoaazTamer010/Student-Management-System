
package istudentmanagementsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
    private final String studentId;
    private String name;
    private double averageMark;
    private String grade;
    private final Map<String, Double> marks = new HashMap<>();

    public Student(String studentId, String name) {
        this.studentId = Objects.requireNonNull(studentId, "Student ID cannot be null");
        this.name = validateName(name);
        this.averageMark = 0.0;
        this.grade = "N/A";
    }

    public void setMarks(Map<String, Double> marks) {
        if (marks == null || marks.isEmpty()) {
            throw new IllegalArgumentException("Marks map cannot be null or empty");
        }
        this.marks.clear();
        for (Map.Entry<String, Double> entry : marks.entrySet()) {
            validateMark(entry.getValue());
            this.marks.put(entry.getKey(), entry.getValue());
        }
        recalculateAverageAndGrade();
    }

    public Map<String, Double> getMarks() {
        return new HashMap<>(marks);
    }

    // Set the student's overall average mark manually
    public void setAverageMark(double averageMark) {
        validateMark(averageMark);
        this.averageMark = averageMark;
        calculateGrade();
    }

    // Calculate grade based on average mark
    void calculateGrade() {
        double mark =0.0;
        if (mark >= 90) {
            grade = "A";
        } else if (mark >= 80) {
            grade = "B";
        } else if (mark >= 70) {
            grade = "C";
        } else if (mark >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }
    }

    private void recalculateAverageAndGrade() {
        if (marks.isEmpty()) {
            averageMark = 0.0;
            grade = "N/A";
            return;
        }
        averageMark = marks.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        calculateGrade();
    }

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