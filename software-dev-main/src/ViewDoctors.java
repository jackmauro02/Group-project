import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ViewDoctors extends JFrame {
    User user;
    Database doctors = new Database();

    public void initialize(User user) {
        this.user = user;
        JPanel doctorPanel = new JPanel();

        // Create an array of image paths
        ArrayList<Doctor> doctor = doctors.getDoctors(user);
        String[] imagePaths = new String[doctor.size()];
        for (int j = 0; j < doctor.size(); j++) {
            Doctor photo = doctor.get(j);
            imagePaths[j] = photo.Photo;
        }

        // Create a new GridLayout with the specified number of rows and columns
        int imagesPerRow = 3;
        GridLayout gridLayout = new GridLayout((int) Math.ceil((double) imagePaths.length / imagesPerRow),
                imagesPerRow);
        doctorPanel.setLayout(gridLayout);

        // Loop through the image paths and create a new JLabel with each image
        for (int i = 0; i < imagePaths.length; i++) {

            // Create a new JPanel with a BorderLayout to hold the image and button
            JPanel imagePanel = new JPanel(new BorderLayout());

            // Loop through the image paths and create a new JLabel with each image
            ImageIcon image = new ImageIcon(imagePaths[i]);
            JLabel imageLabel = new JLabel(image);

            // Add the image label to the center of the image panel
            imagePanel.add(imageLabel, BorderLayout.CENTER);

            // Create a new JButton with the label that contains the name of doctor
            // accordingly
            Doctor details = doctor.get(i);
            JButton button = new JButton(details.Name);
            button.setToolTipText("Click for more details.");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame("Details of the doctor");
                    JPanel toppanel = new JPanel();
                    JPanel bottompanel = new JPanel();
                    JLabel label = new JLabel("<html>Doctor ID: " + details.DoctorID + "<br>Doctor Name: "
                            + details.Name + "<br>Doctor Email: " + details.Email + "<br>Doctor Phone: " + details.Phone
                            + "<br>Doctor Description:<br>" + details.Description + "</html>");
                    label.setPreferredSize(new Dimension(300, 200));
                    JButton back = new JButton("Back");
                    back.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            frame.dispose();
                        }
                    });
                    toppanel.add(label, BorderLayout.EAST);
                    toppanel.add(imageLabel, BorderLayout.WEST);
                    bottompanel.add(back, BorderLayout.CENTER);
                    frame.add(toppanel, BorderLayout.NORTH);
                    frame.add(bottompanel, BorderLayout.SOUTH);
                    frame.pack();
                    frame.setVisible(true);
                }
            });

            // Add the button to the bottom of the image panel
            imagePanel.add(button, BorderLayout.SOUTH);

            // Add the image panel to the doctor panel
            doctorPanel.add(imagePanel);
        }

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(buttonsPanel, BorderLayout.SOUTH);
        JButton okButton = new JButton("OK");
        buttonsPanel.add(okButton); // example button added to the panel

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = new MainFrame();
                mainFrame.initialize(user);
                dispose();
            }
        });

        add(doctorPanel);
        setTitle("Dashboard");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void main(String[] args) {
        ViewDoctors form = new ViewDoctors();
        form.initialize(user);
        form.setVisible(true);
    }

}
