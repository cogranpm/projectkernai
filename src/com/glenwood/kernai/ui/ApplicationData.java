package com.glenwood.kernai.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.persistence.CouchbaseManager;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactoryConstants;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.view.MainShell;

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
	
	private Map<String, ToolItem> toolItemsMap;
	public ToolItem getToolItem(String key)
	{
		return toolItemsMap.get(key);
	}
	public void addToolItem(String key, ToolItem toolItem)
	{
		this.toolItemsMap.put(key, toolItem);
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
	
	private IEntityView currentEntityView;
	
	
	public IEntityView getCurrentEntityView() {
		return currentEntityView;
	}
	public void setCurrentEntityView(IEntityView currentEntityView) {
		this.currentEntityView = currentEntityView;
	}
	
	private ImageRegistry imageRegistry;
	public ImageRegistry getImageRegistry()
	{
		return this.imageRegistry;
	}
	
	/*
	private MainShell mainShell;

	public MainShell getMainShell() {
		return mainShell;
	}
	public void setMainShell(MainShell mainShell) {
		this.mainShell = mainShell;
	}
	*/

	private static ApplicationData instance_;
	public static final String COMPANY_NAME = "Glenwood";
	public static final String APPLICATION_NAME = "Kernai";
	public final static String IMAGES_PATH = "/images/";
	public static final String IMAGE_DIAGRAM = "diagram";
	public static final String IMAGE_MASTERPAGE = "masterpage";
	public static final String IMAGE_ACTIVITY_SMALL = "activitysmall";
	public static final String IMAGE_ACTIVITY_LARGE = "activitylarge";
	
	public static final String DELETE_ACTION_KEY = "delete";
	public static final String NEW_ACTION_KEY = "new";
	public static final String SAVE_ACTION_KEY = "save";
	public static final String GOTO_MASTERPROPERTY_LISTS = "GoToMasterPropertyLists";
	
	protected ApplicationData()
	{
		actionsMap = new HashMap<String, IAction>();
		toolItemsMap = new HashMap<String, ToolItem>();
		this.persistenceType = PersistenceManagerFactoryConstants.PERSISTENCE_FACTORY_TYPE_COUCHBASE_LITE;
		persistenceManager  = new CouchbaseManager();
		persistenceManager.init(APPLICATION_NAME);
		this.currentEntityView = null;
		this.imageRegistry = new ImageRegistry();
	}
	
	public void addImagesToRegistry()
	{
		this.imageRegistry.put(IMAGE_ACTIVITY_SMALL, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Activity_16xSM.png")));
		this.imageRegistry.put(IMAGE_ACTIVITY_LARGE, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Activity_32x.png")));
		this.imageRegistry.put(IMAGE_DIAGRAM, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Diagram_16x.png")));
		this.imageRegistry.put(IMAGE_MASTERPAGE, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "MasterPage_16x.png")));
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
