import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class ForgotPasswordForm extends JFrame {

    final private Font mainFont = new Font("Ariel", Font.BOLD, 18);
    JTextField tfEmail;
    JTextField tfPhone;

    public void initialize() {
        /*************** Form Panel ***************/
        JLabel lbForgotPassword = new JLabel("Forgot Password", SwingConstants.CENTER);
        lbForgotPassword.setFont(mainFont);

        JLabel lbEmail = new JLabel("Email");
        lbEmail.setFont(mainFont);

        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbPhone = new JLabel("Phone");
        lbPhone.setFont(mainFont);

        tfPhone = new JTextField();
        tfPhone.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.add(lbForgotPassword);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbPhone);
        formPanel.add(tfPhone);

        /*************** Buttons Panel ***************/
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setFont(mainFont);
        btnSubmit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Submit form and send password reset link to email or phone
                /*
                 * JOptionPane.showMessageDialog(ForgotPasswordForm.this,
                 * "Password reset link has been sent to your email or phone number.",
                 * "Password reset link sent",
                 * JOptionPane.INFORMATION_MESSAGE);
                 */
                String email = tfEmail.getText().trim();
                String phone = tfPhone.getText().trim();
                String password = getForgottenPassword(email, phone);

                if (password != null) {
                    // If the user input is correct, show a success message and send the password
                    // reset link
                    JOptionPane.showMessageDialog(ForgotPasswordForm.this,
                            "Password reset link has been sent to your email or phone number.",
                            "Password reset link sent",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // If the user input is incorrect, show an error message
                    JOptionPane.showMessageDialog(ForgotPasswordForm.this,
                            "Invalid credentials. Please check your email and phone number and try again.",
                            "Invalid credentials",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        );

        JButton btnCancel = new JButton("go Back");
        btnCancel.setFont(mainFont);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the ForgotPasswordForm window
                LoginForm loginForm = new LoginForm();
                loginForm.initialize(); // Display the LoginPage window
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnSubmit);
        buttonsPanel.add(btnCancel);

        /*************** Initialise the frame ***************/
        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Forgot Password Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setMinimumSize(new Dimension(350, 450));
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private String getForgottenPassword(String email, String phone) {
        Database database = new Database();
        String password = database.getForgottenPassword(email, phone);
        return password;
    }

    public static void main(String[] args) {
        ForgotPasswordForm forgotPasswordForm = new ForgotPasswordForm();
        forgotPasswordForm.initialize();
        // Display the ForgotPasswordForm window
    }
}
