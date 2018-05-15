package com.glenwood.kernai.data.persistence;

import java.io.IOException;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.mongodb.MongoClient;

public class MongoManager  {

	final Morphia morphia;
	final MongoClient client;
	final Datastore datastore;
	
	public MongoManager()
	{
		try
		{
			morphia = new Morphia();
			client = new MongoClient();
			morphia.mapPackage("com.glenwood.kernai.data.entity");
			datastore = morphia.createDatastore(client, "Kernai");
			datastore.ensureIndexes();
		}
		catch(Exception ex)
		{
			throw ex;
		}
	
	}
	
	
	public void close() {
		try
		{
			client.close();
		}
		catch(Exception ex)
		{
			
		}
	}

	
	public void save(BaseEntity entity) {
		

	}

	
	public void delete(BaseEntity entity) {
		// TODO Auto-generated method stub

	}

	
	public <T> List<T> getAll(String queryName, Class<T> aClass) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public <T> T get(Long id, Class<T> aClass) {
		// TODO Auto-generated method stub
		return null;
	}

}
