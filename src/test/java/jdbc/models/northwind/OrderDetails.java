package jdbc.models.northwind;

import lombok.Data;

@Data
public class OrderDetails {
    private Integer order_id;
    private Integer product_id;
    private Integer quantity;
    private Double discount;
}
