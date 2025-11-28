package jdbc.dao.hospital;

import jdbc.dao.AbstractDAO;
import jdbc.models.hospital.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO extends AbstractDAO {
    private final String insert = "INSERT INTO patients (patient_id, first_name, last_name, gender, birth_date, city, province_id, allergies, height, weight) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String getAll = "SELECT * FROM hospital.patients";
    private final String update = "UPDATE hospital.patients SET first_name=?, last_name=?, gender=?, birth_date=?, city=?, province_id=?, allergies=?, height=?, weight=? WHERE patient_id=?";
    private final String delete = "DELETE FROM hospital.patients WHERE patient_id = ?";

    PatientDAO(String DB_SCHEME) {
        super(DB_SCHEME);
    }

    public int insertPatient(Patient patient) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setInt(1, patient.getPatient_id());
        preparedStatement.setString(2, patient.getFirst_name());
        preparedStatement.setString(3, patient.getLast_name());
        preparedStatement.setString(4, patient.getGender());
        preparedStatement.setDate(5, patient.getBirth_date());
        preparedStatement.setString(6, patient.getCity());
        preparedStatement.setString(7, patient.getProvince_id());
        preparedStatement.setString(8, patient.getAllergies());
        preparedStatement.setDouble(9, patient.getHeight());
        preparedStatement.setDouble(10, patient.getWeight());
        return preparedStatement.executeUpdate();
    }

    public List<Patient> getAllPatients() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getAll);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Patient> patients = new ArrayList<>();
        while (resultSet.next()) {
            Patient patient = new Patient();
            patient.setPatient_id(resultSet.getInt("patient_id"));
            patient.setFirst_name(resultSet.getString("first_name"));
            patient.setLast_name(resultSet.getString("last_name"));
            patient.setGender(resultSet.getString("gender"));
            patient.setBirth_date(resultSet.getDate("birth_date"));
            patient.setCity(resultSet.getString("city"));
            patient.setProvince_id(resultSet.getString("province_id"));
            patient.setAllergies(resultSet.getString("allergies"));
            patient.setHeight(resultSet.getDouble("height"));
            patient.setWeight(resultSet.getDouble("weight"));
            patients.add(patient);
        }
        return patients;
    }

    public int updatePatient(Patient patient) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, patient.getFirst_name());
        preparedStatement.setString(2, patient.getLast_name());
        preparedStatement.setString(3, patient.getGender());
        preparedStatement.setDate(4, patient.getBirth_date());
        preparedStatement.setString(5, patient.getCity());
        preparedStatement.setString(6, patient.getProvince_id());
        preparedStatement.setString(7, patient.getAllergies());
        preparedStatement.setDouble(8, patient.getHeight());
        preparedStatement.setDouble(9, patient.getWeight());
        preparedStatement.setInt(10, patient.getPatient_id());
        return preparedStatement.executeUpdate();
    }

    public int deletePatient(int patient_id) throws SQLException {
        new Hospital().admission.deletePatientIDs(patient_id);
        PreparedStatement preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setInt(1, patient_id);
        return preparedStatement.executeUpdate();
    }
}
