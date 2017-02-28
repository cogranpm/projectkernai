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
import com.glenwood.kernai.data.persistence.views.ProjectViewBuilder;

public class AssociationRepository extends BaseRepository<Association> {

	public AssociationRepository(IPersistenceManager manager) {
		super(manager);
	}

	
	public List<Association> getAllByModel(String modelId)
	{
		List<Association> entityList = new ArrayList<Association>();
		Query aquery = this.getManager().getDatabase().getView(ProjectViewBuilder.QUERY_ASSOCIATION_BY_MODEL).createQuery();
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
			Association entity = this.getManager().getEntityMapper().toEntity(row.getDocument(), Association.class);
			entityList.add(entity);
		}
		return entityList;

	}
}
