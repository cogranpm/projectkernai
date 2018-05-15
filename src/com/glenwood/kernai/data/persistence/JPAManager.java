package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.couchbase.lite.Database;
import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.mapping.EntityMapper;

public class JPAManager implements IPersistenceManager  {

	@Override
	public Database getDatabase() {

		return null;
	}

	@Override
	public EntityMapper getEntityMapper() {

		return null;
	}

	@Override
	public void init(String databaseName) {

		
	}

	@Override
	public void close() {

		
	}

	@Override
	public void save(BaseEntity entity) {

		
	}

	@Override
	public void delete(BaseEntity entity) {

		
	}

	@Override
	public <T> List<T> getAll(String queryName, Class<T> aClass) {

		return null;
	}

	@Override
	public <T> T get(String id, Class<T> aClass) {

		return null;
	}

	@Override
	public void registerCustomMapper(String typename, Object mapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getCustomMapper(String typename) {
		// TODO Auto-generated method stub
		return null;
	}

}
