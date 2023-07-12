import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * This is the API for our application.
 *
 * @author Ozzy, Jack, Keith, Thomas, Vil.
 * @version 07/04/23
 */
public class Database {
    final static String DB_URL = "jdbc:mysql://localhost/comp5590_users?";
    final static String USERNAME = "root";
    final static String PASSWORD = ""; // Use your own info

    /**
     * This method gets a user
     *
     * @param name     String containing user name.
     * @param password String containing user password.
     * @return The found user.
     */
    public User getUser(String name, String password) {
        User user = null;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users WHERE name=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.id = resultSet.getInt("id");
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }

            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
        }
        logUsage("Signed in as " + name, user);
        return user;
    }

    /**
     * Get prescription - get's the users prescription.
     *
     * @param prescriptionID Integer - The ID of the prescription we're looking up.
     * @param user           User - whose prescription we're looking for.
     * @return the found prescription.
     */
    public Prescription getPrescription(int prescriptionID, User user) {
        Prescription prescription = new Prescription();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            String sql = "SELECT * FROM Prescription WHERE PrescriptionID=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, prescriptionID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                prescription.PrescriptionID = resultSet.getInt("PrescriptionID");
                prescription.Medicine = resultSet.getString("Medicine");
                prescription.Dosage = resultSet.getString("Dosage");
                prescription.Diagnosis = resultSet.getString("Diagnosis");
                prescription.Duration = resultSet.getString("Duration");
            }

            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
        }
        logUsage("Looked at prescription", user);
        return prescription;
    }

    /**
     * Get Dortors - This method gets all of the doctores who are registered on the
     * system.
     *
     * @param user User - user is used to log usage.
     * @return ArrayList of doctors.
     */
    public ArrayList<Doctor> getDoctors(User user) {
        ArrayList<Doctor> doctors = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM DoctorDetails";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.DoctorID = resultSet.getInt("DoctorID");
                doctor.Photo = resultSet.getString("Photo");
                doctor.Name = resultSet.getString("Name");
                doctor.Email = resultSet.getString("Email");
                doctor.Phone = resultSet.getString("Phone");
                doctor.Description = resultSet.getString("Description");
                doctors.add(doctor);
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
            e.printStackTrace();
        }
        logUsage("Looked at doctors", user);
        return doctors;
    }

    /**
     * Get prescription - get's the users prescription.
     *
     * @param name     String - input from "name" field.
     * @param email    String - input from "email" fieldString.
     * @param phone    String - input from "phone" field.
     * @param address  String - input from "address" field.
     * @param password String - input from "password" field.
     * @return The newly created user.
     */
    public User addUser(String name, String email, String phone, String address, String password) {
        User user = null;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "INSERT INTO users (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE name=VALUES(name), phone=VALUES(phone)," +
                    "address=VALUES(address), password=VALUES(password)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                user = new User();
                user.name = name;
                user.email = email;
                user.phone = phone;
                user.address = address;
                user.password = password;
            }

            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Create a new booking.
     *
     * @param visitDate  Date - The visit date input by the user.
     * @param doctorName String - The name of the doctor.
     * @param user       User - input from "phone" field.
     */
    public void createNewBooking(Date visitDate, String doctorName, User user) {
        Format dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Format timeFormatter = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormatter.format(visitDate);
        String time = timeFormatter.format(visitDate);
        int doctor = findDoctorIndex(doctorName, user);
        int patient = user.id;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO visit (date, time, doctor, patient, prescription) VALUES (?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE date=VALUES(date), time=VALUES(time)," +
                    "doctor=VALUES(doctor), patient=VALUES(patient), prescription=VALUES(prescription)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, time);
            preparedStatement.setInt(3, doctor);
            preparedStatement.setInt(4, patient);
            preparedStatement.setString(5, null);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Visit visit = new Visit();
                visit.Date = date;
                visit.Time = time;
                visit.Doctor = doctor;
                visit.Patient = patient;
            }
            preparedStatement.close();
            conn.close();
            sendMessageToUser("Congratulations your booking has been created. " +
                    "Please login to your account to view or change your booking.");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Get forgotten password.
     *
     * @param email String - The email of the user of the account with the lost
     *              password.
     * @param phone String - The users phone number.
     * @return Returns the users password.
     */
    public String getForgottenPassword(String email, String phone) {

        String password = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {

            String sql = "SELECT password FROM users WHERE email=? AND phone=?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, phone);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                password = resultSet.getString("password");
            } else {
                System.out.println("Invalid credentials");
            }

            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e);
            e.printStackTrace();
        }
        User foundUser = getUserFromEmail(email);
        logUsage("Forgotten password", foundUser);
        return password;
    }

    /**
     * Get user from provided email - used internally on this page.
     *
     * @param email String - The email of the user of the account with the lost.
     * @return The User who uses that email addres.
     */
    private User getUserFromEmail(String email) {
        User user = new User();
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT id FROM users WHERE email=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user.id = resultSet.getInt("id");
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            } else {
                System.out.println("Invalid credentials");
            }
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e);
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Get visits/bookings for the provided user.
     *
     * @param user User we're doing the lookup for.
     * @return An arrayList of users.
     */
    public ArrayList<Visit> getPatientsVisits(User user) {
        ArrayList<Visit> visits = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...
            String sql = "SELECT * FROM Visit WHERE Patient=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user.id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Visit visit = new Visit();
                visit.VisitID = resultSet.getInt("VisitID");
                visit.Date = resultSet.getString("Date");
                visit.Time = resultSet.getString("Time");
                visit.Doctor = resultSet.getInt("Doctor");
                visit.Patient = resultSet.getInt("Patient");
                visit.Prescription = resultSet.getInt("Prescription");
                visits.add(visit);
            }

            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
        }
        logUsage("getPatientsVisits", user);
        return visits;
    }

    /**
     * Function used internally to find the index of a doctor (by name).
     *
     * @param doctor String - The name of the doctor (from dropdown).
     * @param user   User we're doing the lookup for.
     * @return The index of the desired doctor.
     */
    private int findDoctorIndex(String doctor, User user) {
        Database database = new Database();
        ArrayList<Doctor> doctors = database.getDoctors(user);
        int index = 0;
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).Name.equals(doctor)) {
                index = i;
            }
        }
        return index + 1;
    }

    private void sendMessageToUser(String messageText) {
        System.out.println(messageText);
    }

    public static void deleteBooking(int VisitID) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "DELETE FROM visit WHERE VisitID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, VisitID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Booking with VisitID " + VisitID + " deleted successfully.");
            } else {
                System.out.println("No booking with VisitID " + VisitID + " found.");
            }

            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
            e.printStackTrace();
        }
    }

    /**
     * This is called when a user is trying to create a booking.
     *
     * @param date Date of the booking that we're checking.
     * @return True if in the past or false if not.
     */
    public boolean checkBookingDate(Date date) {
        Date today = new Date();
        Instant current = today.toInstant();
        Instant appointment = date.toInstant();
        LocalDateTime now = LocalDateTime.ofInstant(current, ZoneId.systemDefault());
        LocalDateTime then = LocalDateTime.ofInstant(appointment, ZoneId.systemDefault());
        return then.compareTo(now) < 0 ? true : false;
    }

    /**
     * Gets all visits from the database.
     *
     * @return ArrayList of visits.
     */
    public ArrayList<Visit> getVisits() {
        ArrayList<Visit> visits = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM Visit";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Visit visit = new Visit();
                visit.VisitID = resultSet.getInt("VisitID");
                visit.Date = resultSet.getString("Date");
                visit.Time = resultSet.getString("Time");
                visit.Doctor = resultSet.getInt("Doctor");
                visit.Patient = resultSet.getInt("Patient");
                visit.Prescription = resultSet.getInt("Prescription");
                visits.add(visit);
            }

            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
            e.printStackTrace();
        }
        return visits;
    }

    /**
     * This is the second method that is called when user is signing up.
     *
     * @param date   Date of the booking that we're checking.
     * @param doctor String - the name of the doctor.
     * @return False if available and true if not (booking conflict).
     */
    public boolean checkVisitAvailability(Date date, String doctor, User user) {
        boolean bookingConflict = false;
        int doctorID = findDoctorIndex(doctor, user);
        ArrayList<Visit> visits = getVisits();
        for (int i = 0; i < visits.size(); i++) {
            Format dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Format timeFormatter = new SimpleDateFormat("HH:mm:ss");
            String providedDate = dateFormatter.format(date); // This is used in the SQL statement!!
            String time = timeFormatter.format(date); // This is used in the SQL statement!!
            if (visits.get(i).Date.equals(providedDate) &&
                    visits.get(i).Time.equals(time) &&
                    visits.get(i).Doctor == doctorID) {
                bookingConflict = true;
            }
        }
        return bookingConflict;
    }

    /**
     * This message logs all usage on the system.
     *
     * @param description A description of what the user is doing.
     * @param user        User who is performing the action.
     */
    private void logUsage(String description, User user) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO user_activity_logs (activity_datetime, activity_description, userID) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, user.id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
