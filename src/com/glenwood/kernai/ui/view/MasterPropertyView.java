package com.glenwood.kernai.ui.view;

import java.util.List;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
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
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.MasterProperty;
import com.glenwood.kernai.data.entity.PropertyGroup;
import com.glenwood.kernai.data.entity.PropertyType;
import com.glenwood.kernai.data.entity.helper.CheckedNamedItemDataObject;
import com.glenwood.kernai.ui.abstraction.BaseEntityView;
import com.glenwood.kernai.ui.presenter.MasterPropertyViewPresenter;
import com.glenwood.kernai.ui.viewmodel.MasterPropertyViewModel;

public final class MasterPropertyView extends BaseEntityView<MasterProperty> {
	
	private Label lblName;
	private Text txtName;
	private Label lblLabel;
	private Text txtLabel;
	private Label lblDefaultValue;
	private Text txtDefaultValue;
	private Label lblNotes;
	private Text txtNotes;
	
	private Label lblPropertyType;
	private ComboViewer cboPropertyType;
	private Label lblPropertyGroup;
	private ComboViewer cboPropertyGroup;
	
	private CheckboxTableViewer masterCategoryViewer;
	private Table masterCategoryTable;
	
	//IObservableList<PropertyGroup> propertyGroupList;
	
	private MasterPropertyListItemMasterDetailView listItemView;
	private Label lblListItemCaption;

	public MasterPropertyView(Composite parent, int style) {
		super(parent, style);

	}
	
	@Override
	protected void setupModelAndPresenter() {
		this.model = new MasterPropertyViewModel();
		this.presenter = new MasterPropertyViewPresenter(this, this.model);
	}
	
	@Override
	protected void setupListColumns() {
		super.setupListColumns();
		
		TableViewerColumn nameColumn = viewHelper.getListColumn(listViewer, "Name");
		TableViewerColumn labelColumn = viewHelper.getListColumn(listViewer, "Label");
		TableViewerColumn defaultValueColumn = viewHelper.getListColumn(listViewer, "Default");
		
		
//		nameColumn.getColumn().addSelectionListener(this.getSelectionAdapter(nameColumn.getColumn(), 0));
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(labelColumn.getColumn(),  new ColumnWeightData(100));
		tableLayout.setColumnData(defaultValueColumn.getColumn(),  new ColumnWeightData(100));
	}
	
	@Override
	protected void onSetupEditingContainer() {
		lblName = new Label(editMaster, SWT.NONE);
		lblName.setText("Name");
		txtName = viewHelper.getTextEditor(editMaster);
		
		lblLabel = new Label(editMaster, SWT.NONE);
		lblLabel.setText("Label");
		txtLabel = viewHelper.getTextEditor(editMaster);
		
		lblDefaultValue = new Label(editMaster, SWT.NONE);
		lblDefaultValue.setText("Default Value");
		txtDefaultValue = viewHelper.getTextEditor(editMaster);
		
		lblNotes = new Label(editMaster, SWT.NONE);
		lblNotes.setText("Notes");
		txtNotes = viewHelper.getMultiLineTextEditor(editMaster);
		
		viewHelper.layoutEditLabel(lblName);
		viewHelper.layoutEditEditor(txtName);
		viewHelper.layoutEditLabel(lblLabel);
		viewHelper.layoutEditEditor(txtLabel);
		viewHelper.layoutEditLabel(lblDefaultValue);
		viewHelper.layoutEditEditor(txtDefaultValue);
		viewHelper.layoutEditLabel(lblNotes);
		viewHelper.layoutMultiLineText(txtNotes);
		
		MasterPropertyViewModel daModel = (MasterPropertyViewModel)this.model;
		lblPropertyGroup = new Label(editMaster, SWT.NONE);
		lblPropertyGroup.setText("Group");
		cboPropertyGroup = new ComboViewer(editMaster);

		cboPropertyGroup.setContentProvider(ArrayContentProvider.getInstance());
		/*
		propertyGroupList = BeansObservables.observeList(daModel, "propertyGroupLookup", PropertyGroup.class);
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		cboPropertyGroup.setContentProvider(contentProvider);
		*/
		cboPropertyGroup.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element)
			{
				PropertyGroup propertyGroup = (PropertyGroup)element;
				return propertyGroup.getName();
			}
		});
		

		//cboPropertyGroup.setInput(propertyGroupList);
		cboPropertyGroup.setInput(daModel.getPropertyGroupLookup());
		
		lblPropertyType = new Label(editMaster, SWT.NONE);
		lblPropertyType.setText("Type");
		cboPropertyType = new ComboViewer(editMaster);
		cboPropertyType.setContentProvider(ArrayContentProvider.getInstance());
		cboPropertyType.setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element)
			{
				PropertyType propertyType = (PropertyType)element;
				return propertyType.getName();
			}
		});
		cboPropertyType.setInput(daModel.getPropertyTypeLookup());
		
		viewHelper.layoutEditLabel(lblPropertyGroup);
		viewHelper.layoutComboViewer(cboPropertyGroup);
		viewHelper.layoutEditLabel(lblPropertyType);
		viewHelper.layoutComboViewer(cboPropertyType);
		
		Label lblMasterCategory = new Label(editMaster, SWT.NONE);
		lblMasterCategory.setText("Categories");
		viewHelper.layoutEditLabel(lblMasterCategory);
