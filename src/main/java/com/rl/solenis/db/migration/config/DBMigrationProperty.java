package com.rl.solenis.db.migration.config;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import com.rl.solenis.db.migration.model.ExtractionControlConfig;
import java.util.List;
import org.apache.log4j.Logger;

public class DBMigrationProperty
{
    private static final Logger logger;
    private TargetDBConfig config;
    private static String QUERY_CONFIG_EXTRACTION = null;
    
    public static void updateExtractionQuery(String configTable) {
    	QUERY_CONFIG_EXTRACTION = "select id, source_table, source_query, target_table, target_query from " + configTable;
	}
    
    static {
        logger = Logger.getLogger(DBMigrationProperty.class);
    }
    
    public DBMigrationProperty(final TargetDBConfig config) {
        this.config = config;
    }
    
    public List<ExtractionControlConfig> extractDbMigrationConfiguration() throws IOException {
        final List<ExtractionControlConfig> configs = new ArrayList<ExtractionControlConfig>();
            try {
                final Connection con = ConnectionFactory.createConnection(this.config);
                try {
                    final Statement stmt = con.createStatement();
                    try {
                    	logger.info("Extraction Config query: " + QUERY_CONFIG_EXTRACTION);
                        final ResultSet rs = stmt.executeQuery(QUERY_CONFIG_EXTRACTION);
                        try {
                            while (rs.next()) {
                                final ExtractionControlConfig extractionConfig = new ExtractionControlConfig();
                                extractionConfig.setId(rs.getInt("id"));
                                extractionConfig.setSourceTable(rs.getString("source_table"));
                                extractionConfig.setSourceQuery(rs.getString("source_query"));
                                extractionConfig.setTargetTable(rs.getString("target_table"));
                                extractionConfig.setTargetQuery(rs.getString("target_query"));
                                configs.add(extractionConfig);
                            }
                        }catch (Exception e) {
                			logger.error("Error in extractDbMigrationConfigurion Method", e);
                			throw e;
                		}   
                        finally {
                            if (rs != null) {
                                rs.close();
                            }
                        }
                        if (stmt != null) {
                            stmt.close();
                        }
                    }catch (Exception e) {
                    		throw e;
                    }
                    finally {
                        if (stmt != null) {
                            stmt.close();
                        }
                    }
                    if (con != null) {
                        con.close();
                    }
                }
                finally {
                    }
                    if (con != null) {
                        con.close();
                    }
        }
        catch (Exception e) {
            logger.error("Unable to fetch data from table : extraction_config", e);
            System.exit(0);
        }
        logger.info("Total Tables to be Migrated : " + configs.size());
        return configs;
    }
}
