package atm.simulator.system;

import java.sql.*; 
import javax.swing.JOptionPane;
/*
 LEGAL COMMENT: Handles database connection for BankIslami ATM system.
 */
public class Connections {
    private Connection c;
/*
     INFORMATIVE COMMENT: Establishes a connection to the MySQL database.
     */
    public Connections() {  
        try {  
            String url = "jdbc:mysql://localhost:3306/bankmanagementsystem";
            String user = "root";
            String password = "mysql@123";  
            
            c = DriverManager.getConnection(url, user, password);    
            
        } catch(SQLException e) {  
            // LEGAL COMMENT: Ensure the error message is user-friendly.
            JOptionPane.showMessageDialog(null, "Database Connection Failed: " + e.getMessage());
        }  
    }  
/*
     INFORMATIVE COMMENT: Returns the established database connection.
     */
    public Connection getConnection() {
        return c;
    }
}