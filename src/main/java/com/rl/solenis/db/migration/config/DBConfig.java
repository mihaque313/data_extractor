package com.rl.solenis.db.migration.config;

public interface DBConfig
{
    String getDriverClass();
    
    String getUrl();
    
    String getUserName();
    
    String getPassword();
}
