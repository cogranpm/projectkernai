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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityView;
import com.glenwood.kernai.ui.presenter.ProjectViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ProjectViewModel;

public class ProjectView extends BaseEntityView<Project>{
	
	private Label lblName;
	private Text txtName;
	
	public ProjectView(Composite parent, int style) {
		super(parent, style);
	}

	
	@Override
	protected void setupModelAndPresenter() {
		super.setupModelAndPresenter();
		this.model = new ProjectViewModel();
		this.presenter = new ProjectViewPresenter(this, this.model);
	}

	@Override
	protected void setupListColumns() {
		super.setupListColumns();
		TableViewerColumn nameColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		nameColumn.getColumn().setText("Name");
		nameColumn.getColumn().setResizable(false);
		nameColumn.getColumn().setMoveable(false);
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
	}

	
	@Override
	protected void onSetupEditingContainer() {
		lblName = new Label(editMaster, SWT.NONE);
		lblName.setText("Name");
		txtName = viewHelper.getTextEditor(editMaster);
		viewHelper.layoutEditLabel(lblName);
		viewHelper.layoutEditEditor(txtName);
	}

	@Override
	protected void onInitDataBindings() {
        IObservableSet<Project> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(Project.class, "name").observeDetail(knownElements);
        IObservableMap[] labelMaps = {names};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	Project mc = (Project)element;
                	return mc.getName();
                }
        };
        listViewer.setLabelProvider(labelProvider);
        List<Project> el = model.getItems();
        input = new WritableList(el, Project.class);
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
        this.setupToolbarBinding();
        this.setupCurrentProjectBinding();

	}

	@Override
	public void onAdd() {
		this.txtName.setFocus();
	}
	
	private void setupCurrentProjectBinding()
	{
		this.listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = listViewer.getStructuredSelection();
				if(selection == null)
				{
					ApplicationData.instance().setCurrentProject(null);
				}
				else
				{
					Project project = (Project)selection.getFirstElement();
					ApplicationData.instance().setCurrentProject(project);
				}
			}
		});
	}
	
	
	private void setupToolbarBinding()
	{
        /* set the enabled of the toolbar items */
        ToolItem modelToolItem = ApplicationData.instance().getToolItem(ApplicationData.instance().getToolBarManager(ApplicationData.TOOLBAR_MANAGER_PROJECT),
        		ApplicationData.GOTO_PROJECT_MODEL);
        IObservableValue listViewerSelectionForDelete = ViewersObservables.observeSingleSelection(listViewer);
        IObservableValue<ToolItem> modelItemTarget = WidgetProperties.enabled().observe(modelToolItem);
        UpdateValueStrategy convertSelectedToBoolean = new UpdateValueStrategy(){
        	@Override
        	protected IStatus doSet(IObservableValue observableValue, Object value) 
        	{
        		return super.doSet(observableValue, value == null ? Boolean.FALSE : Boolean.TRUE);
        	};
        };
		
        //a binding that sets delete toolitem to disabled based on whether item in list is selected
        Binding deleteBinding = ctx.bindValue(modelItemTarget, listViewerSelectionForDelete,  new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), convertSelectedToBoolean);
        //a listener on above binding that makes sure action enabled is set set toolitem changes, ie can't databind the enbabled of an action
        deleteBinding.getTarget().addChangeListener(new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				IAction gotoModelAction = ApplicationData.instance().getAction(ApplicationData.GOTO_PROJECT_MODEL);
				gotoModelAction.setEnabled(modelToolItem.getEnabled());
			}
		});
	}
}
