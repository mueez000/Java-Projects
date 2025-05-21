package atm.simulator.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
/*
 LEGAL COMMENT: Handles step 2 of account opening process for BankIslami.
 Collects additional user details such as religion, income, and occupation.
 */
public class Signuptwo extends JFrame implements ActionListener{
    // INFORMATIVE COMMENT: UI components for step 2 of the signup proce
    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13;
    JButton b;
    JRadioButton r1,r2,r3,r4;
    JTextField t1,t2,t3;
    JComboBox<String> c1,c2,c3,c4,c5;
    String formno;
    /*
     INFORMATIVE COMMENT: Constructor initializes the account details UI.
     */
    Signuptwo(String formno){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l14 = new JLabel(i3);
        l14.setBounds(150, 0, 100, 100);
        add(l14);
        
        this.formno = formno;
        setTitle("BankIslami Account Opening - Step 2");
        
        l1 = new JLabel("Account Details (Step 2/3)");
        l1.setFont(new Font("Raleway", Font.BOLD, 22));
        
        l2 = new JLabel("Religion:");
        l2.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l3 = new JLabel("Account Type:");
        l3.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l4 = new JLabel("Annual Income (PKR):");
        l4.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l5 = new JLabel("Education:");
        l5.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l11 = new JLabel("Qualification:");
        l11.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l6 = new JLabel("Occupation:");
        l6.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l7 = new JLabel("Tax Number:");
        l7.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l8 = new JLabel("CNIC Number:");
        l8.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l9 = new JLabel("Senior Citizen:");
        l9.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l10 = new JLabel("Existing Bank Account:");
        l10.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l12 = new JLabel("Form No:");
        l12.setFont(new Font("Raleway", Font.BOLD, 13));
        
        l13 = new JLabel(formno);
        l13.setFont(new Font("Raleway", Font.BOLD, 13));
        
        b = new JButton("Next");
        b.setFont(new Font("Raleway", Font.BOLD, 14));
        b.setBackground(Color.BLACK); // Islamic green
        b.setForeground(Color.BLACK);
        
        t1 = new JTextField(); 
        t1.setToolTipText("National Tax Number (NTN)");
        
        t2 = new JTextField(); 
        t2.setToolTipText("Format: 12345-1234567-1");
        
        r1 = new JRadioButton("Yes");
        r2 = new JRadioButton("No");
        r3 = new JRadioButton("Yes");
        r4 = new JRadioButton("No");
       
        String religion[] = {"Muslim", "Christian", "Hindu", "Other"};
        c1 = new JComboBox<>(religion);
        
        String accountTypes[] = {"Islamic Current", "Islamic Savings", "Al-Amanah", "Business", "Student"};
        c2 = new JComboBox<>(accountTypes);
        
        String income[] = {"Select","Below 500,000", "500,000 - 1,000,000", 
                         "1,000,000 - 2,000,000", "2,000,000 - 5,000,000", 
                         "Above 5,000,000"};
        c3 = new JComboBox<>(income);
        
        String education[] = {"Matriculate", "Intermediate", "Bachelor's", 
                            "Master's", "PhD", "Madrasa", "Other"};
        c4 = new JComboBox<>(education);
        
        String occupation[] = {"Salaried", "Self-Employed", "Business", 
                              "Student", "Retired", "Agriculture", "Other"};
        c5 = new JComboBox<>(occupation);
        
        setLayout(null);
        l12.setBounds(300, 10, 100, 30); 
        add(l12);
        
        l13.setBounds(400, 10, 200, 30);
        add(l13);
        
        l1.setBounds(250,30,400,40);
        add(l1);
        
        int y = 80;
        l2.setBounds(50, y+=50, 150,25); add(l2);
        c1.setBounds(250, y, 300,25); add(c1);
        
        l3.setBounds(50, y+=40, 150,25); add(l3);
        c2.setBounds(250, y, 300,25); add(c2);
        
        l4.setBounds(50, y+=40, 200,25); add(l4);
        c3.setBounds(250, y, 300,25); add(c3);
        
        l5.setBounds(50, y+=40, 150,25); add(l5);
        c4.setBounds(250, y, 300,25); add(c4);
        
        l6.setBounds(50, y+=40, 150,25); add(l6);
        c5.setBounds(250, y, 300,25); add(c5);
        
        l7.setBounds(50, y+=40, 150,25); add(l7);
        t1.setBounds(250, y, 300,25); add(t1);
        
        l8.setBounds(50, y+=40, 150,25); add(l8);
        t2.setBounds(250, y, 300,25); add(t2);
        
        l9.setBounds(50, y+=40, 150,25); add(l9);
        r1.setBounds(250, y, 80,25); add(r1);
        r2.setBounds(350, y, 80,25); add(r2);
        
        l10.setBounds(50, y+=40, 200,25); add(l10);
        r3.setBounds(250, y, 80,25); add(r3);
        r4.setBounds(350, y, 80,25); add(r4);
        
        b.setBounds(450, y+40, 120, 30);  add(b);
        b.addActionListener(this);
        
        getContentPane().setBackground(Color.WHITE);
        setSize(650, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
     /*
     INFORMATIVE COMMENT: Handles form submission and validation.
     */

    public void actionPerformed(ActionEvent ae){
        try{
            String religion = (String)c1.getSelectedItem();
            String accountType = (String)c2.getSelectedItem();
            String income = (String)c3.getSelectedItem();
            String education = (String)c4.getSelectedItem();
            String occupation = (String)c5.getSelectedItem();
            String taxNumber = t1.getText().trim();
            String cnic = t2.getText().trim();
            // LEGAL COMMENT: Validate CNIC format.
            if(cnic.isEmpty() || !cnic.matches("\\d{5}-\\d{7}-\\d")){
                JOptionPane.showMessageDialog(null, "Invalid CNIC format!\nUse: 12345-1234567-1");
                return;
            }
            // LEGAL COMMENT: Ensure an income bracket is selected.
            if(income.equals("Select")){
                JOptionPane.showMessageDialog(null, "Please select income bracket!");
                return;
            }
            
            String seniorCitizen = r1.isSelected() ? "Yes" : "No";
            String existingAccount = r3.isSelected() ? "Yes" : "No";
            
            Connections c1 = new Connections();
            String query = "INSERT INTO SignupTwo VALUES (?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps = c1.getConnection().prepareStatement(query);  
            ps.setString(1, formno);
            ps.setString(2, religion);
            ps.setString(3, accountType);
            ps.setString(4, income);
            ps.setString(5, education);
            ps.setString(6, occupation);
            ps.setString(7, taxNumber);
            ps.setString(8, seniorCitizen);
            ps.setString(9, existingAccount);
            
            ps.executeUpdate();
            
            new Signupthree(formno).setVisible(true);
            setVisible(false);
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        new Signuptwo("").setVisible(true);
    }
}