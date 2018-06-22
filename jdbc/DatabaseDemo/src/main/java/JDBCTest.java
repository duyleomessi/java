import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTest {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String username;
    private String password;
    private String url;
    private String dbUrl;
    private Connection connection;
    private Statement sqlStatement;

    public boolean initialize(byte dbmsType) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("database.properties"));

            switch (dbmsType) {
                case 0:
                    url = p.getProperty("url");
                    username = p.getProperty("username");
                    password = p.getProperty("password");
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        dbUrl = url + "?user=" + username + "&password=" + password;
                        connection = DriverManager.getConnection(dbUrl);
                        System.out.println(dbUrl);
                        break;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                case 1:
                    username = p.getProperty("user");
                    password = p.getProperty("pass");

                    break;
                default:
                    throw new IllegalArgumentException("Invalid DBMS type");
            }

            // System.out.println(username + " " + password);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection == null) {
            throw new NullPointerException("Connection is null");
        }

        return true;
    }

    public void createStatement() {
        if (sqlStatement == null) {
            try {
                sqlStatement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet retrieveData(String sqlQuery) {
        createStatement();
        try {
            ResultSet resultSet = sqlStatement.executeQuery(sqlQuery);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void executeUpdate(String sqlUpdate) {
        int result;
        createStatement();
        try {
            result = sqlStatement.executeUpdate(sqlUpdate);
            System.out.println(result + " affect");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printDb() throws SQLException {
        String sqlCommand = "select * from comments";
        ResultSet resultSet = this.retrieveData(sqlCommand);

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
        }

        System.out.println();
    }

    public static void main(String[] args) throws SQLException {
        JDBCTest db = new JDBCTest();
        db.initialize((byte) 0);
        String sqlCommand;
        ResultSet resultSet;

        db.printDb();

        sqlCommand = "insert into comments value (default, 'duy', 'duy@gmail.com', 'http://duy.com', '2018-09-23', 'summary', 'comment')";
        db.executeUpdate(sqlCommand);

        sqlCommand = "select * from comments";
        resultSet = db.retrieveData(sqlCommand);
        System.out.println();

        System.out.println("After insert");
        db.printDb();

        System.out.println("After delete");
        sqlCommand = "delete from comments where email = 'duy@gmail.com'";
        db.executeUpdate(sqlCommand);
        db.printDb();
    }

}
