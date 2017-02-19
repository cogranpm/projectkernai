package com.glenwood.kernai.ui.view;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.MasterProperty;
import com.glenwood.kernai.data.entity.PropertyGroup;
import com.glenwood.kernai.data.entity.PropertyType;
import com.glenwood.kernai.ui.abstraction.BaseEntityView;
import com.glenwood.kernai.ui.presenter.MasterPropertyViewPresenter;
import com.glenwood.kernai.ui.viewmodel.MasterPropertyViewModel;

public class MasterPropertyView extends BaseEntityView<MasterProperty> {
	
	Label lblName;
	Text txtName;
	Label lblLabel;
	Text txtLabel;
	Label lblDefaultValue;
	Text txtDefaultValue;
	Label lblNotes;
	Text txtNotes;
	
	Label lblPropertyType;
	ComboViewer cboPropertyType;
	Label lblPropertyGroup;
	ComboViewer cboPropertyGroup;

	public MasterPropertyView(Composite parent, int style) {
		super(parent, style);

	}
	
	@Override
	protected void setupModelAndPresenter() {
		this.model = new MasterPropertyViewModel();
		this.presenter = new MasterPropertyViewPresenter(this, this.model);
	}
	
	@Override
	protected void setupListColumns() {
		super.setupListColumns();
		
		TableViewerColumn nameColumn = viewHelper.getListColumn(listViewer, "Name");
		TableViewerColumn labelColumn = viewHelper.getListColumn(listViewer, "Label");
		TableViewerColumn defaultValueColumn = viewHelper.getListColumn(listViewer, "Default");
		
		
//		nameColumn.getColumn().addSelectionListener(this.getSelectionAdapter(nameColumn.getColumn(), 0));
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(labelColumn.getColumn(),  new ColumnWeightData(100));
		tableLayout.setColumnData(defaultValueColumn.getColumn(),  new ColumnWeightData(100));
	}
	
	@Override
	protected void setupEditingContainer() {
		super.setupEditingContainer();
		lblName = new Label(editMaster, SWT.NONE);
		lblName.setText("Name");
		txtName = viewHelper.getTextEditor(editMaster);
		
		lblLabel = new Label(editMaster, SWT.NONE);
		lblLabel.setText("Label");
		txtLabel = viewHelper.getTextEditor(editMaster);
		
		lblDefaultValue = new Label(editMaster, SWT.NONE);
		lblDefaultValue.setText("Default Value");
		txtDefaultValue = viewHelper.getTextEditor(editMaster);
		
		lblNotes = new Label(editMaster, SWT.NONE);
		lblNotes.setText("Notes");
		txtNotes = viewHelper.getMultiLineTextEditor(editMaster);
		
		viewHelper.layoutEditLabel(lblName);
		viewHelper.layoutEditEditor(txtName);
		viewHelper.layoutEditLabel(lblLabel);
		viewHelper.layoutEditEditor(txtLabel);
		viewHelper.layoutEditLabel(lblDefaultValue);
		viewHelper.layoutEditEditor(txtDefaultValue);
		viewHelper.layoutEditLabel(lblNotes);
		viewHelper.layoutMultiLineText(txtNotes);
		
		MasterPropertyViewModel daModel = (MasterPropertyViewModel)this.model;
		lblPropertyGroup = new Label(editMaster, SWT.NONE);
		lblPropertyGroup.setText("Group");
		cboPropertyGroup = new ComboViewer(editMaster);

		cboPropertyGroup.setContentProvider(ArrayContentProvider.getInstance());
		cboPropertyGroup.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element)
			{
				PropertyGroup propertyGroup = (PropertyGroup)element;
				return propertyGroup.getName();
			}
		});
		cboPropertyGroup.setInput(daModel.getPropertyGroupLookup());
		
		lblPropertyType = new Label(editMaster, SWT.NONE);
		lblPropertyType.setText("Type");
		cboPropertyType = new ComboViewer(editMaster);
		cboPropertyType.setContentProvider(ArrayContentProvider.getInstance());
		cboPropertyType.setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element)
			{
				PropertyType propertyType = (PropertyType)element;
				return propertyType.getName();
			}
		});
		cboPropertyType.setInput(daModel.getPropertyTypeLookup());
		
		viewHelper.layoutEditLabel(lblPropertyGroup);
		viewHelper.layoutComboViewer(cboPropertyGroup);
		viewHelper.layoutEditLabel(lblPropertyType);
		viewHelper.layoutComboViewer(cboPropertyType);
		
	}
	
	@Override
	protected void initDataBindings() {
		super.initDataBindings();
	}
	
	@Override
	public void add() {
		super.add();
	}

}
