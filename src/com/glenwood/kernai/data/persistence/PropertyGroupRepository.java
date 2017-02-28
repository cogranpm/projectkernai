package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.PropertyGroup;

public class PropertyGroupRepository extends BaseRepository<PropertyGroup> implements IEntityRepository<PropertyGroup> {

	public PropertyGroupRepository(IPersistenceManager manager) {
		super(manager);

	}

}
