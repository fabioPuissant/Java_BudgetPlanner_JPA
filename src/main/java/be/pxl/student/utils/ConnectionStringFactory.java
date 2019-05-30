package be.pxl.student.utils;

public class ConnectionStringFactory {
    public static String getJdbcMysqlConnectionString(String url, String databaseName){
        return "jdbc:mysql://" + url + "/" + databaseName;
    }
}
