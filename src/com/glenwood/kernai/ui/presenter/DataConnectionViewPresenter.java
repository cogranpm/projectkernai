package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.entity.MSSQLDataConnection;
import com.glenwood.kernai.ui.abstraction.IViewModel;
import com.glenwood.kernai.ui.view.DataConnectionView;

public class DataConnectionViewPresenter <T extends BaseEntity> {

	public DataConnectionViewPresenter(DataConnectionView<T> view, IViewModel<MSSQLDataConnection> model) {
		/*
		super(view, model, MSSQLDataConnection.class, MSSQLDataConnection.TYPE_NAME);
		this.repository = new MSSQLDataConnectionRepository(
				PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		*/
	}

}
