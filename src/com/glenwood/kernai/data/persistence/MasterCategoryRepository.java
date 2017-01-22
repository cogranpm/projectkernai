package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;

public class MasterCategoryRepository extends BaseRepository implements IEntityRepository{

	public MasterCategoryRepository(IPersistenceManager manager)
	{
		super(manager);
	}
}