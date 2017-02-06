package com.glenwood.kernai.ui.abstraction;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.WritableValue;
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

import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.ui.ApplicationData;


public class BaseEntityView<T> extends Composite implements IEntityView {
	
	
	protected TableViewer listViewer;
	protected Table listTable;
	protected WritableList<T> input;
	protected WritableValue<T> value;
	protected Binding editBinding;
	protected Binding dirtyBinding;
	protected DataBindingContext ctx;
	protected IChangeListener stateListener; 
	protected IEntityPresenter<ListHeader> presenter;
	protected IViewModel<T> model;
	protected CLabel errorLabel;
	protected Composite listContainer;
	protected Composite editContainer;
	protected SashForm dividerMain;
	protected Composite editMaster;
	protected Composite editDetail;

	public BaseEntityView(Composite parent, int style) {
		super(parent, style);

		dividerMain = new SashForm(this, SWT.HORIZONTAL);
		listContainer = new Composite(dividerMain, SWT.NONE);
		editContainer = new Composite(dividerMain, SWT.NONE);
		dividerMain.setWeights(new int[]{1, 2});
		listViewer = new TableViewer(listContainer, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		listTable = listViewer.getTable();
		listTable.setHeaderVisible(true);
		listTable.setLinesVisible(true);
		
		GridLayout editContainerLayout = new GridLayout(1, false);
		editContainerLayout.marginWidth = 0;
		editContainerLayout.marginHeight = 0;
		editContainer.setLayout(editContainerLayout);
		
		GridLayout masterLayout = new GridLayout(2, false);
		masterLayout.verticalSpacing = SWT.FILL;
		masterLayout.horizontalSpacing = SWT.FILL;
		
		editMaster = new Composite(editContainer, SWT.NONE);
		editMaster.setLayout(masterLayout);
		editMaster.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1 ));
		
		editDetail = new Composite(editContainer, SWT.NONE);
		//ListDetailMasterDetailView editDetail = new ListDetailMasterDetailView(this, SWT.NONE, null);
		editDetail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1 ));
		editDetail.setLayout(new FillLayout());
		
		errorLabel = new CLabel(editMaster, SWT.NONE);
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        gridData.horizontalSpan = 2;
        gridData.verticalSpan = 1;
        errorLabel.setLayoutData(gridData);		
		this.setLayout(new FillLayout());
		
    	ctx = new DataBindingContext();
		value = new WritableValue<T>();
		
		/* undo the global changes */
		this.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent e) {
				ApplicationData.instance().unloadEntityView();
			}
		});		
		

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
