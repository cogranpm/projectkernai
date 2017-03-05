package com.glenwood.kernai.data.modelimport;

public class ColumnDefinition {
	private String name;
	private int dataType;
	private String dbTypeName;
	private int size;
	private int nullable;
	private String remarks;
	private String defaultValue;
	private String isNullable;
	private String isAutoIncrement;
	private String isGenerated;
	
	public ColumnDefinition(String name, int dataType, String dbTypeName, int size, int nullable, String remarks,
			String defaultValue, String isNullable, String isAutoIncrement, String isGenerated) {
		super();
		this.name = name;
		this.dataType = dataType;
		this.dbTypeName = dbTypeName;
		this.size = size;
		this.nullable = nullable;
		this.remarks = remarks;
		this.defaultValue = defaultValue;
		this.isNullable = isNullable;
		this.isAutoIncrement = isAutoIncrement;
		this.isGenerated = isGenerated;
	}

	public String getName() {
		return name;
	}

	public int getDataType() {
		return dataType;
	}

	public String getDbTypeName() {
		return dbTypeName;
	}

	public int getSize() {
		return size;
	}

	public int getNullable() {
		return nullable;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public String getIsAutoIncrement() {
		return isAutoIncrement;
	}

	public String getIsGenerated() {
		return isGenerated;
	}
	
	
	

}
