package dao;

import entity.Appointment;
import entity.Patient;
import myexceptions.AppointmentNotFoundException;
import myexceptions.DoctorNotFoundException;
import myexceptions.InvalidInputException;
import myexceptions.PatientNumberNotFoundException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HospitalServiceImpl implements IHospitalService {

    @Override
    public String addPatient(Patient patient) throws PatientNumberNotFoundException, InvalidInputException {
        if (patient.getPatientId() < 0) {
            throw new PatientNumberNotFoundException("Patient ID cannot be negative.");
        }

        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Patients (patientId, firstName, lastName, dateOfBirth, gender, contactNumber, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patient.getPatientId());
            preparedStatement.setString(2, patient.getFirstName());
            preparedStatement.setString(3, patient.getLastName());
            preparedStatement.setString(4, patient.getDateOfBirth());
            preparedStatement.setString(5, patient.getGender());
            preparedStatement.setString(6, patient.getContactNumber());
            preparedStatement.setString(7, patient.getAddress());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? "Patient added successfully." : "Failed to add patient."; // Success message
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return "An error occurred while adding the patient.";
        }
    }

    @Override
    public String cancelAppointment(int appointmentId) throws AppointmentNotFoundException {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM Appointments WHERE appointmentId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, appointmentId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId);
            }
            return "Appointment canceled successfully."; // Success message
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return "An error occurred while canceling the appointment.";
        }
    }

    @Override
    public String scheduleAppointment(Appointment appointment) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Appointments (appointmentId, patientId, doctorId, appointmentDate, description) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, appointment.getAppointmentId());
            preparedStatement.setInt(2, appointment.getPatientId());
            preparedStatement.setInt(3, appointment.getDoctorId());
            preparedStatement.setString(4, appointment.getAppointmentDate());
            preparedStatement.setString(5, appointment.getDescription());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? "Appointment scheduled successfully." : "Failed to schedule appointment."; // Success message
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return "An error occurred while scheduling the appointment.";
        }
    }

    @Override
    public String updateAppointment(Appointment appointment) throws AppointmentNotFoundException {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE Appointments SET patientId = ?, doctorId = ?, appointmentDate = ?, description = ? WHERE appointmentId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, appointment.getPatientId());
            preparedStatement.setInt(2, appointment.getDoctorId());
            preparedStatement.setString(3, appointment.getAppointmentDate());
            preparedStatement.setString(4, appointment.getDescription());
            preparedStatement.setInt(5, appointment.getAppointmentId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new AppointmentNotFoundException("Appointment not found with ID: " + appointment.getAppointmentId());
            }
            return "Appointment updated successfully."; // Success message
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return "An error occurred while updating the appointment.";
        }
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) throws AppointmentNotFoundException {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Appointments WHERE appointmentId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, appointmentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int patientId = resultSet.getInt("patientId");
                int doctorId = resultSet.getInt("doctorId");
                String appointmentDate = resultSet.getString("appointmentDate");
                String description = resultSet.getString("description");
                return new Appointment(appointmentId, patientId, doctorId, appointmentDate, description);
            } else {
                throw new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return null;
        }
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) throws DoctorNotFoundException {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Appointments WHERE doctorId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointmentId");
                int patientId = resultSet.getInt("patientId");
                String appointmentDate = resultSet.getString("appointmentDate");
                String description = resultSet.getString("description");
                appointments.add(new Appointment(appointmentId, patientId, doctorId, appointmentDate, description));
            }

            if (appointments.isEmpty()) {
                throw new DoctorNotFoundException("No appointments found for Doctor ID: " + doctorId);
            }
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return new ArrayList<>();
        }
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) throws PatientNumberNotFoundException {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Appointments WHERE patientId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointmentId");
                int doctorId = resultSet.getInt("doctorId");
                String appointmentDate = resultSet.getString("appointmentDate");
                String description = resultSet.getString("description");
                appointments.add(new Appointment(appointmentId, patientId, doctorId, appointmentDate, description));
            }

            if (appointments.isEmpty()) {
                throw new PatientNumberNotFoundException("No appointments found for Patient ID: " + patientId);
            }
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return new ArrayList<>();
        }
    }
}
