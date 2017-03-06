package com.glenwood.kernai.data.modelimport;

public class ForeignKeyDefinition {
	
	private final TableDefinition table;
	private final String tableName;
	private final String columnName;
	private final short keySequence;
	
	public ForeignKeyDefinition(String tableName, String columnName, short keySequence, TableDefinition table)
	{
		this.tableName = tableName;
		this.columnName = columnName;
		this.keySequence = keySequence;
		this.table = table;
	}

	public TableDefinition getTable() {
		return table;
	}

	public String getTableName() {
		return tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public short getKeySequence() {
		return keySequence;
	}
	
	

}
