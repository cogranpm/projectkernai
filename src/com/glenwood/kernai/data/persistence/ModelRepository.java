package com.glenwood.kernai.data.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.Model;

public class ModelRepository extends BaseRepository<Model> {

	public ModelRepository(IPersistenceManager manager) {
		super(manager);
		
	}
	
	public List<Model> getAllByListHeader(String listHeaderId)
	{
		List<Model> entityList = new ArrayList<Model>();
		Query aquery = this.getManager().getDatabase().getView(Model.TYPE_NAME).createQuery();
		List<Object> keys = new ArrayList<Object>();
        keys.add(listHeaderId);
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


}
