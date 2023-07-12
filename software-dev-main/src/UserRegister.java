import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UserRegister extends JFrame {
    final private Font mainFont = new Font("Ariel", Font.BOLD, 18);

    JTextField nameInput;
    JTextField phoneInput;
    JTextField emailInput;
    JTextField addressInput;
    JPasswordField passwordInput;
    JPasswordField passwordConfirmInput;

    // this code will be used to create a user object that will be used to store the
    // user's information for registration
    public void initialize() {
        JLabel registerFormLabel = new JLabel("Login Form", SwingConstants.CENTER);
        registerFormLabel.setFont(mainFont);
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(mainFont);
        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setFont(mainFont);
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(mainFont);
        JLabel addressLabel = new JLabel("Address");
        addressLabel.setFont(mainFont);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(mainFont);
        JLabel passwordConfirmLabel = new JLabel("Confirm Password");
        passwordConfirmLabel.setFont(mainFont);

        // some code to create the text fields and password fields
        nameInput = new JTextField();
        phoneInput = new JTextField();
        emailInput = new JTextField();
        addressInput = new JTextField();
        passwordInput = new JPasswordField();
        passwordConfirmInput = new JPasswordField();
        nameInput.setFont(mainFont);
        phoneInput.setFont(mainFont);
        emailInput.setFont(mainFont);
        addressInput.setFont(mainFont);
        passwordInput.setFont(mainFont);
        passwordConfirmInput.setFont(mainFont);

        // this code will be used to create a panel that will be used to store the user input for registration
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.add(registerFormLabel);
        formPanel.add(nameLabel);
        formPanel.add(nameInput);
        formPanel.add(phoneLabel);
        formPanel.add(phoneInput);
        formPanel.add(emailLabel);
        formPanel.add(emailInput);
        formPanel.add(addressLabel);
        formPanel.add(addressInput);
        formPanel.add(passwordLabel);
        formPanel.add(passwordInput);
        formPanel.add(passwordConfirmLabel);
        formPanel.add(passwordConfirmInput);

        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(mainFont);
        // Add button listeners here.
        btnRegister.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to submit data.

                String name = nameInput.getText();
                String email = emailInput.getText();
                String phone = phoneInput.getText();
                String address = addressInput.getText();
                String password = String.valueOf(passwordInput.getPassword());
                String passwordConfirm = String.valueOf(passwordConfirmInput.getPassword());

                if (!password.equals(passwordConfirm)) {
                    JOptionPane.showMessageDialog(UserRegister.this,
                            "Passwords do not match",
                            "Please try again",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(UserRegister.this,
                            "User registered successfully",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    nameInput.setText("");
                    phoneInput.setText("");
                    emailInput.setText("");
                    addressInput.setText("");
                    passwordInput.setText("");
                    passwordConfirmInput.setText("");
                    sendUserToDB(name, email, phone, address, password);
                    dispose();
                    LoginForm loginForm = new LoginForm();
                    loginForm.initialize();
                }
            }
        });
        // the above code will be used to create a button that will be used to submit the user's information for registration

        JButton btnBack = new JButton("Back");
        btnBack.setFont(mainFont);
        
        btnBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // that will close the application
                LoginForm LoginForm = new LoginForm();
                LoginForm.initialize();
                dispose();
            }

        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnBack);
        buttonsPanel.add(btnRegister);

        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setMinimumSize(new Dimension(350, 450));
        // setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        // SUBMIT button (goes to create booking page).
    }

    private User sendUserToDB(String name, String email, String phone, String address, String password) {
        Database database = new Database();
        User user = database.addUser(name, email, phone, address, password);
        // System.out.println(user);
        return user;
    }

}
