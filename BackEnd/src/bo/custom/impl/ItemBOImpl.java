package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ITEMDAO);

    @Override
    public ArrayList<ItemDTO> getAllItem(Connection connection) throws SQLException {
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemDAO.getAll(connection)) {
            itemDTOS.add(new ItemDTO(item.getId(), item.getName(), item.getQtyOnHand(), item.getUnitPrice()));
        }
        return itemDTOS;
    }

    @Override
    public void deleteItem(Connection connection, String code) throws SQLException {

    }

    @Override
    public void addItem(Connection connection, ItemDTO itemDTO) throws SQLException {

    }

    @Override
    public void updateItem(Connection connection, ItemDTO itemDTO) throws SQLException {

    }

    @Override
    public boolean ifItemExist(Connection connection, String code) throws SQLException {
        return false;
    }

    @Override
    public String generateNewID(Connection connection) throws SQLException {
        return null;
    }
}
