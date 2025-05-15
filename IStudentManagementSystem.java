/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package istudentmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mergawy
 */
public class IStudentManagementSystem extends JFrame{
private JTextField idField;
private JTextField nameField;
private JButton addButton ,searchButton, updateButton, deleteButton, markSheetButton ;
private JavaDb database;

public IStudentManagementSystem(){
    setTitle("Student Managment System");
    setSize(500,300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    
    JLabel idLabel = new JLabel("Student ID: ");
    JLabel nameLabel = new JLabel("Name: ");
    idField=new JTextField(16);
    nameField=new JTextField(28);
    addButton=new JButton("Add Student");
    searchButton=new JButton("Search Student");
    updateButton = new JButton("Update Student");
    deleteButton = new JButton("Delete Student");
    markSheetButton = new JButton("Generate Mark Sheet");
    
    JPanel pnl=new JPanel(new GridLayout(6,2,10,10));
    pnl.add(idLabel);
    pnl.add(idField);
    pnl.add(nameLabel);
    pnl.add(nameField);
    pnl.add(new JLabel());
    pnl.add(addButton);
    pnl.add(searchButton);
    pnl.add(updateButton);
    pnl.add(deleteButton);
    pnl.add(markSheetButton);
    add(pnl);
    
    try{
        String url = "jdbc:mysql://localhost:3309/istudent_db"; 
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
    if (name.isEmpty()){
        JOptionPane.showMessageDialog(this,"Name cannot be empty.");
        return;
    }
    database.addStudent(id,name);
    JOptionPane.showMessageDialog(this,"Student added successfully!");
    idField.setText("");
    nameField.setText("");
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
    Student student = database.getStudentById(id);
    if (student != null) {
        nameField.setText(student.getName());
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
    if (newName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Name cannot be empty.");
        return;
            }
    database.updateStudent(id, newName);
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
        }
    catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Student ID must be a number");
        }
    }

    private void generateMarkSheet() throws SQLException {
    try {
    int id = Integer.parseInt(idField.getText());
    Student student = database.getStudentById(id);
    if (student == null) {
        JOptionPane.showMessageDialog(this, "Student not found.");
        return;
    }
    int marks = database.getMarks(id);
    
    student.setMarks(marks);
    MarkSheet ms = new MarkSheet(student);
    String result = ms.generate();
    JOptionPane.showMessageDialog(this, result);
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
