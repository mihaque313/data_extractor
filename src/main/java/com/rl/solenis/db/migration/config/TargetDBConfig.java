package com.rl.solenis.db.migration.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;

public class TargetDBConfig implements DBConfig
{
    private String targetDriverClass;
    private String targetUrl;
    private String targetUserName;
    private String targetPassword;
    
    public TargetDBConfig(final String dbPropertyFilePath) throws IOException {
        final Properties props = new Properties();
        props.load(new FileInputStream(dbPropertyFilePath));
        this.targetDriverClass = props.getProperty("targetDriverclass");
        this.targetUrl = props.getProperty("targetUrl");
        this.targetUserName = props.getProperty("targetUsername");
        this.targetPassword = props.getProperty("targetPassword");
    }
    
    public String getDriverClass() {
        return this.targetDriverClass;
    }
    
    public void setTrargetDriverClass(final String targetDriverClass) {
        this.targetDriverClass = targetDriverClass;
    }
    
    public String getUrl() {
        return this.targetUrl;
    }
    
    public void setTargetUrl(final String targetUrl) {
        this.targetUrl = targetUrl;
    }
    
    public String getUserName() {
        return this.targetUserName;
    }
    
    public void setTargetUserName(final String targetUserName) {
        this.targetUserName = targetUserName;
    }
    
    public String getPassword() {
        return this.targetPassword;
    }
    
    public void setTargetPassword(final String targetPassword) {
        this.targetPassword = targetPassword;
    }

}
