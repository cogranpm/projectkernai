package com.glenwood.kernai.data.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.persistence.views.ProjectViewBuilder;

public class EntityRepository extends BaseRepository<Entity> {

	
	AttributeRepository attributeRepository;
	
	public EntityRepository(IPersistenceManager manager) {
		super(manager);
		attributeRepository = new AttributeRepository(manager);
	}
	
	
	public List<Entity> getAllByModel(String modelId)
	{
		List<Entity> entityList = new ArrayList<Entity>();
		Query aquery = this.getManager().getDatabase().getView(ProjectViewBuilder.QUERY_ENTITY_BY_MODEL).createQuery();
		List<Object> keys = new ArrayList<Object>();
        keys.add(modelId);
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
			Entity entity = this.getManager().getEntityMapper().toEntity(row.getDocument(), Entity.class);
			entityList.add(entity);
			
//			System.out.println(entity.getId());
		}
		return entityList;

	}

	
	@Override
	public void delete(Entity entity) {
		List<Attribute> attributes = this.attributeRepository.getAllByEntity(entity.getId());
		for(Attribute attribute : attributes)
		{
			this.attributeRepository.delete(attribute);
		}
		super.delete(entity);
	}
}
