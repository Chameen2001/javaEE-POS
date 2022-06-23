package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItem();

    void deleteItem(String code) throws SQLException, ClassNotFoundException ;

    void addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException ;

    void updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException ;

    boolean ifItemExist(String code) throws SQLException, ClassNotFoundException ;

    String generateNewID() throws SQLException, ClassNotFoundException ;
}
