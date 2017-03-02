package com.glenwood.kernai.ui.abstraction;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.view.helpers.EntityViewHelper;

/**
 * @author root
 *
 * @param <T>
 * @param <P>
 */
/**
 * @author root
 *
 * @param <T>
 * @param <P>
 */
/**
 * @author root
 *
 * @param <T>
 * @param <P>
 */
public abstract class BaseEntityMasterDetailListEditView<T extends BaseEntity, P extends BaseEntity> 
extends Composite implements IEntityMasterDetailListEditView <T, P>, IEntityView{

	protected TableViewer listViewer;
	protected Table listTable;
	protected WritableList<T> input;
	protected WritableValue<T> value;
	protected Binding dirtyBinding;
	protected Binding allValidationBinding;
	protected ObservableListContentProvider contentProvider;
	protected DataBindingContext ctx;
	protected IEntityMasterDetailListEditPresenter<T, P> presenter;
	protected IMasterDetailViewModel<T, P> model;
	protected CLabel errorLabel;
	protected Composite listContainer;
	protected Composite editContainer;
	protected SashForm dividerMain;
	protected Composite editMaster;
	protected Composite editDetail;
	
	protected EntityViewHelper viewHelper;
	protected boolean fillEditingSpace;
	
	ISelectionChangedListener listViewerChangeListener;
	
	public BaseEntityMasterDetailListEditView(Composite parent, int style, P parentEntity) {
		super(parent, style);
		this.setupModelAndPresenter(parentEntity);	
		this.init();
	}
	
	private ISelectionChangedListener getListViewerSelectionChangedListener()
	{
		if (this.listViewerChangeListener == null)
		{
			return  new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					listSelectionChangedHandler(event);
				}
			};
		}
		else
		{
			return this.listViewerChangeListener;
		}
	}
	
	private void init()
	{
		this.viewHelper = new EntityViewHelper();
		dividerMain = new SashForm(this, SWT.HORIZONTAL);
		listContainer = new Composite(dividerMain, SWT.NONE);
		editContainer = new Composite(dividerMain, SWT.NONE);
		editContainer.setLayout(viewHelper.getViewLayout(1));
		this.setDividerWeights();
		this.listViewer = this.getListViewer(listContainer);
		listViewer.addSelectionChangedListener(this.getListViewerSelectionChangedListener());
		setupListColumns();
		setupEditingContainer();
		this.setLayout(new FillLayout());
		
    	ctx = new DataBindingContext();
		value = new WritableValue<T>();
		
		/* undo the global changes */
		this.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent e) {
				unloadEntityView();
			}


		});		
		
		/********************/
		/* unique to master detail */
		presenter.loadModels(this.model.getParent());
		/********************/
		initDataBindings();
		setupDeleteBinding();
		setupSaveBinding();
		ApplicationData.instance().getAction(ApplicationData.NEW_ACTION_KEY).setEnabled(true);
		ApplicationData.instance().loadEntityView(this);
		this.disableEditControls();
		
        if(this.model.getItems().size() > 0)
        {
        	this.model.setCurrentItem(this.model.getItems().get(0));
        	this.listViewer.setSelection(new StructuredSelection(this.model.getCurrentItem()));
        	this.listViewer.getTable().setFocus();
        }
		onInit();
	}
	
	
	/**
	 * hook method, allowing subclasses to add logic after the init method has finished
	 */
	protected void onInit()
	{
		
	}
	
	/**
	 * 
	 */
	public void unloadEntityView()
	{
		ApplicationData.instance().unloadEntityView();
	}
	
	protected final void listSelectionChangedHandler(SelectionChangedEvent event)
	{
		if(event.getSelection().isEmpty())
		{
			this.onListSelectionChangedHandler(null);
		}
		else if(event.getSelection() instanceof IStructuredSelection)
		{
			if (model.getCurrentItem() != null)
			{
				presenter.saveModel();
			}
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			T item = (T)selection.getFirstElement();
			if(item == null)
			{
				this.onListSelectionChangedHandler(null);
			}
			else
			{
				presenter.loadModel(item);
				this.onListSelectionChangedHandler(item);
			}
		}
		else
		{
			this.onListSelectionChangedHandler(null);
		}
	}
	
	protected void onListSelectionChangedHandler(T entity)
	{
		
	}
	
	protected final TableViewer getListViewer(Composite container)
	{
		TableViewer listViewer = new TableViewer(container, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		listTable = listViewer.getTable();
		listTable.setHeaderVisible(true);
		listTable.setLinesVisible(true);
		return listViewer;
	}
	
	protected void setDividerWeights()
	{
		dividerMain.setWeights(new int[]{1, 2});
	}
	
	protected void setupListColumns()
	{
		
	}
	
	protected void setupModelAndPresenter(P parentEntity)
	{
		
	}
	
	protected final void setupEditingContainer()
	{
		editMaster = new Composite(editContainer, SWT.NONE);
		editMaster.setLayout(viewHelper.getViewLayout(2));
		viewHelper.setViewLayoutData(editMaster, true, fillEditingSpace);
		editDetail = new Composite(editContainer, SWT.NONE);
		editDetail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		editDetail.setLayout(new FillLayout());
		errorLabel = new CLabel(editMaster, SWT.NONE);
		viewHelper.setViewLayoutData(errorLabel, 2);
		onSetupEditingContainer();
	}
	
	protected void onSetupEditingContainer()
	{
		
	}
	
	protected final void initDataBindings()
	{
		ctx.dispose();
        contentProvider = new ObservableListContentProvider();
        listViewer.setContentProvider(contentProvider);
        onInitDataBindings();
	}
	
	protected void onInitDataBindings()
	{
		
	}
	
	private void setupSaveBinding()
	{
        ToolItem saveToolItem = ApplicationData.instance().getToolItem(ApplicationData.SAVE_ACTION_KEY);
        if (saveToolItem != null)
        {
        	IObservableValue save = WidgetProperties.enabled().observe(saveToolItem);
        	IObservableValue mdirty= BeanProperties.value("dirty").observe(this.model);
        	dirtyBinding = ctx.bindValue(save, mdirty);
        	dirtyBinding.getTarget().addChangeListener(new IChangeListener() {
    			@Override
    			public void handleChange(ChangeEvent event) {
    				IAction saveAction = ApplicationData.instance().getAction(ApplicationData.SAVE_ACTION_KEY);
    				saveAction.setEnabled(saveToolItem.getEnabled());
    			}
    		});
        }	
	}
	
	private void setupDeleteBinding()
	{
        /* set the enabled of the toolbar items */
        ToolItem deleteToolItem = ApplicationData.instance().getToolItem(ApplicationData.DELETE_ACTION_KEY);
        IObservableValue listViewerSelectionForDelete = ViewersObservables.observeSingleSelection(listViewer);
        IObservableValue<ToolItem> deleteItemTarget = WidgetProperties.enabled().observe(deleteToolItem);
        UpdateValueStrategy convertSelectedToBoolean = new UpdateValueStrategy(){
        	@Override
        	protected IStatus doSet(IObservableValue observableValue, Object value) 
        	{
        		return super.doSet(observableValue, value == null ? Boolean.FALSE : Boolean.TRUE);
        	};
        };
		
        //a binding that sets delete toolitem to disabled based on whether item in list is selected
        Binding deleteBinding = ctx.bindValue(deleteItemTarget, listViewerSelectionForDelete,  new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), convertSelectedToBoolean);
        //a listener on above binding that makes sure action enabled is set set toolitem changes, ie can't databind the enbabled of an action
        deleteBinding.getTarget().addChangeListener(new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				IAction deleteAction = ApplicationData.instance().getAction(ApplicationData.DELETE_ACTION_KEY);
				deleteAction.setEnabled(deleteToolItem.getEnabled());
			}
		});
	}
	
	 protected final SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
	     SelectionAdapter selectionAdapter = new SelectionAdapter() {
	             @Override
	             public void widgetSelected(SelectionEvent e) {
	            	 IListSortComparator comparator = (IListSortComparator)listViewer.getComparator();
	                    comparator.setColumn(index);
	            	 	int dir = comparator.getDirection();
	                    listViewer.getTable().setSortDirection(dir);
	                    listViewer.getTable().setSortColumn(column);
	                    listViewer.refresh();
	             }
	     };
	     return selectionAdapter;
	 }

	@Override
	public final void delete() {
		boolean confirm = ApplicationData.instance().confirmDelete(getShell());
		if (!confirm){return;}
		input.remove(this.model.getCurrentItem());
		this.presenter.deleteModel();
		onDelete();
		this.disableEditControls();
	}
	
	protected void onDelete()
	{
		
	}

	@Override
	public final void add() {
		this.presenter.addModel();
		onAdd();
	}

	protected void onAdd()
	{
		
	}
	
	@Override
	public final void save() {
		this.presenter.saveModel();
		//selected the newly added item in the list
		StructuredSelection selection = new StructuredSelection(this.model.getCurrentItem());
		this.listViewer.setSelection(selection);
		onSave();
	}
	
	protected void onSave()
	{
		
	}

	@Override
	public final void refreshView() {
		value.setValue(model.getCurrentItem());
	}
	
	
	public final void disableEditControls()
	{
		this.viewHelper.setEnabled(editContainer, false);
	}
	
	public final void enableEditControls()
	{
		this.viewHelper.setEnabled(editContainer, true);
	}

	@Override
	public final void afterAdd() {
		value.setValue(this.model.getCurrentItem());
		input.add(this.model.getCurrentItem());
		this.enableEditControls();
		onAfterAdd();
	}
	
	protected void onAfterAdd()
	{
		
	}

	@Override
	public final void afterSelection() {
		this.enableEditControls();
		onAfterSelection();
	}
	
	protected void onAfterSelection()
	{
		
	}



}
