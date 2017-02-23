package com.glenwood.kernai.data.persistence;

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
import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.entity.MasterCategory;
import com.glenwood.kernai.data.entity.MasterPropertyListItem;
import com.glenwood.kernai.data.entity.MasterPropertyToMasterCategory;
import com.glenwood.kernai.data.mapping.EntityMapper;

public class CouchbaseManager implements IPersistenceManager {
	
	private Manager manager;
	private Database database;
	private EntityMapper entityMapper;
	private static final String ENTITY_BY_TYPE_VIEW = "entityByType";
	
	/* todo, need to look at this, exposing the database type is a no no, but is needed by repository interfaces */
	public Database getDatabase()
	{
		return database;
	}
	
	public EntityMapper getEntityMapper()
	{
		return this.entityMapper;
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
	
	public <T> List<T> getAll(String type, Class<T> aClass)
	{
		List<T> entityList = new ArrayList<T>();
		Query aquery = this.database.getView(ENTITY_BY_TYPE_VIEW).createQuery();
		List<Object> keys = new ArrayList<Object>();
        keys.add(type);
		aquery.setKeys(keys);
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
	

	@Override
	public <T> T get(String id, Class<T> aClass) {
		Document document = this.database.getExistingDocument(id);
		if (document == null)
		{
			return null;
		}
		else
		{
			T entity = this.entityMapper.toEntity(document, aClass);
			return entity;
		}
	}


	
	private void generateViews()
	{
		
		View entityByTypeView = this.database.getView(ENTITY_BY_TYPE_VIEW);
		entityByTypeView.setMap(new Mapper() {
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if (document.containsKey("type"))
				{
					emitter.emit(document.get("type"), null);
				}
			}
		}, "1");
		
		View attributesView = this.database.getView("attributes");
		attributesView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter) {
				if (document.containsKey("type") && Attribute.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("type"), null);
				}
				
			}
		}
		, "3");
		
		
		View listDetailByListHeaderView = this.database.getView(ListDetail.TYPE_NAME);
		listDetailByListHeaderView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && ListDetail.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("listHeaderId"), null);
				}
			}
		}, "1");
		
		View masterPropertyListItemByMasterPropertyView = this.database.getView(MasterPropertyListItem.TYPE_NAME);
		masterPropertyListItemByMasterPropertyView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && MasterPropertyListItem.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("masterPropertyId"), null);
				}
			}
		}, "1");
	
		View masterPropertyToCategoryView = this.database.getView(MasterPropertyToMasterCategory.TYPE_NAME);
		masterPropertyToCategoryView.setMap(new Mapper() {
			@Override
			public void map(Map<String, Object> document, Emitter emitter) {
				if(document.containsKey("type") && MasterPropertyToMasterCategory.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("masterPropertyId"), null);
				}	
			}
			
		}, "1");
		
		View masterPropertyToCategoryGetView = this.database.getView(MasterPropertyToMasterCategoryRepository.GET_BY_PROPERTY_AND_CATEGORY_VIEWNAME);
		masterPropertyToCategoryGetView.setMap(new Mapper() {
			@Override
			public void map(Map<String, Object> document, Emitter emitter) {
				if(document.containsKey("type") && MasterPropertyToMasterCategory.TYPE_NAME.equals(document.get("type")))
				{
					List<Object> keys = new ArrayList<Object>();
		            keys.add(document.get("masterPropertyId"));
		            keys.add(document.get("masterCategoryId"));
		            emitter.emit(keys, null);
				}	
			}
		}, "1");
		
		/* THIS IS OLD STUFF, DON'T NEED IT ANY MORE, REPLACED BY ENTITYBYTYPE VIEW
		View masterCategoryView = this.database.getView(MasterCategory.TYPE_NAME);
		masterCategoryView.setMap(new Mapper() {
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if (document.containsKey("type") && MasterCategory.TYPE_NAME.equals(document.get("type")))
				{
					//emitter.emit(document.get("_id"), document);
					emitter.emit(document.get("type"), null);
				}	
			}
		}, "2");
		
		View listHeaderView = this.database.getView(ListHeader.TYPE_NAME);
		listHeaderView.setMap(new Mapper() {
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && ListHeader.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("type"), null);
				}
			}
		}, "1");
		*/
		

		
	}

}
