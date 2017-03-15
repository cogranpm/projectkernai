package com.glenwood.kernai.ui.view;

import java.util.List;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.presenter.AttributeViewPresenter;
import com.glenwood.kernai.ui.view.helpers.ListSorterHelper;
import com.glenwood.kernai.ui.viewmodel.AttributeViewModel;

public class AttributeView extends BaseEntityMasterDetailListEditView<Attribute, Entity> {

	private Label lblName;
	private Text txtName;
	
	private Label lblDataType;
	private ComboViewer cboDataType;
	
	private Label lblAllowNull;
	private Button btnAllowNull;
	
	private Label lblLength;
	private Text txtLength;
	
	public AttributeView(Composite parent, int style, Entity parentEntity) {
		super(parent, style, parentEntity);
		
	}
	
	@Override
	protected void setupModelAndPresenter(Entity parentEntity) {
		super.setupModelAndPresenter(parentEntity);
		this.model = new AttributeViewModel(parentEntity);
		this.presenter = new AttributeViewPresenter(this, (AttributeViewModel)this.model);
	}
	
	@Override
	protected void setupListColumns() {
		super.setupListColumns();
		AttributeViewModel aModel = (AttributeViewModel)this.model;
		this.listViewer.setComparator(new ViewerComparator());
		TableViewerColumn nameColumn = this.viewHelper.getListColumn(listViewer, "Name");
		TableViewerColumn dataTypeColumn = this.viewHelper.getListColumn(listViewer, "Data Type");
		TableViewerColumn allowNullColumn = this.viewHelper.getListColumn(listViewer, "Allow Null");
		TableViewerColumn lengthColumn = this.viewHelper.getListColumn(listViewer, "Length");
		nameColumn.getColumn().addSelectionListener(this.getSelectionAdapter(nameColumn.getColumn(), 0));
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(dataTypeColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(allowNullColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(lengthColumn.getColumn(), new ColumnWeightData(100));
		
		nameColumn.setEditingSupport(new EditingSupport(this.listViewer) {
			
			@Override
			protected void setValue(Object element, Object value) {
				((Attribute)element).setName(String.valueOf(value));
				listViewer.update(element, null);
			}
			
			@Override
			protected Object getValue(Object element) {
				return ((Attribute)element).getName();
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(listViewer.getTable());
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
		
		dataTypeColumn.setEditingSupport(new EditingSupport(this.listViewer) {
			
			@Override
			protected void setValue(Object element, Object value) {

				if (element == null)
				{
					return;
				}
				if (value == null)
				{
					return;
				}
				Attribute attribute = (Attribute)element;
				ListDetail listDetail = (ListDetail)value;
				attribute.setDataTypeLookup(listDetail);
				listViewer.update(element, null);
			}
			
			@Override
			protected Object getValue(Object element) {
				Attribute attribute = (Attribute)element;
				return attribute.getDataTypeLookup();
				/*
				String dataType = attribute.getDataType();
				for(ListDetail listDetail : aModel.getDataTypeLookup())
				{
					if(listDetail.getId().equalsIgnoreCase(dataType))
					{
						return listDetail;
					}
						
				}
				return null;
				*/
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				//return new TextCellEditor(listViewer.getTable());
				ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(listViewer.getTable(), SWT.READ_ONLY);
				IStructuredContentProvider contentProvider = new ArrayContentProvider();
				editor.setContentProvider( contentProvider );
				editor.setLabelProvider(new LabelProvider(){
					@Override
					public String getText(Object element)
					{
						ListDetail item = (ListDetail)element;
						return item.getLabel();

					}
				});
				editor.setInput(aModel.getDataTypeLookup());
				return editor;
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});

	}
	
	@Override
	protected void onSetupEditingContainer() {
		
		this.lblEditHeader.setText(String.format("Entity: %s", ApplicationData.instance().getCurrentEntity().getName()));
		lblName = new Label(editMaster, SWT.NONE);
		lblName.setText("Name");
		txtName = viewHelper.getTextEditor(editMaster);

		lblDataType = new Label(editMaster, SWT.NONE);
		lblDataType.setText("Data Type");
		cboDataType = new ComboViewer(editMaster);
		cboDataType.setContentProvider(ArrayContentProvider.getInstance());
		cboDataType.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element)
			{
				ListDetail item = (ListDetail)element;
				return item.getLabel();
			}
		});
		AttributeViewModel aModel = (AttributeViewModel)this.model;
		cboDataType.setInput(aModel.getDataTypeLookup());
		
		lblAllowNull = new Label(editMaster, SWT.NONE);
		lblAllowNull.setText("Allow Null");
		btnAllowNull = new Button(editMaster, SWT.CHECK);
		
		lblLength = viewHelper.getEditLabel(editMaster, "Length");
		txtLength = viewHelper.getTextEditor(editMaster);
		
		
		viewHelper.layoutEditLabel(lblName);
		viewHelper.layoutEditEditor(txtName);
		viewHelper.layoutEditLabel(lblDataType);
		viewHelper.layoutComboViewer(cboDataType);
		viewHelper.layoutEditLabel(lblAllowNull);
		viewHelper.layoutEditEditor(btnAllowNull);
		viewHelper.layoutEditLabel(lblLength);
		viewHelper.layoutEditEditor(txtLength);
	}
	
	@Override
	protected void onInitDataBindings() {
       // IObservableSet<Attribute> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(Attribute.class, "name").observeDetail(knownElements);
        final IObservableMap dataTypes = BeanProperties.value(Attribute.class, "dataType").observeDetail(knownElements);
        final IObservableMap allowNulls = BeanProperties.value(Attribute.class, "allowNull").observeDetail(knownElements);
        final IObservableMap lengths = BeanProperties.value(Attribute.class, "length").observeDetail(knownElements);
        IObservableMap[] labelMaps = {names, dataTypes, allowNulls, lengths};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	Attribute mc = (Attribute)element;
                	switch(columnIndex)
                	{
                	case 0:
                    	return mc.getName();
                	case 1:
                		if(mc.getDataTypeLookup() != null)
                		{
                			return mc.getDataTypeLookup().getLabel();
                		}
                		else
                		{
                			return null;
                		}
                	case 2:
                		if(mc.getAllowNull() == null || mc.getAllowNull() == false)
                		{
                			return "No";
                		}
                		else
                		{
                			return "Yes";
                		}
                	case 3:
                		return (mc.getLength() != null) ? mc.getLength().toString() : "";
                    default:
                    	return "";
                	}

                }
        };
        listViewer.setLabelProvider(labelProvider);
        List<Attribute> el = model.getItems();
        input = new WritableList(el, Attribute.class);
        listViewer.setInput(input);
        
        /* binding for the edit screen on name field */
        IObservableValue nameTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtName);
        IObservableValue nameModelObservable = BeanProperties.value("name").observeDetail(value);
		
        IObservableValue dataTypeTargetObservable = ViewerProperties.singleSelection().observe(cboDataType);
        IObservableValue dataTypeModelObservable = BeanProperties.value("dataTypeLookup").observeDetail(value);
        
        IObservableValue allowNullTargetObservable = WidgetProperties.selection().observe(btnAllowNull);
        IObservableValue allowNullModelObservable = BeanProperties.value("allowNull").observeDetail(value);

        IObservableValue lengthTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtLength);
        IObservableValue lengthModelObservable = BeanProperties.value("length").observeDetail(value);


