package be.pxl.student.utils;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryTests {
    @Test
    public void getJdbcMysqlConnection_shouldReturnConnection() throws SQLException {
        String connectionString = ConnectionStringFactory
                        .getJdbcMysqlConnectionString("192.168.33.22", "StudentDBTest");
        Connection con = ConnectionFactory.getJdbcMysqlConnection(connectionString, "student", "root");

        Assert.assertTrue(con.isValid(0));
    }
}
