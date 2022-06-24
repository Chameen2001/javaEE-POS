package servlets;

import bo.BOFactory;
import bo.custom.CustomerBO;
import dao.DAOFactory;
import dto.CustomerDTO;

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
import java.sql.*;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;
    Connection connection = null;

    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBOImpl(BOFactory.BOType.CUSTOMER_BO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("Application/json");
        PrintWriter writer = resp.getWriter();

        try {
            connection = dataSource.getConnection();
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers(connection);
            JsonObjectBuilder object = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            allCustomers.forEach(customer -> {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id",customer.getId());
                objectBuilder.add("name",customer.getName());
                objectBuilder.add("address",customer.getAddress());
                objectBuilder.add("tel",customer.getTel());
                arrayBuilder.add(objectBuilder.build());
            });
            object.add("status","200");
            object.add("message","successfully collected");
            object.add("data",arrayBuilder.build());
            writer.println(object.build());
        } catch (SQLException throwables) {
            sendServerSideError(throwables, writer);
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("Application/json");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        PrintWriter writer = resp.getWriter();

        try {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            connection = dataSource.getConnection();
            if (customerBO.addCustomer(connection, new CustomerDTO(jsonObject.getString("id"), jsonObject.getString("name"), jsonObject.getString("address"), Integer.parseInt(jsonObject.getString("tel"))))) {
                objectBuilder.add("status", "200");
                objectBuilder.add("message", "Successfully Added");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }

        } catch (SQLException throwables) {
            sendServerSideError(throwables, writer);
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        try {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            connection = dataSource.getConnection();
            if (customerBO.updateCustomer(connection, new CustomerDTO(jsonObject.getString("id"), jsonObject.getString("name"), jsonObject.getString("address"), Integer.parseInt(jsonObject.getString("tel"))))) {
                objectBuilder.add("status", "200");
                objectBuilder.add("message", "Successfully updated");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
        } catch (SQLException throwables) {
            sendServerSideError(throwables, writer);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = dataSource.getConnection();
            if (customerBO.deleteCustomer(connection, req.getParameter("cusId"))) {
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
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("status", "400");
        objectBuilder.add("message", throwables.getMessage());
        objectBuilder.add("data", "");
        writer.print(objectBuilder.build());
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
