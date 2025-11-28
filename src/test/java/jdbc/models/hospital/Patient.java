package jdbc.models.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private Integer patient_id;
    private String first_name;
    private String last_name;
    private String gender;
    private Date birth_date;
    private String city;
    private String province_id;
    private String allergies;
    private Double height;
    private Double weight;
}
