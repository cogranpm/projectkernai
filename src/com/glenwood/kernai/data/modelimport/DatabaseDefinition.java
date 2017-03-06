package com.glenwood.kernai.data.modelimport;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDefinition {
	
	private final String name;
	private final List<TableDefinition> tables;
	private final List<UserDefinedTypeDefinition> userDefinedTypes;
	
	protected DatabaseDefinition()
	{
		this.name = null;
		this.tables = null;
		this.userDefinedTypes = null;
	}
	
	public DatabaseDefinition(String name)
	{
		this.name = name;
		this.tables = new ArrayList<TableDefinition>();
		this.userDefinedTypes = new ArrayList<UserDefinedTypeDefinition>();
	}

	public String getName() {
		return name;
	}

	public List<TableDefinition> getTables() {
		return tables;
	}
	
	

	public List<UserDefinedTypeDefinition> getUserDefinedTypes() {
		return userDefinedTypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DatabaseDefinition other = (DatabaseDefinition) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
