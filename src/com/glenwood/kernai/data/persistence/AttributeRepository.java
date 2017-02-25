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
import com.glenwood.kernai.data.persistence.views.ProjectViewBuilder;

public class AttributeRepository extends BaseRepository<Attribute> {
	
	public AttributeRepository(IPersistenceManager manager)
	{
		super(manager);
	}
	
	public List<Attribute> getAllByEntity(String entityId)
	{
		List<Attribute> entityList = new ArrayList<Attribute>();
		Query aquery = this.getManager().getDatabase().getView(ProjectViewBuilder.QUERY_ATTRIBUTE_BY_ENTITY).createQuery();
		List<Object> keys = new ArrayList<Object>();
        keys.add(entityId);
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
			Attribute entity = this.getManager().getEntityMapper().toEntity(row.getDocument(), Attribute.class);
			entityList.add(entity);
		}
		return entityList;

	}

}
