CREATE DATABASE hospital_db;

USE hospital_db;

CREATE TABLE Patient (
    patientId INT PRIMARY KEY,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    dateOfBirth DATE,
    gender VARCHAR(10),
    contactNumber VARCHAR(15),
    address VARCHAR(100)
);

CREATE TABLE Doctor (
    doctorId INT PRIMARY KEY,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    specialization VARCHAR(50),
    contactNumber VARCHAR(15)
);

CREATE TABLE Appointment (
    appointmentId INT PRIMARY KEY,
    patientId INT,
    doctorId INT,
    appointmentDate DATE,
    description VARCHAR(255),
    FOREIGN KEY (patientId) REFERENCES Patient(patientId),
    FOREIGN KEY (doctorId) REFERENCES Doctor(doctorId)
);

INSERT INTO Patient (patientId, firstName, lastName, dateOfBirth, gender, contactNumber, address) VALUES 
(1, 'John', 'Doe', '1985-05-15', 'Male', '1234567890', '123 Main St, CityA'),
(2, 'Jane', 'Smith', '1990-03-22', 'Female', '0987654321', '456 Elm St, CityB'),
(3, 'Alice', 'Johnson', '1978-07-10', 'Female', '1231231234', '789 Pine St, CityC'),
(4, 'Bob', 'Brown', '1965-11-30', 'Male', '9876543210', '321 Oak St, CityD');

INSERT INTO Doctor (doctorId, firstName, lastName, specialization, contactNumber) VALUES 
(1, 'Dr. Emily', 'Clark', 'Cardiology', '1112223333'),
(2, 'Dr. Daniel', 'Roberts', 'Neurology', '4445556666'),
(3, 'Dr. Sarah', 'Wilson', 'Pediatrics', '7778889999'),
(4, 'Dr. Michael', 'Lee', 'Orthopedics', '0001112222');

INSERT INTO Appointment (appointmentId, patientId, doctorId, appointmentDate, description) VALUES 
(1, 1, 1, '2024-10-20', 'Routine check-up'),
(2, 2, 2, '2024-10-21', 'Neurology consultation'),
(3, 3, 3, '2024-10-22', 'Pediatric check-up'),
(4, 4, 4, '2024-10-23', 'Orthopedic assessment');

RENAME TABLE Patient TO Patients;
RENAME TABLE appointment TO appointments;

show tables;

