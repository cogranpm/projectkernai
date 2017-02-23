package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.Project;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.data.persistence.ProjectRepository;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.abstraction.IViewModel;

public class ProjectViewPresenter extends BaseEntityPresenter<Project> {

	
	public ProjectViewPresenter(IEntityView view, IViewModel<Project> model) {
		super(view, model, Project.class, Project.TYPE_NAME);
		this.repository = new ProjectRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	

	


}
