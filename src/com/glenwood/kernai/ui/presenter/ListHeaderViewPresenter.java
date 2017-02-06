package com.glenwood.kernai.ui.presenter;

import java.util.ArrayList;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.persistence.ListheaderRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.abstraction.BaseViewModel;
import com.glenwood.kernai.ui.abstraction.IEntityPresenter;
import com.glenwood.kernai.ui.abstraction.IViewModel;
import com.glenwood.kernai.ui.view.ListHeaderView;
import com.glenwood.kernai.ui.viewmodel.ListHeaderViewModel;

public class ListHeaderViewPresenter extends BaseEntityPresenter<ListHeader> {
	
	//private ListHeaderView view;
	//private BaseViewModel<ListHeader> model;
	
	
	public ListHeaderViewPresenter(ListHeaderView view, ListHeaderViewModel model)
	{
		super(view, model);
		//this.view = view;
		//this.model = model;
		this.repository = new ListheaderRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));

	}
	

	/*
	@Override
	public void loadModel(BaseEntity item)
	{
		//ListHeader domainEntity = (ListHeader)item;
		//model.setCurrentItem(domainEntity);
		model.setCurrentItem(item);
		model.setDirty(false);
		this.view.refreshView();
	}
	*/
	@Override
	public void loadModels()
	{
		this.model.setItems(this.repository.getAll(ListHeader.TYPE_NAME, ListHeader.class));
		if (this.model.getItems() != null && this.model.getItems().size() > 0)
		{
			this.model.setCurrentItem(this.model.getItems().get(0));
		}

		//this.model.setChildItems(new ArrayList<ListDetail>());
		ListHeaderViewModel daModel = (ListHeaderViewModel)this.model;
		daModel.setChildItems(new ArrayList<ListDetail>());
	}
	
	@Override
	public void addModel()
	{
		ListHeader newListHeader= new ListHeader();
		model.setCurrentItem(newListHeader);
	}
	
	/*
	@Override
	public void saveModel()
	{
		this.repository.save(model.getCurrentItem());
	}
	
	@Override
	public void deleteModel()
	{
		this.model.getItems().remove(this.model.getCurrentItem());
		this.repository.delete(model.getCurrentItem());
		this.model.setCurrentItem(null);
		this.view.refreshView();
	}
	*/
	
	
	

}
