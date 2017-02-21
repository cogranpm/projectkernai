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
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.PropertyType;
import com.glenwood.kernai.ui.abstraction.BaseEntityView;
import com.glenwood.kernai.ui.presenter.PropertyTypeViewPresenter;
import com.glenwood.kernai.ui.view.helpers.ListSorterHelper;
import com.glenwood.kernai.ui.viewmodel.PropertyTypeViewModel;

public class PropertyTypeView extends BaseEntityView<PropertyType> {

	private Label lblName;
	private Text txtName;
	private Label lblNotes;
	private Text txtNotes;
	
	public PropertyTypeView(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void setupModelAndPresenter() {
		super.setupModelAndPresenter();
		this.model = new PropertyTypeViewModel();
		this.presenter = new PropertyTypeViewPresenter(this, this.model);
	}
	
	@Override
	protected void initDataBindings() {
		super.initDataBindings();

        IObservableSet<PropertyType> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(PropertyType.class, "name").observeDetail(knownElements);
        final IObservableMap notes = BeanProperties.value(PropertyType.class, "notes").observeDetail(knownElements);
        IObservableMap[] labelMaps = {names, notes};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
            		PropertyType entity = (PropertyType)element;
                	switch (columnIndex)
                	{
                	case 0:
                		return entity.getName();
                	case 1:
                		return entity.getNotes();
                	default:
                		return null;
                	}
                	
                }
        };
        listViewer.setLabelProvider(labelProvider);
        List<PropertyType> el = model.getItems();
        input = new WritableList(el, PropertyType.class);
        listViewer.setInput(input);
        
        /* binding for the edit screen on name field */
        IObservableValue nameTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtName);
        IObservableValue nameModelObservable = BeanProperties.value("name").observeDetail(value);

        IObservableValue notesTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtNotes);
        IObservableValue notesModelObservable = BeanProperties.value("notes").observeDetail(value);

        
       
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
        Binding notesBinding = ctx.bindValue(notesTargetObservable, notesModelObservable);
        
        ControlDecorationSupport.create(nameBinding, SWT.TOP | SWT.LEFT);
        final IObservableValue errorObservable = WidgetProperties.text().observe(errorLabel);
        allValidationBinding = ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
        IObservableList bindings = ctx.getValidationStatusProviders();
	}

	@Override
	protected void setupListColumns() {
		super.setupListColumns();
		
		listViewer.setComparator(new ViewerComparator());
		
		TableViewerColumn nameColumn = viewHelper.getListColumn(listViewer, "Name");
		nameColumn.setEditingSupport(new EditingSupport(this.listViewer) {
			
			@Override
			protected void setValue(Object element, Object value) {
				((PropertyType)element).setName(String.valueOf(value));
				listViewer.update(element, null);
			}
			
			@Override
			protected Object getValue(Object element) {
				return ((PropertyType)element).getName();
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
		nameColumn.getColumn().addSelectionListener(this.getSelectionAdapter(nameColumn.getColumn(), 0));
		TableViewerColumn notesColumn  = viewHelper.getListColumn(listViewer, "Notes");
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(50));		
		tableLayout.setColumnData(notesColumn.getColumn(), new ColumnWeightData(50));
		

	}
	

	
	@Override
	protected void setupEditingContainer() {
		fillEditingSpace = true;
		super.setupEditingContainer();
		lblName = new Label(editMaster, SWT.NONE);
		lblName.setText("Name");
		txtName = viewHelper.getTextEditor(editMaster);
		viewHelper.layoutEditLabel(lblName);
		viewHelper.layoutEditEditor(txtName);
		lblNotes = new Label(editMaster, SWT.NONE);
		lblNotes.setText("Notes");
		txtNotes = viewHelper.getMultiLineTextEditor(editMaster);
		viewHelper.layoutEditLabel(lblNotes);
		viewHelper.layoutMultiLineText(txtNotes);
		
	}
	
	@Override
	public void add() {
		this.txtName.setFocus();
		super.add();
	}
	
	private class ViewerComparator extends ListSorterHelper
	{

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 == null || e2 == null)
			{
				return 0;
			}
			PropertyType p1 = (PropertyType)e1;
			PropertyType p2 = (PropertyType)e2;
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
		
		private int compareName(PropertyType p1, PropertyType p2)
		{
			if (p1 == null || p2 == null)
			{
				return 0;
			}
			String nameOne = "";
			String nameTwo = "";
			nameOne = p1.getName() == null ? "" : p1.getName();
			nameTwo = p2.getName() == null ? "" : p2.getName();
			return nameOne.compareTo(nameTwo);
		}
	}
}
