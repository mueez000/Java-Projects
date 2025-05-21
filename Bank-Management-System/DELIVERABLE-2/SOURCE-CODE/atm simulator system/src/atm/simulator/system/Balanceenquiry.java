package atm.simulator.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
/*
 LEGAL COMMENT: Handles balance inquiry for BankIslami ATM users.
 */
class Balanceenquiry extends JFrame implements ActionListener {
// INFORMATIVE COMMENT: UI components for displaying account balance.
    JButton backButton;
    JLabel balanceLabel;
    String mpin;
/*
     INFORMATIVE COMMENT: Constructor initializing balance inquiry screen.
     */
    Balanceenquiry(String mpin) {
        this.mpin = mpin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 960, 1080);
        add(background);

        balanceLabel = new JLabel();
        balanceLabel.setForeground(new Color(0, 102, 0)); 
        balanceLabel.setFont(new Font("Roboto", Font.BOLD, 18));
        balanceLabel.setBounds(175, 450, 500, 35); 
        background.add(balanceLabel);

        backButton = new JButton("BACK");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(0, 102, 0));
        backButton.setFont(new Font("Roboto", Font.BOLD, 14));
        backButton.setBounds(270, 550, 180, 35); 
        background.add(backButton);

        displayBalance();
        backButton.addActionListener(this);

        setSize(960, 1080);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }
/*
     INFORMATIVE COMMENT: Retrieves and displays the current account balance.
     */
    private void displayBalance() {
        try {
            Connections conn = new Connections();
            Connection connection = conn.getConnection();
// LEGAL COMMENT: Query retrieves sum of deposits and withdraw
            String query = "SELECT SUM(CASE WHEN transaction_type = 'DEPOSIT' THEN amount ELSE -amount END) AS balance FROM bank_transactions WHERE mpin = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, mpin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                balanceLabel.setText("Current Account Balance: PKR " + String.format("%,.2f", balance));
            } else {
                balanceLabel.setText("No transactions found.");
            }

            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving balance: " + e.getMessage());
            e.printStackTrace();
        }
    }
/*
     INFORMATIVE COMMENT: Handles back button action to return to transactions screen.
     */
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(mpin).setVisible(true);
    }

    public static void main(String[] args) {
        new Balanceenquiry("").setVisible(true);
    }
}
