import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class MainFrame extends JFrame {
    final private Font mainFont = new Font("Ariel", Font.BOLD, 18);
    private Timer inactivityTimer;
    private User user;

    public void initialize(User user) {
        this.user = user;
        JLabel lbMainFrame = new JLabel("User Details", SwingConstants.CENTER);

        lbMainFrame.setFont(mainFont);
        /*************** Info Panel ***************/
        // after Login it will take them to this Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        infoPanel.add(new JLabel("Name"));
        infoPanel.add(new JLabel(this.user.name));
        infoPanel.add(new JLabel("Email"));
        infoPanel.add(new JLabel(this.user.email));
        infoPanel.add(new JLabel("Phone"));
        infoPanel.add(new JLabel(this.user.phone));
        infoPanel.add(new JLabel("Address"));
        infoPanel.add(new JLabel(this.user.address));

        Component[] labels = infoPanel.getComponents();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Ariel", Font.BOLD, 18));
        }

        add(infoPanel, BorderLayout.NORTH);

        // take them to LoginForm Panel
        JButton btnSignOut = new JButton("Sign out");
        btnSignOut.setFont(new Font("Ariel", Font.BOLD, 18));
        btnSignOut.addActionListener(e -> {
            this.user = null;
            dispose();
            LoginForm loginForm = new LoginForm();
            loginForm.initialize();
        });

        // Create view doctors button
        JButton btnViewDoctors = new JButton("View Doctors");
        btnViewDoctors.setFont(new Font("Ariel", Font.BOLD, 18));
        btnViewDoctors.addActionListener(e -> {
            dispose();
            ViewDoctors viewDoctors = new ViewDoctors();
            viewDoctors.initialize(this.user);
        });

        // // Take them to Booking
        JButton btnBooking = new JButton("View Bookings");
        btnBooking.setFont(new Font("Arial", Font.BOLD, 18));
        btnBooking.addActionListener(e -> {
            dispose();
            ViewBookings booking = new ViewBookings();
            booking.initialize(this.user);
        });

        // // Take them to Booking
        JButton btnBookingForm = new JButton("Make A Booking");
        btnBookingForm.setFont(new Font("Arial", Font.BOLD, 18));
        btnBookingForm.addActionListener(e -> {
            dispose();
            BookingForm bookingForm = new BookingForm();
            try {
                bookingForm.initialize(this.user);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        // buttonsPanel.add(btnForgotPassword);
        buttonsPanel.add(btnViewDoctors);
        buttonsPanel.add(btnBookingForm);
        buttonsPanel.add(btnBooking);
        buttonsPanel.add(btnSignOut);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Dashboard");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setVisible(true);

        // Start the inactivity timer
        inactivityTimer = new Timer(120000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sign out the user after 2 minutes of inactivity
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.initialize();
            }
        });
        // inactivityTimer.start();

        // Stop the inactivity timer when the user interacts with the frame
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inactivityTimer.restart();
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inactivityTimer.restart();
            }
        });
    }
}
