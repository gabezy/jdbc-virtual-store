package com.jdbc.factory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;
import java.sql.*;

public class ConnectionFactory {
    private String DATABASE_URL = "jdbc:mysql://localhost:3306/virtual_store";
    private String timezoneSettings = "?useTimezone=true&serverTimezone=UTC";
    private String user = "";
    private String password = "";
    private DataSource dataSource;
    private Connection connection;

    public ConnectionFactory () {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setJdbcUrl(this.DATABASE_URL + this.timezoneSettings);
        comboPooledDataSource.setUser(this.user);
        comboPooledDataSource.setPassword(this.password);

        // setting connection pool
        comboPooledDataSource.setMaxPoolSize(10);
        this.dataSource = comboPooledDataSource;
    }

    public Connection connection() throws SQLException {
        try {
            this.connection = this.dataSource.getConnection();
            return this.connection;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

}
