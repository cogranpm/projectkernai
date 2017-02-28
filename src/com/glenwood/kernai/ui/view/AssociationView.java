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
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.Association;
import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.presenter.AssociationViewPresenter;
import com.glenwood.kernai.ui.view.helpers.ListSorterHelper;
import com.glenwood.kernai.ui.viewmodel.AssociationViewModel;

public class AssociationView extends BaseEntityMasterDetailListEditView<Association, Model> {

	Label lblName;
	Text txtName;
	
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
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));		
	}
	
	@Override
	protected void onInitDataBindings() {
        IObservableSet<Association> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(Association.class, "name").observeDetail(knownElements);
        IObservableMap[] labelMaps = {names};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	Association mc = (Association)element;
                	return mc.getName();
                }
        };
        listViewer.setLabelProvider(labelProvider);
        List<Association> el = model.getItems();
        input = new WritableList(el, Association.class);
        listViewer.setInput(input);
        
        /* binding for the edit screen on name field */
        IObservableValue nameTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtName);
        IObservableValue nameModelObservable = BeanProperties.value("name").observeDetail(value);
       
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
        
        ControlDecorationSupport.create(nameBinding, SWT.TOP | SWT.LEFT);
        final IObservableValue errorObservable = WidgetProperties.text().observe(errorLabel);
        allValidationBinding = ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
        IObservableList bindings = ctx.getValidationStatusProviders();
	}
	
	@Override
	protected void onSetupEditingContainer() {
		
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
		
		
	}


}
