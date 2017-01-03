package com.glenwood.kernai.ui.view;



import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.MasterCategory;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.presenter.MasterCategoryViewPresenter;
import com.glenwood.kernai.ui.viewmodel.MasterCategoryViewModel;



public class MasterCategoryView extends Composite implements IEntityView {

	private MasterCategoryViewModel model;
	private MasterCategoryViewPresenter presenter;
	private DataBindingContext ctx;
	
	/* controls */
	private Label lblName;
	private Text txtName;
	private TableViewer listViewer;
	private Table listTable;
	//private Binding editBinding;
	private WritableList input;
	private WritableValue value;
	
	public MasterCategoryView(Composite parent, int style) {
		super(parent, style);
		model = new MasterCategoryViewModel();
		presenter = new MasterCategoryViewPresenter(this, model);
		/* data binding */
		ctx = new DataBindingContext();
		value = new WritableValue();
		
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
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		TableViewerColumn nameColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		nameColumn.getColumn().setWidth(200);
		nameColumn.getColumn().setText("Name");
		nameColumn.getColumn().setResizable(false);
		nameColumn.getColumn().setMoveable(false);
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
		nameColumn.setLabelProvider(new ColumnLabelProvider()
		{
			 @Override
			 public String getText(Object element)
			 {
				 if (element != null)
				 {
					 MasterCategory item = (MasterCategory)element;
					 return item.getName();
				 }
				 else
				 {
					 return null;
				 }
			 }
		});
		
		/*
		CellEditor[] cellEditors = new CellEditor[1];
		cellEditors[0] = new TextCellEditor(listTable);
		listViewer.setCellEditors(cellEditors);
		*/
		
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
					//System.out.println(item.getId() + ": " + item.getName());
					model.setCurrentItem(item);
				//	bindValues();
					for(MasterCategory category : model.getItems())
					{
						System.out.println(category.getId() + ": " + category.getName());
					}
					value.setValue(model.getCurrentItem());
					//editBinding.updateTargetToModel();

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
		
		/* load the data */
		presenter.loadModels();
		initDataBindings();
		//bindValues();

	}
	
	/*
	private void bindValues()
	{
		if (editBinding != null)
		{
			ctx.removeBinding(editBinding);
		}
		IObservableValue<?> target = WidgetProperties.text(SWT.Modify).observe(txtName);
		MasterCategory masterCategory = model.getCurrentItem();
		IObservableValue<MasterCategory> modelObservable = BeanProperties.value("name", MasterCategory.class).observe(masterCategory);
		//IObservableValue<?> entity= BeanProperties.value(MasterCategory.class,"name").observe(masterCategory);
		editBinding = ctx.bindValue(target, modelObservable, null, null);
		
	}
	*/

	@Override
	public void delete() {
		this.presenter.deleteModel();
		
	}

	@Override
	public void add() {
		this.presenter.addModel();
		value.setValue(this.model.getCurrentItem());
		//bindValues();
	}

	@Override
	public void save() {
		this.presenter.saveModel();
		
	}
	
	public void refreshList()
	{
		//this.listViewer.setInput(model.getItems());
		
	}
	
	public void updateList()
	{
		//this.listViewer.refresh();
	}
	
	protected void initDataBindings() {
		
		/* works 
		input = new WritableList(model.getItems(), MasterCategory.class);
        ViewerSupport.bind(listViewer, input, BeanProperties.values(new String[] { "name" }));
		*/
		
        ObservableListContentProvider contentProvider = new ObservableListContentProvider();
        listViewer.setContentProvider(contentProvider);

        // create the label provider including monitoring
        // of label changes
        IObservableSet knownElements = contentProvider.getKnownElements();
        final IObservableMap name = BeanProperties.value(MasterCategory.class, "name").observeDetail(knownElements);
        IObservableMap[] labelMaps = {name};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                public String getText(Object element) {
                        return name.get(element) + "";
                }
        };

        listViewer.setLabelProvider(labelProvider);

        // create sample data
        input = new WritableList(model.getItems(), MasterCategory.class);
        // set the writeableList as input for the viewer
        listViewer.setInput(input);
        
        IObservableValue target = WidgetProperties.text(SWT.Modify).observe(txtName);
        IObservableValue dbmodel = BeanProperties.value("name").observeDetail(value);
        ctx.bindValue(target, dbmodel);
        
	}
}
