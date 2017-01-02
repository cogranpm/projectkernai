package com.glenwood.kernai.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.graphics.Image;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.persistence.CouchbaseManager;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactoryConstants;

public class ApplicationData {
	
	
	private IPersistenceManager persistenceManager;
	public IPersistenceManager getPersistenceManager()
	{
		return instance_.persistenceManager;
	}
	
	private Map<String, IAction> actionsMap;
	public Map<String, IAction> getActionsMap()
	{
		return actionsMap;
	}
	public IAction getAction(String key)
	{
		return actionsMap.get(key);
	}
	public void addAction(String key, IAction action)
	{
		actionsMap.put(key, action);
	}
	
	private String persistenceType;
	public String getPersistenceType()
	{
		return this.persistenceType;
	}
	public void setPersistenceType(String value)
	{
		this.persistenceType = value;
	}
	
	private static ApplicationData instance_;
	public static final String COMPANY_NAME = "Glenwood";
	public static final String APPLICATION_NAME = "Kernai";
	public final static String IMAGES_PATH = "resources/images/";
	public final static Map<String, Image> smallIcons = new HashMap<String, Image>();
	public final static Map<String, Image> largeIcons = new HashMap<String, Image>();
	public static final String IMAGE_DIAGRAM = "diagram";
	public static final String IMAGE_MASTERPAGE = "masterpage";
	
	protected ApplicationData()
	{
		actionsMap = new HashMap<String, IAction>();
		this.persistenceType = PersistenceManagerFactoryConstants.PERSISTENCE_FACTORY_TYPE_COUCHBASE_LITE;
		persistenceManager  = new CouchbaseManager();
		persistenceManager.init(APPLICATION_NAME);
	}
	
	public static ApplicationData instance()
	{
	  if(instance_== null) //don't want to block here
	  {
	    //two or more threads might be here!!!
	    synchronized(ApplicationData.class)
	    {
	      //must check again as one of the
	      //blocked threads can still enter
	      if(instance_==null)
	        instance_= new ApplicationData();//safe
	    }
	  }
	  return instance_;
	}
	

}
