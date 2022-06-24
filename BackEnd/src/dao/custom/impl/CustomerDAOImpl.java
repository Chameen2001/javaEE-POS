package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public boolean add(Connection connection, Customer customer) throws SQLException {
        boolean result = CrudUtil.executeUpdate(connection, "Insert into customer values (?,?,?,?)", customer.getId(), customer.getName(), customer.getAddress(), customer.getTel());
        connection.close();
        return result;
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException {
        boolean result = CrudUtil.executeUpdate(connection, "Delete from customer where id=?", s);
        connection.close();
        return result;
    }

    @Override
    public boolean update(Connection connection, Customer customer) throws SQLException {
        boolean result = CrudUtil.executeUpdate(connection, "Update customer Set name=?, address=?, tel=? Where id=?", customer.getName(), customer.getAddress(), customer.getTel(), customer.getId());
        connection.close();
        return result;
    }

    @Override
    public Customer search(Connection connection, String s) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery(connection, "Select * from customer");
        while (resultSet.next()) {
            customers.add(new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4)));
        }
        connection.close();
        return customers;
    }
}
