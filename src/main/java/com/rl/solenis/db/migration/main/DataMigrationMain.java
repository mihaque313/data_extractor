
package com.rl.solenis.db.migration.main;

import com.rl.solenis.db.migration.process.DBMigration;
import com.rl.solenis.db.migration.config.TargetDBConfig;
import com.rl.solenis.db.migration.config.SourceDBConfig;
import com.rl.solenis.db.migration.utility.VPNConnectionManager;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DataMigrationMain
{
    private static final Logger logger;
    
    static {
        logger = Logger.getLogger(DataMigrationMain.class);
    }
    
    public static void main(final String[] args) throws Exception {
    	/*
    	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                VPNConnectionManager.disconnectVPN();
            }
        }));
        */
    	final Properties props = new Properties();
        props.load(new FileInputStream(args[0]));
        String configTable = props.getProperty("configTable");
        String outFilePath = props.getProperty("outFilePath");
        String outputFormat = props.getProperty("outputFormat");
        String outputDelimitor = props.getProperty("outputDelimitor");
        final SourceDBConfig sourceProperty = new SourceDBConfig(args[0]);
        final TargetDBConfig targetProperty = new TargetDBConfig(args[0]);

        logger.info("\n ========================  Migration Started ======================== \n");        
        logger.info("Source DB: " + props.getProperty("sourceUrl"));
        logger.info("Reading Configuration from: " + props.getProperty("targetUrl"));
        logger.info("Output File location: " + outFilePath);

        final DBMigration dbMigration = new DBMigration();
        dbMigration.startMigration(sourceProperty, targetProperty, configTable, outFilePath, outputFormat, outputDelimitor);
        logger.info("Target  path: " + outFilePath);
        logger.info("\n\n ======================== Migration Completed ======================== \n\n");
    }
}
