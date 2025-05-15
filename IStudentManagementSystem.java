/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package istudentmanagementsystem;

import istudentmanagementsystem.JavaDb;
import javax.swing.*;
import java.awt.*;
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
private JButton addButton;
private JavaDb database;

public IStudentManagementSystem(){
    setTitle("Student Managment System");
    setSize(400,200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    
    JLabel idLabel = new JLabel("Student ID: ");
    JLabel nameLabel = new JLabel("Name: ");
    idField=new JTextField(16);
    nameField=new JTextField(28);
    addButton=new JButton("Add Student");
    
    JPanel pnl=new JPanel(new GridLayout(3,2,10,10));
    pnl.add(idLabel);
    pnl.add(idField);
    pnl.add(nameLabel);
    pnl.add(nameField);
    pnl.add(new JLabel());
    pnl.add(addButton);
    add(pnl);
    
    try{
        Connection conn=DriverManager.getConnection("jdbc:sqlite:students.db");
        database=new JavaDb(conn);
        StudentManagementSystem studentManagementSystem = new StudentManagementSystem(database);
    }
    catch(SQLException e){
        JOptionPane.showMessageDialog(this,"Database Error: "+e.getMessage());
        System.exit(1);   
    }
    addButton.addActionListener(e-> {
        try {
addStudent();
        } catch (SQLException ex) {
            Logger.getLogger(IStudentManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
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