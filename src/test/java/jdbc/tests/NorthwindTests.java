package jdbc.tests;

import io.qameta.allure.Description;
import jdbc.utils.DatabaseUtils;
import jdbc.databases.DatabaseConnection;
import jdbc.utils.SystemUtils;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NorthwindTests {
    private static Connection connection;

    @BeforeAll
    public static void beforeAll() throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection("northwind");
        if (SystemUtils.getSystemPropertyDatabase().equals("h2")) {
            DatabaseUtils.h2CreateNorthwindData();
        }
        if (SystemUtils.getSystemPropertyDatabase().equals("mysql")) {
            DatabaseUtils.mysqlCreateNorthwindData();
        }
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        DatabaseConnection.closeConnection();
    }

    @Test
    @Description("Show the category_name and description from the categories table sorted by category_name.")
    public void testQuestion1() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select category_name, description FROM northwind.categories ORDER BY category_name ASC");
        ResultSet resultSet = preparedStatement.executeQuery();
        record sqlResultItem(String category_name, String description) {
        }
        List<sqlResultItem> sqlResultItems = new ArrayList<>();
        while (resultSet.next()) {
            String category_name = resultSet.getString("category_name");
            String description = resultSet.getString("description");
            sqlResultItems.add(new sqlResultItem(category_name, description));
        }
        List<sqlResultItem> sorterSqlResultItems = sqlResultItems.stream().sorted(Comparator.comparing(sqlResultItem::category_name)).collect(Collectors.toList());
        Assertions.assertAll(
                () -> Assertions.assertEquals(8, sqlResultItems.size()),
                () -> Assertions.assertEquals(sorterSqlResultItems, sqlResultItems)
        );
    }

    @Test
    @Description("Show all the contact_name, address, city of all customers which are not from 'Germany', 'Mexico', 'Spain'")
    public void testQuestion2() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select contact_name, address, city FROM northwind.customers WHERE country NOT IN ('Germany', 'Mexico', 'Spain')");
        ResultSet resultSet = preparedStatement.executeQuery();
        int counter = 0;
        while (resultSet.next()) {
            counter++;
        }
        Assertions.assertEquals(70, counter);
    }

    @Test
    @Description("Show order_date, shipped_date, customer_id, Freight of all orders placed on 2018 Feb 26")
    public void testQuestion3() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select order_date, shipped_date, customer_id, freight FROM northwind.orders WHERE order_date='2018-02-26'");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> orderDates = new ArrayList<>();
        while (resultSet.next()) {
            orderDates.add(resultSet.getString("order_date"));
        }
        Assertions.assertAll(
                () -> Assertions.assertEquals(6, orderDates.size()),
                () -> Assertions.assertTrue(orderDates.stream().allMatch(orderDate -> orderDate.equals("2018-02-26")))
        );
    }

    @Test
    @Description("Show the employee_id, order_id, customer_id, required_date, shipped_date from all orders shipped later than the required date")
    public void testQuestion4() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select employee_id, order_id, customer_id, required_date, shipped_date from northwind.orders where shipped_date>required_date");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show all the even numbered Order_id from the orders table.")
    public void testQuestion5() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select order_id from northwind.orders where order_id%2=0");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the city, company_name, contact_name of all customers from cities which contains the letter 'L' in the city name, sorted by contact_name")
    public void testQuestion6() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select city, company_name, contact_name from northwind.customers where city like '%L%' order by contact_name");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the company_name, contact_name, fax number of all customers that has a fax number. (not null)")
    public void testQuestion7() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select company_name, contact_name, fax from northwind.customers where fax is not null");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the first_name, last_name, hire_date of the most recently hired employee.")
    public void testQuestion8() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name, hire_date from northwind.employees where hire_date=(select max(hire_date) from northwind.employees)");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the average unit price rounded to 2 decimal places, the total units in stock, total discontinued products from the products table.")
    public void testQuestion9() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select round(avg(unit_price),2) as average_price, sum(units_in_stock) as total_stock, sum(discontinued) as total_discontinued from northwind.products");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the ProductName, CompanyName, CategoryName from the products, suppliers, and categories table")
    public void testQuestion10() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select products.product_name, company_name, category_name from northwind.products left join northwind.suppliers on products.supplier_id=suppliers.supplier_id left join northwind.categories on products.category_id=categories.category_id");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the category_name and the average product unit price for each category rounded to 2 decimal places.")
    public void testQuestion11() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select category_name, round(avg(unit_price),2) from northwind.products left join northwind.categories on products.category_id=categories.category_id group by category_name");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Show the city, company_name, contact_name from the customers and suppliers table merged together.
            Create a column which contains 'customers' or 'suppliers' depending on the table it came from.""")
    public void testQuestion12() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT city, company_name, contact_name, 'customers' as relationship from northwind.customers
                union all
                SELECT city, company_name, contact_name, 'suppliers' as relationship from northwind.suppliers""");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the total amount of orders for each year/month.")
    public void testQuestion13() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select year(order_date) as order_year, month(order_date) as order_month, count(*) as no_of_orders from northwind.orders group by order_year, order_month");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the employee's first_name and last_name, a 'num_orders' column with a count of the orders taken, and a column called 'Shipped' that displays 'On Time' if the order shipped_date is less or equal to the required_date, 'Late' if the order shipped late, 'Not Shipped' if shipped_date is null. Order by employee last_name, then by first_name, and then descending by number of orders.")
    public void testQuestion14() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name, count(*) as num_orders, shipped from (select first_name, last_name, case when shipped_date is null then 'Not Shipped' else case when shipped_date<=required_date then 'On Time' else 'Late' end end as shipped from northwind.orders left join northwind.employees on orders.employee_id=employees.employee_id) as T1 group by first_name, last_name, shipped order by last_name, first_name, num_orders desc");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show how much money the company lost due to giving discounts each year, order the years from most recent to least recent. Round to 2 decimal places")
    public void testQuestion15() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select year(order_date) as order_year, round(sum(discount*unit_price*quantity),2) as discount_amount from northwind.order_details left join northwind.orders on order_details.order_id=orders.order_id left join northwind.products on order_details.product_id=products.product_id group by order_year order by order_year desc");
        ResultSet resultSet = preparedStatement.executeQuery();
    }
}
