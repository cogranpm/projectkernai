package com.glenwood.kernai.data.modelimport;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;



public class TableDefinition {
	
	private final String name;
	private final List<ColumnDefinition> columns;
	private final DatabaseDefinition database;
	private final List<PrimaryKeyDefinition> primaryKeys;
	private final List<ForeignKeyDefinition> foreignKeys;

	public TableDefinition(String name, DatabaseDefinition database)
	{
		this.name = name;
		this.columns = new ArrayList<ColumnDefinition>();
		this.primaryKeys = new ArrayList<PrimaryKeyDefinition>();
		this.foreignKeys = new ArrayList<ForeignKeyDefinition>();
		this.database = database;
	}

	public String getName() {
		return name;
	}

	public List<ColumnDefinition> getColumns() {
		return columns;
	}

	public DatabaseDefinition getDatabase() {
		return database;
	}
	
	

	public List<PrimaryKeyDefinition> getPrimaryKeys() {
		return primaryKeys;
	}

	
	
	
	public List<ForeignKeyDefinition> getForeignKeys() {
		return foreignKeys;
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
		result = prime * result + ((database == null) ? 0 : database.hashCode());
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
		TableDefinition other = (TableDefinition) obj;
		if (database == null) {
			if (other.database != null)
				return false;
		} else if (!database.equals(other.database))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	

	
}
