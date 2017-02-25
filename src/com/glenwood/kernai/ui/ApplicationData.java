package com.glenwood.kernai.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.data.persistence.CouchbaseManager;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactoryConstants;
import com.glenwood.kernai.ui.abstraction.IEntityView;

public class ApplicationData {
	
	private MainWindow mainWindow;

	public MainWindow getMainWindow() {
		return mainWindow;
	}
	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

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
	
	private Map<String, ToolBarManager> toolbarManagers;
	public ToolBarManager getToolBarManager(String key)
	{
		return this.toolbarManagers.get(key);
	}
	
	public void putToolBarManager(String key, ToolBarManager manager)
	{
		this.toolbarManagers.put(key, manager);
	}
	

	public ToolItem getToolItem(String key)
	{
		try
		{
			ToolBarManager manager =this.mainWindow.getToolBarManager();
			return this.getToolItem(manager, key);
		}
		catch(Exception ex)
		{
			//could be shutting down window, just close gracefully
			return null;
		}
	}
	
	
	public ToolItem getToolItem(ToolBarManager manager, String key)
	{
		try
		{
			if (manager == null){return null;}
			ToolBar toolBar = manager.getControl();
			if (toolBar == null) { return null;}
			ToolItem[] items = toolBar.getItems();
			if(items == null){return null;}
			for(ToolItem item : items)
			{
				Object itemData = item.getData();
				if(itemData == null)
				{
					return null;
				}
				ActionContributionItem actionItem = (ActionContributionItem)itemData;
				if(actionItem.getAction().getActionDefinitionId().equalsIgnoreCase(key))
				{
					return item;
				}
			}
			return null;
		}
		catch(Exception ex)
		{
			//could be shutting down window, just close gracefully
			return null;
		}
	}
	
	/*
	public void addToolItem(String key, ToolItem toolItem)
	{
		this.toolItemsMap.put(key, toolItem);
	}
	*/
	
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
	
	public void uncheckActions(String[] actionKeys, String currentKey)
	{
		for(String key : actionKeys)
		{
			IAction action =  this.getAction(key);
			if(action != null && !currentKey.equalsIgnoreCase(key) && action.isChecked())
			{
				action.setChecked(false);
			}
		}
		IAction action = this.getAction(currentKey);
		if (action != null && !action.isChecked())
		{
			action.setChecked(true);
		}
	}
	
	private Project currentProject;
	
