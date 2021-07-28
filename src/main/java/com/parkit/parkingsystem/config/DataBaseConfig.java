package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DataBaseConfig {
    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger("DataBaseConfig");

    /**
     * method for connectiong to the database.
     * @return DriverManager.getConnection
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public Connection getConnection() throws ClassNotFoundException,
                                             SQLException,
                                             IOException {
        LOGGER.info("Create DB connection");

        Properties dbProperties = new Properties();
        InputStream inputStream = getClass().getClassLoader().
                getResourceAsStream("log4j2.properties");
        dbProperties.load(inputStream);
        String driver = dbProperties.getProperty("driver");
        String url = dbProperties.getProperty("url");
        String username = dbProperties.getProperty("username");
        String password = dbProperties.getProperty("password");

        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * allows to close Connection with sql error handling.
     * @param con
     */
    public void closeConnection(final Connection con) {
        if (con != null) {
            try {
                con.close();
                LOGGER.info("Closing DB connection");
            } catch (SQLException e) {
                LOGGER.error("Error while closing connection", e);
            }
        }
    }

    /**
     * allows to close PreparedStatement with sql error handling.
     * @param ps
     */
    public void closePreparedStatement(final PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
                LOGGER.info("Closing Prepared Statement");
            } catch (SQLException e) {
                LOGGER.error("Error while closing prepared statement", e);
            }
        }
    }

    /**
     * allows to close resultSet with sql error handling.
     * @param rs
     */
    public void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                LOGGER.info("Closing Result Set");
            } catch (SQLException e) {
                LOGGER.error("Error while closing result set", e);
            }
        }
    }
}
