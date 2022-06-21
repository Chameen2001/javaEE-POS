package servlets;

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

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;
    Connection connection = null;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("Application/json");

        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = preparedStatement.executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                int tel = resultSet.getInt(4);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id",id);
                objectBuilder.add("name",name);
                objectBuilder.add("address",address);
                objectBuilder.add("tel",tel);
                arrayBuilder.add(objectBuilder.build());
            }
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("status",200);
            jsonObjectBuilder.add("message","successfully collected");
            jsonObjectBuilder.add("data",arrayBuilder.build());
            PrintWriter writer = resp.getWriter();
            writer.println(jsonObjectBuilder.build());
            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("status",400);
            jsonObjectBuilder.add("message",throwables.getMessage());
            jsonObjectBuilder.add("data","");
            PrintWriter writer = resp.getWriter();
            writer.println(jsonObjectBuilder.build());
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");
        String tel = jsonObject.getString("tel");
        System.out.println(id+" "+name+" "+address+" "+tel);

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
            preparedStatement.setObject(1,id);
            preparedStatement.setObject(2,name);
            preparedStatement.setObject(3,address);
            preparedStatement.setObject(4,tel);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

            if (preparedStatement.executeUpdate()>0) {

                objectBuilder.add("status","200");
                objectBuilder.add("message","Successfully Added");
                objectBuilder.add("data","");
            }
            resp.setContentType("Application/json");
            PrintWriter writer = resp.getWriter();
            writer.print(objectBuilder.build());
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");
        String tel = jsonObject.getString("tel");
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET name=(?),address=(?),tel=(?) WHERE id=(?)");
            preparedStatement.setObject(1,name);
            preparedStatement.setObject(2,address);
            preparedStatement.setObject(3,tel);
            preparedStatement.setObject(4,id);
            if (preparedStatement.executeUpdate()>0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status","200");
                objectBuilder.add("message","Successfully updated");
                objectBuilder.add("data","");
                writer.print(objectBuilder.build());
                connection.close();
            }
        } catch (SQLException throwables) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status","400");
            objectBuilder.add("message",throwables.getMessage());
            objectBuilder.add("data","");
            writer.print(objectBuilder.build());
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusId = req.getParameter("cusId");
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE id=(?)");
            preparedStatement.setObject(1,cusId);
            if (preparedStatement.executeUpdate()>0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status","200");
                objectBuilder.add("message","Successfully deleted");
                objectBuilder.add("data","");
                writer.print(objectBuilder.build());
                connection.close();
            }
        } catch (SQLException throwables) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status","400");
            objectBuilder.add("message",throwables.getMessage());
            objectBuilder.add("data","");
            writer.print(objectBuilder.build());
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
