package com.glenwood.kernai.data.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.data.persistence.views.ProjectViewBuilder;

public class ImportDefinitionRepository extends BaseRepository<ImportDefinition> {

	
	private ImportTableRepository importTableRepository;
	private DataConnectionRepository dataConnectionRepository;
	
	public ImportDefinitionRepository(IPersistenceManager manager) {
		super(manager);
		this.importTableRepository = new ImportTableRepository(manager);
		this.dataConnectionRepository = new DataConnectionRepository(manager);
	}

	
	@Override
	public void delete(ImportDefinition entity) {
		/* delete child entities */
		List<ImportTable> importTableList = this.importTableRepository.getAllByImportDefinition(entity.getId());
		for(ImportTable importTable : importTableList)
		{
			importTableRepository.delete(importTable);
		}
		super.delete(entity);
	}
	
	public List<ImportDefinition> getAllByProject(String projectId)
	{
		List<ImportDefinition> entityList = new ArrayList<ImportDefinition>();
		Query aquery = this.getManager().getDatabase().getView(ProjectViewBuilder.QUERY_IMPORTDEFINITION_BY_PROJECT).createQuery();
		List<Object> keys = new ArrayList<Object>();
        keys.add(projectId);
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
			ImportDefinition entity = this.getManager().getEntityMapper().toEntity(row.getDocument(), ImportDefinition.class);
			entity.setDataConnection(this.getDataConnection(entity.getDataConnectionId()));
			entityList.add(entity);
		}
		return entityList;

	}
	
	@Override
	public List<ImportDefinition> getAll(String type, Class<ImportDefinition> aClass) {
		List<ImportDefinition> list = super.getAll(type, aClass);
		for(ImportDefinition importDefinition : list)
		{
			importDefinition.setDataConnection(this.getDataConnection(importDefinition.getDataConnectionId()));
		}
		return list;
	}
	
	private DataConnection getDataConnection(String dataConnectionId)
	{
		if(dataConnectionId == null){return null;}
		return this.dataConnectionRepository.get(dataConnectionId, DataConnection.class);
	}
	
	@Override
	public ImportDefinition get(String id, Class<ImportDefinition> aClass) {
		ImportDefinition entity = super.get(id, aClass);
		entity.setDataConnection(this.getDataConnection(entity.getDataConnectionId()));
		return entity;
	}
	
	@Override
	public void save(ImportDefinition entity) {
		if(entity.getDataConnection() != null)
		{
			entity.setDataConnectionId(entity.getDataConnection().getId());
		}
		else
		{
			throw new IllegalArgumentException("ImportDefinition could not be saved, DataConnection was null");
		}
		super.save(entity);
	}
}
