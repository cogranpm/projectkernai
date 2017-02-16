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
		super(view, model, ListHeader.class, ListHeader.TYPE_NAME);
		this.repository = new ListHeaderRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));

	}
	


	@Override
	public void loadModels()
	{
		super.loadModels();
		ListHeaderViewModel daModel = (ListHeaderViewModel)this.model;
		daModel.setChildItems(new ArrayList<ListDetail>());
	}
	

	@Override
	public void loadModel(ListHeader item) {
		super.loadModel(item);
	}

	
	

}
