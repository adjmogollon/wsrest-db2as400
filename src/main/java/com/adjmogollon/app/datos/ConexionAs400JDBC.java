package com.adjmogollon.app.datos;

import com.adjmogollon.app.ApplicationProperties;
import java.sql.*;
import com.ibm.as400.access.AS400JDBCDriver;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConexionAs400JDBC {

    private static  String JDBC_URL;
    private static  String JDBC_USER;
    private static  String JDBC_PASS;

    public ConexionAs400JDBC() throws IOException {

        JDBC_URL = ApplicationProperties.INSTANCE.getIpAs400();
        JDBC_USER = ApplicationProperties.INSTANCE.getUsuarioAs400();
        JDBC_PASS = ApplicationProperties.INSTANCE.getPasswordAs400();
    }

    public static AS400JDBCDriver getAS400JDBCDriver() {
        AS400JDBCDriver driver = new AS400JDBCDriver();
        return driver;

    }

    public Connection getConnection() throws SQLException {

        Properties prop = new Properties();
        prop.setProperty("user", JDBC_USER);
        prop.setProperty("password", JDBC_PASS);
        Connection conn = getAS400JDBCDriver().connect(JDBC_URL, prop);
        return conn;
    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(PreparedStatement stmt) {
        try {
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

}
