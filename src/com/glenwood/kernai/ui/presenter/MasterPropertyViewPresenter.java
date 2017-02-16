package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.MasterProperty;
import com.glenwood.kernai.data.persistence.MasterPropertyRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.abstraction.IViewModel;

public class MasterPropertyViewPresenter extends BaseEntityPresenter<MasterProperty> {

	public MasterPropertyViewPresenter(IEntityView view, IViewModel<MasterProperty> model) {
		super(view, model, MasterProperty.class, MasterProperty.TYPE_NAME);
		this.repository = new MasterPropertyRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	


}
