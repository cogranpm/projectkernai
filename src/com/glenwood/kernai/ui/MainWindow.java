package com.glenwood.kernai.ui;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.persistence.BaseRepository;
import com.glenwood.kernai.data.persistence.EntityRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactoryConstants;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.view.ListHeaderView;
import com.glenwood.kernai.ui.view.MasterCategoryView;

//todo - refactor this to be empty shell that is composed of regions, custom class extending composite.
public class MainWindow extends ApplicationWindow {
	//private DataBindingContext m_bindingContext;
	
	private Composite container;
	private Composite masterPropertyPane;



	
	/**
	 * Create the application window.
	 */
	public MainWindow() {
		super(null);
		ApplicationData.instance().addImagesToRegistry();
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
		
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
		masterPropertyTabItem.setText("Master &Properties");
		
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
		//Color listBackground = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
		//masterPropertyPane.setBackground(listBackground);

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
		/*
		ToolItem listToolItem = this.addNavigationToolItem("Lists", masterPropertyToolBar);
		ToolItem masterCategoryToolItem = this.addNavigationToolItem("Master Category", masterPropertyToolBar);
		ToolItem propertyTypeToolItem = this.addNavigationToolItem("Property Type", masterPropertyToolBar);
		ToolItem propertyGroupToolItem = this.addNavigationToolItem("Property Group", masterPropertyToolBar);
		ToolItem masterPropertyToolItem = this.addNavigationToolItem("Master Property", masterPropertyToolBar);
		*/
		
		item  = new CTabItem(folder, SWT.NONE);
		item.setText("&Models");
		item = new CTabItem(folder, SWT.NONE);
		item.setText("&Scripting");
		


		ToolBar toolbar = this.getToolBarManager().getControl();
		if (toolbar != null )
		{
			for(ToolItem toolItem : toolbar.getItems())
			{
				ApplicationData.instance().addToolItem(toolItem.getText(), toolItem);
			}
		}
		if (ApplicationData.instance().getToolItem("Save") != null)
		{
			ApplicationData.instance().getToolItem("Save").setEnabled(false);
		}
		
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
		// Create the actions
		 IAction testAction = new Action() {
		        @Override
		        public void run() {
		        	EntityRepository entityRepository;
		        	BaseRepository attributeRespository;
		        	entityRepository = new EntityRepository(PersistenceManagerFactory.getPersistenceManager(PersistenceManagerFactoryConstants.PERSISTENCE_FACTORY_TYPE_COUCHBASE_LITE));
		    		attributeRespository = new BaseRepository(PersistenceManagerFactory.getPersistenceManager(PersistenceManagerFactoryConstants.PERSISTENCE_FACTORY_TYPE_COUCHBASE_LITE));
		        	Entity customersEntity = new Entity();
		    		customersEntity.setName("customers");
		    		Attribute companyName = new Attribute();
		    		companyName.setName("CompanyName");
		    		companyName.setLength(40L);
		    		companyName.setAllowNull(false);
		    		companyName.setDataType("String");
		    		companyName.setEntity(customersEntity);
		    		entityRepository.save(customersEntity);
		    		attributeRespository.save(companyName);
		        }
		    };
		    testAction.setText("Test");
		    testAction.setToolTipText("Run a test.");
		    testAction.setEnabled(true);	
		    ApplicationData.instance().addAction("Test", testAction);
		    
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
		 saveAction.setText("Save");
		 saveAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_SAVE_SMALL));
		 saveAction.setEnabled(true);
		 saveAction.setAccelerator(SWT.MOD1 + 'S');
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
		 deleteAction.setText("Delete");
		 deleteAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_CANCEL_SMALL));
		 deleteAction.setDisabledImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_CANCEL_DISABLED_SMALL));
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
		 newAction.setText("&New");
		 newAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_ADD_SMALL));
		 newAction.setEnabled(false);
		 newAction.setAccelerator(SWT.MOD1 | 'N');
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
		 goToMasterPropertyList.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_ACTIVITY_SMALL));
		 goToMasterPropertyList.setEnabled(true);
		 goToMasterPropertyList.setAccelerator(SWT.CTRL | 'L');
		 ApplicationData.instance().addAction(ApplicationData.GOTO_MASTERPROPERTY_LISTS, goToMasterPropertyList);

		 
		 IAction goToMasterPropertyCategory = new Action("&Master Category", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				//ApplicationData.instance().getAction(ApplicationData.GOTO_MASTERPROPERTY_LISTS).get
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
				System.out.println("hello");
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
				System.out.println("hello");
			}
		 };
		 //goToMasterPropertyGroup.setText("Property &Group");
		 goToMasterPropertyGroup.setEnabled(true);
		 //newAction.setAccelerator(SWT.CTRL | 'N');
		 ApplicationData.instance().addAction(ApplicationData.GOTO_MASTERPROPERTY_GROUP, goToMasterPropertyGroup);
		 
		 IAction goToMasterPropertyType = new Action("Property &Type", IAction.AS_CHECK_BOX) {
			@Override 
			public void run() {
				ApplicationData.instance().uncheckActions(masterpropertyActionKeys, ApplicationData.GOTO_MASTERPROPERTY_TYPE);
				System.out.println("hello");
			}
		 };
		 //goToMasterPropertyType.setText("Property &Type");
		 goToMasterPropertyType.setEnabled(true);
		 //newAction.setAccelerator(SWT.CTRL | 'N');
		 ApplicationData.instance().addAction(ApplicationData.GOTO_MASTERPROPERTY_TYPE, goToMasterPropertyType);
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
		
		MenuManager helpMenu = new MenuManager("&Help");
		helpMenu.add(ApplicationData.instance().getAction(ApplicationData.ABOUT_ACTION_KEY));
		menuManager.add(helpMenu);
		
		MenuManager editMenu = new MenuManager("&Edit");
		editMenu.add(ApplicationData.instance().getAction(ApplicationData.DELETE_ACTION_KEY));
		menuManager.add(editMenu);
		
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
				
		item = new ActionContributionItem(ApplicationData.instance().getAction("Test"));
		item.setMode(ActionContributionItem.MODE_FORCE_TEXT);
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
		return new Point(689, 479);
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
		ToolBar t = new ToolBar(parent, SWT.WRAP);
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