	public Project getCurrentProject() {
		return currentProject;
	}
	public void setCurrentProject(Project currentProject) {
		this.currentProject = currentProject;
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
	public static final String IMAGE_ADD_SMALL = "addSmall";
	public static final String IMAGE_CANCEL_SMALL = "cancelSmall";
	public static final String IMAGE_CANCEL_DISABLED_SMALL = "cancelDisabledSmall";
	public static final String IMAGE_EDIT_SMALL = "editSmall";
	public static final String IMAGE_EDIT_DISABLED_SMALL = "editDisabledSmall";
	public static final String IMAGE_SAVE_SMALL = "saveSmall";
	public static final String IMAGE_SAVE_DISABLED_SMALL = "saveDisabledSmall";
	
	//toolbar managers
	public static final String TOOLBAR_MANAGER_PROJECT = "project";

	//global actions
	public static final String EXIT_ACTION_KEY = "exit";
	public static final String ABOUT_ACTION_KEY = "about";
	public static final String DELETE_ACTION_KEY = "delete";
	public static final String DELETE_ACTION_TEXT = "Delete";
	public static final String NEW_ACTION_KEY = "new";
	public static final String NEW_ACTION_TEXT = "&New";
	public static final String SAVE_ACTION_KEY = "save";
	public static final String SAVE_ACTION_TEXT = "Save";
	
	public static final String GOTO_MASTERPROPERTY_LISTS = "GoToMasterPropertyLists";
	public static final String GOTO_MASTERPROPERTY_CATEGORY = "GoToMasterPropertyCategory";
	public static final String GOTO_MASTERPROPERTY_GROUP = "GoToMasterPropertyGroup";
	public static final String GOTO_MASTERPROPERTY_PROPERTY = "GoToMasterPropertyProperty";
	public static final String GOTO_MASTERPROPERTY_TYPE = "GoToMasterPropertyType";
	
	public static final String GOTO_PROJECT_PROJECT = "GoToProjectProject";
	public static final String GOTO_PROJECT_MODEL = "GoToProjectModel";
	public static final String GOTO_PROJECT_ENTITY = "GoToProjectEntity";
	public static final String GOTO_PROJECT_ATTRIBUTE = "GoToProjectAttribute";
	public static final String GOTO_PROJECT_ASSOCIATION = "GoToProjectAssociation";
	public static final String GOTO_PROJECT_BUILD = "GoToProjectBuild";
	
	protected ApplicationData()
	{
		this.actionsMap = new HashMap<String, IAction>();
	//	toolItemsMap = new HashMap<String, ToolItem>();
		this.persistenceType = PersistenceManagerFactoryConstants.PERSISTENCE_FACTORY_TYPE_COUCHBASE_LITE;
		persistenceManager  = new CouchbaseManager();
		persistenceManager.init(APPLICATION_NAME);
		this.currentEntityView = null;
		this.imageRegistry = new ImageRegistry();
		this.toolbarManagers = new HashMap<String, ToolBarManager>();
	}
	
	public void addImagesToRegistry()
	{
		this.imageRegistry.put(IMAGE_ACTIVITY_SMALL, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Activity_16xSM.png")));
		this.imageRegistry.put(IMAGE_ACTIVITY_LARGE, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Activity_32x.png")));
		this.imageRegistry.put(IMAGE_DIAGRAM, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Diagram_16x.png")));
		this.imageRegistry.put(IMAGE_MASTERPAGE, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "MasterPage_16x.png")));
		this.imageRegistry.put(IMAGE_ADD_SMALL, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Add_16x.png")));
		this.imageRegistry.put(IMAGE_CANCEL_SMALL, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Cancel_16x.png")));
		this.imageRegistry.put(IMAGE_CANCEL_DISABLED_SMALL, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Cancel_grey_16x.png")));
		this.imageRegistry.put(IMAGE_EDIT_SMALL, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Edit_16x.png")));
		this.imageRegistry.put(IMAGE_EDIT_DISABLED_SMALL, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Edit_grey_16x.png")));
		this.imageRegistry.put(IMAGE_SAVE_SMALL, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Save_16x.png")));
		this.imageRegistry.put(IMAGE_SAVE_DISABLED_SMALL, ImageDescriptor.createFromFile(ApplicationData.class, String.format("%s%s", ApplicationData.IMAGES_PATH, "Save_16x.png")));

	}
	
	
	
	public boolean confirmDelete(Shell parent)
	{
		return MessageDialog.openConfirm(parent, "Confirm Delete", "Delete, are you sure?");
	}
	
	public void loadEntityView(IEntityView view)
	{
		this.getAction(NEW_ACTION_KEY).setEnabled(true);
	}
	
	public void unloadEntityView()
	{
		ToolItem item = this.getToolItem(SAVE_ACTION_KEY); 
		if (item != null){item.setEnabled(false);}
		
		IAction actionItem = this.getAction(SAVE_ACTION_KEY);
		if(actionItem != null){actionItem.setEnabled(false);}
		
		item = this.getToolItem(DELETE_ACTION_KEY);
		if(item != null){item.setEnabled(false);}
		
		actionItem = this.getAction(DELETE_ACTION_KEY);
		if(actionItem != null){actionItem.setEnabled(false);}
		
		item = this.getToolItem(NEW_ACTION_KEY);
		if(item!= null){item.setEnabled(false);}
		
		actionItem = this.getAction(NEW_ACTION_KEY);
		if(actionItem != null){actionItem.setEnabled(false);}
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
