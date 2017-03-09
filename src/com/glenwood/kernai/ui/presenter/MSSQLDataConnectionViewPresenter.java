package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.MSSQLDataConnection;
import com.glenwood.kernai.data.persistence.MSSQLDataConnectionRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.abstraction.IViewModel;

public class MSSQLDataConnectionViewPresenter extends BaseEntityPresenter<MSSQLDataConnection> {

	public MSSQLDataConnectionViewPresenter(IEntityView view, IViewModel<MSSQLDataConnection> model) {
		super(view, model, MSSQLDataConnection.class, MSSQLDataConnection.TYPE_NAME);
		this.repository = new MSSQLDataConnectionRepository(
				PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}

}
