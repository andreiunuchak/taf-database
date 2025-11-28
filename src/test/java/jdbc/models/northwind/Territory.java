package jdbc.models.northwind;

import lombok.Data;

@Data
public class Territory {
    private String territory_id;
    private String territory_description;
    private Integer region_id;
}
