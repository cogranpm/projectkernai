package com.glenwood.kernai.data.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.MasterPropertyListItem;

public class MasterPropertyListItemRepository extends BaseRepository<MasterPropertyListItem> {

	public MasterPropertyListItemRepository(IPersistenceManager manager) {
		super(manager);

	}

	
	@Override
	public void save(MasterPropertyListItem entity) {
		MasterPropertyListItem masterPropertyListItem = (MasterPropertyListItem)entity;
		if(masterPropertyListItem.getMasterPropertyId().equals(null))
		{
			throw new IllegalArgumentException("MasterPropertyListItem entity does not have a valid MasterPropertyId");
		}
		super.save(entity);
	}
	
	
	public List<MasterPropertyListItem> getAllByMasterProperty(String masterPropertyId)
	{
		List<MasterPropertyListItem> entityList = new ArrayList<MasterPropertyListItem>();
		Query aquery = this.getManager().getDatabase().getView(MasterPropertyListItem.TYPE_NAME).createQuery();
		List<Object> keys = new ArrayList<Object>();
        keys.add(masterPropertyId);
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
			MasterPropertyListItem entity = this.getManager().getEntityMapper().toEntity(row.getDocument(), MasterPropertyListItem.class);
			entityList.add(entity);
		}
		return entityList;

	}
}
