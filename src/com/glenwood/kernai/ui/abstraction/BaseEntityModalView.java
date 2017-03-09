package com.glenwood.kernai.ui.abstraction;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.view.helpers.EntityViewHelper;

public abstract class BaseEntityModalView <T extends BaseEntity> extends Dialog implements IEntityView{
	
	protected Composite dialogContainer;
	protected Composite headerContainer;
	protected Composite listContainer;
	protected Composite editContainer;
	protected SashForm dividerMain;
	protected Composite editMaster;
	protected Composite editDetail;
	
	protected CLabel lblErrorLabel;
	protected TableViewer listViewer;
	protected Table listTable;

	protected Shell parentShell;
	
	protected IEntityPresenter<T> presenter;
	protected IViewModel<T> model;

	
	protected WritableList<T> input;
	protected WritableValue<T> value;
	protected Binding dirtyBinding;
	protected Binding allValidationBinding;
	protected ObservableListContentProvider contentProvider;
	protected DataBindingContext ctx;
	protected AggregateValidationStatus validationStatus;
	
	protected EntityViewHelper viewHelper;
	
	protected BaseEntityModalView(Shell parentShell) {
		super(parentShell);
		this.parentShell = parentShell;
		this.viewHelper = new EntityViewHelper();
		this.setupModelAndPresenter();
	}
	
	protected void setupModelAndPresenter()
	{
		
	}
	
	public final void initDataBindings()
	{
		ctx.dispose();
        contentProvider = new ObservableListContentProvider();
        listViewer.setContentProvider(contentProvider);
		this.onInitDataBindings();
	}
	
	protected void onInitDataBindings()
	{
		
	}
	
	
	@Override
	protected Control createDialogArea(Composite parent) {
		dialogContainer = (Composite)super.createDialogArea(parent);
		headerContainer = new Composite(dialogContainer, SWT.BORDER);
		//headerContainer.setBackground();
		
		dividerMain = new SashForm(dialogContainer, SWT.HORIZONTAL);
		listContainer = new Composite(dividerMain, SWT.NONE);
		editContainer = new Composite(dividerMain, SWT.NONE);
		editContainer.setLayout(viewHelper.getViewLayout(1));
		dividerMain.setWeights(new int[]{1, 2});
		
		this.listViewer = this.getListViewer(listContainer);
		
		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				listSelectionChangedHandler(event);
			}
		});
		setupListColumns();
		setupEditingContainer();
    	ctx = new DataBindingContext();
		value = new WritableValue<T>();
		presenter.loadModels();
		initDataBindings();
		this.disableEditControls();
		
        if(this.model.getItems().size() > 0)
        {
        	this.model.setCurrentItem(this.model.getItems().get(0));
        	this.listViewer.setSelection(new StructuredSelection(this.model.getCurrentItem()));
        	this.listViewer.getTable().setFocus();
        }
        this.onInit();
		
		dialogContainer.setLayout(new FillLayout());
		
		return dialogContainer;
	}
	
	
	protected void onInit()
	{
		
	}
	
	protected void listSelectionChangedHandler(SelectionChangedEvent event)
	{
		if(event.getSelection().isEmpty())
		{
			this.onListSelectionChangedHandler(null);
		}
		else if(event.getSelection() instanceof IStructuredSelection)
		{
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
	
	protected void setupListColumns()
	{
		
	}
	
	protected final void setupEditingContainer()
	{
		editMaster = new Composite(editContainer, SWT.NONE);
		editMaster.setLayout(viewHelper.getViewLayout(2));
		viewHelper.setViewLayoutData(editMaster, true, true);
		editDetail = new Composite(editContainer, SWT.NONE);
		editDetail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		editDetail.setLayout(new FillLayout());
		lblErrorLabel = new CLabel(editMaster, SWT.NONE);
		viewHelper.setViewLayoutData(lblErrorLabel, 2);
		onSetupEditingContainer();
	}
	
	protected void onSetupEditingContainer()
	{
		
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
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		
		/*
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		*/
		
		/*
		 Button okButton = this.getButton(IDialogConstants.OK_ID);
	     IObservableValue<Button> okBtnTarget = WidgetProperties.enabled().observe(okButton);
	     IConverter converter = IConverter.create(IStatus.class, Boolean.TYPE, (daStatus)-> new Boolean(((IStatus)daStatus).isOK()));
	     ctx.bindValue(okBtnTarget, validationStatus, new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), UpdateValueStrategy.create(converter));
	    */
	     this.onCreateButtonsForButtonBar(parent);
	}
	
	protected void onCreateButtonsForButtonBar(Composite parent)
	{
		
	}
	
	@Override
	protected Point getInitialSize() {
		Rectangle clientArea = this.parentShell.getClientArea();// this.getShell().getDisplay().getClientArea();
		return new Point(clientArea.width - 20, clientArea.height - 20);
		
	}
	
	@Override
	protected Point getInitialLocation(Point initialSize) {
		Point parentLocation = this.parentShell.getLocation();
		return new Point(parentLocation.x + 10, parentLocation.y + 10);
	}
	
	@Override
	protected void okPressed() {
		super.okPressed();
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
	}

	

	@Override
	public void delete() {
	}

	@Override
	public void add() {
		this.presenter.addModel();
		this.onAdd();
	}
	
	protected void onAdd()
	{
		
	}

	@Override
	public void save() {
	}

	@Override
	public void refreshView() {
		value.setValue(model.getCurrentItem());
	}

	@Override
	public void afterAdd() {
		value.setValue(this.model.getCurrentItem());
		input.add(this.model.getCurrentItem());
		this.enableEditControls();
	}

	@Override
	public void afterSelection() {
		this.enableEditControls();
		onAfterSelection();
	}

	protected void onAfterSelection()
	{
		
	}
	
}
