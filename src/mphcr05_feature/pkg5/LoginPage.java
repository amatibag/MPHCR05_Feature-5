/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mphcr05_feature.pkg5;

/*
How the code works:
> LoginPage() -- handles the overall look of the GUI where the following methods are called:
    loadEmployees()
    loginButton.addActionListener(new LoginButtonListener());
    resetButton.addActionListener(new ResetButtonListener());
> loadEmployees() -- is a method to read the lines on the password.csv file. 
                Which reads the Employee Number, Last Name and Password Columns which is then being stored on the a temporary array list called employees
> LoginButtonListener implements ActionListener -- defines the action that will be perfomed when the login button was clicked
                it gets the text entered from LoginPage() -- usernameField and passwordField
                it will then perform an iteration to loop to find a match on the csv file which came from the loadEmployees()
                if it found a match of username and password on the csv file it will hide the current window and launch the Employee DropDown Class
                if it didnt found a match on the entered username and password it will then showMessageDialog stating "Invalid username or password."
> ResetButtonListener implements ActionListener -- defines the action that will be perfomed when the reset password button was clicked
                this method requires the user to enter their existing username and password first
                if the entered username is password is correct that's the only time they can go through the reset password option
                once they are on the reset password dialog they user will be required to enter the new password twice which are stored on the following variables:
                    newPassword -- first dialog
                    confirmPassword -- 2nd dialog
                the method requires the newPassword and confirmPassword to match before calling the following:
                    setPassword(newPassword)
                    saveEmployees()
                    showMessageDialog(LoginPage.this, "Password reset successful!")
                if the newPassword and confirmPassword entries doesnt match it will launch the showMessageDialog(LoginPage.this, "Invalid username or old password.") instead
> saveEmployees() -- this method access the password.csv file and performs the print write and file write to override the current data saved on the password.csv file
> Employee -- It contains fields for employee number, username, and password, and provides methods to interact with these fields.
                
    
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class LoginPage extends JFrame {
    // Fields for user input and data storage
    private JTextField usernameField; //a text input field where users can enter their usernames.
    private JPasswordField passwordField; //which is a specialized text input field for passwords. It masks the entered characters to provide security.
    private List<Employee> employees; //represents a list that will store Employee objects. This list will hold the data of employees for authentication purposes in the login page.

    LoginPage() { // constructor
        employees = loadEmployees(); // Load employees from the CSV file

        ImageIcon motorPHHeader = new ImageIcon("MotorPHHeader.png"); // inserts an image for the label
        ImageIcon logo = new ImageIcon("logo.png"); // creates the logo
        ImageIcon smallLogo = new ImageIcon("SmallLogo.png"); // creates the small logo

        JLabel headerLabel = new JLabel(); // creates a label
        headerLabel.setText("Motor PH - CP2 | Group 3: Login Page"); // sets text for the label
        headerLabel.setIcon(motorPHHeader); // makes the motorPHHeader image visible on the label
        headerLabel.setHorizontalTextPosition(JLabel.CENTER); // sets the Horizontal position of the text with image icon
        headerLabel.setVerticalTextPosition(JLabel.TOP); // sets the Vertical position of the text with image icon
        headerLabel.setForeground(new Color(0xFFFFFF)); // sets the font color of the text
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30)); // sets the font of the text
        headerLabel.setBackground(new Color(0x0E3171)); // sets the background color of the label
        headerLabel.setOpaque(true); // this will display background color
        headerLabel.setVerticalAlignment(JLabel.TOP); // sets the Vertical position of the headerLabel (icon + Text)
        headerLabel.setHorizontalAlignment(JLabel.CENTER); // sets the Horizontal position of the headerLabel (icon + Text)
        headerLabel.setBounds(0, 2, 1265, 480); // sets the position of the entire label on the frame

        JPanel passwordPanel = new JPanel(); //creates a pane for the login
        passwordPanel.setBackground(new Color(255, 255, 255, 200)); // sets the background color of the pane
        passwordPanel.setBounds(0, 480, 1265, 520);// sets the position of the pane on the frame
        passwordPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centered components

        // Create UI components for login
        usernameField = new JTextField(20); // This line creates a new text field where the user can enter their username, the number 20 constructs the text field with a default width of 20 columns (the number of characters it can display at a time)
        passwordField = new JPasswordField(20); // This line creates a new password field where the user can enter their password, , the number 20 constructs the text field with a default width of 20 columns (the number of characters it can display at a time)
        JButton loginButton = new JButton("Login"); // This line creates a new button labeled "Login"
        JButton resetButton = new JButton("Reset Password"); // This line creates a new button labeled "Reset Password"

        // Add components to passwordPanel with GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // creates the grid layout for passwordPanel since the passwordPanel is set to use the new GridBagLayout()
        gbc.insets = new Insets(10, 10, 10, 10); //specifies the amount of space (10 pixels) to be added on all four sides (top, left, bottom, right) of each component.

        gbc.gridx = 0; //sets the grid x-coordinate (column)
        gbc.gridy = 0; //sets the grid y-coordinate (row).
        passwordPanel.add(new JLabel("Username:"), gbc); //adds a new JLabel with the text "Username:" to the passwordPanel at the specified grid position (0,0) using the gbc constraints.

        gbc.gridx = 1; //sets the grid x-coordinate (column) to 1.
        passwordPanel.add(usernameField, gbc); //adds the usernameField to the passwordPanel at the specified grid position (1,0) using the gbc constraints.

        gbc.gridx = 0; //sets the grid x-coordinate (column) to 0.
        gbc.gridy = 1; // sets the grid y-coordinate (row) to 1.
        passwordPanel.add(new JLabel("Password:"), gbc); //adds a new JLabel with the text "Password:" to the passwordPanel at the specified grid position (0,1) using the gbc constraints.

        gbc.gridx = 1; //sets the grid x-coordinate (column) to 1.
        passwordPanel.add(passwordField, gbc); // adds the passwordField to the passwordPanel at the specified grid position (1,1) using the gbc constraints.

        gbc.gridx = 0; //sets the grid x-coordinate (column) to 0.
        gbc.gridy = 2; //sets the grid y-coordinate (row) to 2.
        gbc.gridwidth = 2; //sets the width of the component to span 2 columns.
        gbc.anchor = GridBagConstraints.CENTER; // centers the component within its cell.
        passwordPanel.add(loginButton, gbc); // adds the loginButton to the passwordPanel at the specified grid position (0,2), spanning 2 columns, using the gbc constraints.

        gbc.gridy = 3; //sets the grid y-coordinate (row) to 3, keeping gbc.gridx at its previous value (0) and gbc.gridwidth at its previous value (2).
        passwordPanel.add(resetButton, gbc); // adds the resetButton to the passwordPanel at the specified grid position (0,3), spanning 2 columns, using the gbc constraints.

        // Add action listeners for buttons
        loginButton.addActionListener(new LoginButtonListener()); //adds an action listener to the loginButton that will trigger when the button is clicked. The listener is an instance of LoginButtonListener.
        resetButton.addActionListener(new ResetButtonListener()); // adds an action listener to the resetButton that will trigger when the button is clicked. The listener is an instance of ResetButtonListener.

        // Set up the frame
        this.setTitle("Motor PH - CP2 | Group 3: Login Page"); // sets the title for employee selection page
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
        this.setResizable(false); // prevent frame from being resized
        this.setSize(1265, 1000); // sets the x-dimension, and y-dimension of frame
        this.setLayout(null); // allows to define the position of the label by setbounds
        this.setVisible(true); // make frame visible-should be set on the end
        this.add(headerLabel); // add the employeeDDLabel on the frame

        this.setIconImage(logo.getImage()); // to apply the image icon named logo as the frame logo
        this.getContentPane().setBackground(new Color(0x0E3171)); // change color of background
        this.add(passwordPanel);
    }

    // This method is intended to load employee data from a CSV file into a list of Employee objects.
    private List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>(); //This line creates an empty ArrayList to hold Employee objects. The list is named employees.
            //List is an interface that represents an ordered collection of elements.
            //An ArrayList is a resizable array implementation of the List interface in Java, which means it dynamically adjusts its size as elements are added or removed.
        File file = new File("password.csv"); // This line defines the file we want to read
        try (BufferedReader br = new BufferedReader(new FileReader(file))) { // BufferedReader initiates the action to read a specific content
                //FileReader provides intruction to BufferedReader as to which file to read
            String line; //This line declares a String variable named line that will hold each line read from the CSV file.
            while ((line = br.readLine()) != null) { //This line starts a while loop that continues as long as there are lines to read from the CSV file. The br.readLine() method reads a line of text from the file and assigns it to the line variable. The loop exits when there are no more lines to read.
                String[] values = line.split(","); //This line splits the current line of text into an array of strings using a comma (,) as the delimiter. The resulting array, named values, contains the individual fields from the CSV line.
                employees.add(new Employee(values[0], values[1], values[2])); //This line creates a new Employee object using the values from the values array (assumed to be in the order: employee number, username, and password). The new Employee object is then added to the employees list.
            }
        } catch (IOException e) { //This line starts a catch block to handle any IOException that may occur while reading the file.
            e.printStackTrace(); //This line prints the stack trace of the caught exception to the standard error stream. This helps in debugging by providing details about where and why the exception occurred.
        }
        return employees; //This line returns the employees list, which now contains all the Employee objects created from the lines of the CSV file.
    }

    // Action listener for login button
    private class LoginButtonListener implements ActionListener { //The line declares a private inner class named LoginButtonListener that implements the ActionListener interface. This class will handle the action events for the login button.
        @Override //The @Override annotation indicates that this method overrides a method declared in a superclass or interface.
        public void actionPerformed(ActionEvent e) { // This line declares the actionPerformed method, which is required by the ActionListener interface. This method is called when an action event (such as a button click) occurs. The ActionEvent object e contains information about the event.
            String username = usernameField.getText(); //This line retrieves the text entered in the usernameField and assigns it to the username variable.
            String password = new String(passwordField.getPassword()); //This line retrieves the password entered in the passwordField and converts it to a String, then assigns it to the password variable. The getPassword method returns a character array, so it is converted to a string.

            for (Employee emp : employees) { //This line starts a for-each loop that iterates over each Employee object in the employees list.
                if (emp.getUsername().equals(username) && emp.getPassword().equals(password)) { //This line checks if the current Employee object's username and password match the entered username and password.
                        // emp.getUsername().equals(username) checks if the username of the current Employee matches the entered username.
                        //emp.getPassword().equals(password) checks if the password of the current Employee matches the entered password.
                        //Both conditions need to be true for the if statement to execute its body.
                    openEmployeeTable(); // Open the new frame
                    disposeCurrentFrame(); // Close the current frame
                    return; //This line exits the actionPerformed method, stopping further execution of the loop and method since a successful login has been detected.
                }
            }
            JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password."); //This line displays a message dialog with the text "Invalid username or password."
                        //If the loop completes without finding a matching username and password, this dialog is shown to inform the user of the failed login attempt.
        }
    }

    // Action listener for reset password button
    private class ResetButtonListener implements ActionListener { //This line declares a private inner class named ResetButtonListener that implements the ActionListener interface. This class will handle the action events for the reset password button.
        @Override //The @Override annotation indicates that this method overrides a method declared in a superclass or interface.
        public void actionPerformed(ActionEvent e) { // This line declares the actionPerformed method, which is required by the ActionListener interface. This method is called when an action event (such as a button click) occurs. The ActionEvent object e contains information about the event.
            String username = usernameField.getText(); //This line retrieves the text entered in the usernameField and assigns it to the username variable.
            String oldPassword = new String(passwordField.getPassword()); //This line retrieves the password entered in the passwordField and converts it to a String, then assigns it to the oldPassword variable. The getPassword method returns a character array, so it is converted to a string.

            for (Employee emp : employees) { //This line starts a for-each loop that iterates over each Employee object in the employees list.
                if (emp.getUsername().equals(username) && emp.getPassword().equals(oldPassword)) { //This line checks if the current Employee object's username and password match the entered username and old password.
                            //emp.getUsername().equals(username) checks if the username of the current Employee matches the entered username.
                            //emp.getPassword().equals(oldPassword) checks if the password of the current Employee matches the entered old password.
                            //Both conditions need to be true for the if statement to execute its body.
                    String newPassword = JOptionPane.showInputDialog("Enter new password:");
                           //This line shows an input dialog that prompts the user to enter a new password.
                           //The entered password is assigned to the newPassword variable.
                    String confirmPassword = JOptionPane.showInputDialog("Confirm new password:"); //This line shows another input dialog that prompts the user to confirm the new password.
                           //The entered password confirmation is assigned to the confirmPassword variable.

                    if (newPassword.equals(confirmPassword)) { //This line checks if the new password and the confirmation password match.
                            //newPassword.equals(confirmPassword) returns true if the two strings are equal.
                        emp.setPassword(newPassword);
                            //If the new password and confirmation password match, this line sets the Employee object's password to the new password using the setPassword method.
                        saveEmployees();
                            //This line calls the saveEmployees method, which is assumed to save the updated employee data (including the new password) back to the password.csv file.
                        JOptionPane.showMessageDialog(LoginPage.this, "Password reset successful!"); //This line displays a message dialog with the text "Password reset successful!".
                            //JOptionPane.showMessageDialog creates a simple dialog box with the specified message.
                            //LoginPage.this refers to the LoginPage instance to ensure the dialog is displayed relative to this frame.
                    } else {  //This line starts the else block that executes if the new password and confirmation password do not match.
                        JOptionPane.showMessageDialog(LoginPage.this, "Passwords do not match."); //This line displays a message dialog with the text "Passwords do not match.".
                    }
                    return; //This line exits the actionPerformed method, stopping further execution of the loop and method since the password reset process has been handled (either successfully or not).
                }
            }
            JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or old password."); //If the loop completes without finding a matching username and old password, this line displays a message dialog with the text "Invalid username or old password.".
        }
    }

    // Method to save updated employees to CSV file
    private void saveEmployees() { //The line declares a private method named saveEmployees that has no return type (void). This method is intended to write the list of Employee objects back to the password.csv file.
        File file = new File("password.csv"); //This line creates a File object that represents the CSV file named password.csv. This file is assumed to be in the same directory as the application. Adjust the path if the file is located elsewhere.
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) { //This line creates a PrintWriter wrapped around a FileWriter, which is used to write to the file.
                //The try-with-resources statement is used to ensure that the PrintWriter is automatically closed when the try block is exited, either normally or due to an exception.
            for (Employee emp : employees) { //This line starts a for-each loop that iterates over each Employee object in the employees list.
                pw.println(emp.toCsv()); //This line writes a line to the file for each Employee object.
                    //The toCsv method of the Employee class is called, which returns a string representation of the employee data formatted as a CSV line.
                    //The println method of PrintWriter writes this string to the file and appends a newline character.
            }
        } catch (IOException e) { //This line starts a catch block to handle any IOException that may occur while writing to the file.
            e.printStackTrace(); //This line prints the stack trace of the caught exception to the standard error stream. This helps in debugging by providing details about where and why the exception occurred.
        }
    }

    // Employee class to store employee data
    static class Employee {
        private String empNumber; //empNumber is a String that stores the employee number.
        private String username; //username is a String that stores the username.
        private String password; //is a String that stores the password.

        Employee(String empNumber, String username, String password) {
            this.empNumber = empNumber; //assigns the empNumber parameter to the instance variable empNumber.
            this.username = username; // assigns the username parameter to the instance variable username.
            this.password = password; // assigns the password parameter to the instance variable password.
        }
        //This method returns the username of the Employee.
        String getUsername() { // is the method signature, indicating it returns a String and takes no parameters.
            return username; //returns the value of the username instance variable.
        }
        //This method returns the password of the Employee.
        String getPassword() { //is the method signature, indicating it returns a String and takes no parameters.
            return password; //returns the value of the password instance variable.
        }
        //This method sets the password of the Employee.
        void setPassword(String password) { //is the method signature, indicating it returns no value (void) and takes one parameter, a String named password.
            this.password = password; //assigns the password parameter to the instance variable password.
        }
        //This method returns a CSV-formatted String representation of the Employee.
        String toCsv() { //is the method signature, indicating it returns a String and takes no parameters.
            return empNumber + "," + username + "," + password; //concatenates the empNumber, username, and password instance variables with commas separating them, and returns this String.
        }
    }
    private void disposeCurrentFrame() {
    this.setVisible(false); // hides the current frame
    }

private void openEmployeeTable() {
        EmployeeTable employeeTable = new EmployeeTable(); //launch a new frame
        employeeTable.setVisible(true); // Make the new frame visible
    }
}
