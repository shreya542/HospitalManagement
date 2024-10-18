package myexceptions;
public class AppointmentNotFoundException extends Exception {
    private static final long serialVersionUID = 2L; // Unique identifier for serialization

    public AppointmentNotFoundException(String message) {
        super(message);
    }
}