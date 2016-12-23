package com.glensoft.ui.view;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.glensoft.data.entity.Project;
import com.glensoft.ui.abstraction.IProjectView;
import com.glensoft.ui.presenter.ProjectViewPresenter;
import com.glensoft.ui.view.grid.ComboBoxCellEditorExample;
import com.glensoft.ui.view.grid.NameEditor;
import com.glensoft.ui.viewmodel.ProjectViewModel;

public class ProjectView extends Composite implements IProjectView{
	
	private ProjectViewPresenter presenter;
	private ProjectViewModel model;
	
	

	public ProjectViewPresenter getPresenter() {
		return presenter;
	}



	public ProjectViewModel getModel() {
		return model;
	}


	TableViewer mainGrid;
	Table mainGridTable;
	
	public ProjectView(Composite parent, int style) {
		super(parent, style);
		this.model = new ProjectViewModel();
		this.presenter = new ProjectViewPresenter(this, model);

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
					 Project project = (Project)element;
					 return project.getName();
				 }
			});
		nameColumn.setEditingSupport(new NameEditor(mainGrid));
		
		
		
		TableViewerColumn modelColumn = new TableViewerColumn(mainGrid, SWT.NONE);
		modelColumn.getColumn().setWidth(200);
		modelColumn.getColumn().setText("Model");
		modelColumn.getColumn().setResizable(true);
		modelColumn.getColumn().setMoveable(true);
		modelColumn.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element)
			{
				Project project = (Project)element;
				if (project.getId().equalsIgnoreCase("1"))
				{
					return "UserModel";
				}
				else if(project.getId().equalsIgnoreCase("2"))
				{
					return "CompanyModel";
				}
				else
				{
					return project.getId();
				}
				/*return project.getId();*/
			}
		});
		modelColumn.setEditingSupport(new ComboBoxCellEditorExample(mainGrid));
		
		
		mainGrid.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = mainGrid.getStructuredSelection();
				Project selectedProject = (Project)selection.getFirstElement();
				System.out.println(selectedProject.getName());
				
			}
		});
		
		
		/*
		CellEditor[] editors = new CellEditor[1];
		TextCellEditor editor = new TextCellEditor(mainGridTable);
		Text textControl = (Text)editor.getControl();
		textControl.setTextLimit(60);
		editors[0] = editor;
		mainGrid.setCellEditors(editors);
		*/

	}
	
	public void renderProjectList()
	{
		//for(Project project : this.presenter.getModel().getProjects())
		for(Project project : this.model.getProjects())
		{
			System.out.println(project.getName());
		}
		
		mainGrid.setInput(this.model.getProjects());
	}

	
	
}
