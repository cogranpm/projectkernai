package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;

public class BaseRepository implements IEntityRepository {
	
	private IPersistenceManager manager;
	
	protected IPersistenceManager getManager()
	{
		return this.manager;
	}
	
	private BaseRepository()
	{
		
	}
	
	public BaseRepository(IPersistenceManager manager)
	{
		this.manager = manager;
	}

	@Override
	public void save(BaseEntity entity) {
		this.manager.save(entity);
	}

	@Override
	public void delete(BaseEntity entity) {
		this.manager.delete(entity);

	}
	
	@Override
	public <T> List<T> getAll(String type, Class<T> aClass)
	{
		return this.manager.getAll(type, aClass);
	}

	/*
	@Override
	public <T> List<T> getAll(String queryName, Class<T> aClass) {
		return this.manager.getAll(queryName, aClass);
	}
	*/

	@Override
	public void test() {
		this.manager.test();

	}

}
