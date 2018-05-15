package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.persistence.ListDetailRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailListModalPresenter;
import com.glenwood.kernai.ui.view.ListDetailMasterDetailView;
import com.glenwood.kernai.ui.viewmodel.ListDetailViewModel;


public class ListDetailViewPresenter extends BaseEntityMasterDetailListModalPresenter<ListDetail, ListHeader> {

	//private ListDetailMasterDetailView view;
	//private ListDetailViewModel model;
	//private ListDetailRepository repository;
	
	public ListDetailViewPresenter(ListDetailMasterDetailView view, ListDetailViewModel model)
	{
		super(view, model, ListDetail.class, ListDetail.TYPE_NAME);
		this.repository = new ListDetailRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	
	@Override
	public void loadItems()
	{
		ListDetailRepository srepo = (ListDetailRepository)this.repository;
		this.model.setItems(srepo.getAllByListHeader(this.model.getParent().getId()));
		this.view.refreshView();
	}
	
	/*
	public void loadItems(ListHeader listHeader)
	{
		this.model.setParent(listHeader);
		this.loadItems();
	}
	*/
	
	/*
	public void addModel()
	{
		this.model.setCurrentItem(new ListDetail(this.model.getParent()));
		this.view.showAddEdit(true);
	}
	*/
	
	/*
	public void editModel(ListDetail listDetail)
	{
		this.model.setCurrentItem(listDetail);
		this.view.showAddEdit(false);
	}
	
	
	public void deleteModel(ListDetail listDetail)
	{
		if(listDetail != null)
		{
			repository.delete(listDetail);
			
			this.model.getItems().remove(listDetail);
			this.view.refreshView();
		}
	}
	
	public void saveModel()
	{
		this.repository.save(this.model.getCurrentItem());
	}
	
	*/
}
