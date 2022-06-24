package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.Customer;

import java.sql.Connection;

public interface CustomerDAO extends CrudDAO<Connection, Customer, String> {
}
