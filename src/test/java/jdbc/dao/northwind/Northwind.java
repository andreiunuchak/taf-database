package jdbc.dao.northwind;

public class Northwind {
    public static final String DB_SCHEME = "northwind";
    public final CategoryDAO category;
    public final CustomerDAO customer;
    public final EmployeeDAO employee;
    public final EmployeeTerritoryDAO employeeTerritory;
    public final OrderDAO order;
    public final OrderDetailsDAO orderDetails;
    public final ProductDAO product;
    public final RegionDAO region;
    public final ShipperDAO shipper;
    public final SupplierDAO supplier;
    public final TerritoryDAO territory;

    public Northwind() {
        this.category = new CategoryDAO(DB_SCHEME);
        this.customer = new CustomerDAO(DB_SCHEME);
        this.employee = new EmployeeDAO(DB_SCHEME);
        this.employeeTerritory = new EmployeeTerritoryDAO(DB_SCHEME);
        this.order = new OrderDAO(DB_SCHEME);
        this.orderDetails = new OrderDetailsDAO(DB_SCHEME);
        this.product = new ProductDAO(DB_SCHEME);
        this.region = new RegionDAO(DB_SCHEME);
        this.shipper = new ShipperDAO(DB_SCHEME);
        this.supplier = new SupplierDAO(DB_SCHEME);
        this.territory = new TerritoryDAO(DB_SCHEME);
    }
}
