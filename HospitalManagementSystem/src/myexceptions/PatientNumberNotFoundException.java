package myexceptions;

public class PatientNumberNotFoundException extends Exception {
    private static final long serialVersionUID = 1L; // Unique identifier for serialization

    public PatientNumberNotFoundException(String message) {
        super(message);
    }
}







