package jdbc.models.northwind;

import lombok.Data;

import java.sql.Date;

@Data
public class Order {
    private Integer order_id;
    private String customer_id;
    private Integer employee_id;
    private Date order_date;
    private Date required_date;
    private Date shipped_date;
    private Integer ship_via;
    private Double freight;
    private String ship_name;
    private String ship_address;
    private String ship_city;
    private String ship_region;
    private String ship_postal_code;
    private String ship_country;
}
