package com.rl.solenis.db.migration.config;

import java.sql.DriverManager;
import java.sql.Connection;
import org.apache.log4j.Logger;

public class ConnectionFactory
{
    private static final Logger logger;
    
    static {
        logger = Logger.getLogger(ConnectionFactory.class);
    }
    
    private ConnectionFactory() {
    }
    
    public static Connection createConnection(final DBConfig config) {
        Connection con = null;
        try {
            Class.forName(config.getDriverClass());
            con = DriverManager.getConnection(config.getUrl(), config.getUserName(), config.getPassword());
        }
        catch (Exception e) {
            logger.error("Unable to Create Connection to " + config.getUrl());
            logger.error("Please check connection properties and data_extractor.log for detailed exception message!");
            System.out.println("Extraction Unsuccessful");
            logger.info(e);
            System.exit(1);
        }
        return con;
    }
    
    public static void closeConnection(final Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        }
        catch (Exception e) {
            logger.error("Unable to close connection", e);
        }
    }
}
