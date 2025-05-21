package atm.simulator.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
/*
 LEGAL COMMENT: Handles the process of MPIN change for BankIslami ATM users.
 */
public class Pinchange extends JFrame implements ActionListener {
// INFORMATIVE COMMENT: UI components for MPIN change interface.
    private JPasswordField newPinField, confirmPinField;
    private JButton changeButton, backButton;
    private String cardNumber;
    private static final Color ISLAMIC_GREEN = new Color(0, 102, 0);
/*
     INFORMATIVE COMMENT: Constructor initializes the MPIN change screen.
     */
    public Pinchange(String cardNumber) {
        this.cardNumber = cardNumber;
        createUI();
        setSize(960, 1080);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }

    private void createUI() {
        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        JLabel background = new JLabel(new ImageIcon(bgIcon.getImage().getScaledInstance(960, 1080, Image.SCALE_SMOOTH)));
        background.setBounds(0, 0, 960, 1080);
        add(background);

        setLayout(null);

        JLabel titleLabel = new JLabel("CHANGE MPIN");
        titleLabel.setFont(new Font("System", Font.BOLD, 16));
        titleLabel.setForeground(ISLAMIC_GREEN);
        titleLabel.setBounds(280, 350, 800, 35);
        background.add(titleLabel);

        JLabel newPinLabel = new JLabel("New MPIN:");
        newPinLabel.setFont(new Font("System", Font.BOLD, 16));
        newPinLabel.setForeground(ISLAMIC_GREEN);
        newPinLabel.setBounds(180, 390, 150, 35);
        background.add(newPinLabel);

        newPinField = new JPasswordField();
        newPinField.setFont(new Font("Raleway", Font.BOLD, 25));
        newPinField.setBounds(350, 390, 180, 25);
        background.add(newPinField);

        JLabel confirmPinLabel = new JLabel("Confirm MPIN:");
        confirmPinLabel.setFont(new Font("System", Font.BOLD, 16));
        confirmPinLabel.setForeground(ISLAMIC_GREEN);
        confirmPinLabel.setBounds(180, 440, 200, 35);
        background.add(confirmPinLabel);

        confirmPinField = new JPasswordField();
        confirmPinField.setFont(new Font("Raleway", Font.BOLD, 25));
        confirmPinField.setBounds(350, 440, 180, 25);
        background.add(confirmPinField);

        changeButton = new JButton("CHANGE");
        backButton = new JButton("BACK");

        styleButton(changeButton);
        styleButton(backButton);

        changeButton.setBounds(380, 570, 150, 35);
        background.add(changeButton);

        backButton.setBounds(380, 615, 150, 35);
        background.add(backButton);

        changeButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(Color.WHITE);
        btn.setForeground(ISLAMIC_GREEN);
        btn.setFont(new Font("Roboto", Font.BOLD, 14));
    }
/*
     INFORMATIVE COMMENT: Handles MPIN validation and update in database.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backButton) {
            setVisible(false);
            new Transactions(cardNumber).setVisible(true);
            return;
        }

        try {
            String newPin = new String(newPinField.getPassword()).trim();
            String confirmPin = new String(confirmPinField.getPassword()).trim();
// LEGAL COMMENT: Ensure the entered MPINs match and meet 
            if (newPin.isEmpty() || confirmPin.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill both MPIN fields.");
                return;
            }

            if (!newPin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(null, "Entered MPINs do not match.");
                return;
            }

            if (newPin.length() != 4 || !newPin.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "MPIN must be a 4-digit number.");
                return;
            }
// INFORMATIVE COMMENT: Verify the card number in the database.
            Connections conn = new Connections();
            Connection connection = conn.getConnection();

            String verifyQuery = "SELECT * FROM Login WHERE card_number = ?";
            PreparedStatement verifyStmt = connection.prepareStatement(verifyQuery);
            verifyStmt.setString(1, cardNumber);
            ResultSet rs = verifyStmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Invalid card number. Cannot update MPIN.");
                return;
            }

            String oldMpin = rs.getString("mpin");
// INFORMATIVE COMMENT: Update MPIN in different tables.
            String updateLogin = "UPDATE Login SET mpin = ? WHERE card_number = ?";
            PreparedStatement ps1 = connection.prepareStatement(updateLogin);
            ps1.setString(1, newPin);
            ps1.setString(2, cardNumber);
            ps1.executeUpdate();

            String updateSignup = "UPDATE SignupThree SET mpin = ? WHERE card_number = ?";
            PreparedStatement ps2 = connection.prepareStatement(updateSignup);
            ps2.setString(1, newPin);
            ps2.setString(2, cardNumber);
            ps2.executeUpdate();

            String updateTrans = "UPDATE bank_transactions SET mpin = ? WHERE mpin = ?";
            PreparedStatement ps3 = connection.prepareStatement(updateTrans);
            ps3.setString(1, newPin);
            ps3.setString(2, oldMpin);
            ps3.executeUpdate();

            JOptionPane.showMessageDialog(null, "MPIN changed successfully.");
            setVisible(false);
            new Transactions(cardNumber).setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error changing MPIN: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Pinchange("");
    }
}
