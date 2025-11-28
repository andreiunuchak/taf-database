package jdbc.dao.hospital;

import jdbc.dao.AbstractDAO;
import jdbc.models.hospital.Admission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdmissionDAO extends AbstractDAO {
    private final String insert = "INSERT INTO hospital.admissions (patient_id, admission_date, discharge_date, diagnosis, attending_doctor_id) VALUES (?, ?, ?, ?, ?)";
    private final String getAll = "SELECT * FROM hospital.admissions";
    private final String updateAttendingDoctorIDWithNull = "UPDATE hospital.admissions SET attending_doctor_id = NULL WHERE attending_doctor_id = ?";
    private final String updateAttendingDoctorID = "UPDATE hospital.admissions SET attending_doctor_id = ? WHERE attending_doctor_id = ?";
    private final String deleteByPatientID = "DELETE FROM hospital.admissions WHERE patient_id = ?";

    AdmissionDAO(String DB_SCHEME) {
        super(DB_SCHEME);
    }

    public int insertAdmission(Admission admission) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setInt(1, admission.getPatient_id());
        preparedStatement.setDate(2, admission.getAdmission_date());
        preparedStatement.setDate(3, admission.getDischarge_date());
        preparedStatement.setString(4, admission.getDiagnosis());
        preparedStatement.setInt(5, admission.getAttending_doctor_id());
        return preparedStatement.executeUpdate();
    }

    public List<Admission> getAllAdmissions() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getAll);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Admission> admissions = new ArrayList<>();
        while (resultSet.next()) {
            Admission admission = new Admission();
            admission.setPatient_id(resultSet.getInt("patient_id"));
            admission.setAdmission_date(resultSet.getDate("admission_date"));
            admission.setDischarge_date(resultSet.getDate("discharge_date"));
            admission.setDiagnosis(resultSet.getString("diagnosis"));
            admission.setAttending_doctor_id(resultSet.getInt("attending_doctor_id"));
            admissions.add(admission);
        }
        return admissions;
    }

    public int updateAttendingDoctorIDs(Integer oldAttendingDoctorID, Integer newAttendingDoctorID) throws SQLException {
        PreparedStatement preparedStatement;
        if (newAttendingDoctorID == null) {
            preparedStatement = connection.prepareStatement(updateAttendingDoctorIDWithNull);
            preparedStatement.setInt(1, oldAttendingDoctorID);
        } else {
            preparedStatement = connection.prepareStatement(updateAttendingDoctorID);
            preparedStatement.setInt(1, newAttendingDoctorID);
            preparedStatement.setInt(2, oldAttendingDoctorID);
        }
        return preparedStatement.executeUpdate();
    }

    public int deletePatientIDs(int patientID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(deleteByPatientID);
        preparedStatement.setInt(1, patientID);
        return preparedStatement.executeUpdate();
    }
}
