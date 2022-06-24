package servlets;

import bo.BOFactory;
import bo.SuperBO;
import bo.custom.ItemBO;
import bo.custom.impl.ItemBOImpl;
import dao.CrudUtil;
import dto.ItemDTO;

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
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;
    ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBOImpl(BOFactory.BOType.ITEM_BO);
    Connection connection = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            connection = dataSource.getConnection();
            ArrayList<ItemDTO> allItem = itemBO.getAllItem(connection);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            allItem.forEach(itemDTO -> {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", itemDTO.getId());
                objectBuilder.add("name", itemDTO.getName());
                objectBuilder.add("qty", itemDTO.getQtyOnHand());
                objectBuilder.add("price", itemDTO.getUnitPrice());
                arrayBuilder.add(objectBuilder.build());
            });
            JsonObjectBuilder object = Json.createObjectBuilder();
            object.add("status",200);
            object.add("message","successfully collected");
            object.add("data",arrayBuilder.build());
            writer.print(object.build());
        } catch (SQLException throwables) {
            sendServerSideError(throwables, writer);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    private void sendServerSideError(SQLException throwables, PrintWriter writer) {
        JsonObjectBuilder object = Json.createObjectBuilder();
        object.add("status", 200);
        object.add("message", throwables.getMessage());
        object.add("data", "");
        writer.print(object.build());
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
