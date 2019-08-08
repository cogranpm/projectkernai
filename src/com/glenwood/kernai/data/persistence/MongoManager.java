package com.glenwood.kernai.data.persistence;

import java.util.List;

/*
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
*/

import com.glenwood.kernai.data.abstractions.BaseEntity;
//import com.mongodb.MongoClient;

public class MongoManager  {

/*	final Morphia morphia;
	final MongoClient client;
	final Datastore datastore;
*/
	
	public MongoManager()
	{
		try
		{
		/*	morphia = new Morphia();
			client = new MongoClient();
			morphia.mapPackage("com.glenwood.kernai.data.entity");
			datastore = morphia.createDatastore(client, "Kernai");
			datastore.ensureIndexes();
		*/	
		}
		catch(Exception ex)
		{
			throw ex;
		}
	
	}
	
	
	public void close() {
		try
		{
		//	client.close();
		}
		catch(Exception ex)
		{
			
		}
	}

	
	public void save(BaseEntity entity) {
		
		//datastore.save(entity);
	}

	
	public void delete(BaseEntity entity) {
		//datastore.delete(entity);

	}

	
	public <T> List<T> getAll(String queryName, Class<T> aClass) {
		/*final Query<T> query = datastore.createQuery(aClass);
		final List<T> list = query.asList();
		return list;
		*/
		return null;
	}

	
	public <T> T get(Long id, Class<T> aClass) {

		return null;
	}

}