        /* just the validators and decorators in the name field */
        IValidator nameValidator = new IValidator() {
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
        UpdateValueStrategy nameUpdateStrategy = new UpdateValueStrategy();
        nameUpdateStrategy.setAfterConvertValidator(nameValidator);
        Binding nameBinding = ctx.bindValue(nameTargetObservable, nameModelObservable, nameUpdateStrategy, null);

        Binding dataTypeBinding = ctx.bindValue(dataTypeTargetObservable, dataTypeModelObservable);
        Binding allowNullBinding = ctx.bindValue(allowNullTargetObservable, allowNullModelObservable);

        Binding lengthBinding = ctx.bindValue(lengthTargetObservable, lengthModelObservable, 
        		UpdateValueStrategy.create(viewHelper.convertStringToInteger), 
        		UpdateValueStrategy.create(viewHelper.convertIntegerToString));
        
        
        ControlDecorationSupport.create(nameBinding, SWT.TOP | SWT.LEFT);
        final IObservableValue errorObservable = WidgetProperties.text().observe(errorLabel);
        allValidationBinding = ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
        IObservableList bindings = ctx.getValidationStatusProviders();
	}
	
	@Override
	public void onAdd() {
		this.txtName.setFocus();
	}
	
	private class ViewerComparator extends ListSorterHelper
	{

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 == null || e2 == null)
			{
				return 0;
			}
			Attribute p1 = (Attribute)e1;
			Attribute p2 = (Attribute)e2;
			int rc = 0;
			switch(this.propertyIndex)
			{
			case 0:
				rc = this.compareName(p1, p2);
				break;
			default:
				rc = 0;
			}
			
			if (this.direction == DESCENDING)
			{
				rc = -rc;
			}
			return rc;
		}
		
		private int compareName(Attribute p1, Attribute p2)
		{
			if (p1 == null || p2 == null)
			{
				return 0;
			}
			String nameOne = "";
			String nameTwo = "";
			nameOne = (p1.getName() == null) ? "" : p1.getName();
			nameTwo = (p2.getName() == null) ? "" : p2.getName();
			return nameOne.compareTo(nameTwo);
		}
		
		
	}
	
	/*****************************
	 * 
	 * was a working example of combobox cell editor

	public AttributeView(Composite parent, int style)
	{
		TableViewerColumn nameColumn = new TableViewerColumn(mainGrid, SWT.NONE);
		nameColumn.getColumn().setWidth(100);
		nameColumn.getColumn().setText("Name");
		nameColumn.getColumn().setResizable(true);
		nameColumn.getColumn().setMoveable(true);
		nameColumn.setLabelProvider(new ColumnLabelProvider()
			{
				 @Override
				 public String getText(Object element)
				 {
					 Attribute attribute = (Attribute)element;
					 return attribute.getName();
				 }
			});
		nameColumn.setEditingSupport(new NameEditor(mainGrid));

		TableViewerColumn dataTypeColumn = new TableViewerColumn(mainGrid, SWT.NONE);
		dataTypeColumn.getColumn().setWidth(200);
		dataTypeColumn.getColumn().setText("Data Type");
		dataTypeColumn.getColumn().setResizable(true);
		dataTypeColumn.getColumn().setMoveable(true);
		dataTypeColumn.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element)
			{
				Attribute attribute = (Attribute)element;
				return attribute.getDataType();

			}
		});
		
		dataTypeColumn.setEditingSupport(new ComboBoxCellViewerEditorExample(mainGrid, dataTypeLookup));
		mainGrid.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = mainGrid.getStructuredSelection();
				Attribute selected = (Attribute)selection.getFirstElement();

				
			}
		});
		
		mainGrid.setInput(this.model.getAttributes());
		
	}
	
	******************************************************/

}
