package com.glenwood.kernai.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.persistence.CouchbaseManager;

public class ApplicationData {
	
	


	private static ApplicationData instance_;
	private IPersistenceManager persistenceManager;
	public static final String COMPANY_NAME = "Glenwood";
	public static final String APPLICATION_NAME = "Kernai";
	public final static Map<String, Image> smallIcons = new HashMap<String, Image>();
	public final static Map<String, Image> largeIcons = new HashMap<String, Image>();
	public static final String IMAGE_DIAGRAM = "diagram";
	
	protected ApplicationData()
	{
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
	
	public IPersistenceManager getPersistenceManager()
	{
		return instance_.persistenceManager;
	}
}
