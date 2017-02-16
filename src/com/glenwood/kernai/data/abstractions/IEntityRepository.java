package com.glenwood.kernai.data.abstractions;

import java.util.List;

public interface IEntityRepository<T extends BaseEntity> {
	public void save(T entity);
	public void delete(T entity);
	public List<T> getAll(String type, Class<T> aClass);

}
