package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.MasterProperty;
import com.glenwood.kernai.data.entity.MasterPropertyListItem;
import com.glenwood.kernai.data.persistence.ListDetailRepository;
import com.glenwood.kernai.data.persistence.MasterPropertyListItemRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityMasterDetailPresenter;
import com.glenwood.kernai.ui.view.MasterPropertyListItemMasterDetailView;
import com.glenwood.kernai.ui.viewmodel.MasterPropertyListItemViewModel;

public class MasterPropertyListItemViewPresenter extends BaseEntityMasterDetailPresenter<MasterPropertyListItem, MasterProperty> {

	public MasterPropertyListItemViewPresenter(MasterPropertyListItemMasterDetailView view, MasterPropertyListItemViewModel model)
	{
		super(view, model, MasterPropertyListItem.class, MasterPropertyListItem.TYPE_NAME);
		this.repository = new MasterPropertyListItemRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	
	@Override
	public void loadItems()
	{
		MasterPropertyListItemRepository srepo = (MasterPropertyListItemRepository)this.repository;
		this.model.setItems(srepo.getAllByMasterProperty(this.model.getParent().getId()));
		this.view.refreshView();
	}
	

}
