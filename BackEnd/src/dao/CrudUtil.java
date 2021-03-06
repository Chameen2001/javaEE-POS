package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    private static PreparedStatement getPreparedStatement(Connection connection, String sql, Object... args) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        return preparedStatement;
    }

    public static boolean executeUpdate(Connection connection, String sql, Object... args) throws SQLException {
        return getPreparedStatement(connection, sql, args).executeUpdate() > 0;
    }

    public static ResultSet executeQuery(Connection connection, String sql, Object... args) throws SQLException {
        return getPreparedStatement(connection, sql, args).executeQuery();
    }
}
