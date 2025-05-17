
package istudentmanagementsystem;

import java.util.Map;


public class MarkSheet {
    
   
    public final Student student;
    private double totalMarks;
    private double averageMarks;
    public double marks;
    
    public MarkSheet(Student student) {
        this.student = student;
        calculateTotals();
    }

    MarkSheet(JavaDb.Student student) {
        this.student = null;
    }

    private void calculateTotals() {
        totalMarks = 0.0;
        Map<String, Double> marks = student.getMarks();
        for (double mark : marks.values()) {
            totalMarks += mark;
        }
        averageMarks = marks.isEmpty() ? 0.0 : totalMarks / marks.size();
    }

    public double generate() {
        student.calculateGrade();  // Ensure grade is up to date
        calculateTotals();
        return averageMarks;
    }

    public void setMarks(int marks) {
    if (marks < 0 || marks > 100) {
        throw new IllegalArgumentException("Marks must be 0-100");
    }
    this.marks = marks;
    }
    
    public void display() {
        System.out.println("\n========= Mark Sheet =========");
        System.out.println("Student ID  : " + student.getStudentId());
        System.out.println("Name        : " + student.getName());
        System.out.println("Marks:");
        for (Map.Entry<String, Double> entry : student.getMarks().entrySet()) {
            System.out.printf("  %-10s: %.2f%n", entry.getKey(), entry.getValue());
        }
        System.out.printf("Total Marks : %.2f%n", totalMarks);
        System.out.printf("Average     : %.2f%n", averageMarks);
        System.out.println("Grade       : " + student.getGrade());
        System.out.println("==============================\n");
    }
}