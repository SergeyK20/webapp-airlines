package connection;

import org.apache.commons.dbcp.BasicDataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class  ConnectionPool {
    private ConnectionPool() {
    }

    private static BasicDataSource ods;

     static {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionPool.class.getResourceAsStream("/database.properties"));
            ods = new BasicDataSource();
            ods.setUrl(properties.getProperty("jdbc.urlCP"));
            ods.setUsername(properties.getProperty("jdbc.user"));
            ods.setPassword(properties.getProperty("jdbc.password"));
            Properties cacheProps = new Properties();
            cacheProps.setProperty("MinLimit", "0");
            cacheProps.setProperty("MaxLimit", "4");
            cacheProps.setProperty("InitialLimit", "1");
            cacheProps.setProperty("ConnectionWaitTimeout", "5");
            cacheProps.setProperty("ValidateConnection", "true");
            ods.setDriverClassName("com.mysql.cj.jdbc.Driver");
            ods.setConnectionProperties(String.valueOf(cacheProps));
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return ods.getConnection();
    }
}
