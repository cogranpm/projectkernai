package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.Entity;

public class EntityRepository extends BaseRepository<Entity> {

	/* refactor this to have instance that is singleton */
	public EntityRepository(IPersistenceManager manager) {
		super(manager);
	}
	

	
	@Override
	public void save(Entity entity) {
		super.save(entity);
	}
}
