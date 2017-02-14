package com.glenwood.kernai.ui.abstraction;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.ui.ApplicationData;

public class BaseEntityMasterDetailView<T extends BaseEntity, P extends BaseEntity> extends Composite implements IEntityMasterDetailView<T, P> {

	protected Map<String, IAction> actionMap = new HashMap<String, IAction>();
	protected Map<String, ToolItem> toolItemMap = new HashMap<String, ToolItem>();
	protected static final String NEW_ACTION_KEY = "new";
	protected static final String DELETE_ACTION_KEY = "delete";
	protected static final String EDIT_ACTION_KEY = "edit";
	protected static final String ID_PREFIX = "com.glenwood.kernai.ui.view.masterDetailView.";
	protected DataBindingContext ctx;
	protected TableViewer listViewer;
	protected Table listTable;
	protected Composite headerContainer;
	protected Composite listContainer;
	
	protected IEntityMasterDetailPresenter<T, P> presenter;
	protected IMasterDetailViewModel<T, P> model;
	protected WritableList<T> input;
	
	public BaseEntityMasterDetailView(Composite parent, int style, P parentEntity) {
		super(parent, style);
		setupModelAndPresenter(parentEntity);
		init();
	}
	
	
	public IEntityMasterDetailPresenter<T, P> getPresenter() {
		return presenter;
	}

	protected void setupModelAndPresenter(P parentEntity)
	{
		
	}
	
	protected void setupListColumns()
	{
		
	}
	
	protected void initDataBindings()
	{
		
	}

	
	protected void init()
	{
		this.createActions();
		
		headerContainer = new Composite(this, SWT.NONE);
		GridLayout headerLayout = new GridLayout(1, false);
		headerLayout.marginHeight = 0;
		headerLayout.marginWidth = 0;
		headerContainer.setLayout(headerLayout);
		

		ToolBar actionsBar = new ToolBar(headerContainer, SWT.NONE);
		ToolBarManager toolBarManager = new ToolBarManager(actionsBar);
		ActionContributionItem newAction = new ActionContributionItem(this.actionMap.get(NEW_ACTION_KEY));
		
		toolBarManager.add(newAction);
		newAction = new ActionContributionItem(this.actionMap.get(DELETE_ACTION_KEY));
		toolBarManager.add(newAction);
		newAction = new ActionContributionItem(this.actionMap.get(EDIT_ACTION_KEY));
		toolBarManager.add(newAction);
		toolBarManager.update(true);
		

		ToolBar toolbar = toolBarManager.getControl();
		toolbar.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("farook");
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("farook");				
			}
		});
		
		if (toolbar != null )
		{
			for(ToolItem toolItem : toolbar.getItems())
			{
				ActionContributionItem actionItem = (ActionContributionItem)toolItem.getData();
				toolItemMap.put(actionItem.getAction().getActionDefinitionId(), toolItem);
			}
		}
		
	//	headerLabel.setText("List Items");
		actionsBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1 ));
		//headerLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		
		listContainer = new Composite(this, SWT.NONE);
		listViewer = new TableViewer(listContainer, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		listTable = listViewer.getTable();
		listTable.setHeaderVisible(true);
		listTable.setLinesVisible(true);
		this.setupListColumns();
		listViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				T selectedItem = getSelection();
				if(selectedItem != null)
				{
					presenter.editModel(selectedItem);
				}
				
			}
		});
		
		this.ctx = new DataBindingContext();
		
		initDataBindings();
		this.presenter.loadItems();
		GridLayout mainLayout = new GridLayout(1, false);
		mainLayout.verticalSpacing = SWT.FILL;
		mainLayout.horizontalSpacing = SWT.FILL;
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		headerContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1 ));
		listContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		this.setLayout(mainLayout);
	}
	
	private void createActions()
	{
		 IAction newAction = new Action() {
			@Override 
			public void run() {
				presenter.addModel();
			}
		 };
		 newAction.setEnabled(true);
		 newAction.setAccelerator(SWT.ALT | 'j');
		 newAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_ADD_SMALL));
		 newAction.setActionDefinitionId(ID_PREFIX + NEW_ACTION_KEY);
		 this.actionMap.put(NEW_ACTION_KEY, newAction);
		 
		 IAction deleteAction = new Action(){
			 @Override
			 public void run(){
				 T selectedItem = getSelection();
				 if (selectedItem != null)
				 {
					 if(ApplicationData.instance().confirmDelete(getShell()) == true)
					 {
						 presenter.deleteModel(selectedItem);
					 }
				 }
			 }
		 };
		 deleteAction.setEnabled(false);
		 deleteAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_CANCEL_SMALL));
		 deleteAction.setDisabledImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_CANCEL_DISABLED_SMALL));
		 deleteAction.setActionDefinitionId(ID_PREFIX + DELETE_ACTION_KEY);
		 this.actionMap.put(DELETE_ACTION_KEY, deleteAction);
		 
		 IAction editAction = new Action(){
			 @Override
			 public void run(){
				 T selectedItem = getSelection();
				 if (selectedItem != null)
				 {
					 presenter.editModel(selectedItem);
				 }
			 }
		 };
		 editAction.setEnabled(false);
		 editAction.setImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_EDIT_SMALL));
		 editAction.setDisabledImageDescriptor(ApplicationData.instance().getImageRegistry().getDescriptor(ApplicationData.IMAGE_EDIT_DISABLED_SMALL));
		 editAction.setActionDefinitionId(ID_PREFIX + EDIT_ACTION_KEY);
		 this.actionMap.put(EDIT_ACTION_KEY, editAction);
	}

	
	protected T getSelection()
	{
		T item = null;
		IStructuredSelection selection = (IStructuredSelection)listViewer.getSelection();
		if(selection != null)
		{
			item = (T)selection.getFirstElement();
		}
		return item;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAddEdit(Boolean adding) {
		// TODO Auto-generated method stub
		
	}

}
