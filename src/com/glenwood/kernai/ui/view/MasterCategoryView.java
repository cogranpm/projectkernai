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
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.internal.databinding.swt.SWTVetoableValueDecorator;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.entity.MasterCategory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.presenter.MasterCategoryViewPresenter;
import com.glenwood.kernai.ui.viewmodel.MasterCategoryViewModel;

public class MasterCategoryView extends Composite implements IEntityView {

	private MasterCategoryViewModel model;
	private MasterCategoryViewPresenter presenter;
	private DataBindingContext ctx;
	private IChangeListener stateListener; 
	
	/* controls */
	private Label lblName;
	private Text txtName;
	private Label errorLabel;
	private TableViewer listViewer;
	private Table listTable;
	private Binding editBinding;
	private WritableList input;
	private WritableValue<MasterCategory> value;
	//private WritableValue dirtyValue;
	private Binding dirtyBinding;
	
	public MasterCategoryView(Composite parent, int style) {
		super(parent, style);
		model = new MasterCategoryViewModel();
		presenter = new MasterCategoryViewPresenter(this, model);
		/* data binding */
		ctx = new DataBindingContext();
		value = new WritableValue<MasterCategory>();
		//dirtyValue = new WritableValue();
		//dirtyValue.setValue(model);
		
		this.setLayout(new FillLayout());
		
		/* firstly a list and then an edit view */
		SashForm dividerMain = new SashForm(this, SWT.HORIZONTAL);
		
		Composite listContainer = new Composite(dividerMain, SWT.NONE);
		Composite editContainer = new Composite(dividerMain, SWT.NONE);
		dividerMain.setWeights(new int[]{1, 2});
		
		/* list container */
		//listContainer.setLayout(new FillLayout());
		listViewer = new TableViewer(listContainer, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		listTable = listViewer.getTable();
		listTable.setHeaderVisible(true);
		listTable.setLinesVisible(true);
		//listViewer.setContentProvider(ArrayContentProvider.getInstance());
		
		TableViewerColumn nameColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		nameColumn.getColumn().setText("Name");
		nameColumn.getColumn().setResizable(false);
		nameColumn.getColumn().setMoveable(false);
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
		nameColumn.setEditingSupport(new NameEditor(listViewer));
		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(event.getSelection().isEmpty())
				{
					return;
				}
				if(event.getSelection() instanceof IStructuredSelection)
				{
					IStructuredSelection selection = (IStructuredSelection)event.getSelection();
					MasterCategory item = (MasterCategory)selection.getFirstElement();
					model.setCurrentItem(item);
					value.setValue(model.getCurrentItem());
					model.setDirty(false);
				}				
			}
		});
		
		/* edit container */
		GridLayout layout = new GridLayout(2, false);
		editContainer.setLayout(layout);
		lblName = new Label(editContainer, SWT.NONE);
		lblName.setText("Name");
		txtName = new Text(editContainer, SWT.LEFT | SWT.SINGLE | SWT.BORDER);
		lblName.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING, SWT.FILL, false, false, 1, 1 ));
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1 ));
		
		Label descAllLabel = new Label(editContainer, SWT.NONE);
        descAllLabel.setText("All Validation Problems:");
        descAllLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING, SWT.FILL, true, false, 1, 1 ));
        
        errorLabel = new Label(editContainer, SWT.NONE);
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        gridData.horizontalSpan = 1;
        gridData.verticalSpan = 1;
        errorLabel.setLayoutData(gridData);		
		
		/* load the data */
		presenter.loadModels();
		
		initDataBindings();

	}
	
	@Override
	public void delete() {
		input.remove(model.getCurrentItem());
		this.presenter.deleteModel();
	}

	@Override
	public void add() {
		this.txtName.setFocus();
		this.presenter.addModel();
		value.setValue(this.model.getCurrentItem());
		input.add(this.model.getCurrentItem());
	}

	@Override
	public void save() {
		this.presenter.saveModel();
		model.setDirty(false);
	}
	
	public void refreshList()
	{
		//this.listViewer.setInput(model.getItems());
		this.listViewer.refresh();
		
	}

	protected void initDataBindings() {
		
		/*
		 IObservableList<ValidationStatusProvider> providers = ctx.getValidationStatusProviders();
         for (Object o : providers) {
                 Binding b = (Binding) o;
                 b.getTarget().removeChangeListener(stateListener);
         }
         */
		if (editBinding != null)
		{
			editBinding.getTarget().removeChangeListener(stateListener);
		}
		ctx.dispose();
		
		/* works 
		input = new WritableList(model.getItems(), MasterCategory.class);
        ViewerSupport.bind(listViewer, input, BeanProperties.values(new String[] { "name" }));
		*/
		
		/* content provider is not array provider but the following: */
        ObservableListContentProvider contentProvider = new ObservableListContentProvider();
        listViewer.setContentProvider(contentProvider);
        
        IObservableSet<MasterCategory> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(MasterCategory.class, "name").observeDetail(knownElements);
        IObservableMap[] labelMaps = {names};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	MasterCategory mc = (MasterCategory)element;
                	return mc.getName();
                }
        };
        listViewer.setLabelProvider(labelProvider);
        List<MasterCategory> el = model.getItems();
        input = new WritableList(el, MasterCategory.class);
        listViewer.setInput(input);
        
        
        /* this is from master detail example, but changes do not reflect from table to text box immediately and new doesn't work either 
    	IObservableValue selection = ViewersObservables.observeSingleSelection(listViewer);
    	IObservableValue detailObservable = BeanProperties.value((Class) selection.getValueType(), "name", String.class).observeDetail(selection);
		editBinding = ctx.bindValue(WidgetProperties.text(SWT.Modify).observe(txtName), detailObservable , new UpdateValueStrategy(false, UpdateValueStrategy.POLICY_UPDATE), null);
		*/
		
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
        //editBinding = ctx.bindValue(WidgetProperties.text(SWT.Modify).observe(txtName), detailObservable , strategy, null);
        
        
        ControlDecorationSupport.create(editBinding, SWT.TOP | SWT.LEFT);
        final IObservableValue errorObservable = WidgetProperties.text().observe(errorLabel);
        
        //ToolItems
        ToolItem saveToolitem = ApplicationData.instance().getToolItem("Save");
        IObservableValue save = WidgetProperties.enabled().observe(saveToolitem);
        IObservableValue mdirty= BeanProperties.value(MasterCategoryViewModel.class, "dirty").observe(this.model);
        dirtyBinding = ctx.bindValue(save, mdirty);
        
        // this one listenes to all changes
        ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
        
        /* listening to all changes */
        stateListener = new IChangeListener() {
            @Override
            public void handleChange(ChangeEvent event) {
            	System.out.println("something changed:  " + event.getSource().toString());
            	if(event.getSource() instanceof SWTVetoableValueDecorator)
            	{
            		SWTVetoableValueDecorator source = (SWTVetoableValueDecorator) event.getSource();
            		System.out.println(source.getValue().toString());
            	}
            	model.setDirty(true);
            }
        };
        
     // get the validation status provides
        IObservableList bindings = ctx.getValidationStatusProviders();

        // mot all validation status providers
        // are bindings, e.g. MultiValidator
        // otherwise you could use
        // context.getBindings()

        // register the listener to all bindings
        /* 
        for (Object o : bindings) {
                Binding b = (Binding) o;
                b.getTarget().addChangeListener(stateListener);
        }
        */
        editBinding.getTarget().addChangeListener(stateListener);
	}
	
	private class NameEditor extends EditingSupport {
		
		private final TableViewer viewer;
		private final CellEditor editor;
		
		public NameEditor(TableViewer viewer)
		{
			super(viewer);
			this.viewer = viewer;
			this.editor = new TextCellEditor(viewer.getTable());
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return new TextCellEditor(viewer.getTable());
		}
		
		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			return ((MasterCategory)element).getName();

		}
		
		@Override
		protected void setValue(Object element, Object userInputValue) {
			((MasterCategory)element).setName(String.valueOf(userInputValue));
			viewer.update(element, null);
		}
	}
}
