package com.rl.solenis.db.migration.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import com.rl.solenis.db.migration.config.ConnectionFactory;
import com.rl.solenis.db.migration.utility.VPNConnectionManager;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import com.rl.solenis.db.migration.config.DBConfig;
import com.rl.solenis.db.migration.model.ExtractionControlConfig;
import com.rl.solenis.db.migration.config.DBMigrationProperty;
import com.rl.solenis.db.migration.config.TargetDBConfig;
import com.rl.solenis.db.migration.config.SourceDBConfig;
import org.apache.log4j.Logger;

public class DBMigration
{
    private static final Logger logger;
    private int totalRecords;
    
    static {
        logger = Logger.getLogger(DBMigration.class);
    }
    
    public DBMigration() {
        this.totalRecords = 0;
    }
    
    public void startMigration(final SourceDBConfig sourceProperty, final TargetDBConfig targetProperty, final String configTable, final String OutFilePath, final String outputFormat, final String outputDelimitor) throws IOException, SQLException {
        final DBMigrationProperty property = new DBMigrationProperty(targetProperty);
        DBMigrationProperty.updateExtractionQuery(configTable);
        final List<ExtractionControlConfig> list = property.extractDbMigrationConfiguration();
        final long operationStart = System.currentTimeMillis();
        boolean flag = true;
        for (final ExtractionControlConfig config : list) {
            final boolean value = this.performMigration(config, sourceProperty,  OutFilePath, outputFormat, outputDelimitor);
            if (!value) {
                flag = false;
            }
        }
        final long operationEnd = System.currentTimeMillis();
        if (flag) {
            System.out.println("Extraction Successful");
        }
        else {
            System.out.println("Extraction Unsuccessful");
        }
        logger.info("Total Record Migrated " + this.totalRecords);
        logger.info("Total Time taken : " + (operationEnd - operationStart));
    }
    
    private boolean performMigration(final ExtractionControlConfig config, final DBConfig sourceProperty, final String OutFilePath, final String outputFormat, final String outputDelimitor) throws IOException, SQLException {
        logger.info("\n ============== Data extraction from Table : " + config.getSourceTable() + " ====================== \n");
        boolean flag = true;
        //VPNConnectionManager.connectVPN();
        final Connection sourceCon = ConnectionFactory.createConnection(sourceProperty);
        int count = 0;
        final List<String> rows = new ArrayList<String>(1000);
        final File file = new File(OutFilePath + config.getTargetTable() + "." + outputFormat);
            try {
                final FileWriter writer = new FileWriter(file);
                try {
                    final BufferedWriter bfWriter = new BufferedWriter(writer);
                    try {
                        final Statement sourceStatement = sourceCon.createStatement();
                        sourceStatement.setFetchSize(200000);
                        final long startTime = System.currentTimeMillis();
                        final ResultSet sourceRs = sourceStatement.executeQuery(config.getSourceQuery());
                        final int totalColumnCount = this.getColumnCount(config);
                        while (sourceRs.next()) {
                            final StringBuilder sb = new StringBuilder();
                            for (int i = 1; i <= totalColumnCount; ++i) {
                                sb.append(sourceRs.getObject(i) + outputDelimitor);
                            }
                            rows.add(String.valueOf(sb.toString()) + "\n");
                            ++count;
                        }
                        final long fileWriteTime = System.currentTimeMillis();
                        for (final String record : rows) {
                            bfWriter.write(record);
                        }
                        bfWriter.flush();
                        final long fileWriteEndTime = System.currentTimeMillis();
                        logger.info("Time Taken to write into file for records : " + count + " Time : " + (fileWriteEndTime - fileWriteTime));
                        final long endTime = System.currentTimeMillis();
                        logger.info("Inserted records : " + count);
                        logger.info("Time taken to migrate data : " + (endTime - startTime));
                        this.totalRecords += count;
                        logger.info("\n ========== Data Inserted to file : " + config.getTargetTable() + "." + outputFormat + "=========== \n\n");
                    }
                    finally {
                        if (bfWriter != null) {
                            bfWriter.close();
                        }
                    }
                    if (writer != null) {
                        writer.close();
                    }
                }
                finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
        }
        catch (Exception e) {
            logger.error("Unable to insert data into table " + config.getTargetTable(), e);
            flag = false;
            return flag;
        }
        finally {
            ConnectionFactory.closeConnection(sourceCon);
        }
        ConnectionFactory.closeConnection(sourceCon);
        return flag;
    }
    
    public int getColumnCount(final ExtractionControlConfig config) {
        final String query = config.getSourceQuery();
        final String electColumns = query.substring(query.indexOf(32), query.indexOf("from"));
        final String[] columns = electColumns.trim().split(",");
        return columns.length;
    }
}
