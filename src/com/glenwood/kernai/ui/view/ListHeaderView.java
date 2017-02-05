/*
 * list on left for the the list headers
 * on the right static area for editing and below that 
 * a list for the list details
 * list details table uses a modal dialog for editing
 * 
 * 
 */
package com.glenwood.kernai.ui.view;




import java.util.List;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.presenter.ListHeaderPresenter;
import com.glenwood.kernai.ui.viewmodel.ListHeaderViewModel;


public class ListHeaderView extends Composite implements IEntityView {

	private ListHeaderViewModel model;
	private ListHeaderPresenter presenter;
	private TableViewer listViewer;
	private Table listTable;
	private WritableList<ListHeader> input;
	private WritableValue<ListHeader> value;
	private Binding editBinding;
	private Binding dirtyBinding;
	private DataBindingContext ctx;
	private IChangeListener stateListener; 

	private Label lblName;
	private Text txtName;
	private CLabel errorLabel;

	private Composite editDetail;
	private ListDetailMasterDetailView listDetailView;
	
	private TableViewer detailViewer;
	private Table detailTable;
	private WritableList<ListDetail> detailInput;
	
	public ListHeaderView(Composite parent, int style) {
		super(parent, style);
		this.model = new ListHeaderViewModel();
		this.presenter = new ListHeaderPresenter(this, model);

		SashForm dividerMain = new SashForm(this, SWT.HORIZONTAL);
		
		Composite listContainer = new Composite(dividerMain, SWT.NONE);
		Composite editContainer = new Composite(dividerMain, SWT.NONE);
		dividerMain.setWeights(new int[]{1, 2});
		
		
		listViewer = new TableViewer(listContainer, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		listTable = listViewer.getTable();
		listTable.setHeaderVisible(true);
		listTable.setLinesVisible(true);
		TableViewerColumn nameColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		nameColumn.getColumn().setText("Name");
		nameColumn.getColumn().setResizable(false);
		nameColumn.getColumn().setMoveable(false);
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
	//need to make this an inline class	nameColumn.setEditingSupport(new NameEditor(listViewer));
		
		
		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(event.getSelection().isEmpty())
				{
					return;
				}
				if(event.getSelection() instanceof IStructuredSelection)
				{
					if (model.getCurrentItem() != null)
					{
						presenter.saveModel();
					}

					IStructuredSelection selection = (IStructuredSelection)event.getSelection();
					ListHeader item = (ListHeader)selection.getFirstElement();
					presenter.loadModel(item);
					refreshListDetailView(item);

				}				
			}
		});

		
		/* edit container */
		
		GridLayout editContainerLayout = new GridLayout(1, false);
		editContainer.setLayout(editContainerLayout);
		
		GridLayout masterLayout = new GridLayout(2, false);
		masterLayout.verticalSpacing = SWT.FILL;
		masterLayout.horizontalSpacing = SWT.FILL;
		
		Composite editMaster = new Composite(editContainer, SWT.NONE);
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
		
		lblName = new Label(editMaster, SWT.NONE);
		lblName.setText("Name");
		txtName = new Text(editMaster, SWT.LEFT | SWT.SINGLE | SWT.BORDER);
		lblName.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING, SWT.FILL, false, false, 1, 1 ));
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1 ));

		this.setLayout(new FillLayout());

		ctx = new DataBindingContext();
		value = new WritableValue<ListHeader>();

		presenter.loadModels();
		initDataBindings();
		ApplicationData.instance().getAction(ApplicationData.NEW_ACTION_KEY).setEnabled(true);
		
		/* undo the global changes */
		this.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent e) {
				ApplicationData.instance().unloadEntityView();
			}
		});
		
		ApplicationData.instance().loadEntityView(this);

	}
	
	protected void initDataBindings() {
		if (editBinding != null)
		{
			editBinding.getTarget().removeChangeListener(stateListener);
		}
		ctx.dispose();
		
		
		/* content provider is not array provider but the following: */
        ObservableListContentProvider contentProvider = new ObservableListContentProvider();
        listViewer.setContentProvider(contentProvider);
        
        IObservableSet<ListHeader> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(ListHeader.class, "name").observeDetail(knownElements);
        IObservableMap[] labelMaps = {names};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	ListHeader mc = (ListHeader)element;
                	return mc.getName();
                }
        };
        listViewer.setLabelProvider(labelProvider);
        List<ListHeader> el = model.getItems();
        input = new WritableList(el, ListHeader.class);
        listViewer.setInput(input);
        
        /* binding for the edit screen on name field */
        IObservableValue target = WidgetProperties.text(SWT.Modify).observe(txtName);
        IObservableValue dbmodel = BeanProperties.value("name").observeDetail(value);
       
        /* just the validators and decorators in the name field */
        IValidator validator = new IValidator() {
            @Override
            public IStatus validate(Object value) {
                String nameValue = String.valueOf(value).replaceAll("\\s", "");
               // String namveValue = String.valueOf(value).trim();
                if (nameValue.length() > 0){
                  return ValidationStatus.ok();
                }
                return ValidationStatus.error("Name must be entered");
            }
            
          };
        UpdateValueStrategy strategy = new UpdateValueStrategy();
        strategy.setAfterConvertValidator(validator);
        
        editBinding = ctx.bindValue(target, dbmodel, strategy, null);
        
        ControlDecorationSupport.create(editBinding, SWT.TOP | SWT.LEFT);
        final IObservableValue errorObservable = WidgetProperties.text().observe(errorLabel);
        
        //ToolItems
        ToolItem saveToolitem = ApplicationData.instance().getToolItem(ApplicationData.SAVE_ACTION_KEY);
        if (saveToolitem != null)
        {
        	IObservableValue save = WidgetProperties.enabled().observe(saveToolitem);
        	IObservableValue mdirty= BeanProperties.value(ListHeaderViewModel.class, "dirty").observe(this.model);
        	dirtyBinding = ctx.bindValue(save, mdirty);
        }
        
        Binding validationBinding = ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
        
        /* listening to all changes */
        stateListener = new IChangeListener() {
            @Override
            public void handleChange(ChangeEvent event) {
            	/* is there any way to check if just the properties of model changed */
            	model.setDirty(true);
            }
        };
        
        IObservableList bindings = ctx.getValidationStatusProviders();
        editBinding.getTarget().addChangeListener(stateListener);
        
        
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
	
	public void refreshView()
	{
		value.setValue(model.getCurrentItem());
	}
	
	
	public void refreshListDetailView(ListHeader listHeader)
	{
		if(listDetailView == null)
		{
			listDetailView = new ListDetailMasterDetailView(editDetail, SWT.NONE, listHeader);
			editDetail.layout();
		}
		else
		{
			listDetailView.getPresenter().loadItems(listHeader);
			listDetailView.setVisible(true);
		}
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
		this.txtName.setFocus();
		this.presenter.addModel();
		value.setValue(this.model.getCurrentItem());
		input.add(this.model.getCurrentItem());
		if (this.listDetailView != null)
		{
			this.listDetailView.setVisible(false);
		}
		
	}

	@Override
	public void save() {
		this.presenter.saveModel();
		this.model.setDirty(false);
		//this.refreshListDetailView(this.model.getCurrentItem());
		//selected the newly added item in the list
		StructuredSelection selection = new StructuredSelection(this.model.getCurrentItem());
		this.listViewer.setSelection(selection);
	}

}
