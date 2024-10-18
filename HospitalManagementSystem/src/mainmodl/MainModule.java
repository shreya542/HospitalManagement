package mainmodl;

import dao.HospitalServiceImpl;
import entity.Appointment;
import entity.Patient;
import myexceptions.AppointmentNotFoundException;
import myexceptions.DoctorNotFoundException;
import myexceptions.InvalidInputException;
import myexceptions.PatientNumberNotFoundException;

import java.util.List;
import java.util.Scanner;

public class MainModule {
    private static final HospitalServiceImpl hospitalService = new HospitalServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("1. Add Patient");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. Update Appointment");
            System.out.println("5. Get Appointment by ID");
            System.out.println("6. Get Appointments for Doctor");
            System.out.println("7. Get Appointments for Patient");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    scheduleAppointment();
                    break;
                case 3:
                    cancelAppointment();
                    break;
                case 4:
                    updateAppointment();
                    break;
                case 5:
                    getAppointmentById();
                    break;
                case 6:
                    getAppointmentsForDoctor();
                    break;
                case 7:
                    getAppointmentsForPatient();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void addPatient() {
        try {
            System.out.print("Enter Patient ID: ");
            int patientId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            String dateOfBirth = scanner.nextLine();
            System.out.print("Enter Gender: ");
            String gender = scanner.nextLine();
            System.out.print("Enter Contact Number: ");
            String contactNumber = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            Patient patient = new Patient(patientId, firstName, lastName, dateOfBirth, gender, contactNumber, address);
            boolean result = hospitalService.addPatient(patient);
            if (result) {
                System.out.println("Patient added successfully.");
            } else {
                System.out.println("Failed to add patient.");
            }
        } catch (PatientNumberNotFoundException | InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void scheduleAppointment() {
        try {
            System.out.print("Enter Appointment ID: ");
            int appointmentId = scanner.nextInt();
            System.out.print("Enter Patient ID: ");
            int patientId = scanner.nextInt();
            System.out.print("Enter Doctor ID: ");
            int doctorId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String appointmentDate = scanner.nextLine();
            System.out.print("Enter Description: ");
            String description = scanner.nextLine();

            Appointment appointment = new Appointment(appointmentId, patientId, doctorId, appointmentDate, description);
            boolean result = hospitalService.scheduleAppointment(appointment);
            if (result) {
                System.out.println("Appointment scheduled successfully.");
            } else {
                System.out.println("Failed to schedule appointment.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cancelAppointment() {
        System.out.print("Enter Appointment ID to cancel: ");
        int appointmentId = scanner.nextInt();
        try {
            boolean result = hospitalService.cancelAppointment(appointmentId);
            if (result) {
                System.out.println("Appointment canceled successfully.");
            } else {
                System.out.println("Failed to cancel appointment.");
            }
        } catch (AppointmentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateAppointment() {
        try {
            System.out.print("Enter Appointment ID to update: ");
            int appointmentId = scanner.nextInt();
            System.out.print("Enter new Patient ID: ");
            int patientId = scanner.nextInt();
            System.out.print("Enter new Doctor ID: ");
            int doctorId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new Appointment Date (YYYY-MM-DD): ");
            String appointmentDate = scanner.nextLine();
            System.out.print("Enter new Description: ");
            String description = scanner.nextLine();

            Appointment appointment = new Appointment(appointmentId, patientId, doctorId, appointmentDate, description);
            boolean result = hospitalService.updateAppointment(appointment);
            if (result) {
                System.out.println("Appointment updated successfully.");
            } else {
                System.out.println("Failed to update appointment.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getAppointmentById() {
        System.out.print("Enter Appointment ID to retrieve: ");
        int appointmentId = scanner.nextInt();
        try {
            Appointment appointment = hospitalService.getAppointmentById(appointmentId);
            System.out.println("Appointment Details: " + appointment);
        } catch (AppointmentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getAppointmentsForDoctor() {
        System.out.print("Enter Doctor ID to retrieve appointments: ");
        int doctorId = scanner.nextInt();
        try {
            List<Appointment> appointments = hospitalService.getAppointmentsForDoctor(doctorId);
            System.out.println("Appointments for Doctor ID " + doctorId + ": " + appointments);
        } catch (DoctorNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getAppointmentsForPatient() {
        System.out.print("Enter Patient ID to retrieve appointments: ");
        int patientId = scanner.nextInt();
        try {
            List<Appointment> appointments = hospitalService.getAppointmentsForPatient(patientId);
            System.out.println("Appointments for Patient ID " + patientId + ": " + appointments);
        } catch (PatientNumberNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
