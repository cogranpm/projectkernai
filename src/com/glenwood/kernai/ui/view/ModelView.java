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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.presenter.ModelViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ModelViewModel;

public class ModelView extends BaseEntityMasterDetailListEditView<Model, Project>{

	
	private Label lblName;
	private Text txtName;
	
	public ModelView(Composite parent, int style, Project parentEntity) {
		super(parent, style, parentEntity);

	}
	
	@Override
	protected void setupModelAndPresenter(Project parentEntity) {
		super.setupModelAndPresenter(parentEntity);
		this.model = new ModelViewModel(parentEntity);
		this.presenter = new ModelViewPresenter(this, (ModelViewModel)this.model);
		System.out.println(parentEntity.getId());
	}
	
	@Override
	protected void setupListColumns() {
		super.setupListColumns();
		
		TableViewerColumn nameColumn = this.viewHelper.getListColumn(listViewer, "Name");
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));

	}
	
	@Override
	protected void initDataBindings() {
		super.initDataBindings();
        IObservableSet<Model> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(Model.class, "name").observeDetail(knownElements);
        IObservableMap[] labelMaps = {names};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	Model mc = (Model)element;
                	return mc.getName();
                }
        };
        listViewer.setLabelProvider(labelProvider);
        List<Model> el = model.getItems();
        input = new WritableList(el, Model.class);
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
	protected void setupEditingContainer() {
		super.setupEditingContainer();
		lblName = new Label(editMaster, SWT.NONE);
		lblName.setText("Name");
		txtName = viewHelper.getTextEditor(editMaster);
		viewHelper.layoutEditLabel(lblName);
		viewHelper.layoutEditEditor(txtName);
	}
	
	@Override
	public void add() {
		super.add();
		this.txtName.setFocus();
	}
	

}
