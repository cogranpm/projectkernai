package com.glensoft.data.abstractions;

import java.util.List;

public interface IEntityRepository {
	public void save(BaseEntity entity);
	public void delete(BaseEntity entity);
	public <T> List<T> getAll(String queryName, Class<T> aClass);
	public void test();

}
