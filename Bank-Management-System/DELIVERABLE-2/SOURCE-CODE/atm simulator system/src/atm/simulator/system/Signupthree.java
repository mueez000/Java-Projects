// LEGAL COMMENT: Handles the final step of account creation for BankIsl
package atm.simulator.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
/*
 LEGAL COMMENT: Collects final account preferences, card details, and MPIN setup.
 */
public class Signupthree extends JFrame implements ActionListener{
    // INFORMATIVE COMMENT: UI components for final account setup.
    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12;
    JRadioButton r1,r2,r3,r4;
    JButton b1,b2;
    JCheckBox c1,c2,c3,c4,c5,c6,c7;
    String formno;
    /*
     INFORMATIVE COMMENT: Constructor initializes the UI for the final step.
     */
    Signupthree(String formno){
        this.formno = formno;
        setTitle("BankIslami Account Opening - Final Step");
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l14 = new JLabel(i3);
        l14.setBounds(150, 0, 100, 100);
        add(l14);
        
        l1 = new JLabel("Final Account Setup");
        l1.setFont(new Font("Raleway", Font.BOLD, 22));
        
        l2 = new JLabel("Account Type:");
        l2.setFont(new Font("Raleway", Font.BOLD, 16));
        
        r1 = new JRadioButton("Islamic Savings Account");
        r2 = new JRadioButton("Al-Amanah Investment");
        r3 = new JRadioButton("Current Account");
        r4 = new JRadioButton("Term Deposit Account");
        
        l3 = new JLabel("Card Number:");
        l3.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l4 = new JLabel("XXXX-XXXX-XXXX-XXXX");
        l4.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l5 = new JLabel("(16-digit BankIslami Card number)");
        l5.setFont(new Font("Raleway", Font.BOLD, 12));
        
        l6 = new JLabel("Will be embossed on your ATM Card & Cheque Book");
        l6.setFont(new Font("Raleway", Font.BOLD, 12));
        
        l7 = new JLabel("MPIN:");
        l7.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l8 = new JLabel("XXXX");
        l8.setFont(new Font("Raleway", Font.BOLD, 16));
    
        l9 = new JLabel("(4-digit Mobile PIN for transactions)");
        l9.setFont(new Font("Raleway", Font.BOLD, 12));
    
        l10 = new JLabel("Additional Services:");
        l10.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l11 = new JLabel("Form No:");
        l11.setFont(new Font("Raleway", Font.BOLD, 14));
        
        l12 = new JLabel(formno);
        l12.setFont(new Font("Raleway", Font.BOLD, 14));
        
        b1 = new JButton("Submit");
        b1.setFont(new Font("Raleway", Font.BOLD, 14));
        b1.setBackground(Color.BLACK); // Islamic green
        b1.setForeground(Color.BLACK);
        
        b2 = new JButton("Cancel");
        b2.setFont(new Font("Raleway", Font.BOLD, 14));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.BLACK);
        
        c1 = new JCheckBox("ATM Card");
        c2 = new JCheckBox("BankIslami Digital");
        c3 = new JCheckBox("Mobile Banking");
        c4 = new JCheckBox("SMS Alerts");
        c5 = new JCheckBox("Cheque Book");
        c6 = new JCheckBox("e-Statement");
        
        c7 = new JCheckBox("I declare compliance with State Bank of Pakistan regulations and BankIslami terms.");
        c7.setFont(new Font("Raleway", Font.BOLD, 12));
        
        ButtonGroup accountGroup = new ButtonGroup();
        accountGroup.add(r1);
        accountGroup.add(r2);
        accountGroup.add(r3);
        accountGroup.add(r4);
        
        setLayout(null);
        l11.setBounds(250, 10, 100, 30);
        add(l11);
        
        l12.setBounds(350, 10, 200, 30);
        add(l12);
        
        l1.setBounds(250,40,400,40);
        add(l1); 
        
        l2.setBounds(50,100,200,30);
        add(l2);
        
