package atm.simulator.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;
/*
 LEGAL COMMENT: Handles deposit transactions for BankIslami ATM users.
 */
public class Deposit extends JFrame implements ActionListener {
// **INFORMATIVE COMMENT**: UI components for deposit interface.
    private JTextField amountField;
    private JButton depositButton, backButton;
    private String mpin;
/*
     INFORMATIVE COMMENT: Constructor initializing deposit transaction options.
     */
    Deposit(String mpin){
        this.mpin = mpin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 960, 1080);
        add(background);

        Color islamicGreen = new Color(0, 102, 0);

        JLabel titleLabel = new JLabel("ENTER DEPOSIT AMOUNT (PKR)");
        titleLabel.setForeground(islamicGreen);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 18));

        amountField = new JTextField();
        amountField.setFont(new Font("Roboto", Font.BOLD, 20));
        amountField.setHorizontalAlignment(JTextField.CENTER);

        depositButton = createStyledButton("CONFIRM DEPOSIT", islamicGreen);
        backButton = createStyledButton("BACK", islamicGreen);

        titleLabel.setBounds(200, 350, 400, 30);
        background.add(titleLabel);

        amountField.setBounds(220, 400, 300, 40);
        background.add(amountField);

        depositButton.setBounds(270, 500, 200, 40);
        background.add(depositButton);

        backButton.setBounds(270, 550, 200, 40);
        background.add(backButton);

        depositButton.addActionListener(this);
        backButton.addActionListener(this);

        setSize(960, 1080);
        setUndecorated(true);
        setLocationRelativeTo(null);
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
     INFORMATIVE COMMENT: Handles deposit validation and processing.
     */
    public void actionPerformed(ActionEvent ae){
        try{        
            String amount = amountField.getText().trim();

            if (ae.getSource() == depositButton) {
                // LEGAL COMMENT: Ensure deposit amount is valid.
                if (amount.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter deposit amount");
                    return;
                }

                if (!amount.matches("^\\d+(\\.\\d{1,2})?$")) {
                    JOptionPane.showMessageDialog(null, "Invalid amount format! Use numbers only.");
                    return;
                }

                double depositAmount = Double.parseDouble(amount);

                if (depositAmount > 500000) {
                    JOptionPane.showMessageDialog(null, "Maximum deposit limit is PKR 500,000");
                    return;
                }
// INFORMATIVE COMMENT: Connect to database for transacti
                Connections conn = new Connections();
                if (conn.getConnection() == null) {
                    JOptionPane.showMessageDialog(null, "Database Connection Failed!\nPlease check MySQL server.");
                    return;
                }
// INFORMATIVE COMMENT: Record deposit transaction in the 
                String query = "INSERT INTO bank_transactions (mpin, transaction_date, transaction_type, amount) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.getConnection().prepareStatement(query);

                ps.setString(1, mpin.trim()); 
                ps.setTimestamp(2, new Timestamp(new Date().getTime()));
                ps.setString(3, "DEPOSIT");
                ps.setDouble(4, depositAmount);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "PKR " + amount + " deposited successfully");
                    setVisible(false);
                    new Transactions(mpin).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Transaction Failed! No rows affected.");
                }

            } else if (ae.getSource() == backButton) {
                setVisible(false);
                new Transactions(mpin).setVisible(true);
            }
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

    public static void main(String[] args){
        new Deposit("").setVisible(true);
    }
}