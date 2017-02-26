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
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.persistence.views.ProjectViewBuilder;

public class AttributeRepository extends BaseRepository<Attribute> {
	
	ListDetailRepository listDetailRepository;
	
	public AttributeRepository(IPersistenceManager manager)
	{
		super(manager);
		this.listDetailRepository = new ListDetailRepository(manager);
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
			if(entity.getDataType() != null)
			{
				entity.setDataTypeLookup(this.listDetailRepository.get(entity.getDataType(), ListDetail.class));
			}
			entityList.add(entity);
		}
		return entityList;

	}
	
	@Override
	public void save(Attribute entity) {
	
		if(entity.getDataTypeLookup() != null)
		{
			entity.setDataType(entity.getDataTypeLookup().getId());
		}
		else
		{
			entity.setDataType(null);
		}
		super.save(entity);
	}

}
