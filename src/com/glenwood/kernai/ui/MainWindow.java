package com.glenwood.kernai.ui;

import java.util.List;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.modelimport.ColumnDefinition;
import com.glenwood.kernai.data.persistence.JDBCManager;
import com.glenwood.kernai.data.persistence.connection.SQLServerConnection;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.view.AssociationView;
import com.glenwood.kernai.ui.view.AttributeView;
import com.glenwood.kernai.ui.view.EntityView;
import com.glenwood.kernai.ui.view.ListHeaderView;
import com.glenwood.kernai.ui.view.MasterCategoryView;
import com.glenwood.kernai.ui.view.MasterPropertyView;
import com.glenwood.kernai.ui.view.ModelView;
import com.glenwood.kernai.ui.view.ProjectView;
import com.glenwood.kernai.ui.view.PropertyGroupView;
import com.glenwood.kernai.ui.view.PropertyTypeView;

//todo - refactor this to be empty shell that is composed of regions, custom class extending composite.
public class MainWindow extends ApplicationWindow {
	//private DataBindingContext m_bindingContext;
	
	private Composite container;
	private Composite masterPropertyPane;
	private Composite projectPane;
	private ToolBarManager projectBarManager;

	
	/**
	 * Create the application window.
	 */
	public MainWindow() {
		super(null);
		ApplicationData.instance().setMainWindow(this);
		ApplicationData.instance().addImagesToRegistry();
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
		ApplicationData.instance().createDefaultData();
	}
	
	


	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());

		/* top level tabs */
		CTabFolder folder = new CTabFolder(container, SWT.TOP);
		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText("&Getting Started");
		CTabItem masterPropertyTabItem = new CTabItem(folder, SWT.NONE);
		masterPropertyTabItem.setText("&Master Properties");
		
		Composite masterPropertyContainingPane = new Composite(folder, SWT.NONE);
		ToolBar masterPropertyToolBar = this.addNavigationToolbar(masterPropertyContainingPane, masterPropertyTabItem);
		ToolBarManager toolBarManager = new ToolBarManager(masterPropertyToolBar);
		masterPropertyPane = new Composite(masterPropertyContainingPane, SWT.NONE);
		GridData tabControlPaneData = new GridData();
		tabControlPaneData.grabExcessHorizontalSpace = true;
		tabControlPaneData.grabExcessVerticalSpace = true;
		tabControlPaneData.horizontalAlignment = SWT.FILL;
		tabControlPaneData.verticalAlignment = SWT.FILL;
		masterPropertyPane.setLayoutData(tabControlPaneData);
		masterPropertyPane.setLayout(new FillLayout());


		ActionContributionItem tabItemAction = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_MASTERPROPERTY_LISTS));
		tabItemAction.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolBarManager.add(tabItemAction);
		
		tabItemAction = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_MASTERPROPERTY_CATEGORY));
		tabItemAction.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolBarManager.add(tabItemAction);
		
		tabItemAction = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_MASTERPROPERTY_GROUP));
		tabItemAction.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolBarManager.add(tabItemAction);

		tabItemAction = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_MASTERPROPERTY_TYPE));
		tabItemAction.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolBarManager.add(tabItemAction);

		tabItemAction = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_MASTERPROPERTY_PROPERTY));
		tabItemAction.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolBarManager.add(tabItemAction);

		toolBarManager.update(true);
		
		CTabItem projectItem  = new CTabItem(folder, SWT.NONE);
		projectItem.setText("&Projects");
		Composite projectContainerPane = new Composite(folder, SWT.NONE);
		ToolBar projectToolBar = this.addNavigationToolbar(projectContainerPane, projectItem);
		projectBarManager = new ToolBarManager(projectToolBar);
		ApplicationData.instance().putToolBarManager(ApplicationData.TOOLBAR_MANAGER_PROJECT, projectBarManager);

		tabItemAction = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_PROJECT));
		tabItemAction.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		projectBarManager.add(tabItemAction);
		
		tabItemAction = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_MODEL));
		tabItemAction.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		projectBarManager.add(tabItemAction);
		
		tabItemAction = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_ENTITY));
		tabItemAction.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		projectBarManager.add(tabItemAction);
		
		tabItemAction = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_ATTRIBUTE));
		tabItemAction.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		projectBarManager.add(tabItemAction);
		
		tabItemAction = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_ASSOCIATION));
		tabItemAction.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		projectBarManager.add(tabItemAction);
		
		tabItemAction = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_BUILD));
		tabItemAction.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		projectBarManager.add(tabItemAction);

		projectPane = new Composite(projectContainerPane, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(projectPane);
		projectPane.setLayout(new FillLayout());
		projectBarManager.update(true);
		
		folder.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				CTabItem item = folder.getSelection();
				if(item == projectItem)
				{
					if(projectPane.getChildren().length == 0)
					{
						ProjectView projectView = new ProjectView(projectPane, SWT.NONE);
						ApplicationData.instance().setCurrentEntityView(projectView);
						projectPane.layout();
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		item = new CTabItem(folder, SWT.NONE);
		item.setText("&Scripting");
		

		this.getShell().getDisplay().addFilter(SWT.KeyUp, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				/* this would be for child toolbar items */
				IEntityView view = ApplicationData.instance().getCurrentEntityView();
				if (view != null)
				{
					if ((event.stateMask & SWT.CTRL) == SWT.CTRL)
	                {
	                    //System.out.println("Ctrl pressed");
					 	
	                }
				}
				
			}
		});
		
		
		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		
		    
		 IAction exitAction = new Action("E&xit\tCtrl+X"){
			 @Override
			 public void run()
			 {
				 close();
			 }
		 };
		 ApplicationData.instance().addAction(ApplicationData.EXIT_ACTION_KEY, exitAction);
		 
		 IAction aboutAction = new Action("&About") {
			 @Override
			 public void run() {
				 System.out.println("About");
				 BusyIndicator.showWhile(getShell().getDisplay(), new Thread(){
					 @Override
					public void run() {
						 IConnection connection = new SQLServerConnection("kron1", "dotconnectservice", "reddingo", true);
						 JDBCManager man = new JDBCManager(connection);
						 man.connect();
						 man.getImportEngine().init(connection);
						 List<String> databases = man.getImportEngine().getDatabases();
						 for(String database : databases)
						 {
							 System.out.println(database);
							 int tableCounter = 0;
							 List<String> tables = man.getImportEngine().getTables(database);
							 for(String table : tables)
							 {
								 System.out.println(table);
								 List<ColumnDefinition> columns = man.getImportEngine().getColumns(database, table);
								 for(ColumnDefinition column : columns)
								 {
									 System.out.println(column.getName());
								 }
								 if(tableCounter > 50)
								 {
									 break;
								 }
								 tableCounter++;
							 }
						 }
						 man.disconnect();
					}
				 });

			 }
		 };
		 ApplicationData.instance().addAction(ApplicationData.ABOUT_ACTION_KEY, aboutAction);
		    
		 IAction saveAction = new Action() {
			@Override 
			public void run() {
				/* get the active view and call save */
				IEntityView currentEntityView = ApplicationData.instance().getCurrentEntityView();
				if(currentEntityView != null)
				{
					currentEntityView.save();
				}				
			}
		 };
		 saveAction.setText(ApplicationData.SAVE_ACTION_TEXT);
		 saveAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_SAVE_SMALL));
		 saveAction.setEnabled(false);
		 saveAction.setAccelerator(SWT.MOD1 + 'S');
		 saveAction.setActionDefinitionId(ApplicationData.SAVE_ACTION_KEY);
		 ApplicationData.instance().addAction(ApplicationData.SAVE_ACTION_KEY, saveAction);

		 IAction deleteAction = new Action() {
			@Override 
			public void run() {
				/* get the active view and call delete */
				IEntityView currentEntityView = ApplicationData.instance().getCurrentEntityView();
				if(currentEntityView != null)
				{
					currentEntityView.delete();
				}				
			}
		 };
		 deleteAction.setAccelerator(SWT.MOD1 + 'D');
		 deleteAction.setText(ApplicationData.DELETE_ACTION_TEXT);
		 deleteAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_CANCEL_SMALL));
		 deleteAction.setDisabledImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_CANCEL_DISABLED_SMALL));
		 deleteAction.setEnabled(false);
		 deleteAction.setActionDefinitionId(ApplicationData.DELETE_ACTION_KEY);
		 ApplicationData.instance().addAction(ApplicationData.DELETE_ACTION_KEY, deleteAction);

		 IAction newAction = new Action() {
			@Override 
			public void run() {
				/* get the active view and call new */
				IEntityView currentEntityView = ApplicationData.instance().getCurrentEntityView();
				if(currentEntityView != null)
				{
					currentEntityView.add();
				}
			}
		 };
		 newAction.setText(ApplicationData.NEW_ACTION_TEXT);
		 newAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_ADD_SMALL));
		 newAction.setEnabled(false);
		 newAction.setAccelerator(SWT.MOD1 | 'N');
		 newAction.setActionDefinitionId(ApplicationData.NEW_ACTION_KEY);
		 ApplicationData.instance().addAction(ApplicationData.NEW_ACTION_KEY, newAction);
		 

		 String[] masterpropertyActionKeys = new String[]{ApplicationData.GOTO_MASTERPROPERTY_CATEGORY, ApplicationData.GOTO_MASTERPROPERTY_GROUP,
				 ApplicationData.GOTO_MASTERPROPERTY_LISTS, ApplicationData.GOTO_MASTERPROPERTY_PROPERTY, ApplicationData.GOTO_MASTERPROPERTY_TYPE};
		 
		 /* master properties */
		 IAction goToMasterPropertyList = new Action("Lists", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				ApplicationData.instance().uncheckActions(masterpropertyActionKeys, ApplicationData.GOTO_MASTERPROPERTY_LISTS);
				ApplicationData.instance().setCurrentEntityView(new ListHeaderView(clearComposite(masterPropertyPane), SWT.NONE));
				masterPropertyPane.layout();
			}
		 };
		 //goToMasterPropertyList.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_ACTIVITY_SMALL));
		 goToMasterPropertyList.setEnabled(true);
		 goToMasterPropertyList.setAccelerator(SWT.MOD1 | 'L');
		 ApplicationData.instance().addAction(ApplicationData.GOTO_MASTERPROPERTY_LISTS, goToMasterPropertyList);

		 
		 IAction goToMasterPropertyCategory = new Action("&Master Category", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				ApplicationData.instance().uncheckActions(masterpropertyActionKeys, ApplicationData.GOTO_MASTERPROPERTY_CATEGORY);
				ApplicationData.instance().setCurrentEntityView(new MasterCategoryView(clearComposite(masterPropertyPane), SWT.NONE));
				masterPropertyPane.layout();
			}
		 };
		 goToMasterPropertyCategory.setEnabled(true);
		 //newAction.setAccelerator(SWT.CTRL | 'N');
		 ApplicationData.instance().addAction(ApplicationData.GOTO_MASTERPROPERTY_CATEGORY, goToMasterPropertyCategory);


		 
		 IAction goToMasterPropertyProperty = new Action("Master Property", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				ApplicationData.instance().uncheckActions(masterpropertyActionKeys, ApplicationData.GOTO_MASTERPROPERTY_PROPERTY);
				ApplicationData.instance().setCurrentEntityView(new MasterPropertyView(clearComposite(masterPropertyPane), SWT.NONE));
				masterPropertyPane.layout();
			}
		 };
		 //goToMasterPropertyProperty.setText("Master &Property");
		 goToMasterPropertyProperty.setEnabled(true);
		 //newAction.setAccelerator(SWT.CTRL | 'N');
		 ApplicationData.instance().addAction(ApplicationData.GOTO_MASTERPROPERTY_PROPERTY, goToMasterPropertyProperty);
		 
		 IAction goToMasterPropertyGroup = new Action("Property Group", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				ApplicationData.instance().uncheckActions(masterpropertyActionKeys, ApplicationData.GOTO_MASTERPROPERTY_GROUP);
				ApplicationData.instance().setCurrentEntityView(new PropertyGroupView(clearComposite(masterPropertyPane), SWT.NONE));
				masterPropertyPane.layout();
			}
		 };
		 goToMasterPropertyGroup.setEnabled(true);
		 ApplicationData.instance().addAction(ApplicationData.GOTO_MASTERPROPERTY_GROUP, goToMasterPropertyGroup);
		 
		 IAction goToMasterPropertyType = new Action("Property &Type", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				ApplicationData.instance().uncheckActions(masterpropertyActionKeys, ApplicationData.GOTO_MASTERPROPERTY_TYPE);
				ApplicationData.instance().setCurrentEntityView(new PropertyTypeView(clearComposite(masterPropertyPane), SWT.NONE));
				masterPropertyPane.layout();
			}
		 };
		 //goToMasterPropertyType.setText("Property &Type");
		 goToMasterPropertyType.setEnabled(true);
		 //newAction.setAccelerator(SWT.CTRL | 'N');
		 ApplicationData.instance().addAction(ApplicationData.GOTO_MASTERPROPERTY_TYPE, goToMasterPropertyType);
		 
		 
		 
		 
		 /* project menus */
		 String[] projectActionKeys = new String[]{ApplicationData.GOTO_PROJECT_PROJECT, ApplicationData.GOTO_PROJECT_MODEL,
				 ApplicationData.GOTO_PROJECT_ENTITY, ApplicationData.GOTO_PROJECT_ATTRIBUTE, ApplicationData.GOTO_PROJECT_ASSOCIATION,
				 ApplicationData.GOTO_PROJECT_BUILD};

		 IAction goToProjectProject = new Action("Project", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				ApplicationData.instance().uncheckActions(projectActionKeys, ApplicationData.GOTO_PROJECT_PROJECT);
				/* unload the project pane and load the project view */
				ApplicationData.instance().setCurrentEntityView(new ProjectView(clearComposite(projectPane), SWT.NONE));
				projectPane.layout();
			}
		 };
		 goToProjectProject.setEnabled(true);
		 goToProjectProject.setAccelerator(SWT.MOD1 | 'J');
		 goToProjectProject.setActionDefinitionId(ApplicationData.GOTO_PROJECT_PROJECT);
		 ApplicationData.instance().addAction(ApplicationData.GOTO_PROJECT_PROJECT, goToProjectProject);
		 
		 IAction goToProjectModel = new Action("Model", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				ApplicationData.instance().uncheckActions(projectActionKeys, ApplicationData.GOTO_PROJECT_MODEL);
				ModelView modelView = new ModelView(clearComposite(projectPane), SWT.NONE, ApplicationData.instance().getCurrentProject());
				ApplicationData.instance().setCurrentEntityView((IEntityView)modelView);
				projectPane.layout();
			}
		 };
		 goToProjectModel.setEnabled(false);
		 goToProjectModel.setAccelerator(SWT.MOD1 | 'M');
		 goToProjectModel.setActionDefinitionId(ApplicationData.GOTO_PROJECT_MODEL);
		 ApplicationData.instance().addAction(ApplicationData.GOTO_PROJECT_MODEL, goToProjectModel);

		 
		 IAction goToProjectEntity = new Action("Entity", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				ApplicationData.instance().uncheckActions(projectActionKeys, ApplicationData.GOTO_PROJECT_ENTITY);
				EntityView entityView = new EntityView(clearComposite(projectPane), SWT.NONE, ApplicationData.instance().getCurrentModel());
				ApplicationData.instance().setCurrentEntityView(entityView);
				projectPane.layout();
			}
		 };
		 goToProjectEntity.setEnabled(false);
		 goToProjectEntity.setAccelerator(SWT.MOD1 | 'E');
		 goToProjectEntity.setActionDefinitionId(ApplicationData.GOTO_PROJECT_ENTITY);
		 ApplicationData.instance().addAction(ApplicationData.GOTO_PROJECT_ENTITY, goToProjectEntity);
		 
		 IAction goToProjectAttribute = new Action("Attribute", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				ApplicationData.instance().uncheckActions(projectActionKeys, ApplicationData.GOTO_PROJECT_ATTRIBUTE);
				AttributeView attributeView = new AttributeView(clearComposite(projectPane), SWT.NONE, ApplicationData.instance().getCurrentEntity());
				ApplicationData.instance().setCurrentEntityView(attributeView);
				projectPane.layout();
			}
		 };
		 goToProjectAttribute.setEnabled(false);
		 goToProjectAttribute.setAccelerator(SWT.MOD1 | 'A');
		 goToProjectAttribute.setActionDefinitionId(ApplicationData.GOTO_PROJECT_ATTRIBUTE);
		 ApplicationData.instance().addAction(ApplicationData.GOTO_PROJECT_ATTRIBUTE, goToProjectAttribute);
		 
		 IAction goToProjectAssociation = new Action("Association", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				ApplicationData.instance().uncheckActions(projectActionKeys, ApplicationData.GOTO_PROJECT_ASSOCIATION);
				AssociationView associationView = new AssociationView(clearComposite(projectPane), SWT.NONE, ApplicationData.instance().getCurrentModel());
				ApplicationData.instance().setCurrentEntityView(associationView);
				projectPane.layout();
			}
		 };
		 goToProjectAssociation.setEnabled(false);
		 goToProjectAssociation.setAccelerator(SWT.MOD1 | 'T');
		 goToProjectAssociation.setActionDefinitionId(ApplicationData.GOTO_PROJECT_ASSOCIATION);
		 ApplicationData.instance().addAction(ApplicationData.GOTO_PROJECT_ASSOCIATION, goToProjectAssociation);
		 
		 /* NO, THIS SHOULD BE UNDER SCRIPTS, AND TAKES THE PLACE OF BUILD CONTROLLER */
		 IAction goToProjectBuild = new Action("Build", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				System.out.print("here");
			}
		 };
		 goToProjectBuild.setEnabled(false);
		 ApplicationData.instance().addAction(ApplicationData.GOTO_PROJECT_BUILD, goToProjectBuild);
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		
		MenuManager fileMenu = new MenuManager("&File");
		fileMenu.add(ApplicationData.instance().getAction(ApplicationData.NEW_ACTION_KEY));
		fileMenu.add(ApplicationData.instance().getAction(ApplicationData.SAVE_ACTION_KEY));
		fileMenu.add(new Separator());
		fileMenu.add(ApplicationData.instance().getAction(ApplicationData.EXIT_ACTION_KEY));
		menuManager.add(fileMenu);
		
		MenuManager editMenu = new MenuManager("&Edit");
		editMenu.add(ApplicationData.instance().getAction(ApplicationData.DELETE_ACTION_KEY));
		menuManager.add(editMenu);
		
		MenuManager goMenu = new MenuManager("&Go");
		goMenu.add(ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_PROJECT));
		goMenu.add(ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_MODEL));
		goMenu.add(ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_ENTITY));
		goMenu.add(ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_ASSOCIATION));
		goMenu.add(ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_ATTRIBUTE));
		menuManager.add(goMenu);
		
		MenuManager helpMenu = new MenuManager("&Help");
		helpMenu.add(ApplicationData.instance().getAction(ApplicationData.ABOUT_ACTION_KEY));
		menuManager.add(helpMenu);
		

		
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(SWT.NONE);
		
		ActionContributionItem item = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.SAVE_ACTION_KEY));
		//item.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolBarManager.add(item);
		
		item = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.NEW_ACTION_KEY));
		//item.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolBarManager.add(item);
		
		
		item = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.DELETE_ACTION_KEY));
		//item.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolBarManager.add(item);


		toolBarManager.update(true);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		Display display = Display.getDefault();
		//Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			public void run() {
				try {
					
					MainWindow window = new MainWindow();
					window.setBlockOnOpen(true);
					window.open();
					Display.getCurrent().dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Kernai");
		newShell.setImages(new Image[]{ApplicationData.instance().getImageRegistry().get(ApplicationData.IMAGE_ACTIVITY_SMALL), 
				ApplicationData.instance().getImageRegistry().get(ApplicationData.IMAGE_ACTIVITY_SMALL)});
		

		}
	
	
	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(900, 800);
	}
	

	@Override
	public boolean close() {
		ApplicationData.instance().getPersistenceManager().close();
		
		/* todo, change to use the image registry of jface 
		for (Image image : this.applicationImages)
		{
			image.dispose();
		}
		ApplicationData.smallIcons.forEach((key, value) -> value.dispose());
		*/
		return super.close();
	}
	
	private ToolBar addNavigationToolbar(Composite parent, CTabItem parentTabItem)
	{
		GridLayout layout = new GridLayout(1, true);
		parent.setLayout(layout);
		parentTabItem.setControl(parent);
		ToolBar t = new ToolBar(parent, SWT.WRAP | SWT.BORDER | SWT.BOLD);
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace=false;
		gd.grabExcessVerticalSpace = false;
		t.setLayoutData(gd);
		return t;
	}
	
	private Composite clearComposite(Composite composite)
	{
		for(Control control : composite.getChildren())
		{
			control.dispose();
		}
		return composite;
	}
	
	/*
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTooltipTextContainerObserveWidget = WidgetProperties.tooltipText().observe(container);
		IObservableValue parenttoolTipTextMainShellObserveValue = PojoProperties.value("parent.toolTipText").observe(mainShell);
		bindingContext.bindValue(observeTooltipTextContainerObserveWidget, parenttoolTipTextMainShellObserveValue, null, null);
		//
		return bindingContext;
	}
	*/
}
