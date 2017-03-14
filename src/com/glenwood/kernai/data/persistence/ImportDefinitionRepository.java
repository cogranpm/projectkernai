package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.ImportTable;

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
			entity.setDataConnectionId(entity.getDataConnectionId());
		}
		super.save(entity);
	}
}
