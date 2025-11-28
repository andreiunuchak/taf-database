package jdbc.tests;

import jdbc.dao.hospital.Hospital;
import jdbc.databases.DatabaseConnection;
import jdbc.models.hospital.Doctor;
import jdbc.models.hospital.Patient;
import jdbc.utils.DatabaseUtils;
import jdbc.utils.SystemUtils;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Random;

public class HospitalCRUDTests {
    private final Hospital hospital = new Hospital();

    @BeforeAll
    public static void beforeAll() {
        if (SystemUtils.getSystemPropertyDatabase().equals("h2")) {
            DatabaseUtils.h2CreateHospitalData();
        }
        if (SystemUtils.getSystemPropertyDatabase().equals("mysql")) {
            DatabaseUtils.mysqlCreateHospitalData();
        }
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        DatabaseConnection.closeConnection();
    }

    @Test
    public void insertDoctorTest() throws SQLException, ClassNotFoundException {
        List<Doctor> doctorsBefore = hospital.doctor.getAllDoctors();
        Doctor doctor = new Doctor(doctorsBefore.getLast().getDoctor_id() + 1, "FirstName", "LastName", "Specialty");
        hospital.doctor.insertDoctor(doctor);
        List<Doctor> doctorsAfter = hospital.doctor.getAllDoctors();
        Assertions.assertAll(
                () -> Assertions.assertTrue(doctorsAfter.contains(doctor)),
                () -> Assertions.assertEquals(doctorsBefore.size() + 1, doctorsAfter.size())
        );
    }

    @Test
    public void insertExistingDoctorTest() throws SQLException, ClassNotFoundException {
        Doctor doctor = new Doctor(hospital.doctor.getAllDoctors().getFirst().getDoctor_id(), "FirstName", "LastName", "Specialty");
        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            hospital.doctor.insertDoctor(doctor);
        });
    }

    @Test
    public void updateDoctorTest() throws SQLException, ClassNotFoundException {
        List<Doctor> doctorsBefore = hospital.doctor.getAllDoctors();
        Doctor doctor = Doctor.builder()
                .doctor_id(doctorsBefore.get(new Random().nextInt(doctorsBefore.size())).getDoctor_id())
                .first_name("FirstName")
                .last_name("LastName")
                .specialty("Specialty")
                .build();
        int id = doctor.getDoctor_id();
        int rowsAffected = hospital.doctor.updateDoctor(doctor);
        List<Doctor> doctorsAfter = hospital.doctor.getAllDoctors();
        Doctor doctorUpdated = doctorsAfter.stream().filter(x -> x.getDoctor_id() == id).findFirst().orElse(null);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, rowsAffected),
                () -> Assertions.assertEquals(doctor, doctorUpdated)
        );
    }

    @Test
    public void deleteDoctorTest() throws SQLException, ClassNotFoundException {
        List<Doctor> doctorsBefore = hospital.doctor.getAllDoctors();
        hospital.doctor.deleteDoctor(doctorsBefore.getFirst().getDoctor_id());
        List<Doctor> doctorsAfter = hospital.doctor.getAllDoctors();
        Assertions.assertAll(
                () -> Assertions.assertEquals(doctorsBefore.size() - 1, doctorsAfter.size()),
                () -> Assertions.assertFalse(doctorsAfter.contains(doctorsBefore.getFirst()))
        );
    }

    @Test
    public void insertPatientTest() throws SQLException, ClassNotFoundException {
        List<Patient> patientsBefore = hospital.patient.getAllPatients();
        Patient patient = new Patient(patientsBefore.getLast().getPatient_id() + 1, "Firstname", "Lastname", "M", Date.valueOf("1981-05-28"), "Dundas", "ON", "Sulfa", 185.0, 76.0);
        hospital.patient.insertPatient(patient);
        List<Patient> patientsAfter = hospital.patient.getAllPatients();
        Assertions.assertAll(
                () -> Assertions.assertTrue(patientsAfter.contains(patient)),
                () -> Assertions.assertEquals(patientsBefore.size() + 1, patientsAfter.size())
        );
    }

    @Test
    public void insertExistingPatientTest() throws SQLException, ClassNotFoundException {
        Patient patient = new Patient(hospital.patient.getAllPatients().getFirst().getPatient_id(), "Mickey", "Baasha", "M", Date.valueOf("1981-05-28"), "Dundas", "ON", "Sulfa", 185.0, 76.0);
        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            hospital.patient.insertPatient(patient);
        });
    }

    @Test
    public void updatePatientTest() throws SQLException, ClassNotFoundException {
        List<Patient> patientsBefore = hospital.patient.getAllPatients();
        Patient patient = Patient.builder()
                .patient_id(patientsBefore.get(new Random().nextInt(patientsBefore.size())).getPatient_id())
                .first_name("FirstName")
                .last_name("LastName")
                .gender("M")
                .birth_date(Date.valueOf("1989-03-21"))
                .city("Warsaw")
                .province_id("ON")
                .allergies(null)
                .weight(187.0)
                .height(84.0)
                .build();
        int id = patient.getPatient_id();
        int rowsAffected = hospital.patient.updatePatient(patient);
        List<Patient> patientsAfter = hospital.patient.getAllPatients();
        Patient updatedPatient = patientsAfter.stream().filter(x -> x.getPatient_id() == id).findFirst().orElse(null);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, rowsAffected),
                () -> Assertions.assertEquals(patient, updatedPatient)
        );
    }

    @Test
    public void deletePatientTest() throws SQLException, ClassNotFoundException {
        List<Patient> patientsBefore = hospital.patient.getAllPatients();
        hospital.patient.deletePatient(patientsBefore.getFirst().getPatient_id());
        List<Patient> patientsAfter = hospital.patient.getAllPatients();
        Assertions.assertAll(
                () -> Assertions.assertEquals(patientsBefore.size() - 1, patientsAfter.size()),
                () -> Assertions.assertFalse(patientsAfter.contains(patientsBefore.getFirst()))
        );
    }
}
