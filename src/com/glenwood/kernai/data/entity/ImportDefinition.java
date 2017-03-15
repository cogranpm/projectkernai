/* entity represents saved settings for an import 
 * import can be run over and over
 */

package com.glenwood.kernai.data.entity;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;


@JsonIgnoreProperties(ignoreUnknown=true)
public class ImportDefinition extends BaseEntity {
	
	public static final String TYPE_NAME = "IMPORTDEFINITION";
	
	/* which connection is being used - um could be oracle, mysql or mssql
	 * is it ok to reference the base abstract class here?
	 * user interface should use a modal popup to let user specify the connection,
	 * including add/edit capability
	 *  */
	DataConnection dataConnection;
	String dataConnectionId;
	
	/* list of table definitions, which is entity with database name & table name
	 * this is all that needs to be saved in order to re-run the import
	 * note that these are different from table definitions which are instantiated
	 * as the import is running, and has the foreign & primary keys, columns etc
	 * none of which should be saved for future runs, its just hydrated into the 
	 * target model
	 */
	
	List<ImportTable> selectedImportTables;

	/* imports are defined per project */
	private String projectId;
	
	private Time lastRun;
	

	public ImportDefinition()
	{
		super();
		this.type = TYPE_NAME;
	}
	
	public ImportDefinition(Project parent)
	{
		if(parent == null || parent.getId() == null)
		{
			throw new IllegalArgumentException("Supplied Project was null or did not have valid id");
		}
		this.projectId = parent.getId();
	}
	
	@JsonIgnore
	public DataConnection getDataConnection() {
		return dataConnection;
	}

	public void setDataConnection(DataConnection dataConnection) {
		DataConnection oldValue = this.dataConnection;
		this.dataConnection = dataConnection;
		this.firePropertyChange("dataConnection", oldValue, this.dataConnection);
	}

	@JsonProperty
	public String getDataConnectionId() {
		return dataConnectionId;
	}

	public void setDataConnectionId(String dataConnectionId) {
		String oldValue = this.dataConnectionId;
		this.dataConnectionId = dataConnectionId;
		this.firePropertyChange("dataConnectionId", oldValue, this.dataConnectionId);
	}

	@JsonIgnore
	public List<ImportTable> getSelectedImportTables() {
		return selectedImportTables;
	}

	public void setSelectedImportTables(List<ImportTable> selectedImportTables) {
		List<ImportTable> oldValue = this.selectedImportTables;
		this.selectedImportTables = selectedImportTables;
		this.firePropertyChange("selectedImportTables", oldValue, this.selectedImportTables);
	}
	
	
	
	@JsonProperty
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		String oldValue = this.projectId;
		this.projectId = projectId;
		this.firePropertyChange("projectId", oldValue, this.projectId);
	}
	
	
	@JsonProperty
	public Time getLastRun()
	{
		return this.lastRun;
	}
	
	public void setLastRun(Time lastRun)
	{
		Time oldValue = this.lastRun;
		this.lastRun = lastRun;
		firePropertyChange("lastRun", oldValue, this.lastRun);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.dataConnectionId, this.projectId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImportDefinition other = (ImportDefinition) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}
}
