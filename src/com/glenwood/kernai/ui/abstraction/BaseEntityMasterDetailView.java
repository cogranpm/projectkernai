package com.glenwood.kernai.ui.abstraction;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolItem;

import com.glenwood.kernai.data.abstractions.BaseEntity;

public class BaseEntityMasterDetailView<T extends BaseEntity, P extends BaseEntity> extends Composite implements IEntityMasterDetailView<T, P> {

	protected Map<String, IAction> actionMap = new HashMap<String, IAction>();
	protected Map<String, ToolItem> toolItemMap = new HashMap<String, ToolItem>();
	protected static final String NEW_ACTION_KEY = "new";
	protected static final String DELETE_ACTION_KEY = "delete";
	protected static final String EDIT_ACTION_KEY = "edit";
	protected static final String ID_PREFIX = "com.glenwood.kernai.ui.view.masterDetailView.";
	protected DataBindingContext ctx;
	protected TableViewer listViewer;
	protected Table listTable;
	
	public BaseEntityMasterDetailView(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAddEdit(Boolean adding) {
		// TODO Auto-generated method stub
		
	}

}
