package jdbc.models.northwind;

import lombok.Data;

import java.sql.Date;

@Data
public class Employee {
    private Integer employee_id;
    private String last_name;
    private String first_name;
    private String title;
    private String title_of_courtesy;
    private Date birth_date;
    private Date hire_date;
    private String address;
    private String city;
    private String region;
    private String postal_code;
    private String country;
    private String home_phone;
    private String extension;
    private Integer reports_to;
}
