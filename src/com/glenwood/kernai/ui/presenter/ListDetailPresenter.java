package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.persistence.ListDetailRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.view.ListDetailMasterDetailView;
import com.glenwood.kernai.ui.viewmodel.ListDetailViewModel;


public class ListDetailPresenter {

	private ListDetailMasterDetailView view;
	private ListDetailViewModel model;
	private ListDetailRepository repository;
	
	public ListDetailPresenter(ListDetailMasterDetailView view, ListDetailViewModel model)
	{
		this.view = view;
		this.model = model;
		this.repository = new ListDetailRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	
	
	public void loadItems()
	{
		this.model.setItems(this.repository.getAllByListHeader(this.model.getListHeader().getId()));
		this.view.refreshView();
	}
	
	public void loadItems(ListHeader listHeader)
	{
		this.model.setListHeader(listHeader);
		this.loadItems();
	}
}
