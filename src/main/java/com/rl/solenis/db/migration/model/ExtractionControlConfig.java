package com.rl.solenis.db.migration.model;

public class ExtractionControlConfig
{
    private int id;
    private String sourceTable;
    private String sourceQuery;
    private String targetTable;
    private String targetQuery;
    private String outputPath;
    public ExtractionControlConfig() {
    }
    
    public ExtractionControlConfig(final int id, final String sourceTable, final String sourceQuery, final String targetTable, final String targetQuery) {
        this.id = id;
        this.sourceTable = sourceTable;
        this.sourceQuery = sourceQuery;
        this.targetTable = targetTable;
        this.targetQuery = targetQuery;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getSourceTable() {
        return this.sourceTable;
    }
    
    public void setSourceTable(final String sourceTable) {
        this.sourceTable = sourceTable;
    }
    
    public String getSourceQuery() {
        return this.sourceQuery;
    }
    
    public void setSourceQuery(final String sourceQuery) {
        this.sourceQuery = sourceQuery;
    }
    
    public String getTargetTable() {
        return this.targetTable;
    }
    
    public void setTargetTable(final String targetTable) {
        this.targetTable = targetTable;
    }
    
    public String getTargetQuery() {
        return this.targetQuery;
    }
    
    public void setTargetQuery(final String targetQuery) {
        this.targetQuery = targetQuery;
    }
    
    @Override
    public String toString() {
        return "ExtractionConfig [id=" + this.id + ", sourceTable=" + this.sourceTable + ", sourceQuery=" + this.sourceQuery + ", targetTable=" + this.targetTable + ", targetQuery=" + this.targetQuery + "]";
    }

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
}
