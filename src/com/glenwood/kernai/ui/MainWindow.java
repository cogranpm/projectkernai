package com.glenwood.kernai.ui;





import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
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

//todo - refactor this to be empty shell that is composed of regions, custom class extending composite.
public class MainWindow extends ApplicationWindow {
	//private DataBindingContext m_bindingContext;
	
	private Composite container;
	
	/**
	 * Create the application window.
	 */
	public MainWindow() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
		ApplicationData.instance().addImagesToRegistry();
	}
	
	


	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		
		
		CTabFolder folder = new CTabFolder(container, SWT.TOP);
		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText("&Getting Started");
		CTabItem masterPropertyTabItem = new CTabItem(folder, SWT.NONE);
		masterPropertyTabItem.setText("Master &Properties");
		
		Composite masterPropertyPane = new Composite(folder, SWT.NONE);
		ToolBar masterPropertyToolBar = this.addNavigationToolbar(masterPropertyPane, masterPropertyTabItem);
		ToolBarManager toolBarManager = new ToolBarManager(masterPropertyToolBar);
		

		//toolBarManager.add(actionCut);
		ActionContributionItem ai = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.GOTO_MASTERPROPERTY_LISTS));
		ai.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolBarManager.add(ai);
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
		

		//Composite test = new Composite(container, SWT.NONE);
		//test.setLayout(new FillLayout());
		//ApplicationData.instance().setMainShell(new MainShell(container, SWT.NONE));
		
		/*
		NavView nav = new NavView(mainShell.getLeftRegion(), SWT.NONE);
		mainShell.getLeftRegion().layout();
		*/
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
		
		/* a row of  buttons for the master property tab */
	
		
		
		
		//masterPropertyPane.setLayout(new GridLayout(2, 1));
		//ToolBar masterPropertyToolbar = new ToolBar(masterPropertyTabItem.GET, SWT.None);
		
		/* This is not actually right, should be a row buttons 
		CTabFolder masterPropertyFolder = new CTabFolder(folder, SWT.NONE);
		
		
		CTabItem masterCategoryItem = new CTabItem(masterPropertyFolder, SWT.NONE);
		masterCategoryItem.setText("Master &Category");
		
		CTabItem propertyGroupItem = new CTabItem(masterPropertyFolder, SWT.NONE);
		propertyGroupItem.setText("Property Group");

		CTabItem propertyTypeItem = new CTabItem(masterPropertyFolder, SWT.NONE);
		propertyTypeItem.setText("Property Type");
		 
		CTabItem masterPropertyItem = new CTabItem(masterPropertyFolder, SWT.NONE);
		masterPropertyItem.setText("Master Property");
		
		masterPropertyFolder.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				CTabItem item = (CTabItem)e.item;
				//lazily load the control
				if (item.equals(masterCategoryItem))
				{
					if (item.getControl() == null)
					{
						masterCategoryItem.setControl(new MasterCategoryView(masterPropertyFolder, SWT.NONE));
					}
					ApplicationData.instance().setCurrentEntityView((IEntityView)item.getControl());
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		masterPropertyTabItem.setControl(masterPropertyFolder);
		*/
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
		 saveAction.setEnabled(true);
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
		 deleteAction.setText("Delete");
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
		 newAction.setEnabled(false);
		 newAction.setAccelerator(SWT.CTRL | 'N');
		 ApplicationData.instance().addAction(ApplicationData.NEW_ACTION_KEY, newAction);
		 
		 
		 IAction goToMasterPropertyList = new Action() {
			@Override 
			public void run() {
				System.out.println("hello");
				/* get the active view and call new
				
				IEntityView currentEntityView = ApplicationData.instance().getCurrentEntityView();
				if(currentEntityView != null)
				{
					currentEntityView.add();
				}
				 */
			}
		 };
		 goToMasterPropertyList.setText("&Lists");
		 goToMasterPropertyList.setEnabled(true);
		 //newAction.setAccelerator(SWT.CTRL | 'N');
		 ApplicationData.instance().addAction(ApplicationData.GOTO_MASTERPROPERTY_LISTS, goToMasterPropertyList);

		 
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(SWT.NONE);

		ActionContributionItem item = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.DELETE_ACTION_KEY));
		item.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolBarManager.add(item);
		
		item = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.NEW_ACTION_KEY));
		item.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolBarManager.add(item);
		
		item = new ActionContributionItem(ApplicationData.instance().getAction(ApplicationData.SAVE_ACTION_KEY));
		item.setMode(ActionContributionItem.MODE_FORCE_TEXT);
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
		/*
		loadApplicationImages(newShell);
		newShell.setImages(applicationImages);
		loadCachedIcons(newShell);
		*/
	}
	
	private void loadCachedIcons(Shell shell)
	{
	
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
		GridLayout layout = new GridLayout(1, false);
		parent.setLayout(layout);
		parentTabItem.setControl(parent);
		ToolBar t = new ToolBar(parent, SWT.WRAP);
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace=false;
		gd.grabExcessVerticalSpace = false;
		t.setLayoutData(gd);
		return t;
	}
	
	private ToolItem addNavigationToolItem(String label, ToolBar toolbar)
	{
		ToolItem titem = new ToolItem(toolbar, SWT.PUSH);
		titem.setText(label);
		return titem;
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
