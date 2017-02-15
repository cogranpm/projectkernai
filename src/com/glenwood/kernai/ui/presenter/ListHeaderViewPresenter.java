package com.glenwood.kernai.ui.presenter;

import java.util.ArrayList;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.persistence.ListHeaderRepository;
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
		this.repository = new ListHeaderRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));

	}
	


	@Override
	public void loadModels()
	{
		this.model.setItems(this.repository.getAll(ListHeader.TYPE_NAME, ListHeader.class));
		if (this.model.getItems() != null && this.model.getItems().size() > 0)
		{
			this.model.setCurrentItem(this.model.getItems().get(0));
		}

		ListHeaderViewModel daModel = (ListHeaderViewModel)this.model;
		daModel.setChildItems(new ArrayList<ListDetail>());
	}
	
	@Override
	public void addModel()
	{
		ListHeader newListHeader= new ListHeader();
		model.setCurrentItem(newListHeader);
	}
	

	
	

}
