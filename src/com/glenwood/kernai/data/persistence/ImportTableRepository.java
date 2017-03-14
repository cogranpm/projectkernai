package com.glenwood.kernai.data.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.data.persistence.views.ProjectViewBuilder;

public class ImportTableRepository extends BaseRepository<ImportTable> {

	public ImportTableRepository(IPersistenceManager manager) {
		super(manager);
	}

	
	public List<ImportTable> getAllByImportDefinition(String importDefinitionId)
	{
		List<ImportTable> entityList = new ArrayList<ImportTable>();
		Query aquery = this.getManager().getDatabase().getView(ProjectViewBuilder.QUERY_IMPORTTABLLE_BY_IMPORTDEFINITION).createQuery();
		List<Object> keys = new ArrayList<Object>();
        keys.add(importDefinitionId);
		aquery.setKeys(keys);
		QueryEnumerator result = null;
		try {
			result = aquery.run();
		} catch (CouchbaseLiteException e) {
			e.printStackTrace();
		}
		for(Iterator<QueryRow> it = result; it.hasNext();)
		{
			QueryRow row = it.next();
			ImportTable entity = this.getManager().getEntityMapper().toEntity(row.getDocument(), ImportTable.class);
			entityList.add(entity);
		}
		return entityList;

	}
}
