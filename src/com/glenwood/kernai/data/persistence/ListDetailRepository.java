package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;

public class ListDetailRepository extends BaseRepository implements IEntityRepository {

	public ListDetailRepository(IPersistenceManager manager) {
		super(manager);
	}

}
