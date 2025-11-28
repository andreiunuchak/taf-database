package jdbc.models.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    private Integer doctor_id;
    private String first_name;
    private String last_name;
    private String specialty;
}
