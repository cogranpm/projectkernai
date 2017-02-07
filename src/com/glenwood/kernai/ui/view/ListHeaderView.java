/*
 * list on left for the the list headers
 * on the right static area for editing and below that 
 * a list for the list details
 * list details table uses a modal dialog for editing
 * 
 * 
 */
package com.glenwood.kernai.ui.view;

import java.util.List;

import org.eclipse.core.databinding.AggregateValidationStatus;
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
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.ui.abstraction.BaseEntityView;
import com.glenwood.kernai.ui.presenter.ListHeaderViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ListHeaderViewModel;


public class ListHeaderView extends BaseEntityView<ListHeader> {

	private Label lblName;
	private Text txtName;
	private ListDetailMasterDetailView listDetailView;
	
	public ListHeaderView(Composite parent, int style) {
		super(parent, style);
	}
	
	@Override
	protected void setupModelAndPresenter()
	{
		this.model = new ListHeaderViewModel();
		this.presenter = new ListHeaderViewPresenter(this, (ListHeaderViewModel) this.model);

	}
	
	@Override
	protected ListHeader listSelectionChangedHandler(SelectionChangedEvent event)
	{
		ListHeader item = super.listSelectionChangedHandler(event);
		if(item != null)
		{
			refreshListDetailView(item);
		}
		return item;
	}

	@Override
	protected void setupListColumns()
	{
		TableViewerColumn nameColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		nameColumn.getColumn().setText("Name");
		nameColumn.getColumn().setResizable(false);
		nameColumn.getColumn().setMoveable(false);
		
		nameColumn.setEditingSupport(new EditingSupport(this.listViewer) {
			
			@Override
			protected void setValue(Object element, Object value) {
				((ListHeader)element).setName(String.valueOf(value));
				listViewer.update(element, null);
			}
			
			@Override
			protected Object getValue(Object element) {
				return ((ListHeader)element).getName();
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
		
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
	}
	
	@Override
	protected void setupEditingContainer()
	{
		super.setupEditingContainer();
		lblName = new Label(editMaster, SWT.NONE);
		lblName.setText("Name");
		txtName = viewHelper.getTextEditor(editMaster);
		viewHelper.layoutEditLabel(lblName);
		viewHelper.layoutEditEditor(txtName);

		Label lblListDetailCaption = new Label(editMaster, SWT.NONE);
		lblListDetailCaption.setText("List Items");
		viewHelper.layoutMasterDetailCaption(lblListDetailCaption);
		
	}
	
	@Override
	protected void initDataBindings() {
		super.initDataBindings();
		
        ObservableListContentProvider contentProvider = new ObservableListContentProvider();
        listViewer.setContentProvider(contentProvider);
        
        IObservableSet<ListHeader> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(ListHeader.class, "name").observeDetail(knownElements);
        IObservableMap[] labelMaps = {names};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	ListHeader mc = (ListHeader)element;
                	return mc.getName();
                }
        };
        listViewer.setLabelProvider(labelProvider);
        List<ListHeader> el = model.getItems();
        input = new WritableList(el, ListHeader.class);
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
        editBinding = ctx.bindValue(nameTargetObservable, nameModelObservable, nameUpdateStrategy, null);
        
        ControlDecorationSupport.create(editBinding, SWT.TOP | SWT.LEFT);
        final IObservableValue errorObservable = WidgetProperties.text().observe(errorLabel);
        allValidationBinding = ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
        IObservableList bindings = ctx.getValidationStatusProviders();
        editBinding.getTarget().addChangeListener(stateListener);

	}
	
	public void refreshListDetailView(ListHeader listHeader)
	{
		if(listDetailView == null)
		{
			listDetailView = new ListDetailMasterDetailView(editDetail, SWT.NONE, listHeader);
			editDetail.layout();
		}
		else
		{
			listDetailView.getPresenter().loadItems(listHeader);
			listDetailView.setVisible(true);
		}
	}


	@Override
	public void add() {
		this.txtName.setFocus();
		super.add();
		//input.add(this.model.getCurrentItem());
		if (this.listDetailView != null)
		{
			this.listDetailView.setVisible(false);
		}
		
	}

}
