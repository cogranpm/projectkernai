package com.glenwood.kernai.ui.view;

import java.util.List;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.IConverter;
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
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.entity.ListDetail;
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
		this.listViewer.setComparator(new ViewerComparator());
		TableViewerColumn nameColumn = this.viewHelper.getListColumn(listViewer, "Name");
		nameColumn.getColumn().addSelectionListener(this.getSelectionAdapter(nameColumn.getColumn(), 0));
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
	}
	
	@Override
	protected void setupEditingContainer() {
		super.setupEditingContainer();
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
	protected void initDataBindings() {
		super.initDataBindings();
        IObservableSet<Attribute> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(Attribute.class, "name").observeDetail(knownElements);
        IObservableMap[] labelMaps = {names};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	Attribute mc = (Attribute)element;
                	return mc.getName();
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
        IObservableValue dataTypeModelObservable = BeanProperties.value("dataType").observeDetail(value);
        
        IObservableValue allowNullTargetObservable = WidgetProperties.selection().observe(btnAllowNull);
        IObservableValue allowNullModelObservable = BeanProperties.value("allowNull").observeDetail(value);

        IObservableValue lengthTargetObservable = WidgetProperties.text().observe(txtLength);
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

//        Binding dataTypeBinding = ctx.bindValue(dataTypeTargetObservable, dataTypeModelObservable);
        Binding allowNullBinding = ctx.bindValue(allowNullTargetObservable, allowNullModelObservable);
        

        IConverter convertStringToInteger = IConverter.create(Object.class, Integer.class, (val) ->  (val == null || val == "") ? null : Integer.parseInt(val.toString()) );
       // IConverter convertStringToInteger = IConverter.create(Object.class, Integer.class, (val) ->  (val == null ) ? null : (Integer)val);
        IConverter convertIntegerToString = IConverter.create(Integer.class, String.class, (val) -> val == null ? null : val.toString());
        Binding lengthBinding = ctx.bindValue(lengthTargetObservable, lengthModelObservable, UpdateValueStrategy.create(convertStringToInteger), UpdateValueStrategy.create(convertIntegerToString));
        
        
        ControlDecorationSupport.create(nameBinding, SWT.TOP | SWT.LEFT);
        final IObservableValue errorObservable = WidgetProperties.text().observe(errorLabel);
        allValidationBinding = ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
        IObservableList bindings = ctx.getValidationStatusProviders();
	}
	
	@Override
	public void add() {
		super.add();
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
