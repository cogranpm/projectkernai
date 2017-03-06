package com.glenwood.kernai.data.modelimport;

public class PrimaryKeyDefinition {
	
	private final String columnName;
	private final String keyName;
	private final short sequence;
	private final TableDefinition table;
	
	public PrimaryKeyDefinition(String columnName, String keyName, short sequence, TableDefinition table)
	{
		this.columnName = columnName;
		this.keyName = keyName;
		this.sequence = sequence;
		this.table = table;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getKeyName() {
		return keyName;
	}

	public short getSequence() {
		return sequence;
	}

	public TableDefinition getTable() {
		return table;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result + ((keyName == null) ? 0 : keyName.hashCode());
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
		PrimaryKeyDefinition other = (PrimaryKeyDefinition) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (keyName == null) {
			if (other.keyName != null)
				return false;
		} else if (!keyName.equals(other.keyName))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}
	
	
	
	

}
