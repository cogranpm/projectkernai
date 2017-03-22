package com.glenwood.kernai.data.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ImportTable extends BaseEntity {

	
	public static final String TYPE_NAME = "IMPORTTABLE";
	
	private String name;
	private String databaseName;
	private String importDefinitionId;
	
	public ImportTable()
	{
		super();
		this.type = TYPE_NAME;
	}
	
	public ImportTable(ImportDefinition parent)
	{
		this();
		this.importDefinitionId = parent.getId();
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		firePropertyChange("name", oldValue, this.name);
	}

	@JsonProperty
	public String getImportDefinitionId() {
		return importDefinitionId;
	}

	public void setImportDefinitionId(String importDefinitionId) {
		String oldValue = this.importDefinitionId;
		this.importDefinitionId = importDefinitionId;
		firePropertyChange("importDefinitionId", oldValue, this.importDefinitionId);
	}
	
	@JsonProperty
	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		String oldValue = this.databaseName;
		this.databaseName = databaseName;
		firePropertyChange("databaseName", oldValue, this.databaseName);
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.databaseName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImportTable other = (ImportTable) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}
	
}
