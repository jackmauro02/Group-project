import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;

import javax.swing.*;

/**
 * This class is the JFrame of the "create a new booking" form.
 *
 * @author Vil.
 * @version 07/04/23
 */
public class BookingForm extends JFrame {

    User user;

    final private Font mainFont = new Font("Ariel", Font.BOLD, 18);
    final private Font bigFont = new Font("Ariel", Font.BOLD, 21);
    JTextField date;
    JTextField time;
    JTextField doctor;
    JTextField prescription;

    /**
     * The initializer of the JFrame (builds and displays the window).
     *
     * @param user User.
     */
    public void initialize(User user) throws ParseException {

        this.user = user;
        JLabel lbBookingForm = new JLabel("Booking Form", SwingConstants.CENTER);
        lbBookingForm.setFont(mainFont);

        JLabel lbDoctor = new JLabel("Please choose your doctor:");
        lbDoctor.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.add(lbBookingForm);

        JPanel datePanel = new JPanel();
        FlowLayout dateLayout = new FlowLayout();
        datePanel.setLayout(dateLayout);

        JPanel timePanel = new JPanel();
        FlowLayout timeLayout = new FlowLayout();
        timePanel.setLayout(timeLayout);

        String[] years = getYears();
        String[] months = getMonths();
        String[] days = getDays();
        String[] hours = getHours();
        String[] mins = getMins();

        final JComboBox<String> yearList = new JComboBox<String>(years);
        yearList.setMaximumSize(yearList.getPreferredSize());
        yearList.setAlignmentX(Component.CENTER_ALIGNMENT);
        yearList.setFont(bigFont);

        final JComboBox<String> monthList = new JComboBox<String>(months);
        monthList.setMaximumSize(monthList.getPreferredSize());
        monthList.setAlignmentX(Component.CENTER_ALIGNMENT);
        monthList.setFont(bigFont);

        final JComboBox<String> daysList = new JComboBox<String>(days);
        daysList.setMaximumSize(daysList.getPreferredSize());
        daysList.setAlignmentX(Component.CENTER_ALIGNMENT);
        daysList.setFont(bigFont);

        final JComboBox<String> hoursList = new JComboBox<String>(hours);
        hoursList.setMaximumSize(hoursList.getPreferredSize());
        hoursList.setAlignmentX(Component.CENTER_ALIGNMENT);
        hoursList.setFont(bigFont);

        final JComboBox<String> minsList = new JComboBox<String>(mins);
        minsList.setMaximumSize(minsList.getPreferredSize());
        minsList.setAlignmentX(Component.CENTER_ALIGNMENT);
        minsList.setFont(bigFont);

        datePanel.add(daysList);
        datePanel.add(monthList);
        datePanel.add(yearList);

        timePanel.add(hoursList);
        timePanel.add(minsList);

        formPanel.add(datePanel);
        formPanel.add(timePanel);

        Database database = new Database();
        ArrayList<Doctor> doctors = database.getDoctors(user);
        String[] choices = new String[doctors.size()];
        for (int i = 0; i < doctors.size(); i++) {
            choices[i] = doctors.get(i).Name;
        }

        formPanel.add(lbDoctor);
        final JComboBox<String> doctorsList = new JComboBox<String>(choices);
        doctorsList.setMaximumSize(doctorsList.getPreferredSize());
        doctorsList.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(doctorsList);

        JButton btnBook = new JButton("Book");
        btnBook.setFont(mainFont);
        btnBook.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Database database = new Database();
                Date visitDate;
                try {
                    visitDate = getDate(minsList, hoursList, daysList, yearList, monthList);
                    String doctor = (String) doctorsList.getSelectedItem();
                    if (!database.checkBookingDate(visitDate)) {
                        if (!database.checkVisitAvailability(visitDate, doctor, user)) {
                            database.createNewBooking(visitDate, doctor, user);
                            ViewBookings viewBookings = new ViewBookings();
                            viewBookings.initialize(user);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(BookingForm.this,
                                    "Sorry that booking has already been taken. Please select another time or try another doctor.",
                                    "Try again",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(BookingForm.this,
                                "That date is in the past. Please try again!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(mainFont);
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // that will close the application
                MainFrame mainFrame = new MainFrame();
                mainFrame.initialize(user);
                dispose();
            }

        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnBook);
        buttonsPanel.add(btnCancel);

        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Booking Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setMinimumSize(new Dimension(350, 450));
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private String[] getDays() {
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = "" + (i + 1);
        }
        return days;
    }

    private String[] getMonths() {
        String[] months = new String[12];
        months[0] = "Jan";
        months[1] = "Feb";
        months[2] = "Mar";
        months[3] = "Apr";
        months[4] = "May";
        months[5] = "Jun";
        months[6] = "Jul";
        months[7] = "Aug";
        months[8] = "Spt";
        months[9] = "Oct";
        months[10] = "Nov";
        months[11] = "Dec";
        return months;
    }

    private String[] getYears() {
        String[] years = new String[20];
        years[0] = "2023";
        years[1] = "2024";
        years[2] = "2025";
        years[3] = "2026";
        return years;
    }

    private String[] getHours() {
        String[] hours = new String[9];
        hours[0] = "09";
        hours[1] = "10";
        hours[2] = "11";
        hours[3] = "12";
        hours[4] = "13";
        hours[5] = "14";
        hours[6] = "15";
        hours[7] = "16";
        hours[8] = "17";
        return hours;
    }

    private String[] getMins() {
        String[] mins = new String[4];
        mins[0] = "00";
        mins[1] = "15";
        mins[2] = "30";
        mins[3] = "45";
        return mins;
    }

    /**
     * Returns the index of the month by name.
     *
     * @param month String the name of the month..
     * @return The date of type Date.
     */
    private int getMonthFromString(String month) {
        String[] months = getMonths();
        for (int i = 0; i < 12; i++) {
            if (months[i].equals(month)) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * This method builds a Date from the dropdown inputs
     *
     * @param minutes int[] [00,15,30,45].
     * @param hours   int[] [9..17] For opening hours
     * @param days    int[] [1..31] for days in a month.
     * @param months  int[] [1..12] for months in a year.
     * @param years   int[] [2023..2026].
     * @return The date of type Date.
     */
    private Date getDate(JComboBox<String> minutes, JComboBox<String> hours, JComboBox<String> days,
            JComboBox<String> years, JComboBox<String> months) throws ParseException {

        String min = (String) minutes.getSelectedItem();
        String hrs = (String) hours.getSelectedItem();

        String day = (String) days.getSelectedItem();
        int month = getMonthFromString((String) months.getSelectedItem());
        String year = (String) years.getSelectedItem();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm", Locale.ENGLISH);
        String newMonth = "";
        if (Integer.parseInt(day) <= 9) {
            day = "0" + day;
        }
        if (month <= 9) {
            newMonth = "0" + month;
        }
        String dateInString = day + "-" + newMonth + "-" + year + "T" + hrs + ":" + min + ":00";
        Date date = formatter.parse(dateInString);
        return date;
    }
};
