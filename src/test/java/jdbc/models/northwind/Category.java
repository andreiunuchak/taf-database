package jdbc.models.northwind;

import lombok.Data;

@Data
public class Category {
    private Integer category_id;
    private String category_name;
    private String description;
}
