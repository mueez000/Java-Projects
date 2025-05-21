package atm.simulator.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;
/*
 LEGAL COMMENT: Handles fast cash withdrawals for BankIslami ATM users.
 */
public class Fastcash extends JFrame implements ActionListener {
// INFORMATIVE COMMENT: UI components for fast cash interface.
    JLabel l1;
    JButton b1, b2, b3, b4, b5, b6; 
    String pin;
/*
     INFORMATIVE COMMENT: Constructor initializing fast cash withdrawal options.
     */
    public Fastcash(String pin) {
        this.pin = pin;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 960, 1080);
        add(l3);

        l1 = new JLabel("SELECT WITHDRAWL AMOUNT");
        l1.setForeground(new Color(0, 102, 0)); 
        l1.setFont(new Font("Roboto", Font.BOLD, 16));

        b1 = new JButton("PKR 1000");
        b2 = new JButton("PKR 5000");
        b3 = new JButton("PKR 10000");
        b4 = new JButton("PKR 15000");
        b5 = new JButton("PKR 25000");
        b6 = new JButton("BACK");

        Color btnColor = new Color(0, 102, 0);
        for (JButton btn : new JButton[]{b1, b2, b3, b4, b5, b6}) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(btnColor);
            btn.setFont(new Font("Roboto", Font.BOLD, 14));
        }

        setLayout(null);

        l1.setBounds(235, 400, 700, 35);
        l3.add(l1);

        b1.setBounds(170, 450, 150, 35);
        l3.add(b1);

        b2.setBounds(390, 450, 150, 35);
        l3.add(b2);

        b3.setBounds(170, 500, 150, 35);
        l3.add(b3);

        b4.setBounds(390, 500, 150, 35);
        l3.add(b4);

        b5.setBounds(170, 550, 150, 35);
        l3.add(b5);

        b6.setBounds(390, 550, 150, 35);
        l3.add(b6);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);

        setSize(960, 1080);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }
/*
     INFORMATIVE COMMENT: Handles fast cash transaction processing.
     */
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == b6) {
                dispose();
                new Transactions(pin).setVisible(true);
                return;
            }

            String amountText = ((JButton) ae.getSource()).getText().replace("PKR ", "");
            int amount = Integer.parseInt(amountText.replace(",", ""));
 // INFORMATIVE COMMENT: Connect to database for transaction
            Connections conn = new Connections();
            Connection dbConn = conn.getConnection();
            if (dbConn == null) {
                JOptionPane.showMessageDialog(null, "Database Connection Failed!\nPlease check MySQL server.");
                return;
            }
// LEGAL COMMENT: Verify if the user has sufficient balance be
            String balanceQuery = "SELECT SUM(CASE WHEN transaction_type = 'DEPOSIT' THEN amount ELSE -amount END) AS balance " +
                                  "FROM bank_transactions WHERE mpin = ?";
            PreparedStatement ps = dbConn.prepareStatement(balanceQuery);
            ps.setString(1, pin);
            ResultSet rs = ps.executeQuery();

            int balance = 0;
            if (rs.next()) {
                balance = rs.getInt("balance");
            }
            rs.close();
            ps.close();

            if (balance < amount) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance");
                dbConn.close();
                return;
            }
// INFORMATIVE COMMENT: Record withdrawal transaction.
            String insertQuery = "INSERT INTO bank_transactions (mpin, transaction_date, transaction_type, amount) " +
                                 "VALUES (?, ?, ?, ?)";
            PreparedStatement ps2 = dbConn.prepareStatement(insertQuery);
            ps2.setString(1, pin);
            ps2.setTimestamp(2, new Timestamp(new Date().getTime()));
            ps2.setString(3, "WITHDRAWAL");
            ps2.setInt(4, amount);
            ps2.executeUpdate();
            ps2.close();

            JOptionPane.showMessageDialog(null, "PKR " + String.format("%,d", amount) + " withdrawn successfully");
            dbConn.close();
            dispose();
            new Transactions(pin).setVisible(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount format");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Transaction failed: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Fastcash("").setVisible(true);
    }
}
