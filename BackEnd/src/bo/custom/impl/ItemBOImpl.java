package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    @Override
    public ArrayList<ItemDTO> getAllItem() {
        return null;
    }

    @Override
    public void deleteItem(String code) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {

    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
