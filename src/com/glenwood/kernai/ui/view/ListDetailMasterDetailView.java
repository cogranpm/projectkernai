package com.glenwood.kernai.ui.view;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailView;
import com.glenwood.kernai.ui.abstraction.IEntityMasterDetailPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityMasterDetailView;
import com.glenwood.kernai.ui.presenter.ListDetailViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ListDetailViewModel;

public final class ListDetailMasterDetailView extends BaseEntityMasterDetailView<ListDetail, ListHeader> {
	
	private  ListDetailMasterDetailView(Composite parent, int style) {
		this(parent, style, null);
	}
	
	
	public ListDetailMasterDetailView(Composite parent, int style, ListHeader listHeader)
	{
		super(parent, SWT.BORDER, listHeader);
	}
	
	@Override
	protected void setupModelAndPresenter(ListHeader parent)
	{
		this.model = new ListDetailViewModel(parent);
		this.presenter = new ListDetailViewPresenter(this, (ListDetailViewModel) model);
	}


	@Override
	protected void setupListColumns() {
		super.setupListColumns();
		
		TableViewerColumn keyColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		keyColumn.getColumn().setText("Key");
		keyColumn.getColumn().setResizable(false);
		keyColumn.getColumn().setMoveable(false);
		TableViewerColumn labelColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		labelColumn.getColumn().setText("Label");
		labelColumn.getColumn().setResizable(false);
		labelColumn.getColumn().setMoveable(false);
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(keyColumn.getColumn(), new ColumnWeightData(50));
		tableLayout.setColumnData(labelColumn.getColumn(), new ColumnWeightData(50));
	}

	
	
	
	

	
	public void showAddEdit(Boolean adding)
	{
		ListDetailModalView modalView = new ListDetailModalView(getShell());
		modalView.setModel((ListDetailViewModel) model);
		if(modalView.open() == Window.OK)
		{
			this.presenter.saveModel();
			if(adding)
			{
				this.input.add(this.model.getCurrentItem());
			}
			else
			{
				this.listViewer.refresh();
			}
		}
	}
	
	public void refreshView()
	{
		input = new WritableList(model.getItems(), ListDetail.class);
		listViewer.setInput(input);	
	}
	
	@Override
	protected void initDataBindings()
	{
        ObservableListContentProvider detailContentProvider = new ObservableListContentProvider();
        listViewer.setContentProvider(detailContentProvider);
        
        IObservableSet<ListDetail> detailElements = detailContentProvider.getKnownElements();
        final IObservableMap keys = BeanProperties.value(ListDetail.class, "key").observeDetail(detailElements);
        final IObservableMap<String, String> labels = BeanProperties.value(ListDetail.class, "label").observeDetail(detailElements);
        IObservableMap[] detailLabelMaps = {keys, labels};
        ILabelProvider detailLabelProvider = new ObservableMapLabelProvider(detailLabelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	ListDetail mc = (ListDetail)element;
                	switch(columnIndex)
                	{
                	case 0:
                		return mc.getKey();
                	case 1:
                		return mc.getLabel();
                	default:
                		return null;
                	}
                }
        };
        listViewer.setLabelProvider(detailLabelProvider);
        
        /* set the enabled of the toolbar items */
        IObservableValue listViewerSelectionForDelete = ViewersObservables.observeSingleSelection(listViewer);
        IObservableValue listViewerSelectionForEdit = ViewersObservables.observeSingleSelection(listViewer);
        IObservableValue<ToolItem> deleteItemTarget = WidgetProperties.enabled().observe(this.toolItemMap.get(ID_PREFIX + DELETE_ACTION_KEY));
        IObservableValue<ToolItem> editItemTarget = WidgetProperties.enabled().observe(this.toolItemMap.get(ID_PREFIX + EDIT_ACTION_KEY));
        UpdateValueStrategy convertSelectedToBoolean = new UpdateValueStrategy(){
        	@Override
        	protected IStatus doSet(IObservableValue observableValue, Object value) 
        	{
        		return super.doSet(observableValue, value == null ? Boolean.FALSE : Boolean.TRUE);
        	};
        };
        Binding deleteBinding = ctx.bindValue(deleteItemTarget, listViewerSelectionForDelete,  new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), convertSelectedToBoolean);
        Binding editBinding = ctx.bindValue(editItemTarget, listViewerSelectionForEdit, new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), convertSelectedToBoolean);
       
        //listener required to enable/disable action associated with toolItem, can't databind an action
        editBinding.getTarget().addChangeListener(new IChangeListener() {
			
			@Override
			public void handleChange(ChangeEvent event) {
				IAction editAction = actionMap.get(EDIT_ACTION_KEY);
				ToolItem editToolItem = toolItemMap.get(ID_PREFIX + EDIT_ACTION_KEY);
				editAction.setEnabled(editToolItem.getEnabled());
			}
		});
        
        deleteBinding.getTarget().addChangeListener(new IChangeListener() {
			
			@Override
			public void handleChange(ChangeEvent event) {
				IAction deleteAction = actionMap.get(DELETE_ACTION_KEY);
				ToolItem deleteToolItem = toolItemMap.get(ID_PREFIX + DELETE_ACTION_KEY);
				deleteAction.setEnabled(deleteToolItem.getEnabled());				
			}
		});
        
     // ISideEffect sideEffect =ISideEffect.create( () -> { editAction.setEnabled(editItemTarget.getValue().isEnabled());});
      
      //ISideEffectFactory sfactory = WidgetSideEffects.createFactory(this.toolItemMap.get(ID_PREFIX + EDIT_ACTION_KEY));
      //sfactory.create(this.toolItemMap.get(ID_PREFIX + EDIT_ACTION_KEY)::getEnabled, this.actionMap.get(EDIT_ACTION_KEY)::setEnabled);
      //ISideEffect sideEffect = ISideEffect.create(() -> {return deleteItemTarget.getValue()}, this.actionMap.get(DELETE_ACTION_KEY)::setEnabled);
     // sfactory.create(this.toolItemMap.get(ID_PREFIX + EDIT_ACTION_KEY)::toString, dummyLabel::setText);
   

	}

}
