package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItem(Connection connection) throws SQLException;

    void deleteItem(Connection connection, String code) throws SQLException;

    void addItem(Connection connection, ItemDTO itemDTO) throws SQLException;

    void updateItem(Connection connection, ItemDTO itemDTO) throws SQLException;

    boolean ifItemExist(Connection connection, String code) throws SQLException;

    String generateNewID(Connection connection) throws SQLException;
}