//		this.masterCategoryTable = new Table(editMaster, SWT.BORDER);
		this.masterCategoryViewer = CheckboxTableViewer.newCheckList(editMaster, SWT.BORDER);
		this.masterCategoryTable = this.masterCategoryViewer.getTable();
		this.masterCategoryTable.setHeaderVisible(false);
		this.masterCategoryTable.setLinesVisible(false);
		this.masterCategoryViewer.setContentProvider(ArrayContentProvider.getInstance());
		this.masterCategoryViewer.setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element)
			{
				CheckedNamedItemDataObject item = (CheckedNamedItemDataObject)element;
				return item.getLabel();
			}
		});
		
		
		this.masterCategoryViewer.setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(Object element) {
				 return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				if (element == null)
				{
					return false;
				}
				else
				{
					CheckedNamedItemDataObject item = (CheckedNamedItemDataObject)element;
					return item.getAssigned();
				}
			}
		});
		
		this.masterCategoryViewer.addCheckStateListener(new ICheckStateListener() {
			
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Object checkedElement = event.getElement();
				if(checkedElement != null)
				{
					CheckedNamedItemDataObject item = (CheckedNamedItemDataObject)checkedElement;
					item.setAssigned(event.getChecked());
					model.setDirty(true);
				}
				
			}
		});
		GridDataFactory.fillDefaults().grab(true, true).span(1, 1).align(SWT.FILL, SWT.FILL).applyTo(this.masterCategoryTable);
		
		lblListItemCaption = new Label(editMaster, SWT.NONE);
		lblListItemCaption.setText("List Items");
		viewHelper.layoutMasterDetailCaption(lblListItemCaption);
		
	}
	
	@Override
	protected void onInitDataBindings() {
		
		IObservableSet<MasterProperty> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(MasterProperty.class, "name").observeDetail(knownElements);
        final IObservableMap notes = BeanProperties.value(MasterProperty.class, "notes").observeDetail(knownElements);
        IObservableMap[] labelMaps = {names, notes};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
            		MasterProperty entity = (MasterProperty)element;
                	switch (columnIndex)
                	{
                	case 0:
                		return entity.getName();
                	case 1:
                		return entity.getLabel();
                	case 2:
                		return entity.getDefaultValue();
                	default:
                		return null;
                	}
                	
                }
        };
        listViewer.setLabelProvider(labelProvider);
        List<MasterProperty> el = model.getItems();
        input = new WritableList(el, MasterProperty.class);
        listViewer.setInput(input);
        
        /* binding for the edit screen on name field */
        IObservableValue nameTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtName);
        IObservableValue nameModelObservable = BeanProperties.value("name").observeDetail(value);

        IObservableValue notesTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtNotes);
        IObservableValue notesModelObservable = BeanProperties.value("notes").observeDetail(value);
        
        IObservableValue defaultValueTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtDefaultValue);
        IObservableValue defaultValueModelObservable = BeanProperties.value("defaultValue").observeDetail(value);
        
        
        IObservableValue labelTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtLabel);
        IObservableValue labelModelObservable = BeanProperties.value("label").observeDetail(value);
		
        IObservableValue propertyGroupTargetObservable = ViewerProperties.singleSelection().observe(cboPropertyGroup);
        IObservableValue propertyGroupModelObservable = BeanProperties.value("propertyGroup").observeDetail(value);
        
        IObservableValue propertyTypeTargetObservable = ViewerProperties.singleSelection().observe(cboPropertyType);
        IObservableValue propertyTypeModelObservable = BeanProperties.value("propertyType").observeDetail(value);
       
        /* just the validators and decorators in the name field */
        IValidator nameValidator = new IValidator() {
            @Override
            public IStatus validate(Object value) {
                String nameValue = String.valueOf(value).replaceAll("\\s", "");
                if (nameValue.length() > 0){
                  return ValidationStatus.ok();
                }
                return ValidationStatus.error("Name must be entered");
            }
            
          };
        UpdateValueStrategy nameUpdateStrategy = new UpdateValueStrategy();
        nameUpdateStrategy.setAfterConvertValidator(nameValidator);
        Binding nameBinding = ctx.bindValue(nameTargetObservable, nameModelObservable, nameUpdateStrategy, null);
        Binding labelBinding = ctx.bindValue(labelTargetObservable, labelModelObservable);
        Binding notesBinding = ctx.bindValue(notesTargetObservable, notesModelObservable);
        Binding defaultValueBinding = ctx.bindValue(defaultValueTargetObservable, defaultValueModelObservable);
        
        Binding propertyGroupBinding = ctx.bindValue(propertyGroupTargetObservable, propertyGroupModelObservable);
        Binding propertyTypeBinding = ctx.bindValue(propertyTypeTargetObservable, propertyTypeModelObservable);
        
        ControlDecorationSupport.create(nameBinding, SWT.TOP | SWT.LEFT);
        final IObservableValue errorObservable = WidgetProperties.text().observe(errorLabel);
        allValidationBinding = ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
        IObservableList bindings = ctx.getValidationStatusProviders();
        
        propertyTypeBinding.getTarget().addChangeListener(new IChangeListener() {
			
			@Override
			public void handleChange(ChangeEvent event) {
				propertyTypeChanged();
				
			}
		});

	}
	
	@Override
	protected void onListSelectionChangedHandler(MasterProperty item)
	{
		if(item != null)
		{
			refreshListItemView(item);
		}
	}
	
	@Override
	public void onAdd() {
		this.txtName.setFocus();
		this.masterCategoryViewer.setInput(this.model.getCurrentItem().getMasterCategories());
	}
	
	@Override
	public void onAfterSelection() {
		this.masterCategoryViewer.setInput(this.model.getCurrentItem().getMasterCategories());
	}
	
	private void propertyTypeChanged()
	{
		IStructuredSelection selection =  this.cboPropertyType.getStructuredSelection();
		if (selection == null){return;}
		PropertyType propertyType = (PropertyType)selection.getFirstElement();
		if(propertyType == null){return;}
		if(propertyType.getName().equalsIgnoreCase((PropertyType.LIST_ITEM_NAME)))
		{
			this.setEnabledListItems(true);
		}
		else
		{
			this.setEnabledListItems(false);
		}
	}
	
	private void setEnabledListItems(Boolean enable)
	{
		this.lblListItemCaption.setEnabled(enable);
		if(this.listItemView != null)
		{
			this.listItemView.setToolbarEnabled(enable);
			this.listItemView.setEnabled(enable);
			for(Control control : this.listItemView.getChildren())
			{
				control.setEnabled(enable);
			}
		}
	}
	
	public void refreshListItemView(MasterProperty masterProperty)
	{
		if(listItemView == null)
		{
			listItemView = new MasterPropertyListItemMasterDetailView(editDetail, SWT.NONE, masterProperty);
			editDetail.layout();
		}
		else
		{
			listItemView.getPresenter().loadItems(masterProperty);
			listItemView.setVisible(true);
		}
	}

}
