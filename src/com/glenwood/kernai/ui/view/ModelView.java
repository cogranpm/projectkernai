package com.glenwood.kernai.ui.view;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;

import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailView;
import com.glenwood.kernai.ui.abstraction.MasterDetailListEditViewEngine;
import com.glenwood.kernai.ui.presenter.ModelViewPresenter;
import com.glenwood.kernai.ui.viewmodel.ModelViewModel;

public class ModelView extends BaseEntityMasterDetailView<Model, Project>{

	public ModelView(Composite parent, int style, Project parentEntity) {
		super(parent, style, parentEntity, new MasterDetailListEditViewEngine());

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
	

}
