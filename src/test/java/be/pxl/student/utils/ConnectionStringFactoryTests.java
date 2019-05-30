package be.pxl.student.utils;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionStringFactoryTests {
    @Test
    public void getJdbcMysqlConnectionString_shouldReturnConnectionString_string(){
        String expected = "jdbc:mysql://192.168.33.22/StudentDB";
        String actual =
                ConnectionStringFactory.getJdbcMysqlConnectionString("192.168.33.22", "StudentDB");

        Assert.assertEquals(expected, actual);
    }
}
