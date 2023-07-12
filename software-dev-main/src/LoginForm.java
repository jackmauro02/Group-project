import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginForm extends JFrame {
    final private Font mainFont = new Font("Ariel", Font.BOLD, 18);
    JTextField tfName;
    JPasswordField pfPassword;

    public void initialize() {
        JLabel lbLoginForm = new JLabel("Login Form", SwingConstants.CENTER);
        lbLoginForm.setFont(mainFont);

        JLabel lbName = new JLabel("Name");
        lbName.setFont(mainFont);

        tfName = new JTextField();
        tfName.setFont(mainFont);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.add(lbLoginForm);
        formPanel.add(lbName);
        formPanel.add(tfName);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(mainFont);
        btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();
                String password = String.valueOf(pfPassword.getPassword());

                User user = getAuthenticatedUser(name, password);

                if (user != null) {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.initialize(user);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Name or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        JButton btnForgotPassword = new JButton("Forgot Password?");
        btnForgotPassword.setFont(mainFont);
        btnForgotPassword.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Forgot Password form
                dispose();
                ForgotPasswordForm forgotPasswordForm = new ForgotPasswordForm();
                forgotPasswordForm.initialize();
            }

        });

        JButton btnCancel = new JButton("Register");
        btnCancel.setFont(mainFont);
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // that will close the application
                dispose();
                UserRegister UserRegister = new UserRegister();
                UserRegister.initialize();
            }

        });
        
        // this will open the register form

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnLogin);
        buttonsPanel.add(btnCancel);
        buttonsPanel.add(btnForgotPassword);

        /*************** Initialise the frame ***************/
        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setMinimumSize(new Dimension(350, 450));
        // setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /// This will connect to DB and check if username and password are correctly
    /// entered
    protected User getAuthenticatedUser(String name, String password) {
        Database database = new Database();
        User user = database.getUser(name, password);
        return user;
    }

    public static void main(String[] args) {

        LoginForm loginForm = new LoginForm();
        loginForm.initialize();
        // this opes the login form
    }
}