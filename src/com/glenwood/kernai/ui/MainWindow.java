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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.persistence.BaseRepository;
import com.glenwood.kernai.data.persistence.EntityRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactoryConstants;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.view.MainShell;
import com.glenwood.kernai.ui.view.NavView;

//todo - refactor this to be empty shell that is composed of regions, custom class extending composite.
public class MainWindow extends ApplicationWindow {
	//private DataBindingContext m_bindingContext;
	
	Composite container;
	


	public static MainShell mainShell = null;
	private Image[] applicationImages;

	/**
	 * Create the application window.
	 */
	public MainWindow() {
		super(null);
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
		container.setLayout(new FillLayout(SWT.VERTICAL));
		
		mainShell = new MainShell(container, SWT.NONE);
		
		NavView nav = new NavView(mainShell.getLeftRegion(), SWT.NONE);
		mainShell.getLeftRegion().layout();

		/*
		Button btnDynamic = new Button(container, SWT.NONE);
		btnDynamic.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				mainShell.clearEditRegion();
				ModelView dynamic = new ModelView(mainShell.getEditRegion(), SWT.NONE);
				mainShell.getEditRegion().layout();
			}
		});
		btnDynamic.setText("Dynamic");
		*/
	//	m_bindingContext = initDataBindings();

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
		 ApplicationData.instance().addAction("Save", saveAction);

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
		 ApplicationData.instance().addAction("Delete", deleteAction);

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
		 newAction.setText("New");
		 newAction.setEnabled(true);
		 ApplicationData.instance().addAction("New", newAction);

		 
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
		ToolBarManager toolBarManager = new ToolBarManager(style);
		
		ApplicationData.instance().getActionsMap().forEach((key, value) -> {
			ActionContributionItem item = new ActionContributionItem(value);
			item.setMode(ActionContributionItem.MODE_FORCE_TEXT);
			toolBarManager.add(item);
		
		});
		
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
		loadApplicationImages(newShell);
		newShell.setImages(applicationImages);
		loadCachedIcons(newShell);
	}
	
	private void loadApplicationImages(Shell shell)
	{
		final Image small = new Image(shell.getDisplay(), String.format("%s%s", ApplicationData.IMAGES_PATH, "Activity_16xSM.png"));
		final Image large = new Image(shell.getDisplay(), String.format("%s%s", ApplicationData.IMAGES_PATH, "Activity_32x.png"));
		applicationImages= new Image[] { small, large };
	}

	private void loadCachedIcons(Shell shell)
	{
		Image image = new Image(shell.getDisplay(), String.format("%s%s", ApplicationData.IMAGES_PATH, "Diagram_16x.png"));
		ApplicationData.smallIcons.put(ApplicationData.IMAGE_DIAGRAM, image);
		image = new Image(shell.getDisplay(), String.format("%s%s",  ApplicationData.IMAGES_PATH, "MasterPage_16x.png"));
		ApplicationData.smallIcons.put(ApplicationData.IMAGE_MASTERPAGE, image);
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
		for (Image image : this.applicationImages)
		{
			image.dispose();
		}
		ApplicationData.smallIcons.forEach((key, value) -> value.dispose());
		return super.close();
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
