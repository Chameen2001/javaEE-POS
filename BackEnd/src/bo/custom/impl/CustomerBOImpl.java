package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.CUSTOMERDAO);
    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        ArrayList<CustomerDTO> customers = new ArrayList<>();
        for (Customer customer : customerDAO.getAll(connection)) {
            customers.add(new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress(),customer.getTel()));
        }
        return customers;


    }
}
