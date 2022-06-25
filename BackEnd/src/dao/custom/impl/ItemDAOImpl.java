package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Connection connection, Item item) throws SQLException {
        boolean result = CrudUtil.executeUpdate(connection, "Insert into item values(?,?,?,?)", item.getId(), item.getName(), item.getQtyOnHand(), item.getUnitPrice());
        connection.close();
        return result;
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException {
        boolean result = CrudUtil.executeUpdate(connection, "Delete from item where id=?", s);
        connection.close();
        return result;
    }

    @Override
    public boolean update(Connection connection, Item item) throws SQLException {
        boolean result = CrudUtil.executeUpdate(connection, "Update item set name=?,quantity=?,price=? where id=?", item.getName(), item.getQtyOnHand(), item.getUnitPrice(), item.getId());
        connection.close();
        return result;
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
            allItems.add(new Item(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getDouble(4)));
        }
        connection.close();
        return allItems;
    }
}
