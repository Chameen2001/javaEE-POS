package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;
}
