package be.pxl.student.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getJdbcMysqlConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
