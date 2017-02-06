package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.MasterCategory;

public class MasterCategoryRepository extends BaseRepository<MasterCategory> implements IEntityRepository<MasterCategory>{

	public MasterCategoryRepository(IPersistenceManager manager)
	{
		super(manager);
	}
}
