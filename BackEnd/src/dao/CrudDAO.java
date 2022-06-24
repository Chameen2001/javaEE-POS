package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<C, T, ID> extends SuperDAO {
    boolean add(C c, T t) throws SQLException;

    boolean delete(C c, ID id) throws SQLException;

    boolean update(C c, T t) throws SQLException;

    T search(C c, ID id) throws SQLException;

    ArrayList<T> getAll(C c) throws SQLException;
}
