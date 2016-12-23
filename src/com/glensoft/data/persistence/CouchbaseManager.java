package com.glensoft.data.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.View;
import com.glensoft.data.entity.Attribute;
import com.glensoft.data.mapping.EntityMapper;
import com.glensoft.data.abstractions.BaseEntity;
import com.glensoft.data.abstractions.IPersistenceManager;

public class CouchbaseManager implements IPersistenceManager {
	
	private Manager manager;
	private Database database;
	private EntityMapper entityMapper;
	
	/* todo, need to look at this, exposing the database type is a no no, but is needed by repository interfaces */
	public Database getDatabase()
	{
		return database;
	}
	
	Logger log = Logger.getLogger("Kernai-CouchbaseRepository");
	
	//todo expose a map of views with the view name being the key
	//which will let clients have a single source to get view names


	public void init(String databaseName)
	{
		
		log.setLevel(Level.ALL);
		
		this.entityMapper = new EntityMapper();
		CustomContext context = new CustomContext();
		manager = null;
		try
		{
			manager = new Manager(context, Manager.DEFAULT_OPTIONS);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		this.manager.setStorageType("ForestDB");
		this.database  = null;
		try
		{
			/*
			Path databasePath = Paths.get( String.format("%s%s%s%s%s",
					System.getProperty("user.home"), System.getProperty("file.separator"), 
					"Glensoft", System.getProperty("file.separator"), 
					databaseName.toLowerCase()));
			try
			{
				Files.createDirectories(databasePath);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			*/
			this.database = this.manager.getDatabase(databaseName.toLowerCase());
			//this.database = this.manager.getDatabase(databaseName);
		}
		catch(CouchbaseLiteException ex)
		{
			ex.printStackTrace();
		}
		this.generateViews();
		
	}
	
	public void close()
	{
		this.database.close();
		this.manager.close();
	}
	

	public void save(BaseEntity entity)
	{
		Map<String, Object> map = entityMapper.toMap(entity);
		String id = (String) map.get("_id");
		Document document;
        if (id == null) 
        {
            document = this.database.createDocument();
        } 
        else 
        {
            document = this.database.getExistingDocument(id);
            if (document == null) 
            {
                document = this.database.getDocument(id);
            } 
            else 
            {
                map.put("_rev", document.getProperty("_rev"));
            }
        }

        try 
        {
            document.putProperties(map);
        } 
        catch (CouchbaseLiteException e) 
        {
            e.printStackTrace();
        }	
        entity.setId(document.getId());
        
	}
	
	public void delete(BaseEntity entity)
	{
		Document foundDocument = this.database.getDocument(entity.getId());
		if (foundDocument != null)
		{
			try {
				foundDocument.delete();
			} catch (CouchbaseLiteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public <T> List<T> getAll(String queryName, Class<T> aClass)
	{
		List<T> entityList = new ArrayList<T>();
		Query aquery = this.database.getView(queryName).createQuery();
		QueryEnumerator result = null;
		try {
			result = aquery.run();
		} catch (CouchbaseLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Iterator<QueryRow> it = result; it.hasNext();)
		{
			QueryRow row = it.next();
			T entity = this.entityMapper.toEntity(row.getDocument(), aClass);
			entityList.add(entity);
		}
		return entityList;

	}
	
	public void test()
	{
		Attribute attribute = new Attribute(null, "FirstName", "varchar", 35L, true);
		this.save(attribute);
		List<Attribute> list = this.getAll("attributes", Attribute.class);
		for(Attribute listAttribute : list)
		{
			log.info(String.format("howday %s", listAttribute.toString()));
			/* update */
			listAttribute.setName("LastName");
			listAttribute.setLength(50L);
			this.save(listAttribute);
		}
		this.delete(attribute);    			
	}
	
	private void generateViews()
	{
		View attributesView = this.database.getView("attributes");
		attributesView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter) {
				if (document.containsKey("type") && Attribute.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("_id"), document);
				}
				
			}
		}
		, "3");
	
	}

}
