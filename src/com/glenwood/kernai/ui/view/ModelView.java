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
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.presenter.ModelViewPresenter;
import com.glenwood.kernai.ui.view.helpers.ListSorterHelper;
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
        //IObservableSet<Model> knownElements = contentProvider.getKnownElements();
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
        this.setupCurrentEntityBinding();
        this.setupToolbarBinding();
	}
	
	private void setupCurrentEntityBinding()
	{
		this.listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = listViewer.getStructuredSelection();
				if(selection == null)
				{
					ApplicationData.instance().setCurrentModel(null);
				}
				else
				{
					Model model = (Model)selection.getFirstElement();
					ApplicationData.instance().setCurrentModel(model);
				}
			}
		});
	}
	
	
	private void setupToolbarBinding()
	{
        /* set the enabled of the toolbar items */
        ToolItem entityToolItem = ApplicationData.instance().getToolItem(ApplicationData.instance().getToolBarManager(ApplicationData.TOOLBAR_MANAGER_PROJECT),
        		ApplicationData.GOTO_PROJECT_ENTITY);
        
        ToolItem associationToolItem = ApplicationData.instance().getToolItem(ApplicationData.instance().getToolBarManager(ApplicationData.TOOLBAR_MANAGER_PROJECT),
        		ApplicationData.GOTO_PROJECT_ASSOCIATION);
        IObservableValue listViewerSelection= ViewersObservables.observeSingleSelection(listViewer);
        IObservableValue<ToolItem> entityItemTarget = WidgetProperties.enabled().observe(entityToolItem);
        IObservableValue<ToolItem> associationItemTarget = WidgetProperties.enabled().observe(associationToolItem);
        UpdateValueStrategy convertSelectedToBoolean = new UpdateValueStrategy(){
        	@Override
        	protected IStatus doSet(IObservableValue observableValue, Object value) 
        	{
        		return super.doSet(observableValue, value == null ? Boolean.FALSE : Boolean.TRUE);
        	};
        };
		
        Binding binding = ctx.bindValue(entityItemTarget, listViewerSelection,  new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), convertSelectedToBoolean);
        Binding associationBinding = ctx.bindValue(associationItemTarget, listViewerSelection,  new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), convertSelectedToBoolean);
        //a listener on above binding that makes sure action enabled is set set toolitem changes, ie can't databind the enbabled of an action
        binding.getTarget().addChangeListener(new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				IAction gotoEntityAction = ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_ENTITY);
				gotoEntityAction.setEnabled(entityToolItem.getEnabled());

			}
		});
        
        associationBinding.getTarget().addChangeListener(new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				IAction gotoAssociationAction = ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_ASSOCIATION);
				gotoAssociationAction.setEnabled(associationToolItem.getEnabled());
			}
		});
	}
	
	@Override
	protected void onSetupEditingContainer() {
		this.lblEditHeader.setText(String.format("Project: %s", ApplicationData.instance().getCurrentProject().getName()));
		lblName = new Label(editMaster, SWT.NONE);
		lblName.setText("Name");
		txtName = viewHelper.getTextEditor(editMaster);
		viewHelper.layoutEditLabel(lblName);
		viewHelper.layoutEditEditor(txtName);
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
			Model p1 = (Model)e1;
			Model p2 = (Model)e2;
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
		
		private int compareName(Model p1, Model p2)
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
