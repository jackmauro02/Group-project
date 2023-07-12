import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PrescriptionForm extends JFrame {
    User user;

    public void initialize(User user, Prescription prescription, ArrayList<Visit> visits, int VisitID) {
        this.user = user;
        final Font mainFont = new Font("Ariel", Font.BOLD, 18);
        JLabel lbLoginForm = new JLabel("Prescription Form", SwingConstants.CENTER);
        lbLoginForm.setFont(mainFont);

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        infoPanel.add(new JLabel("Prescription ID"));
        infoPanel.add(new JLabel("" + prescription.PrescriptionID));
        infoPanel.add(new JLabel("Medicine"));
        infoPanel.add(new JLabel(prescription.Medicine));
        infoPanel.add(new JLabel("Dosage"));
        infoPanel.add(new JLabel(prescription.Dosage));
        infoPanel.add(new JLabel("Diagnosis"));
        infoPanel.add(new JLabel(prescription.Diagnosis));
        infoPanel.add(new JLabel("Duration"));
        infoPanel.add(new JLabel("" + prescription.Duration));

        Component[] labels = infoPanel.getComponents();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Arial", Font.BOLD, 18));
        }

        add(infoPanel, BorderLayout.CENTER);

        // Sign out button
        JButton btnSignOut = new JButton("Go Back");
        btnSignOut.setFont(new Font("Arial", Font.BOLD, 18));
        btnSignOut.addActionListener(e -> {
            ViewBooking viewBooking = new ViewBooking();
            viewBooking.initialize(VisitID, visits, user);
            dispose();
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnSignOut);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Dashboard");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    // the above code is used to display the prescription form

    public static void main(String[] args) {
        PrescriptionForm form = new PrescriptionForm();
        form.setVisible(true);
    }
}