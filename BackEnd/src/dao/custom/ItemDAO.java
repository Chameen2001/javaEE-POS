package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.Item;

import java.sql.Connection;

public interface ItemDAO extends CrudDAO<Connection, Item, String> {
}
