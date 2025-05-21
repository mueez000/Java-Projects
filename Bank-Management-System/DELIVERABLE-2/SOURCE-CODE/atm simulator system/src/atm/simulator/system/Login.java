package atm.simulator.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
/*
 LEGAL COMMENT: Handles user authentication for BankIslami ATM Simulator.
 */

public class Login extends JFrame implements ActionListener{
    // INFORMATIVE COMMENT: UI components for the login screen.
    JLabel l1,l2,l3;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1,b2,b3;
    /*
     INFORMATIVE COMMENT: Constructor initializes the login interface.
     */
    Login(){
        setTitle("BankIslami ATM Simulator");  
        // INFORMATIVE COMMENT: Setup logo image at the top of the interface.
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l11 = new JLabel(i3);
        l11.setBounds(70, 10, 100, 100);
        add(l11);
        // INFORMATIVE COMMENT: Labels for welcome text and input fields.
        l1 = new JLabel("Welcome to Bank Islami");  
        l1.setFont(new Font("Osward", Font.BOLD, 38));
        l1.setBounds(200,40,450,40);
        add(l1);
        
        l2 = new JLabel("Card Number:");  
        l2.setFont(new Font("Raleway", Font.BOLD, 28));
        l2.setBounds(170,150,375,30);
        add(l2);
        
        tf1 = new JTextField(15);
        tf1.setBounds(380,150,230,30);
        tf1.setFont(new Font("Arial", Font.BOLD, 14));
        add(tf1);
        
        l3 = new JLabel("MPIN:");  
        l3.setFont(new Font("Raleway", Font.BOLD, 28));
        l3.setBounds(170,220,375,30);
        add(l3);
        
        pf2 = new JPasswordField(15);
        pf2.setFont(new Font("Arial", Font.BOLD, 14));
        pf2.setBounds(380,220,230,30);
        add(pf2);
                
        b1 = new JButton("SIGN IN");  
        b1.setBackground(Color.BLACK);  
        b1.setForeground(Color.BLACK);
        
        b2 = new JButton("RESET");
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.BLACK);
        
        b3 = new JButton("REGISTER CARD");
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.BLACK);
        
        setLayout(null);
        
        b1.setFont(new Font("Arial", Font.BOLD, 14));
        b1.setBounds(300,300,150,30);  
        add(b1);
        
        b2.setFont(new Font("Arial", Font.BOLD, 14));
        b2.setBounds(460,300,150,30);
        add(b2);
        
        b3.setFont(new Font("Arial", Font.BOLD, 14));
        b3.setBounds(300,350,310,30);
        add(b3);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        // INFORMATIVE COMMENT: Setup buttons for login actions.
        getContentPane().setBackground(Color.WHITE);
        
        setSize(800,480);
        setLocationRelativeTo(null);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
     /*
     INFORMATIVE COMMENT: Handles button actions including login, reset, and register.
     */
    public void actionPerformed(ActionEvent ae){
    try{        
        if(ae.getSource() == b1){
            Connections c1 = new Connections();
            
            Connection conn = c1.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Database Connection Failed!\nPlease check MySQL server.");
                return;
            }

            String cardno = tf1.getText();
            char[] pinChars = pf2.getPassword();
            String pin = new String(pinChars);
            // LEGAL COMMENT: Card number must be exactly 16 digits.
            if (!cardno.matches("\\d{16}")) {
                JOptionPane.showMessageDialog(null, "Invalid Card Number!\nMust be exactly 16 digits.");
                return;
            }
            // LEGAL COMMENT: MPIN must be exactly 4 digits for security reasons.
            if (!pin.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(null, "Invalid MPIN!\nMust be exactly 4 digits.");
                return;
            }

            String query = "SELECT * FROM Login WHERE card_number = ? AND mpin = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, cardno);
            ps.setString(2, pin);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                setVisible(false);
                new Transactions(cardno).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Authentication Failed!\nPlease check your credentials.");
            }
        } else if (ae.getSource() == b2) {
            tf1.setText("");
            pf2.setText("");
        } else if (ae.getSource() == b3) {
            setVisible(false);
            new Signupone().setVisible(true);
        }
    } catch(Exception e){
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        e.printStackTrace();  
    }
}
   
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Login();
    }
}