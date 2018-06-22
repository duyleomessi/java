package car.dbConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbHandler {
    String url;
    String username;
    String password;
    String dbUrl;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public Connection getConnection() {
        Properties p = new Properties();

        try {
            p.load(new FileInputStream("database.properties"));
            // get properties
            url = p.getProperty("url");
            username = p.getProperty("username");
            password = p.getProperty("password");

            Class.forName("com.mysql.jdbc.Driver");
            dbUrl = url + "?user=" + username + "&password=" + password; //+ "?autoReconnect=true&useSSL=false";
            connection = DriverManager.getConnection(dbUrl);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (connection == null) {
            throw new NullPointerException("Connection is null");
        }

        return connection;
    }

    // check if user is already exist in database
    public boolean isUserExists(String userName, String tableName) {
        boolean isExists = false;
        resultSet = null;
        DbHandler dbHandler = new DbHandler();
        connection = dbHandler.getConnection();

        String getUserByNameQuery = "select * from " + tableName + " where username=?";
        try {
            preparedStatement = connection.prepareStatement(getUserByNameQuery);
            preparedStatement.setString(1, userName);
            resultSet= preparedStatement.executeQuery();

            if (resultSet.next()) {
               isExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return  isExists;
    }

    public boolean isAccountExists(String userName, String password, String tableName) {
        boolean isExists = false;
        resultSet = null;
        DbHandler dbHandler = new DbHandler();
        connection = dbHandler.getConnection();

        String query = "select * from " + tableName + " where username=? and password=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            resultSet= preparedStatement.executeQuery();

            if (resultSet.next()) {
               isExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return  isExists;
    }
}
