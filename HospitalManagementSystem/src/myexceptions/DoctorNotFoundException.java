package myexceptions;

public class DoctorNotFoundException extends Exception {
    private static final long serialVersionUID = 3L; // Unique identifier for serialization

    public DoctorNotFoundException(String message) {
        super(message);
    }
}