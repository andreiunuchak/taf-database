package jdbc.models.northwind;

import lombok.Data;

@Data
public class Customer {
    private Integer customer_id;
    private String company_name;
    private String contact_name;
    private String contact_title;
    private String address;
    private String city;
    private String region;
    private String postal_code;
    private String country;
    private String phone;
    private String fax;
    private String tax;
}
