package istudentmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IStudentManagementSystem extends JFrame{        // class of Mohaned
private JTextField idField;
private JTextField nameField;
private JTextField mark1Field;
private JTextField mark2Field;
private JTextField mark3Field;


private JButton addButton ,searchButton, updateButton, deleteButton, markSheetButton ;
private JavaDb database;

public IStudentManagementSystem(){
    setTitle("Student Managment System");
    setSize(500,300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    
    JLabel idLabel = new JLabel("Student ID: ");
    JLabel nameLabel = new JLabel("Name: ");
    JLabel mark1Label = new JLabel("Mark1: ");
    JLabel mark2Label = new JLabel("Mark2: ");
    JLabel mark3Label = new JLabel("Mark3: ");

    idField=new JTextField(16);
    nameField=new JTextField(28);
    mark1Field=new JTextField(10);
    mark2Field=new JTextField(10);
    mark3Field=new JTextField(10);
    addButton=new JButton("Add Student");
    searchButton=new JButton("Search Student");
    updateButton = new JButton("Update Student");
    deleteButton = new JButton("Delete Student");
    markSheetButton = new JButton("Generate Mark Sheet");
    
    JPanel pnl=new JPanel(new GridLayout(7,2,10,10));
    pnl.add(idLabel);
    pnl.add(idField);
    pnl.add(nameLabel);
    pnl.add(nameField);
    pnl.add(mark1Field);
    pnl.add(mark2Field);
    pnl.add(mark3Field);
    pnl.add(new JLabel());
    pnl.add(addButton);
    pnl.add(searchButton);
    pnl.add(updateButton);
    pnl.add(deleteButton);
    pnl.add(markSheetButton);
    add(pnl);
    
    try{
        String url = "jdbc:mysql://localhost:3306/istudent_db"; 
        String user = "root"; 
        String password = ""; 
        java.sql.Connection conn = java.sql.DriverManager.getConnection(url, user, password);
        database = new JavaDb(conn);
    }
    catch(SQLException e){
        JOptionPane.showMessageDialog(this,"Database Error: "+e.getMessage());
        System.exit(1);   
    }
    addButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            addStudent();
        } catch (SQLException ex) {
            Logger.getLogger(IStudentManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
});

searchButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            searchStudent();
        } catch (SQLException ex) {
            Logger.getLogger(IStudentManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
});

updateButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            updateStudent();
        } catch (SQLException ex) {
            Logger.getLogger(IStudentManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
});

deleteButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            deleteStudent();
        } catch (SQLException ex) {
            Logger.getLogger(IStudentManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
});

markSheetButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            generateMarkSheet();
        } catch (SQLException ex) {
            Logger.getLogger(IStudentManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
});
}
    private void addStudent() throws SQLException{
try{
    int id=Integer.parseInt(idField.getText());
    String name=nameField.getText().trim();
    String mark1=mark1Field.getText().trim();
    String mark2=mark2Field.getText().trim();
    String mark3=mark3Field.getText().trim();

    if (name.isEmpty() || mark1.isEmpty() || mark2.isEmpty() || mark3.isEmpty()){
        JOptionPane.showMessageDialog(this,"Name and Mark cannot be empty.");
        return;
    }
    double M1, M2, M3;
    M1 = Double.parseDouble(mark1);
    M2 = Double.parseDouble(mark2);
    M3 = Double.parseDouble(mark3);

    database.addStudent(id, name, "" + (M1+M2+M3)/3 );
    JOptionPane.showMessageDialog(this,"Student added successfully!");
    idField.setText("");
    nameField.setText("");
    mark1Field.setText("");
    mark2Field.setText("");
    mark3Field.setText("");

}
catch (NumberFormatException e){
JOptionPane.showMessageDialog(this,"Student ID must be a number");
}
catch(HeadlessException e){
JOptionPane.showMessageDialog(this,"Error"+ e.getMessage());
}
}
    private void searchStudent() throws SQLException {
    try {
    int id = Integer.parseInt(idField.getText());
    JavaDb.Student student = database.getStudentById(id);
    if (student != null) {
        nameField.setText(student.getName());
         // markField.setText(student.getMarks());
        JOptionPane.showMessageDialog(this, "Student Found: " + student.getName());
    }
    else {
        JOptionPane.showMessageDialog(this, "Student not found.");
         }
        } 
catch (NumberFormatException e) {
JOptionPane.showMessageDialog(this, "Student ID must be a number");
        }
    }

    private void updateStudent() throws SQLException {
    try {
    int id = Integer.parseInt(idField.getText());
    String newName = nameField.getText().trim();
    String newMark1 = mark1Field.getText().trim();
    String newMark2 = mark2Field.getText().trim();
    String newMark3 = mark3Field.getText().trim();
    if (newName.isEmpty() || newMark1.isEmpty() || newMark2.isEmpty() || newMark3.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Name and Mark cannot be empty.");
        return;
            }
    double NM1, NM2, NM3;
    NM1 = Double.parseDouble(newMark1);
    NM2 = Double.parseDouble(newMark2);
    NM3 = Double.parseDouble(newMark3);
    
    database.updateStudent(id, newName, "" + (NM1+NM2+NM3)/3 );
    JOptionPane.showMessageDialog(this, "Student updated successfully!");
        } 
catch (NumberFormatException e) {
JOptionPane.showMessageDialog(this, "Student ID must be a number");
        }
    }

    private void deleteStudent() throws SQLException {
    try {
    int id = Integer.parseInt(idField.getText());
    database.deleteStudent(id);
    JOptionPane.showMessageDialog(this, "Student deleted successfully!");
    idField.setText("");
    nameField.setText("");
    mark1Field.setText("");
    mark2Field.setText("");
    mark3Field.setText("");
        }
    catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Student ID must be a number");
        }
    }
    String calculateGrade(double mark) {
        String grade;
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
        return grade;
    }
    private void generateMarkSheet() throws SQLException {
    try {
    int id = Integer.parseInt(idField.getText());
    JavaDb.Student student = database.getStudentById(id);
    if (student == null) {
        JOptionPane.showMessageDialog(this, "Student not found.");
        return;
    }
    double marks = database.getMarks(id);
    
    JOptionPane.showMessageDialog(this, calculateGrade(marks));
        }
    catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Student ID must be a number");
        }
    
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
IStudentManagementSystem gui =new IStudentManagementSystem();
gui.setVisible(true);
 });
    }
}

