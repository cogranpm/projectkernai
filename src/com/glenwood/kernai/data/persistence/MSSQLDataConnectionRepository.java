package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.entity.MSSQLDataConnection;

public class MSSQLDataConnectionRepository extends BaseRepository<MSSQLDataConnection> {

	public MSSQLDataConnectionRepository(IPersistenceManager manager) {
		super(manager);
		
	}

}
