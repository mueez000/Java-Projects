package atm.simulator.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
/*
 LEGAL COMMENT: Handles ATM transactions for BankIslami.
 */
public class Transactions extends JFrame implements ActionListener{
// INFORMATIVE COMMENT: UI components for transaction selection.
    JLabel l1;
    JButton b1,b2,b3,b4,b5,b6,b7;
    String mpin; 
    /*
     INFORMATIVE COMMENT: Constructor initializing transaction options.
     */
    Transactions(String mpin){
        this.mpin = mpin;
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l2 = new JLabel(i3);
        l2.setBounds(0, 0, 960, 1080);
        add(l2);
        
        l1 = new JLabel("Please Select Your Transaction");
        l1.setForeground(new Color(0, 102, 0)); 
        l1.setFont(new Font("Roboto", Font.BOLD, 20));
        
        b1 = new JButton("DEPOSIT");
        b2 = new JButton("WITHDRAWAL");
        b3 = new JButton("FAST CASH");
        b4 = new JButton("MINI STATEMENT");
        b5 = new JButton("MPIN CHANGE");
        b6 = new JButton("BALANCE INFO");
        b7 = new JButton("EXIT");
        
        Color btnColor = new Color(0, 102, 0);
        for(JButton btn : new JButton[]{b1,b2,b3,b4,b5,b6,b7}) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(btnColor);
            btn.setFont(new Font("Roboto", Font.BOLD, 14));
        }

        setLayout(null);
        
        l1.setBounds(200,400,700,35);
        l2.add(l1);
        
        b1.setBounds(170,450,150,35);
        b2.setBounds(390,450,150,35);
        b3.setBounds(170,500,150,35);
        b4.setBounds(390,500,150,35);
        b5.setBounds(170,550,150,35);
        b6.setBounds(390,550,150,35);
        b7.setBounds(300,600,150,35);
        
        for(JButton btn : new JButton[]{b1,b2,b3,b4,b5,b6,b7}) {
            l2.add(btn);
            btn.addActionListener(this);
        }
        
        setSize(960,1080);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        
    }
    /*
     INFORMATIVE COMMENT: Handles transaction selection.
     */
    public void actionPerformed(ActionEvent ae){
        try{
            if(ae.getSource()==b1){ 
                setVisible(false);
                new Deposit(mpin).setVisible(true);
            }else if(ae.getSource()==b2){ 
                setVisible(false);
                new Withdrawal(mpin).setVisible(true);
            }else if(ae.getSource()==b3){ 
                setVisible(false);
                new Fastcash(mpin).setVisible(true);
            }else if(ae.getSource()==b4){ 
                new Ministatement(mpin).setVisible(true);
            }else if(ae.getSource()==b5){ 
                setVisible(false);
                new Pinchange(mpin).setVisible(true);
            }else if(ae.getSource()==b6){ 
                setVisible(false);
                new Balanceenquiry(mpin).setVisible(true);
            }else if(ae.getSource()==b7){ 
                System.exit(0);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Transaction Error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args){
        new Transactions("").setVisible(true);
    }
}