package atm.simulator.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;
import java.sql.*;
/*
 LEGAL COMMENT: Handles withdrawal transactions for BankIslami ATM users.
 */
public class Withdrawal extends JFrame implements ActionListener {
// INFORMATIVE COMMENT: UI components for withdrawal interface.
    JTextField t1;
    JButton b1, b2;
    JLabel l1, l2, l3;
    String mpin; 
/*
  INFORMATIVE COMMENT**: Constructor initializing withdrawal transaction options.
     */
    Withdrawal(String mpin){
        this.mpin = mpin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 960, 1080);
        add(background);

        Color islamicGreen = new Color(0, 102, 0);

        l1 = new JLabel("MAX WITHDRAWAL: PKR 50,000");
        l1.setForeground(islamicGreen);
        l1.setFont(new Font("Roboto", Font.BOLD, 18));

        l2 = new JLabel("ENTER AMOUNT (PKR):");
        l2.setForeground(islamicGreen);
        l2.setFont(new Font("Roboto", Font.BOLD, 16));

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 20));

        b1 = createStyledButton("CONFIRM WITHDRAWAL", islamicGreen);
        b2 = createStyledButton("BACK", islamicGreen);

        setLayout(null);

        l1.setBounds(200, 350, 400, 25);
        background.add(l1);

        l2.setBounds(200, 400, 400, 25);
        background.add(l2);

        t1.setBounds(200, 440, 330, 35);
        background.add(t1);

        b1.setBounds(260, 500, 200, 40);
        background.add(b1);

        b2.setBounds(260, 550, 200, 40);
        background.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(960, 1080);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.WHITE);
        btn.setForeground(color);
        btn.setFont(new Font("Roboto", Font.BOLD, 14));
        btn.setFocusPainted(false);
        return btn;
    }
/*
     INFORMATIVE COMMENT: Handles withdrawal transaction validation and processing.
     */
    public void actionPerformed(ActionEvent ae){
        try{
            String amount = t1.getText().trim();
            Date date = new Date();

            if(ae.getSource() == b1){
                // LEGAL COMMENT: Ensure withdrawal amount is valid.
                if(amount.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter withdrawal amount");
                    return;
                }

                if(!amount.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Invalid amount! Please enter numbers only");
                    return;
                }

                int withdrawalAmount = Integer.parseInt(amount);

                if(withdrawalAmount > 50000) {
                    JOptionPane.showMessageDialog(null, "Maximum withdrawal limit is PKR 50,000");
                    return;
                }
// INFORMATIVE COMMENT: Connect to database to verify ba
                Connections c1 = new Connections();
                if (c1.getConnection() == null) {
                    JOptionPane.showMessageDialog(null, "Database Connection Failed!\nPlease check MySQL server.");
                    return;
                }

                String balanceQuery = "SELECT SUM(CASE WHEN transaction_type = 'DEPOSIT' THEN amount ELSE -amount END) AS balance FROM bank_transactions WHERE mpin = ?";
                PreparedStatement ps = c1.getConnection().prepareStatement(balanceQuery);
                ps.setString(1, mpin);
                ResultSet rs = ps.executeQuery();

                int balance = 0;
                if(rs.next()) {
                    balance = rs.getInt("balance");
                }

                if(balance < withdrawalAmount){
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }
// INFORMATIVE COMMENT: Record withdrawal transaction in
                String insertQuery = "INSERT INTO bank_transactions (mpin, transaction_date, transaction_type, amount) VALUES (?, ?, ?, ?)";
                PreparedStatement ps2 = c1.getConnection().prepareStatement(insertQuery);
                ps2.setString(1, mpin);
                ps2.setTimestamp(2, new Timestamp(date.getTime()));
                ps2.setString(3, "WITHDRAWAL");
                ps2.setInt(4, withdrawalAmount);
                ps2.executeUpdate();

                JOptionPane.showMessageDialog(null, "PKR " + amount + " withdrawn successfully");

                setVisible(false);
                new Transactions(mpin).setVisible(true);

            } else if(ae.getSource() == b2){
                setVisible(false);
                new Transactions(mpin).setVisible(true);
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Transaction Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Withdrawal("").setVisible(true);
    }
}