package com.glenwood.kernai.ui.abstraction;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.view.helpers.EntityViewHelper;


public class BaseEntityView<T> extends Composite implements IEntityView {
	
	
	protected TableViewer listViewer;
	protected Table listTable;
	protected WritableList<T> input;
	protected WritableValue<T> value;
	protected Binding editBinding;
	protected Binding dirtyBinding;
	protected DataBindingContext ctx;
	protected IChangeListener stateListener; 
	protected IEntityPresenter<T> presenter;
	protected IViewModel<T> model;
	protected CLabel errorLabel;
	protected Composite listContainer;
	protected Composite editContainer;
	protected SashForm dividerMain;
	protected Composite editMaster;
	protected Composite editDetail;
	
	protected EntityViewHelper viewHelper;

	public BaseEntityView(Composite parent, int style) {
		super(parent, style);
		this.init();


	}
	
	protected void init()
	{
		this.viewHelper = new EntityViewHelper();
		dividerMain = new SashForm(this, SWT.HORIZONTAL);
		listContainer = new Composite(dividerMain, SWT.NONE);
		editContainer = new Composite(dividerMain, SWT.NONE);
		editContainer.setLayout(viewHelper.getViewLayout(1));
		this.setDividerWeights();
		this.listViewer = this.getListViewer(listContainer);
		
		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				listSelectionChangedHandler(event);
			}
		});

		
		editMaster = new Composite(editContainer, SWT.NONE);
		editMaster.setLayout(viewHelper.getViewLayout(2));
		viewHelper.setViewLayoutData(editMaster, true, false);

		
		editDetail = new Composite(editContainer, SWT.NONE);
		editDetail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		editDetail.setLayout(new FillLayout());
		errorLabel = new CLabel(editMaster, SWT.NONE);
		viewHelper.setViewLayoutData(errorLabel, 2);
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
		
	}
	
	public void unloadEntityView()
	{
		ApplicationData.instance().unloadEntityView();
	}
	
	protected T listSelectionChangedHandler(SelectionChangedEvent event)
	{
		if(event.getSelection().isEmpty())
		{
			return null;
		}
		if(event.getSelection() instanceof IStructuredSelection)
		{
			if (model.getCurrentItem() != null)
			{
				presenter.saveModel();
			}

			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			T item = (T)selection.getFirstElement();
			presenter.loadModel(item);
			return item;

		}
		return null;
	}
	
	protected TableViewer getListViewer(Composite container)
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

	@Override
	public void delete() {
		boolean confirm = ApplicationData.instance().confirmDelete(getShell());
		if (!confirm){return;}
		input.remove(this.model.getCurrentItem());
		this.presenter.deleteModel();
	}

	@Override
	public void add() {
		this.presenter.addModel();
		value.setValue(this.model.getCurrentItem());
		input.add(this.model.getCurrentItem());
	}

	@Override
	public void save() {
		this.presenter.saveModel();
	}

	@Override
	public void refreshView() {
		value.setValue(model.getCurrentItem());
	}
	
	

}
