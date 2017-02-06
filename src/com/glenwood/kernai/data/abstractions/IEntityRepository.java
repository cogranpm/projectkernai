package com.glenwood.kernai.data.abstractions;

import java.util.List;

public interface IEntityRepository<T> {
	public void save(T entity);
	public void delete(T entity);
	public List<T> getAll(String type, Class<T> aClass);
	//public <T> List<T> getAll(String queryName, Class<T> aClass);
	public void test();

}
