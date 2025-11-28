package jdbc.models.hospital;

import lombok.Data;

import java.sql.Date;

@Data
public class Admission {
    private Integer patient_id;
    private Date admission_date;
    private Date discharge_date;
    private String diagnosis;
    private Integer attending_doctor_id;
}
