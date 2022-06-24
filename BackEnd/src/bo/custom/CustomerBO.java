package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;

    boolean addCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException;

    boolean updateCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException;

    boolean deleteCustomer(Connection connection, String id) throws SQLException;
}
