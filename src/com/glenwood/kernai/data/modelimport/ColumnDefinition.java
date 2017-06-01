package com.glenwood.kernai.data.modelimport;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ColumnDefinition {
	private final String name;
	private final int dataType;
	private final String dbTypeName;
	private final short sourceDataType;
	private final int size;
	private final int nullable;
	private final String remarks;
	private final String defaultValue;
	private final String isNullable;
	private final String isAutoIncrement;
	//private final String isGenerated;
	private final TableDefinition table;
	
	public ColumnDefinition(String name, int dataType, String dbTypeName, short sourceDataType, int size, int nullable, String remarks,
			String defaultValue, String isNullable, String isAutoIncrement, TableDefinition table) {
		super();
		this.name = name;
		this.dataType = dataType;
		this.dbTypeName = dbTypeName;
		this.sourceDataType = sourceDataType;
		this.size = size;
		this.nullable = nullable;
		this.remarks = remarks;
		this.defaultValue = defaultValue;
		this.isNullable = isNullable;
		this.isAutoIncrement = isAutoIncrement;
		//this.isGenerated = isGenerated;
		this.table = table;
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
	
	

	public short getSourceDataType() {
		return sourceDataType;
	}

	public TableDefinition getTable() {
		return table;
	}
	
	protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.removePropertyChangeListener(listener);
	}
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnDefinition other = (ColumnDefinition) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}

	/*
	public String getIsGenerated() {
		return isGenerated;
	}
	*/
	
	
	
	
	

}
