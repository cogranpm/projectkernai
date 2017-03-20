package com.glenwood.kernai.ui.view;

import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.entity.ImportDefinition;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListEditView;
import com.glenwood.kernai.ui.presenter.ImportDefinitionViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ImportDefinitionViewModel;
import com.glenwood.kernai.ui.workers.ImportWorker;

public class ImportDefinitionView extends BaseEntityMasterDetailListEditView<ImportDefinition, Project> {
	
	DataConnectionInlineView connectionView;
	Button btnNext;

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
		/* lets go into new mode immediately */
		this.onAdd();
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
		btnNext = new Button(editMaster, SWT.PUSH);
		btnNext.setText("Next");
		btnNext.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				/* connect first, showing a progress, must be asynchronous */
				DataConnection connection = connectionView.getModel().getCurrentItem();
				ImportWorker importWorker = new ImportWorker(connection);
				importWorker.openConnection(getShell().getDisplay());
				MessageDialog.openInformation(getShell(), "Complete", "Connection Complete");
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		GridDataFactory.fillDefaults().span(2,1).align(SWT.RIGHT, SWT.CENTER).grab(false, false).applyTo(btnNext);
		
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
