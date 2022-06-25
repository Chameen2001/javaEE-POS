package servlets;

import bo.BOFactory;
import bo.SuperBO;
import bo.custom.ItemBO;
import bo.custom.impl.ItemBOImpl;
import dao.CrudUtil;
import dto.ItemDTO;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        PrintWriter writer = resp.getWriter();
        try {
            connection = dataSource.getConnection();
            if (itemBO.addItem(connection, new ItemDTO(jsonObject.getString("id"), jsonObject.getString("name"), Integer.parseInt(jsonObject.getString("qty")), Double.parseDouble(jsonObject.getString("price"))))) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", "200");
                objectBuilder.add("message", "successfully added");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
        } catch (SQLException throwables) {
            sendServerSideError(throwables, writer);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        PrintWriter writer = resp.getWriter();
        try {
            connection = dataSource.getConnection();
            if (itemBO.updateItem(connection, new ItemDTO(jsonObject.getString("id"), jsonObject.getString("name"), Integer.parseInt(jsonObject.getString("qty")), Double.parseDouble(jsonObject.getString("price"))))) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", "200");
                objectBuilder.add("message", "successfully update");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
        } catch (SQLException throwables) {
            sendServerSideError(throwables, writer);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        try {
            connection = dataSource.getConnection();
            if (itemBO.deleteItem(connection, req.getParameter("itemId"))) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", "200");
                objectBuilder.add("message", "Successfully deleted");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
        } catch (SQLException throwables) {
            sendServerSideError(throwables, writer);
        }
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
