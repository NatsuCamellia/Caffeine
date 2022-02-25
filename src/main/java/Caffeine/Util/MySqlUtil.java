package Caffeine.util;

import java.sql.*;

import Caffeine.data.Userdata;

public class MySqlUtil {
    static final String JDBC_DRIVER = System.getenv("JDBC_DRIVER");
    static final String DB_URL = System.getenv("DB_URL");
    static final String DB_USER = System.getenv("DB_USER");
    static final String DB_PASS = System.getenv("DB_PASS");

    private Connection connection;
    private Statement statement;
    private String sql;

    public MySqlUtil() {
        try {
            Class.forName(JDBC_DRIVER);
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            this.statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }

    public Userdata getUserdata(String user_id) {
        try {
            sql = "SELECT * FROM caffeine.userdata WHERE id = %s";
            ResultSet resultSet = statement.executeQuery(String.format(sql, user_id));
            resultSet.next();
            Integer balance = resultSet.getInt("balance");
            String last_signed = resultSet.getString("last_signed");
            Userdata data = new Userdata(user_id, balance, last_signed);
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateUserdata(Userdata data) {
        String user_id = data.getUser_id();
        Integer balance = data.getBalance();
        String last_signed = data.getLast_signed();
        try {
            sql = "UPDATE caffeine.userdata SET balance = %d, last_signed = '%s' WHERE id = %s";
            statement.execute(String.format(sql, balance, last_signed, user_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
