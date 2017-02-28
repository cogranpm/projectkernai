package com.glenwood.kernai.data.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.Association;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.data.persistence.views.ProjectViewBuilder;

public class ModelRepository extends BaseRepository<Model> {
	
	
	EntityRepository entityRepository;
	AssociationRepository associationRepository;

	public ModelRepository(IPersistenceManager manager) {
		super(manager);
		this.entityRepository = new EntityRepository(manager);
		this.associationRepository = new AssociationRepository(manager);
	}
	
	public List<Model> getAllByProject(String projectId)
	{
		List<Model> entityList = new ArrayList<Model>();
		Query aquery = this.getManager().getDatabase().getView(ProjectViewBuilder.QUERY_MODEL_BY_PROJECT).createQuery();
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
			Model entity = this.getManager().getEntityMapper().toEntity(row.getDocument(), Model.class);
			entityList.add(entity);
		}
		return entityList;

	}
	
	@Override
	public void delete(Model entity) {

		List<Entity> entityList = this.entityRepository.getAllByModel(entity.getId());
		for(Entity aEntity : entityList)
		{
			this.entityRepository.delete(aEntity);
		}
		
		List<Association> associationList = this.associationRepository.getAllByModel(entity.getId());
		for(Association association : associationList)
		{
			this.associationRepository.delete(association);
		}
		super.delete(entity);
	}


}