        r1.setBounds(50,140,250,30);
        add(r1);
        
        r2.setBounds(320,140,250,30);
        add(r2);
        
        r3.setBounds(50,180,250,30);
        add(r3);
        
        r4.setBounds(320,180,250,30);
        add(r4);
        
        l3.setBounds(50,240,200,30);
        add(l3);
        
        l4.setBounds(300,240,250,30);
        add(l4);
        
        l5.setBounds(50,270,300,20);
        add(l5);
        
        l6.setBounds(300,270,400,20);
        add(l6);
        
        l7.setBounds(50,310,200,30);
        add(l7);
        
        l8.setBounds(300,310,200,30);
        add(l8);
        
        l9.setBounds(50,340,300,20);
        add(l9);
        
        l10.setBounds(50,380,200,30);
        add(l10);
        
        int y = 420;
        c1.setBounds(50, y, 200,30); add(c1);
        c2.setBounds(300, y, 200,30); add(c2); y += 40;
        c3.setBounds(50, y, 200,30); add(c3);
        c4.setBounds(300, y, 200,30); add(c4); y += 40;
        c5.setBounds(50, y, 200,30); add(c5);
        c6.setBounds(300, y, 200,30); add(c6); y += 40;
        
        c7.setBounds(50, y+20, 600,20);
        add(c7);
        
        b1.setBounds(200, y+60, 120,30);
        add(b1);
        
        b2.setBounds(350, y+60, 120,30);
        add(b2);
        
        getContentPane().setBackground(Color.WHITE);
        setSize(700, 820);
        setLocationRelativeTo(null);
        setVisible(true);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
    }
    /*
     INFORMATIVE COMMENT: Handles the final form submission and validation.
     */
    public void actionPerformed(ActionEvent ae){
        String atype = "";
        if(r1.isSelected()) atype = "Islamic Savings";
        else if(r2.isSelected()) atype = "Al-Amanah";
        else if(r3.isSelected()) atype = "Current";
        else if(r4.isSelected()) atype = "Term Deposit";
        // LEGAL COMMENT: Generate random card number and MPIN for se
        Random ran = new Random();
        String cardno = "4" + String.format("%015d", Math.abs(ran.nextLong() % 1000000000000000L));
        
        String mpin = String.format("%04d", ran.nextInt(10000));
        
        String facility = "";
        if(c1.isSelected()) facility += "ATM Card,";
        if(c2.isSelected()) facility += "Digital Banking,";
        if(c3.isSelected()) facility += "Mobile Banking,";
        if(c4.isSelected()) facility += "SMS Alerts,";
        if(c5.isSelected()) facility += "Cheque Book,";
        if(c6.isSelected()) facility += "e-Statement";
        
        try{
            if(ae.getSource()==b1){
                if(atype.isEmpty() || !c7.isSelected()){
                    JOptionPane.showMessageDialog(null, "Please select account type and accept terms");
                    return;
                }
                // INFORMATIVE COMMENT: Store final user details into the database.
                Connections c = new Connections();
                String query = "INSERT INTO SignupThree VALUES (?,?,?,?,?)";
                PreparedStatement ps = c.getConnection().prepareStatement(query); 
                ps.setString(1, formno);
                ps.setString(2, atype);
                ps.setString(3, cardno);
                ps.setString(4, mpin);
                ps.setString(5, facility);
                ps.executeUpdate();
               
                String loginQuery = "INSERT INTO Login (form_no, card_number, mpin) VALUES (?,?,?)";
                PreparedStatement ps2 = c.getConnection().prepareStatement(loginQuery); 
                ps2.setString(1, formno);
                ps2.setString(2, cardno);
                ps2.setString(3, mpin);
                ps2.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Account Created Successfully!\n" +
                    "Card Number: " + cardno + "\nMPIN: " + mpin);
                
                new Login().setVisible(true);
                setVisible(false);
            
            }else if(ae.getSource()==b2){
                System.exit(0);
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        new Signupthree("").setVisible(true);
    }
}