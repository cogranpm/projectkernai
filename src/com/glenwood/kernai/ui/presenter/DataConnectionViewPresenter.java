package com.glenwood.kernai.ui.presenter;

import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.persistence.DataConnectionRepository;
import com.glenwood.kernai.data.persistence.ListHeaderRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.abstraction.IModelChangeListener;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.abstraction.IViewModel;
import com.glenwood.kernai.ui.viewmodel.DataConnectionViewModel;

public class DataConnectionViewPresenter extends BaseEntityPresenter<DataConnection> {

	
	//private DataConnectionRepository repository;
	private ListHeaderRepository listHeaderRepository;
	//IViewModel<DataConnection> model;
	//DataConnectionInlineView view;
	
	private List<IModelChangeListener> connectionContainers;
	
	public DataConnectionViewPresenter(IEntityView view, IViewModel<DataConnection> model) {
		super(view, model, DataConnection.class, DataConnection.TYPE_NAME);
		this.repository = new DataConnectionRepository(
				PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));

		this.listHeaderRepository = new ListHeaderRepository(
				PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		
		DataConnectionViewModel aModel = (DataConnectionViewModel)this.model;
		aModel.setVendorNameLookup(this.listHeaderRepository.getListItemsByName(ApplicationData.LIST_DATABASE_VENDOR_NAME));
		
		this.connectionContainers = new ArrayList<IModelChangeListener>();
	}
	
	public void addConnectionContainer(IModelChangeListener container)
	{
		this.connectionContainers.add(container);
	}
	
	public void removeConnectionContainer(IModelChangeListener container)
	{
		this.connectionContainers.remove(container);
	}
	
	private void broadcastModelChange()
	{
		for(IModelChangeListener container : this.connectionContainers)
		{
			container.OnModelChanged(this.model.getCurrentItem());
		}
	}
	
	@Override
	public void modelChanged() {
		super.modelChanged();
		//broadcast this to any registered listener
		this.broadcastModelChange();
	}

}
