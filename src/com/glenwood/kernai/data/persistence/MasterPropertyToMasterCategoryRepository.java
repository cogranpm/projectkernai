package com.glenwood.kernai.data.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.MasterPropertyToMasterCategory;

public class MasterPropertyToMasterCategoryRepository extends BaseRepository<MasterPropertyToMasterCategory> {
	
	public static final String GET_BY_PROPERTY_AND_CATEGORY_VIEWNAME = MasterPropertyToMasterCategory.TYPE_NAME + "GET";

	public MasterPropertyToMasterCategoryRepository(IPersistenceManager manager) {
		super(manager);
	}

	
	public List<MasterPropertyToMasterCategory> getAllByMasterProperty(String masterPropertyId)
	{
		List<MasterPropertyToMasterCategory> entityList = new ArrayList<>();
		Query query = this.getManager().getDatabase().getView(MasterPropertyToMasterCategory.TYPE_NAME).createQuery();
		List<Object> keys = new ArrayList<Object>();
        keys.add(masterPropertyId);
		query.setKeys(keys);
		QueryEnumerator result = null;
		try {
			result = query.run();
		} catch (CouchbaseLiteException e) {
			e.printStackTrace();
		}
		for(Iterator<QueryRow> it = result; it.hasNext();)
		{
			QueryRow row = it.next();
			MasterPropertyToMasterCategory entity = this.getManager().getEntityMapper().toEntity(row.getDocument(), MasterPropertyToMasterCategory.class);
			entityList.add(entity);
		}
		return entityList;		
	}
	
	public MasterPropertyToMasterCategory get(String masterPropertyId, String masterCategoryId)
	{
		MasterPropertyToMasterCategory entity = null;
		Query query = this.getManager().getDatabase().getView(GET_BY_PROPERTY_AND_CATEGORY_VIEWNAME).createQuery();
		List<Object> keys = new ArrayList<Object>();
		List<Object> key = new ArrayList<Object>();
		key.add(masterPropertyId);
		key.add(masterCategoryId);
		keys.add(key);
		query.setKeys(keys);
		QueryEnumerator result = null;
		try {
			result = query.run();
		} catch (CouchbaseLiteException e) {
			e.printStackTrace();
		}
		for(Iterator<QueryRow> it = result; it.hasNext();)
		{
			QueryRow row = it.next();
			entity = this.getManager().getEntityMapper().toEntity(row.getDocument(), MasterPropertyToMasterCategory.class);
			break;
		}
		return entity;
	}
}
