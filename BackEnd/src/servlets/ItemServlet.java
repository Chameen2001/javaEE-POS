package servlets;

import bo.BOFactory;
import bo.SuperBO;
import bo.custom.ItemBO;
import bo.custom.impl.ItemBOImpl;
import dao.CrudUtil;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBOImpl(BOFactory.BOType.ITEM_BO);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        Connection connection = null;
        try {
            connection=dataSource.getConnection();
            ResultSet resultSet =CrudUtil.executeQuery(connection,"SELECT * FROM item");
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            while (resultSet.next()) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id",resultSet.getString(1));
                objectBuilder.add("name",resultSet.getString(2));
                objectBuilder.add("qty",resultSet.getInt(3));
                objectBuilder.add("price",resultSet.getDouble(4));
                arrayBuilder.add(objectBuilder.build());

            }
            JsonObjectBuilder object = Json.createObjectBuilder();
            object.add("status",200);
            object.add("message","successfully collected");
            object.add("data",arrayBuilder.build());
            writer.print(object.build());
            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder object = Json.createObjectBuilder();
            object.add("status",200);
            object.add("message","successfully collected");
            object.add("data","");
            writer.print(object.build());
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
