package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.DataConnection;

public class DataConnectionRepository extends BaseRepository<DataConnection> {

	public DataConnectionRepository(IPersistenceManager manager) {
		super(manager);
		
	}

}
