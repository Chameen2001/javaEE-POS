package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Connection connection, Item item) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Connection connection, Item item) throws SQLException {
        return false;
    }

    @Override
    public Item search(Connection connection, String s) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "Select * from item");
        ArrayList<Item> allItems = new ArrayList<>();
        while (resultSet.next()) {
            allItems.add(new Item(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getBigDecimal(4)));
        }
        connection.close();
        return allItems;
    }
}
