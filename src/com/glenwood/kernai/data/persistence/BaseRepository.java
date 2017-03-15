package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;

public class BaseRepository<T extends BaseEntity> implements IEntityRepository<T> {
	
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
	public void save(T entity) {
		if(entity.getType() == null || entity.getType().trim() == "")
		{
			throw new IllegalArgumentException("The entity could not be saved, type was null or empty");
		}
		this.manager.save((BaseEntity)entity);
	}

	@Override
	public void delete(T entity) {
		this.manager.delete((BaseEntity)entity);

	}
	
	@Override
	public List<T> getAll(String type, Class<T> aClass)
	{
		return this.manager.getAll(type, aClass);
	}

	


	@Override
	public T get(String id, Class<T> aClass) {
		return manager.get(id, aClass);
	}

}
