/* entity represents saved settings for an import 
 * import can be run over and over
 */

package com.glenwood.kernai.data.entity;

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

	
	
	@JsonIgnore
	public DataConnection getDataConnection() {
		return dataConnection;
	}

	public void setDataConnection(DataConnection dataConnection) {
		this.dataConnection = dataConnection;
	}

	@JsonProperty
	public String getDataConnectionId() {
		return dataConnectionId;
	}

	public void setDataConnectionId(String dataConnectionId) {
		this.dataConnectionId = dataConnectionId;
	}

	@JsonIgnore
	public List<ImportTable> getSelectedImportTables() {
		return selectedImportTables;
	}

	public void setSelectedImportTables(List<ImportTable> selectedImportTables) {
		this.selectedImportTables = selectedImportTables;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.dataConnectionId);
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
