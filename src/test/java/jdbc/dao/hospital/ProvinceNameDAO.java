package jdbc.dao.hospital;

import jdbc.dao.AbstractDAO;
import jdbc.models.hospital.ProvinceName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceNameDAO extends AbstractDAO {
    private final String insert = "INSERT INTO province_names (province_id, province_name) VALUES (?, ?)";
    private final String getAll = "SELECT * FROM hospital.province_names";
    private final String delete = "DELETE FROM hospital.province_names WHERE province_id = ?";
    private final String update = "UPDATE hospital.province_names SET province_name=? WHERE province_id=?";

    ProvinceNameDAO(String DB_SCHEME) {
        super(DB_SCHEME);
    }

    public int insertProvinceName(ProvinceName provinceName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setString(1, provinceName.getProvince_id());
        preparedStatement.setString(2, provinceName.getProvince_name());
        return preparedStatement.executeUpdate();
    }

    public List<ProvinceName> getAllProvinceNames() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getAll);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ProvinceName> provinceNames = new ArrayList<>();
        while (resultSet.next()) {
            ProvinceName provinceName = new ProvinceName();
            provinceName.setProvince_id(resultSet.getString("province_id"));
            provinceName.setProvince_name(resultSet.getString("province_name"));
            provinceNames.add(provinceName);
        }
        return provinceNames;
    }

    public int updateProvinceName(ProvinceName provinceName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, provinceName.getProvince_name());
        preparedStatement.setString(2, provinceName.getProvince_id());
        return preparedStatement.executeUpdate();
    }

    public int deleteProvinceName(ProvinceName provinceName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setString(1, provinceName.getProvince_id());
        return preparedStatement.executeUpdate();
    }
}
