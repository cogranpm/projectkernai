package com.glenwood.kernai.data.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.ListDetail;

public class ListDetailRepository extends BaseRepository implements IEntityRepository {

	public ListDetailRepository(IPersistenceManager manager) {
		super(manager);
	}
	
	@Override
	public void save(BaseEntity entity) {
		ListDetail listDetail = (ListDetail)entity;
		if(listDetail.getListHeaderId().equals(null))
		{
			throw new IllegalArgumentException("ListDetail entity does not have a valid ListHeaderId");
		}
		super.save(entity);
	}
	
	public List<ListDetail> getAllByListHeader(String listHeaderId)
	{
		List<ListDetail> entityList = new ArrayList<ListDetail>();
		Query aquery = this.getManager().getDatabase().getView(ListDetail.TYPE_NAME).createQuery();
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
			ListDetail entity = this.getManager().getEntityMapper().toEntity(row.getDocument(), ListDetail.class);
			entityList.add(entity);
		}
		return entityList;

	}

}
