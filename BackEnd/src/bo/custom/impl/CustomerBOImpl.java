package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.CUSTOMERDAO);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        ArrayList<CustomerDTO> customers = new ArrayList<>();
        for (Customer customer : customerDAO.getAll(connection)) {
            customers.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getTel()));
        }
        return customers;
    }

    @Override
    public boolean addCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException {
        return customerDAO.add(connection, new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getTel()));
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(connection, new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getTel()));
    }

    @Override
    public boolean deleteCustomer(Connection connection, String id) throws SQLException {
        return customerDAO.delete(connection, id);
    }
}
