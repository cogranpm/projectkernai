package com.glenwood.kernai.ui.view;

import java.util.List;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
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
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.Association;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.presenter.AssociationViewPresenter;
import com.glenwood.kernai.ui.view.helpers.ListSorterHelper;
import com.glenwood.kernai.ui.viewmodel.AssociationViewModel;

public class AssociationView extends BaseEntityMasterDetailListEditView<Association, Model> {

	Label lblName;
	Text txtName;
	
	Label lblAssociationType;
	ComboViewer cboAssociationType;
	
	Label lblOwnerEntity;
	ComboViewer cboOwnerEntity;
	
	Label lblOwnedEntity;
	ComboViewer cboOwnedEntity;
	
	
	
	public AssociationView(Composite parent, int style, Model parentEntity) {
		super(parent, style, parentEntity);
		
	}
	
	@Override
	protected void setupModelAndPresenter(Model parentEntity) {
		this.model = new AssociationViewModel(parentEntity);
		this.presenter = new AssociationViewPresenter(this, (AssociationViewModel)model);
	}
	
	@Override
	protected void setupListColumns() {
		super.setupListColumns();
		this.listViewer.setComparator(new ViewerComparator());
		TableViewerColumn nameColumn = this.viewHelper.getListColumn(listViewer, "Name");
		nameColumn.getColumn().addSelectionListener(this.getSelectionAdapter(nameColumn.getColumn(), 0));
		TableViewerColumn associationTypeColumn = this.viewHelper.getListColumn(listViewer, "Type");
		associationTypeColumn.getColumn().addSelectionListener(this.getSelectionAdapter(associationTypeColumn.getColumn(), 1));
		TableViewerColumn ownerEntityColumn = this.viewHelper.getListColumn(listViewer, "Owner Entity");
		ownerEntityColumn.getColumn().addSelectionListener(this.getSelectionAdapter(ownerEntityColumn.getColumn(), 2));
		TableViewerColumn ownedEntityColumn = this.viewHelper.getListColumn(listViewer, "Owned Entity");
		ownedEntityColumn.getColumn().addSelectionListener(this.getSelectionAdapter(ownedEntityColumn.getColumn(), 3));
        
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(associationTypeColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(ownerEntityColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(ownedEntityColumn.getColumn(), new ColumnWeightData(100));
		
	}
	
	@Override
	protected void onInitDataBindings() {
        IObservableSet<Association> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(Association.class, "name").observeDetail(knownElements);
        final IObservableMap associationTypes = BeanProperties.value(Association.class, "associationTypeLookup").observeDetail(knownElements);
        final IObservableMap ownerEntitys = BeanProperties.value(Association.class, "ownerEntity").observeDetail(knownElements);
        final IObservableMap ownedEntitys = BeanProperties.value(Association.class, "ownedEntity").observeDetail(knownElements);
        IObservableMap[] labelMaps = {names, associationTypes, ownerEntitys, ownedEntitys};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	Association mc = (Association)element;
                	switch (columnIndex)
                	{
                		case 0:
                			return mc.getName();
                		case 1:
                			if(mc.getAssociationTypeLookup() != null)
                			{
                				return mc.getAssociationTypeLookup().getLabel();
                			}
                			else
                			{
                				return "";
                			}
                		case 2:
                			if(mc.getOwnerEntity() != null)
                			{
                				return mc.getOwnerEntity().getName();
                			}
                			else
                			{
                				return "";
                			}
                		case 3:
                			if(mc.getOwnedEntity() != null)
                			{
                				return mc.getOwnedEntity().getName();
                			}
                			else
                			{
                				return "";
                			}
                		default:
                			return "";
                	}
                }
        };
        listViewer.setLabelProvider(labelProvider);
        List<Association> el = model.getItems();
        input = new WritableList(el, Association.class);
        listViewer.setInput(input);
        
        /* binding for the edit screen on name field */
        IObservableValue nameTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtName);
        IObservableValue nameModelObservable = BeanProperties.value("name").observeDetail(value);

        IObservableValue ownerTargetObservable = ViewerProperties.singleSelection().observe(cboOwnerEntity);
        IObservableValue ownerModelObservable = BeanProperties.value("ownerEntity").observeDetail(value);

        IObservableValue ownedTargetObservable = ViewerProperties.singleSelection().observe(cboOwnedEntity);
        IObservableValue ownedModelObservable = BeanProperties.value("ownedEntity").observeDetail(value);

