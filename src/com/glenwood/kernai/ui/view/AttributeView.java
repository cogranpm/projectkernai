package com.glenwood.kernai.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.ui.view.grid.ComboBoxCellViewerEditorExample;
import com.glenwood.kernai.ui.view.grid.NameEditor;
import com.glenwood.kernai.ui.viewmodel.AttributeViewModel;

public class AttributeView extends Composite {
	
	private AttributeViewModel model;
	
	private List<ListDetail> dataTypeLookup;
	
	TableViewer mainGrid;
	Table mainGridTable;

	private void setupDummyData()
	{
		Attribute one = new Attribute();
		one.setName("DataType");
		one.setDataType("string");
		one.setAllowNull(false);
		one.setLength(50L);
		this.model.getAttributes().add(one);
		
		Attribute two = new Attribute();
		two.setName("IsActive");
		two.setDataType("bool");
		two.setAllowNull(false);
		two.setLength(50L);
		this.model.getAttributes().add(two);
		
		this.dataTypeLookup = new ArrayList<ListDetail>();
		ListDetail stringType = new ListDetail();
		stringType.setKey("string");
		stringType.setLabel("String");
		this.dataTypeLookup.add(stringType);
		
		ListDetail booleanType = new ListDetail();
		booleanType.setKey("bool");
		booleanType.setLabel("Boolean");
		this.dataTypeLookup.add(booleanType);
	}
	
	
	public AttributeView(Composite parent, int style)
	{
		super(parent, style);
		
		this.model = new AttributeViewModel();
	//	this.presenter = new ProjectViewPresenter(this, model);
		
		/* temp */
		this.setupDummyData();

		this.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		mainGrid = new TableViewer(this, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		mainGridTable = mainGrid.getTable();
		mainGridTable.setHeaderVisible(true);
		mainGridTable.setLinesVisible(true);
		mainGrid.setContentProvider(ArrayContentProvider.getInstance());
		
		TableViewerColumn nameColumn = new TableViewerColumn(mainGrid, SWT.NONE);
		nameColumn.getColumn().setWidth(100);
		nameColumn.getColumn().setText("Name");
		nameColumn.getColumn().setResizable(true);
		nameColumn.getColumn().setMoveable(true);
		nameColumn.setLabelProvider(new ColumnLabelProvider()
			{
				 @Override
				 public String getText(Object element)
				 {
					 Attribute attribute = (Attribute)element;
					 return attribute.getName();
				 }
			});
		nameColumn.setEditingSupport(new NameEditor(mainGrid));
		
		
		
		TableViewerColumn dataTypeColumn = new TableViewerColumn(mainGrid, SWT.NONE);
		dataTypeColumn.getColumn().setWidth(200);
		dataTypeColumn.getColumn().setText("Data Type");
		dataTypeColumn.getColumn().setResizable(true);
		dataTypeColumn.getColumn().setMoveable(true);
		dataTypeColumn.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element)
			{
				Attribute attribute = (Attribute)element;
				return attribute.getDataType();

			}
		});
		
		/*
		String[] items = {"string", "bool"};
		CellEditor[] editors = new CellEditor[2];
		CellEditor nameEditor = new TextCellEditor(mainGridTable);
		CellEditor editor = new ComboBoxCellEditor(mainGridTable, items, SWT.READ_ONLY);
		editors[0] = nameEditor;
		editors[1] = editor;
		mainGrid.setCellModifier(new AttributeCellModifier(mainGrid));
		mainGrid.setCellEditors(editors);
		String[] props = {"Name", "DataType"};
		mainGrid.setColumnProperties(props);
		*/
		
		//dataTypeColumn.setEditingSupport(new ComboBoxCellEditorExample(mainGrid, dataTypeLookup));
		dataTypeColumn.setEditingSupport(new ComboBoxCellViewerEditorExample(mainGrid, dataTypeLookup));
		
		mainGrid.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = mainGrid.getStructuredSelection();
				Attribute selected = (Attribute)selection.getFirstElement();

				
			}
		});
		
		mainGrid.setInput(this.model.getAttributes());
		
		/*
		CellEditor[] editors = new CellEditor[1];
		TextCellEditor editor = new TextCellEditor(mainGridTable);
		Text textControl = (Text)editor.getControl();
		textControl.setTextLimit(60);
		editors[0] = editor;
		mainGrid.setCellEditors(editors);
		*/
		
	}

}
