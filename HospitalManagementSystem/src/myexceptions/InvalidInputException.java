package myexceptions;

public class InvalidInputException extends Exception {
    private static final long serialVersionUID = 4L; // Unique identifier for serialization

    public InvalidInputException(String message) {
        super(message);
    }
}
