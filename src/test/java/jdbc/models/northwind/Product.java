package jdbc.models.northwind;

import lombok.Data;

@Data
public class Product {
    private Integer product_id;
    private String product_name;
    private Integer supplier_id;
    private Integer category_id;
    private String quantity_per_unit;
    private Double unit_price;
    private Integer units_in_stock;
    private Integer units_on_order;
    private Integer reorder_level;
    private Integer discontinued;
}
