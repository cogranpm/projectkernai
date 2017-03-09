/* entity represents saved settings for an import 
 * import can be run over and over
 */

package com.glenwood.kernai.data.entity;

import com.glenwood.kernai.data.abstractions.BaseEntity;

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
	
	//List<ImportTable> selectectImportTables;

}
