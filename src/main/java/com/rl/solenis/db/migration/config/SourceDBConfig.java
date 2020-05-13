
package com.rl.solenis.db.migration.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;

public class SourceDBConfig implements DBConfig
{
    private String sourceDriverClass;
    private String sourceUrl;
    private String sourceUserName;
    private String sourcePassword;
    
    public SourceDBConfig(final String dbPropertyFilePath) throws IOException {
        final Properties props = new Properties();
        props.load(new FileInputStream(dbPropertyFilePath));
        this.sourceDriverClass = props.getProperty("sourceDriverclass");
        this.sourceUrl = props.getProperty("sourceUrl");
        this.sourceUserName = props.getProperty("sourceUsername");
        this.sourcePassword = props.getProperty("sourcePassword");
    }
    
    @Override
    public String getDriverClass() {
        return this.sourceDriverClass;
    }
    
    public void setSourceDriverClass(final String sourceDriverClass) {
        this.sourceDriverClass = sourceDriverClass;
    }
    
    @Override
    public String getUrl() {
        return this.sourceUrl;
    }
    
    public void setSourceUrl(final String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
    
    @Override
    public String getUserName() {
        return this.sourceUserName;
    }
    
    public void setSourceUserName(final String sourceUserName) {
        this.sourceUserName = sourceUserName;
    }
    
    @Override
    public String getPassword() {
        return this.sourcePassword;
    }
    
    public void setSourcePassword(final String sourcePassword) {
        this.sourcePassword = sourcePassword;
    }
    
    @Override
    public String toString() {
        return "SourceDBConfig [sourceDriverClass=" + this.sourceDriverClass + ", sourceUrl=" + this.sourceUrl + ", sourceUserName=" + this.sourceUserName + ", sourcePassword=" + this.sourcePassword + "]";
    }
}
