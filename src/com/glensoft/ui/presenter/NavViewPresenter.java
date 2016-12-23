package com.glensoft.ui.presenter;

import java.util.ArrayList;
import java.util.List;

import com.glensoft.data.entity.Attribute;
import com.glensoft.data.entity.Entity;
import com.glensoft.data.persistence.BaseRepository;
import com.glensoft.data.persistence.EntityRepository;
import com.glensoft.data.persistence.PersistenceManagerFactory;
import com.glensoft.data.persistence.PersistenceManagerFactoryConstants;
import com.glensoft.ui.abstraction.INavView;
import com.glensoft.ui.abstraction.INavigator;
import com.glensoft.ui.navigation.Navigator;
import com.glensoft.ui.viewmodel.NavViewModel;
import com.glenwood.kernai.ui.MainWindow;

public class NavViewPresenter {
	
	INavigator navigator;
	INavView view;
	NavViewModel model;
	EntityRepository entityRepository;
	BaseRepository attributeRespository;
	
	public NavViewPresenter(INavView view )
	{
		navigator = new Navigator();
		this.model = new NavViewModel();
		this.view = view;
		this.entityRepository = new EntityRepository(PersistenceManagerFactory.getPersistenceManager(PersistenceManagerFactoryConstants.PERSISTENCE_FACTORY_TYPE_COUCHBASE_LITE));
		this.attributeRespository = new BaseRepository(PersistenceManagerFactory.getPersistenceManager(PersistenceManagerFactoryConstants.PERSISTENCE_FACTORY_TYPE_COUCHBASE_LITE));
	}
	
	public void loadProjects()
	{
		List<String> dummy = new ArrayList<String>();
		List<Attribute> attributes = attributeRespository.getAll("attributes", Attribute.class);
		for(Attribute attribute : attributes)
		{
			dummy.add(attribute.toString());
		}
		this.view.renderProjects(dummy);		
	}
	
	public void loadProject(String projectId)
	{
		//swap the edit region
		navigator.loadProjectView(projectId);
		
	}
	
	public void test()
	{
		Entity customersEntity = new Entity();
		customersEntity.setName("customers");
		Attribute companyName = new Attribute();
		companyName.setName("CompanyName");
		companyName.setLength(40L);
		companyName.setAllowNull(false);
		companyName.setDataType("String");
		companyName.setEntity(customersEntity);
		this.entityRepository.save(customersEntity);
		this.attributeRespository.save(companyName);
		
	}

}
