package com.glensoft.ui.view;

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

import com.glensoft.data.entity.Attribute;
import com.glensoft.data.entity.ListDetail;
import com.glensoft.data.entity.Project;
import com.glensoft.ui.view.grid.ComboBoxCellEditorExample;
import com.glensoft.ui.view.grid.NameEditor;
import com.glensoft.ui.viewmodel.AttributeViewModel;

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
		
		
		
		TableViewerColumn modelColumn = new TableViewerColumn(mainGrid, SWT.NONE);
		modelColumn.getColumn().setWidth(200);
		modelColumn.getColumn().setText("Data Type");
		modelColumn.getColumn().setResizable(true);
		modelColumn.getColumn().setMoveable(true);
		modelColumn.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element)
			{
				Attribute attribute = (Attribute)element;
				return attribute.getDataType();

			}
		});
		modelColumn.setEditingSupport(new ComboBoxCellEditorExample(mainGrid, this.dataTypeLookup));
		
		
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
