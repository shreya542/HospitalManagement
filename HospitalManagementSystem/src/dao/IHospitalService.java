package dao;

import entity.Appointment;
import entity.Patient;
import myexceptions.AppointmentNotFoundException;
import myexceptions.DoctorNotFoundException;
import myexceptions.InvalidInputException;
import myexceptions.PatientNumberNotFoundException;

import java.util.List;

public interface IHospitalService {
    String addPatient(Patient patient) throws PatientNumberNotFoundException, InvalidInputException;
    String cancelAppointment(int appointmentId) throws AppointmentNotFoundException;
    String scheduleAppointment(Appointment appointment); // Updated return type
    String updateAppointment(Appointment appointment) throws AppointmentNotFoundException;
    Appointment getAppointmentById(int appointmentId) throws AppointmentNotFoundException;
    List<Appointment> getAppointmentsForDoctor(int doctorId) throws DoctorNotFoundException;
    List<Appointment> getAppointmentsForPatient(int patientId) throws PatientNumberNotFoundException;
}
