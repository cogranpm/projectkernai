package com.glenwood.kernai.ui.presenter;

import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.persistence.BaseRepository;
import com.glenwood.kernai.data.persistence.EntityRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactoryConstants;
import com.glenwood.kernai.ui.MainWindow;
import com.glenwood.kernai.ui.abstraction.INavView;
import com.glenwood.kernai.ui.abstraction.INavigator;
import com.glenwood.kernai.ui.navigation.Navigator;
import com.glenwood.kernai.ui.viewmodel.NavViewModel;
import com.glenwood.kernai.ui.viewmodel.NavigationMenu;
import com.glenwood.kernai.ui.viewmodel.NavigationMenuItem;

public class NavViewPresenter {
	
	private INavigator navigator;
	private INavView view;
	private NavViewModel model;
	private EntityRepository entityRepository;
	private BaseRepository attributeRespository;
	private List<NavigationMenu> menus;
	
	public NavViewPresenter(INavView view )
	{
		navigator = new Navigator();
		this.model = new NavViewModel();
		this.view = view;
		this.entityRepository = new EntityRepository(PersistenceManagerFactory.getPersistenceManager(PersistenceManagerFactoryConstants.PERSISTENCE_FACTORY_TYPE_COUCHBASE_LITE));
		this.attributeRespository = new BaseRepository(PersistenceManagerFactory.getPersistenceManager(PersistenceManagerFactoryConstants.PERSISTENCE_FACTORY_TYPE_COUCHBASE_LITE));
		this.menus = new ArrayList<NavigationMenu>();
	}
	
	public void loadMenus()
	{
		NavigationMenu menu = new NavigationMenu("Projects");
		NavigationMenuItem test = new NavigationMenuItem();
		test.setLabel("Testing");
		menu.getRoot().getChildren().add(test);
		this.menus.add(menu);
		this.view.renderMenus(menus);
	}
	
	public void loadProjects()
	{
		//this.test();
		List<String> dummy = new ArrayList<String>();
		List<Attribute> attributes = attributeRespository.getAll("attributes", Attribute.class);
		for(Attribute attribute : attributes)
		{
			if (attribute != null)
			{
				dummy.add(attribute.toString());
			}
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
