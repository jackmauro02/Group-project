import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is the JFrame of the "view a booking" form.
 *
 * @author Vil.
 * @version 07/04/23
 */

public class ViewBookings extends JFrame {
    private User user;

    /**
     * The initializer of the the View Bookings JFrame.
     *
     * @param user User.
     */
    public void initialize(User user) {
        this.user = user;
        Database database = new Database();
        ArrayList<Visit> visits = database.getPatientsVisits(user);
        Collections.reverse(visits);

        JPanel BookingPanel = new JPanel();
        BookingPanel.add(new JLabel("Bookings Page"));
        BookingPanel.add(new JLabel(""));
        BookingPanel.setLayout(new GridLayout(0, 2, 5, 5));
        BookingPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        JButton[] buttons = new JButton[visits.size()];
        for (int i = 0; i < visits.size(); i++) {
            int index = i;
            buttons[index] = new JButton(
                    "#" + visits.get(i).VisitID + " " + visits.get(i).Date + " " + visits.get(i).Time);
            buttons[index].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    ViewBooking viewBooking = new ViewBooking();
                    viewBooking.initialize(visits.get(index).VisitID, visits, user);
                }
            });
        }
        for (int i = 0; i < visits.size(); i++) {
            BookingPanel.add(buttons[i]);
        }

        Component[] labels = BookingPanel.getComponents();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Arial", Font.BOLD, 18));
        }

        add(BookingPanel, BorderLayout.CENTER);

        JButton btnBack = new JButton("Back To Account");
        btnBack.setFont(new Font("Arial", Font.BOLD, 18));
        btnBack.addActionListener(e -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.initialize(this.user);
            dispose();
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnBack);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Dashboard");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void main(String[] args) {
        ViewBookings form = new ViewBookings();
        form.initialize(this.user);
        form.setVisible(true);
    }
}