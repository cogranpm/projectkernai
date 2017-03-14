package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.persistence.DataConnectionRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.IViewModel;
import com.glenwood.kernai.ui.view.DataConnectionInlineView;

public class DataConnectionViewPresenter {

	public DataConnectionViewPresenter(DataConnectionInlineView view, IViewModel<DataConnection> model) {

		/*
		super(view, model, DataConnection.class, DataConnection.TYPE_NAME);
		this.repository = new DataConnectionRepository(
				PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	*/
	}

}
