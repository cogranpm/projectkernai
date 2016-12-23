package com.glensoft.data.persistence;

import java.util.List;

import com.glensoft.data.abstractions.BaseEntity;
import com.glensoft.data.abstractions.IPersistenceManager;
import com.glensoft.data.entity.Attribute;
import com.glensoft.data.entity.Entity;

public class EntityRepository extends BaseRepository {

	/* refactor this to have instance that is singleton */
	public EntityRepository(IPersistenceManager manager) {
		super(manager);
	}
	

	
	@Override
	public void save(BaseEntity entity) {
		super.save(entity);
	}
}
