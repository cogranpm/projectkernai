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
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.persistence.views.ProjectViewBuilder;

public class AssociationRepository extends BaseRepository<Association> {

	ListDetailRepository listDetailRepository;
	EntityRepository entityRepository;
	
	public AssociationRepository(IPersistenceManager manager) {
		super(manager);
		this.listDetailRepository = new ListDetailRepository(manager);
		this.entityRepository = new EntityRepository(manager);
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
			if(entity.getAssociationType() != null)
			{
				entity.setAssociationTypeLookup(this.listDetailRepository.get(entity.getAssociationType(), ListDetail.class));
			}
			if(entity.getOwnerEntityId() != null)
			{
				entity.setOwnerEntity(this.entityRepository.get(entity.getOwnerEntityId(), Entity.class));
			}
			if(entity.getOwnedEntityId() != null)
			{
				entity.setOwnedEntity(this.entityRepository.get(entity.getOwnedEntityId(), Entity.class));
			}
			entityList.add(entity);
		}
		return entityList;

	}
	
	
	@Override
	public void save(Association entity) {
		if(entity.getAssociationTypeLookup() != null)
		{
			entity.setAssociationType(entity.getAssociationTypeLookup().getId());
		}
		
		if(entity.getOwnerEntity() != null)
		{
			entity.setOwnerEntityId(entity.getOwnerEntity().getId());
		}
		
		if(entity.getOwnedEntity() != null)
		{
			entity.setOwnedEntityId(entity.getOwnedEntity().getId());
		}
		super.save(entity);
	}
}
