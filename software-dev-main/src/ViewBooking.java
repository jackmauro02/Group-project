import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public class ViewBooking extends JFrame {
    User user = new User();

    public void initialize(int VisitID, ArrayList<Visit> visits, User user) {
        this.user = user;
        final Font mainFont = new Font("Ariel", Font.BOLD, 18);
        Database database = new Database();
        ArrayList<Doctor> doctors = database.getDoctors(user);
        String doctorsName = getDoctorsName(VisitID, visits, doctors);
        Visit visit = getVisit(visits, VisitID);
        Prescription prescription = fetchPrescription(visit.Prescription, user);

        JLabel lbMainFrame = new JLabel("Booking Details", SwingConstants.CENTER);

        lbMainFrame.setFont(mainFont);
        /*************** Info Panel ***************/
        // after Login it will take them to this Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        infoPanel.add(new JLabel("Visit ID"));
        infoPanel.add(new JLabel("" + visit.VisitID));
        infoPanel.add(new JLabel("Date"));
        infoPanel.add(new JLabel(visit.Date));
        infoPanel.add(new JLabel("Time"));
        infoPanel.add(new JLabel(visit.Time));
        infoPanel.add(new JLabel("Doctor"));
        infoPanel.add(new JLabel(doctorsName));
        infoPanel.add(new JLabel("Prescription"));
        infoPanel.add(new JLabel("" + prescription.Medicine));

        Component[] labels = infoPanel.getComponents();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Ariel", Font.BOLD, 18));
        }

        add(infoPanel, BorderLayout.NORTH);

        // take them to LoginForm Panel
        JButton btnSignOut = new JButton("Back To Bookings");
        btnSignOut.setFont(new Font("Ariel", Font.BOLD, 18));
        btnSignOut.addActionListener(e -> {
            dispose();
            ViewBookings booking = new ViewBookings();
            booking.initialize(this.user);
        });

        // Create view doctors button
        JButton btnViewDoctors = new JButton("Change Doctor");
        btnViewDoctors.setFont(new Font("Ariel", Font.BOLD, 18));
        btnViewDoctors.addActionListener(e -> {
            dispose();
            ViewDoctors viewDoctors = new ViewDoctors();
            viewDoctors.initialize(user);
        });

        // // Take them to Prescriptions
        JButton btnPrescription = new JButton("View Prescription");
        btnPrescription.setFont(new Font("Arial", Font.BOLD, 18));
        btnPrescription.addActionListener(e -> {
            dispose();
            PrescriptionForm prescriptionForm = new PrescriptionForm();
            prescriptionForm.initialize(user, prescription, visits, VisitID);
        });

        // Take them to Change Booking
        JButton btnChangeBooking = new JButton("Change Booking");
        btnChangeBooking.setFont(new Font("Arial", Font.BOLD, 18));
        btnChangeBooking.addActionListener(e -> {
            dispose();
            ChangeBooking changeBooking = new ChangeBooking();
            try {
                changeBooking.initialize(this.user, VisitID);
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        // Take them to Delete Booking
        JButton btnDeleteBooking = new JButton("Delete Booking");
        btnDeleteBooking.setFont(new Font("Arial", Font.BOLD, 18));
        btnDeleteBooking.addActionListener(e -> {

            // String sqlQuery = "DELETE FROM bookings WHERE VisitID = " + VisitID;
            Database.deleteBooking(VisitID);
            ViewBookings booking = new ViewBookings();
            booking.initialize(this.user);
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        // buttonsPanel.add(btnForgotPassword);
        buttonsPanel.add(btnChangeBooking);
        buttonsPanel.add(btnDeleteBooking);
        buttonsPanel.add(btnViewDoctors);
        buttonsPanel.add(btnPrescription);
        buttonsPanel.add(btnSignOut);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Dashboard");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Visit getVisit(ArrayList<Visit> visits, int VisitID) {
        Visit visit = new Visit();
        for (int i = 0; i < visits.size(); i++) {
            if (visits.get(i).VisitID == VisitID) {
                visit.VisitID = visits.get(i).VisitID;
                visit.Date = visits.get(i).Date;
                visit.Time = visits.get(i).Time;
                visit.Doctor = visits.get(i).Doctor;
                visit.Patient = visits.get(i).Patient;
                visit.Prescription = visits.get(i).Prescription;
            }
        }
        return visit;
    }

    private String getDoctorsName(int VisitID, ArrayList<Visit> visits, ArrayList<Doctor> doctors) {
        int index = 0;
        for (int i = 0; i < visits.size(); i++) {
            if (visits.get(i).VisitID == VisitID) {
                index = i;
            }
        }
        String doctorsName = "";
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).DoctorID == visits.get(index).Doctor) {
                doctorsName = doctors.get(i).Name;
            }
        }
        return doctorsName;
    }
    // the above code gets the doctors name from the visitID

    private Prescription fetchPrescription(int prescriptionID, User user) {
        Database database = new Database();
        Prescription prescription = database.getPrescription(prescriptionID, user);
        return prescription;
    }
}
