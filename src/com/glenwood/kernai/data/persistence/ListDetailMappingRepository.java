package com.glenwood.kernai.data.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.ListDetailMapping;

public class ListDetailMappingRepository extends BaseRepository<ListDetailMapping> {

	public ListDetailMappingRepository(IPersistenceManager manager) {
		super(manager);
		
	}
	
	@Override
	public void save(ListDetailMapping entity) {
		if(entity.getListHeaderMappingId() == null)
		{
			throw new IllegalArgumentException("ListDetailMapping entity does not have a valid ListHeaderMappingId");
		}
		super.save(entity);
	}
	
	
	public List<ListDetailMapping> getAllByListHeaderMapping(String listHeaderMappingId)
	{
		List<ListDetailMapping> entityList = new ArrayList<ListDetailMapping>();
		Query aquery = this.getManager().getDatabase().getView(ListDetailMapping.TYPE_NAME).createQuery();
		List<Object> keys = new ArrayList<Object>();
        keys.add(listHeaderMappingId);
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
			ListDetailMapping entity = this.getManager().getEntityMapper().toEntity(row.getDocument(), ListDetailMapping.class);
			entityList.add(entity);
		}
		return entityList;

	}

}