        IObservableValue associationTypeTargetObservable = ViewerProperties.singleSelection().observe(cboAssociationType);
        IObservableValue associationTypeModelObservable = BeanProperties.value("associationTypeLookup").observeDetail(value);
        
       
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
        Binding ownerEntityBinding = ctx.bindValue(ownerTargetObservable, ownerModelObservable, null, null);
        Binding ownedEntityBinding = ctx.bindValue(ownedTargetObservable, ownedModelObservable, null, null);
        Binding associationTypeBinding = ctx.bindValue(associationTypeTargetObservable, associationTypeModelObservable, null, null);
        
        ControlDecorationSupport.create(nameBinding, SWT.TOP | SWT.LEFT);
        final IObservableValue errorObservable = WidgetProperties.text().observe(errorLabel);
        allValidationBinding = ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
       // IObservableList bindings = ctx.getValidationStatusProviders();
	}
	
	@Override
	protected void onSetupEditingContainer() {
		AssociationViewModel aModel = (AssociationViewModel)this.model;
		lblName = this.viewHelper.getEditLabel(editMaster, "Name");
		txtName = this.viewHelper.getTextEditor(editMaster);

		lblOwnerEntity = this.viewHelper.getEditLabel(editMaster, "Owner Entity");
		cboOwnerEntity = this.viewHelper.getComboViewer(editMaster);
		cboOwnerEntity.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element)
			{
				Entity item= (Entity)element;
				return item.getName();
			}
		});
		cboOwnerEntity.setInput(aModel.getEntityLookup());
		

		lblOwnedEntity = this.viewHelper.getEditLabel(editMaster, "Owned Entity");
		cboOwnedEntity = this.viewHelper.getComboViewer(editMaster);
		cboOwnedEntity.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element)
			{
				Entity item= (Entity)element;
				return item.getName();
			}
		});
		cboOwnedEntity.setInput(aModel.getEntityLookup());

		
		lblAssociationType = this.viewHelper.getEditLabel(editMaster, "Type");
		cboAssociationType = this.viewHelper.getComboViewer(editMaster);
		cboAssociationType.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element)
			{
				ListDetail item= (ListDetail)element;
				return item.getLabel();
			}
		});
		cboAssociationType.setInput(aModel.getAssociationTypeLoookup());
		

		
		this.viewHelper.layoutEditLabel(lblName);
		this.viewHelper.layoutEditEditor(txtName);
		this.viewHelper.layoutEditLabel(lblOwnerEntity);
		this.viewHelper.layoutComboViewer(cboOwnerEntity);
		this.viewHelper.layoutEditLabel(lblOwnedEntity);
		this.viewHelper.layoutComboViewer(cboOwnedEntity);
		this.viewHelper.layoutEditLabel(lblAssociationType);
		this.viewHelper.layoutComboViewer(cboAssociationType);

	}
	
	@Override
	protected void onAdd() {
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
			Association p1 = (Association)e1;
			Association p2 = (Association)e2;
			int rc = 0;
			switch(this.propertyIndex)
			{
			case 0:
				rc = this.compareName(p1, p2);
				break;
			case 1:
				rc = this.compareAssociationType(p1, p2);
				break;
			case 2:
				rc = this.compareOwnerEntity(p1, p2);
				break;
			case 3:
				rc = this.compareOwnedEntity(p1, p2);
			default:
				rc = 0;
			}
			
			if (this.direction == DESCENDING)
			{
				rc = -rc;
			}
			return rc;
		}
		
		private int compareName(Association p1, Association p2)
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

		private int compareAssociationType(Association p1, Association p2)
		{
			if (p1 == null || p2 == null)
			{
				return 0;
			}
			String keyOne = "";
			String keyTwo = "";
			keyOne = (p1.getAssociationTypeLookup() == null) ? "" : p1.getAssociationTypeLookup().getKey();
			keyTwo = (p2.getAssociationTypeLookup() == null) ? "" : p2.getAssociationTypeLookup().getKey();
			return keyOne.compareTo(keyTwo);
		}

		private int compareOwnerEntity(Association p1, Association p2)
		{
			return this.compareEntity(p1.getOwnerEntity(), p2.getOwnerEntity());
		}
		
		private int compareOwnedEntity(Association p1, Association p2)
		{
			return this.compareEntity(p1.getOwnedEntity(), p2.getOwnedEntity());
		}
		
		private int compareEntity(Entity p1, Entity p2)
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


}
