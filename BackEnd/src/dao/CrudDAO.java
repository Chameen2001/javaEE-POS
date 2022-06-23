package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T,ID> extends SuperDAO{
    boolean add(T t) throws SQLException;

    boolean delete(ID id) throws SQLException;

    boolean update(T t) throws SQLException;

    T search(ID id) throws SQLException;

    ArrayList<T> getAll(Connection connection) throws SQLException;
}
