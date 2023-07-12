import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;

import javax.swing.*;

public class ChangeBooking extends JFrame {
    User user;
    final private Font mainFont = new Font("Arial", Font.BOLD, 18);
    final private Font bigFont = new Font("Arial", Font.BOLD, 21);
    JTextField date;
    JTextField time;
    JTextField doctor;
    JTextField prescription;
    // the above are the fields that will be used to change the booking

    public void initialize(User user, int VisitID) throws ParseException {
        this.user = user;
        JLabel lbChangeBookingForm = new JLabel("Change Booking Form", SwingConstants.CENTER);
        lbChangeBookingForm.setFont(mainFont);

        JLabel lbDoctor = new JLabel("Please choose your doctor:");
        lbDoctor.setFont(mainFont);
        // the above is the label for the doctor field

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.add(lbChangeBookingForm);

        JPanel datePanel = new JPanel();
        FlowLayout dateLayout = new FlowLayout();
        datePanel.setLayout(dateLayout);

        // the above code is for the date panel

        JPanel timePanel = new JPanel();
        FlowLayout timeLayout = new FlowLayout();
        timePanel.setLayout(timeLayout);

        String[] years = getYears();
        String[] months = getMonths();
        String[] days = getDays();
        String[] hours = getHours();
        String[] mins = getMins();

        // the above code is for the time panel

        final JComboBox<String> yearList = new JComboBox<String>(years);
        yearList.setMaximumSize(yearList.getPreferredSize()); // added code
        yearList.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        yearList.setFont(bigFont);
        // cb.setVisible(true); // Not needed

        // the above code is for the year list

        final JComboBox<String> monthList = new JComboBox<String>(months);
        monthList.setMaximumSize(monthList.getPreferredSize()); // added code
        monthList.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        monthList.setFont(bigFont);
        // cb.setVisible(true); // Not needed

        // the above code is for the month list
        final JComboBox<String> daysList = new JComboBox<String>(days);
        daysList.setMaximumSize(daysList.getPreferredSize()); // added code
        daysList.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        daysList.setFont(bigFont);
        // cb.setVisible(true); // Not needed

        // the above code is for the day list
        final JComboBox<String> hoursList = new JComboBox<String>(hours);
        hoursList.setMaximumSize(hoursList.getPreferredSize()); // added code
        hoursList.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        hoursList.setFont(bigFont);
        // cb.setVisible(true); // Not needed
        // the above code is for the hour list

        final JComboBox<String> minsList = new JComboBox<String>(mins);
        minsList.setMaximumSize(minsList.getPreferredSize()); // added code
        minsList.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        minsList.setFont(bigFont);
        // cb.setVisible(true); // Not needed
        // the above code is for the minute list

        datePanel.add(daysList);
        datePanel.add(monthList);
        datePanel.add(yearList);

        timePanel.add(hoursList);
        timePanel.add(minsList);

        formPanel.add(datePanel);
        formPanel.add(timePanel);
        // the above code is for the date and time panel

        Database database = new Database();
        ArrayList<Doctor> doctors = database.getDoctors(user);
        String[] choices = new String[doctors.size()];
        for (int i = 0; i < doctors.size(); i++) {
            choices[i] = doctors.get(i).Name;
        }

        // the above code is for the doctor list and the choices

        formPanel.add(lbDoctor);
        final JComboBox<String> doctorsList = new JComboBox<String>(choices);
        doctorsList.setMaximumSize(doctorsList.getPreferredSize()); // added code
        doctorsList.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        // cb.setVisible(true); // Not needed
        formPanel.add(doctorsList);

        // the above code is for the doctor list

        JButton btnBook = new JButton("Change booking");
        btnBook.setFont(mainFont);
        btnBook.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Database database = new Database();
                Date visitDate;
                try {
                    visitDate = getDate(minsList, hoursList, daysList, yearList, monthList);
                    if (!database.checkBookingDate(visitDate)) {
                        String doctor = (String) doctorsList.getSelectedItem();
                        database.createNewBooking(visitDate, doctor, user);
                        ViewBookings viewBookings = new ViewBookings();
                        viewBookings.initialize(user);
                        Database.deleteBooking(VisitID);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(ChangeBooking.this,
                                "That date is in the past. Please try again!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // the above code is for the book button

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

        // the above code is for the cancel button

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnBook);
        buttonsPanel.add(btnCancel);

        // the above code is for the buttons panel
        /*************** Initialise the frame ***************/
        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Booking Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setMinimumSize(new Dimension(350, 450));
        // setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    // the above code is for the constructor

    private String[] getDays() {
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = "" + (i + 1);
        }
        return days;
    }

    // the above code is for the days list
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
    // the above code is for the months list

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

    private int getMonthFromString(String month) {
        String[] months = getMonths();
        for (int i = 0; i < 12; i++) {
            if (months[i].equals(month)) {
                return i + 1;
            }
        }
        return 0;
    }

    // the above code is for the mins list

    private Date getDate(JComboBox<String> minsList, JComboBox<String> hoursList, JComboBox<String> daysList,
            JComboBox<String> yearList, JComboBox<String> monthList) throws ParseException {

        String min = (String) minsList.getSelectedItem();
        String hrs = (String) hoursList.getSelectedItem();

        String day = (String) daysList.getSelectedItem();
        int month = getMonthFromString((String) monthList.getSelectedItem());
        String year = (String) yearList.getSelectedItem();

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

    // the above code is for the date
};
