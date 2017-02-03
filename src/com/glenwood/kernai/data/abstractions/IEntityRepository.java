package com.glenwood.kernai.data.abstractions;

import java.util.List;

public interface IEntityRepository {
	public void save(BaseEntity entity);
	public void delete(BaseEntity entity);
	public <T> List<T> getAll(String type, Class<T> aClass);
	//public <T> List<T> getAll(String queryName, Class<T> aClass);
	public void test();

}
