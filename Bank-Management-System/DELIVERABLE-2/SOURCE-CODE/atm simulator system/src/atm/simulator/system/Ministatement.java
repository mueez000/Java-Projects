package atm.simulator.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
/*
 LEGAL COMMENT: Handles retrieval of the latest bank transactions for users.
 */
public class Ministatement extends JFrame implements ActionListener {
// INFORMATIVE COMMENT: UI components for displaying transaction history.
    private JButton exitButton;
    private JLabel statementLabel, balanceLabel;
/*
     INFORMATIVE COMMENT: Constructor initializes the mini statement interface.
     */
    public Ministatement(String mpin) {
        super("BankIslami Transaction History");
        initializeUI();
        loadTransactionData(mpin);
        setSize(400, 600);  
        setLocation(20, 20); 
        setVisible(true);
    }

    private void initializeUI() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel bankNameLabel = new JLabel("Bank Islami");
        bankNameLabel.setBounds(150, 20, 100, 20);
        add(bankNameLabel);

        JPanel statementPanel = new JPanel();
        statementPanel.setLayout(new BoxLayout(statementPanel, BoxLayout.Y_AXIS));
        statementPanel.setBounds(20, 80, 360, 250);  
        statementPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(statementPanel);

        statementLabel = new JLabel();
        statementLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        statementLabel.setVerticalAlignment(SwingConstants.TOP);
        statementPanel.add(statementLabel);

        JScrollPane scrollPane = new JScrollPane(statementPanel);
        scrollPane.setBorder(null);
        scrollPane.setBounds(20, 80, 360, 250); 
        add(scrollPane);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBounds(20, 400, 360, 50); 
        bottomPanel.setBackground(Color.WHITE);
        add(bottomPanel);

        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        balanceLabel.setForeground(new Color(0, 102, 0));
        bottomPanel.add(balanceLabel, BorderLayout.WEST);

        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(0, 102, 0));
        exitButton.setForeground(Color.BLACK);
        exitButton.setFont(new Font("Roboto", Font.BOLD, 14));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(this);
        bottomPanel.add(exitButton, BorderLayout.EAST);
    }
/*
     INFORMATIVE COMMENT: Loads and displays the transaction history.
     */
    private void loadTransactionData(String mpin) {
        Connections conn = new Connections(); 
        ResultSet rs = null;
        try {
            // INFORMATIVE COMMENT: Retrieve card number and CNIC for sec
            String accountQuery = "SELECT card_number, cnic FROM SignupOne JOIN SignupTwo USING(form_no) JOIN SignupThree USING(form_no) WHERE mpin = ?";
            PreparedStatement ps = conn.getConnection().prepareStatement(accountQuery);
            ps.setString(1, mpin);
            rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder("<html>");
            if (rs.next()) {
                String cardNumber = maskCardNumber(rs.getString("cardno"));
                String cnic = maskCNIC(rs.getString("cnic"));
                sb.append("<b>Card Number:</b> ").append(cardNumber).append("<br>")
                  .append("<b>CNIC:</b> ").append(cnic).append("<br><br>");
            }
// INFORMATIVE COMMENT: Retrieve the last 10 transactions.
            String transactionQuery = "SELECT transaction_date, transaction_type, amount FROM bank_transactions WHERE mpin = ? ORDER BY transaction_date DESC LIMIT 10";
            ps = conn.getConnection().prepareStatement(transactionQuery);
            ps.setString(1, mpin);
            rs = ps.executeQuery();

            while (rs.next()) {
                String date = rs.getString("transaction_date");
                String mode = rs.getString("transaction_type");
                String amount = rs.getString("amount");
                sb.append(String.format("%s - %s: Rs %s<br>", date, mode, amount));
            }

            sb.append("</html>");
            statementLabel.setText(sb.toString());
// INFORMATIVE COMMENT: Retrieve and display the account balanc
            String balanceQuery = "SELECT SUM(CASE WHEN transaction_type = 'DEPOSIT' THEN amount ELSE -amount END) AS balance FROM bank_transactions WHERE mpin = ?";
            ps = conn.getConnection().prepareStatement(balanceQuery);
            ps.setString(1, mpin);
            rs = ps.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                balanceLabel.setText("Current Balance: Rs " + balance);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading statement: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.getConnection().close(); } catch (Exception ignored) {}
        }
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber != null && cardNumber.length() == 16) {
            return cardNumber.substring(0, 4) + " **** **** " + cardNumber.substring(12);
        }
        return "**** **** **** ****";
    }

    private String maskCNIC(String cnic) {
        if (cnic != null && cnic.length() == 13) {
            return cnic.substring(0, 5) + "-*******-" + cnic.substring(12);
        }
        return "*****-*******-*";
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }

    public static void main(String[] args) {
        new Ministatement("1234").setVisible(true);
    }
}
