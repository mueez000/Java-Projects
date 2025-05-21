package atm.simulator.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;
import java.text.SimpleDateFormat;
/*
 LEGAL COMMENT: Signup form for opening a new BankIslami account.
 Captures personal details and submits them to the database.
 */
public class Signupone extends JFrame implements ActionListener{
    // INFORMATIVE COMMENT: UI components for account signup form.
    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15;
    JTextField t1,t2,t3,t4,t5,t6,t7,t8;
    JRadioButton r1,r2,r3,r4,r5;
    JComboBox<String> provinceCombo, cityCombo;
    JButton b;
    JDateChooser dateChooser;
    
    Random ran = new Random();
    long first4 = (ran.nextLong() % 9000L) + 1000L;
    String first = "BKI-" + Math.abs(first4);
    
    private final String[] PROVINCES = {"Select Province", "Sindh", "Punjab", "Khyber Pakhtunkhwa", "Balochistan", "Gilgit-Baltistan"};
    private final String[] CITIES = {"Select City", "Karachi", "Lahore", "Islamabad", "Rawalpindi", "Peshawar", "Quetta"};
    /*
     INFORMATIVE COMMENT: Constructor initializing the signup form interface.
     */
    Signupone(){
        setTitle("BankIslami Account Opening Form");
        // INFORMATIVE COMMENT: Setup logo image for branding.
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l11 = new JLabel(i3);
        l11.setBounds(20, 0, 100, 100);
        add(l11);
        // INFORMATIVE COMMENT: Application number display.
        l1 = new JLabel("Application No: "+first);
        l1.setFont(new Font("Raleway", Font.BOLD, 24));
        
        l2 = new JLabel("Personal Information");
        l2.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l3 = new JLabel("Full Name:");
        l3.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l4 = new JLabel("Father Name:");
        l4.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l5 = new JLabel("Date of Birth:");
        l5.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l6 = new JLabel("Gender:");
        l6.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l7 = new JLabel("CNIC Number:");
        l7.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l8 = new JLabel("Marital Status:");
        l8.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l9 = new JLabel("Address:");
        l9.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l10 = new JLabel("City:");
        l10.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l11 = new JLabel("Postal Code:");
        l11.setFont(new Font("Raleway", Font.BOLD, 16));
        
        l12 = new JLabel("Province:");
        l12.setFont(new Font("Raleway", Font.BOLD, 16));
        
        t1 = new JTextField(); 
        t2 = new JTextField();
        t3 = new JTextField();
        t4 = new JTextField(); 
        t5 = new JTextField(); 
        
        t3.setToolTipText("Format: 12345-1234567-1");
        
        provinceCombo = new JComboBox<>(PROVINCES);
        cityCombo = new JComboBox<>(CITIES);
        
        b = new JButton("Next");
        b.setFont(new Font("Raleway", Font.BOLD, 14));
        b.setBackground(Color.BLACK); 
        b.setForeground(Color.BLACK);
        
        r1 = new JRadioButton("Male");
        r2 = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(r1);
        genderGroup.add(r2);
        
        r3 = new JRadioButton("Married");
        r4 = new JRadioButton("Single");
        r5 = new JRadioButton("Other");
        ButtonGroup maritalGroup = new ButtonGroup();
        maritalGroup.add(r3);
        maritalGroup.add(r4);
        maritalGroup.add(r5);
        
        dateChooser = new JDateChooser();
        dateChooser.setMaxSelectableDate(new java.util.Date()); 
        
        setLayout(null);
        l1.setBounds(140,20,400,30);
        add(l1);
        
        l2.setBounds(140,60,300,30);
        add(l2);
        
        int y = 100;
        l3.setBounds(50, y, 150, 25); add(l3);
        t1.setBounds(220, y, 300, 25); add(t1); y += 40;
        
        l4.setBounds(50, y, 200, 25); add(l4);
        t2.setBounds(220, y, 300, 25); add(t2); y += 40;
        
        l5.setBounds(50, y, 150, 25); add(l5);
        dateChooser.setBounds(220, y, 300, 25); add(dateChooser); y += 40;
        
        l6.setBounds(50, y, 150, 25); add(l6);
        r1.setBounds(220, y, 80, 25); add(r1);
        r2.setBounds(320, y, 80, 25); add(r2); y += 40;
        
        l7.setBounds(50, y, 150, 25); add(l7);
        t3.setBounds(220, y, 300, 25); add(t3); y += 40;
        
        l8.setBounds(50, y, 150, 25); add(l8);
        r3.setBounds(220, y, 100, 25); add(r3);
        r4.setBounds(330, y, 100, 25); add(r4);
        r5.setBounds(440, y, 100, 25); add(r5); y += 40;
        
        l9.setBounds(50, y, 150, 25); add(l9);
        t4.setBounds(220, y, 300, 25); add(t4); y += 40;
        
        l10.setBounds(50, y, 150, 25); add(l10);
        cityCombo.setBounds(220, y, 300, 25); add(cityCombo); y += 40;
        
        l11.setBounds(50, y, 150, 25); add(l11);
        t5.setBounds(220, y, 300, 25); add(t5); y += 40;
        
        l12.setBounds(50, y, 150, 25); add(l12);
        provinceCombo.setBounds(220, y, 300, 25); add(provinceCombo); y += 40;
        
        b.setBounds(400, y+20, 120, 30); add(b);
        b.addActionListener(this);
        
        getContentPane().setBackground(Color.WHITE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    /*
     INFORMATIVE COMMENT: Handles the form submission.
     */
    public void actionPerformed(ActionEvent ae){
        try{
            String formno = first;
            String name = t1.getText().trim();
            String guardian = t2.getText().trim();
            java.util.Date selectedDate = dateChooser.getDate();
            if (selectedDate == null) {  
            JOptionPane.showMessageDialog(null, "Please select a valid date!");
            return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dob = sdf.format(selectedDate); 

            String gender = r1.isSelected() ? "Male" : r2.isSelected() ? "Female" : "";
            String cnic = t3.getText().trim();
            String marital = "";
            if(r3.isSelected()) marital = "Married";
            else if(r4.isSelected()) marital = "Single";
            else if(r5.isSelected()) marital = "Other";
            String address = t4.getText().trim();
            String city = (String) cityCombo.getSelectedItem();
            String postal = t5.getText().trim();
            String province = (String) provinceCombo.getSelectedItem();
            // LEGAL COMMENT: Validate required fields before storing data.
            if(name.isEmpty() || guardian.isEmpty() || dob.isEmpty() || gender.isEmpty() ||
               cnic.isEmpty() || marital.isEmpty() || address.isEmpty() || 
               city.equals("Select City") || province.equals("Select Province")){
                JOptionPane.showMessageDialog(null, "Please fill all required fields!");
                return;
            }
            // LEGAL COMMENT: CNIC format must match the national identificat
            if(!cnic.matches("\\d{5}-\\d{7}-\\d")){
                JOptionPane.showMessageDialog(null, "Invalid CNIC format!\nUse: 12345-1234567-1");
                return;
            }
            // INFORMATIVE COMMENT: Storing user data into the database.
            Connections c1 = new Connections();

            String query = "INSERT INTO SignupOne (form_no, full_name, guardian_name, dob, gender, " +
                         "cnic, marital_status, address, city, postal_code, province) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = c1.getConnection().prepareStatement(query); 
            
            ps.setString(1, formno);
            ps.setString(2, name);
            ps.setString(3, guardian);
            ps.setString(4, dob);
            ps.setString(5, gender);
            ps.setString(6, cnic);
            ps.setString(7, marital);
            ps.setString(8, address);
            ps.setString(9, city);
            ps.setString(10, postal);
            ps.setString(11, province);
            
            ps.executeUpdate();
            
            new Signuptwo(formno).setVisible(true);
            setVisible(false);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        new Signupone().setVisible(true);
    }
} 