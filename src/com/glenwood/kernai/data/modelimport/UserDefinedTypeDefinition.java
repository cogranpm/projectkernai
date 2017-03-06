package com.glenwood.kernai.data.modelimport;

public class UserDefinedTypeDefinition {
	private final String typeName;
	private final String className;
	private final int dataType;
	private final short baseType;
	private DatabaseDefinition database;
	
	public UserDefinedTypeDefinition(String typeName, String className, int dataType, short baseType,
			DatabaseDefinition database) {
		super();
		this.typeName = typeName;
		this.className = className;
		this.dataType = dataType;
		this.baseType = baseType;
		this.database = database;
	}

	public DatabaseDefinition getDatabase() {
		return database;
	}

	public void setDatabase(DatabaseDefinition database) {
		this.database = database;
	}

	public String getTypeName() {
		return typeName;
	}

	public String getClassName() {
		return className;
	}

	public int getDataType() {
		return dataType;
	}

	public short getBaseType() {
		return baseType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDefinedTypeDefinition [typeName=");
		builder.append(typeName);
		builder.append(", className=");
		builder.append(className);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append(", baseType=");
		builder.append(baseType);
		builder.append(", database=");
		builder.append(database);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
