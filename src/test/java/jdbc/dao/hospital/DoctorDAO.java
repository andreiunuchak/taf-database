package jdbc.dao.hospital;

import jdbc.dao.AbstractDAO;
import jdbc.models.hospital.Doctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO extends AbstractDAO {
    private final String insert = "INSERT INTO hospital.doctors (doctor_id, first_name, last_name, specialty) VALUES (?, ?, ?, ?)";
    private final String getAll = "SELECT * FROM hospital.doctors";
    private final String delete = "DELETE FROM hospital.doctors WHERE doctor_id = ?";
    private final String update = "UPDATE hospital.doctors SET first_name=?, last_name=?, specialty=? WHERE doctor_id=?";

    DoctorDAO(String DB_SCHEME) {
        super(DB_SCHEME);
    }

    public int insertDoctor(Doctor doctor) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setInt(1, doctor.getDoctor_id());
        preparedStatement.setString(2, doctor.getFirst_name());
        preparedStatement.setString(3, doctor.getLast_name());
        preparedStatement.setString(4, doctor.getSpecialty());
        return preparedStatement.executeUpdate();
    }

    public List<Doctor> getAllDoctors() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getAll);
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        List<Doctor> doctors = new ArrayList<>();
        while (resultSet.next()) {
            Doctor doctor = new Doctor();
            doctor.setDoctor_id(resultSet.getInt("doctor_id"));
            doctor.setFirst_name(resultSet.getString("first_name"));
            doctor.setLast_name(resultSet.getString("last_name"));
            doctor.setSpecialty(resultSet.getString("specialty"));
            doctors.add(doctor);
        }
        return doctors;
    }

    public int updateDoctor(Doctor doctor) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, doctor.getFirst_name());
        preparedStatement.setString(2, doctor.getLast_name());
        preparedStatement.setString(3, doctor.getSpecialty());
        preparedStatement.setInt(4, doctor.getDoctor_id());
        return preparedStatement.executeUpdate();
    }

    public int deleteDoctor(int doctor_id) throws SQLException {
        new Hospital().admission.updateAttendingDoctorIDs(doctor_id, null);
        PreparedStatement preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setInt(1, doctor_id);
        return preparedStatement.executeUpdate();
    }
}
