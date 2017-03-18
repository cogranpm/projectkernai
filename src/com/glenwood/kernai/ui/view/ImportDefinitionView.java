package com.glenwood.kernai.ui.view;

import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.presenter.ImportDefinitionViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ImportDefinitionViewModel;

public class ImportDefinitionView extends BaseEntityMasterDetailListEditView<ImportDefinition, Project> {
	
	DataConnectionInlineView connectionView;

	public ImportDefinitionView(Composite parent, int style, Project parentEntity) {
		super(parent, style, parentEntity);
		
	}
	
	@Override
	protected void setupModelAndPresenter(Project parentEntity) {
		super.setupModelAndPresenter(parentEntity);
		this.model = new ImportDefinitionViewModel(parentEntity);
		this.presenter = new ImportDefinitionViewPresenter(this, this.model);
	}
	
	@Override
	protected void onInit() {
		super.onInit();
	}
	
	@Override
	protected void setupListColumns() {
		super.setupListColumns();
		TableViewerColumn connectionColumn = this.viewHelper.getListColumn(listViewer, "Connection");
		connectionColumn.getColumn().addSelectionListener(this.getSelectionAdapter(connectionColumn.getColumn(), 0));
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(connectionColumn.getColumn(), new ColumnWeightData(100));
	}

	
	@Override
	protected void onInitDataBindings() {
		super.onInitDataBindings();
        List<ImportDefinition> el = model.getItems();
        input = new WritableList(el, ImportDefinition.class);

		
        
        /*
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
        */
        
        
        //listViewer.setInput(input);
	}
	
	@Override
	protected void onSetupEditingContainer() {
		super.onSetupEditingContainer();
		connectionView = new DataConnectionInlineView(editMaster, SWT.NONE);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, true).indent(0, 0).applyTo(connectionView);
		
	}
	
	@Override
	protected void onAdd() {
		super.onAdd();
		if(connectionView != null)
		{
			this.connectionView.presenter.addModel();
		}
	}
	
	
}
