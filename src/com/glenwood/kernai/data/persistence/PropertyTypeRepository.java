package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.PropertyType;

public class PropertyTypeRepository extends BaseRepository<PropertyType> {

	public PropertyTypeRepository(IPersistenceManager manager) {
		super(manager);
	}

}
