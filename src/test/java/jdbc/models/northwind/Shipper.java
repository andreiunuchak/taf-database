package jdbc.models.northwind;

import lombok.Data;

@Data
public class Shipper {
    private Integer shipper_id;
    private String company_name;
    private String phone;
}
