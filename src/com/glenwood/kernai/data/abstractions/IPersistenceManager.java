package com.glenwood.kernai.data.abstractions;

import java.util.List;

import com.couchbase.lite.Database;
import com.glenwood.kernai.data.mapping.EntityMapper;

public interface IPersistenceManager {
	public Database getDatabase();
	public EntityMapper getEntityMapper();
	public void init(String databaseName);
	public void close();
	public void save(BaseEntity entity);
	public void delete(BaseEntity entity);
	public <T> List<T> getAll(String queryName, Class<T> aClass);
	public <T> T get(String id, Class<T> aClass);
	public void test();

}
