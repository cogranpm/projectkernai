package com.glenwood.kernai.ui.presenter;

import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.persistence.BaseRepository;
import com.glenwood.kernai.data.persistence.EntityRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactoryConstants;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.MainWindow;
import com.glenwood.kernai.ui.abstraction.INavView;
import com.glenwood.kernai.ui.abstraction.INavigator;
import com.glenwood.kernai.ui.navigation.MasterCategoryNavigationAction;
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
	private NavigationMenu leftMenu;
	
	public NavViewPresenter(INavView view )
	{
		navigator = new Navigator();
		this.model = new NavViewModel();
		this.view = view;
		this.entityRepository = new EntityRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		this.attributeRespository = new BaseRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	
	public void loadMenus()
	{
		leftMenu = new NavigationMenu("Left Menu");
		NavigationMenuItem item = this.addRootMenuItem("Projects", ApplicationData.IMAGE_DIAGRAM);
		NavigationMenuItem childItem;
		//NavigationMenuItem childItem = new NavigationMenuItem("One", ApplicationData.IMAGE_MASTERPAGE, item);
		
		
		item = this.addRootMenuItem("Lists", ApplicationData.IMAGE_MASTERPAGE);
		item = this.addRootMenuItem("Master Properties", ApplicationData.IMAGE_DIAGRAM);
		childItem = new NavigationMenuItem("Property", ApplicationData.IMAGE_MASTERPAGE, new MasterCategoryNavigationAction(),  item);
		childItem = new NavigationMenuItem("Type", ApplicationData.IMAGE_MASTERPAGE, new MasterCategoryNavigationAction(), item);
		childItem = new NavigationMenuItem("Category", ApplicationData.IMAGE_MASTERPAGE, new MasterCategoryNavigationAction(), item);
		childItem = new NavigationMenuItem("Group", ApplicationData.IMAGE_MASTERPAGE, new MasterCategoryNavigationAction(), item);
		
		item = this.addRootMenuItem("Scripting", ApplicationData.IMAGE_DIAGRAM);
		item = this.addRootMenuItem("Imports", ApplicationData.IMAGE_MASTERPAGE);
		childItem = new NavigationMenuItem("Template", ApplicationData.IMAGE_MASTERPAGE, new MasterCategoryNavigationAction(), item);
		childItem = new NavigationMenuItem("Script", ApplicationData.IMAGE_MASTERPAGE, new MasterCategoryNavigationAction(), item);
		childItem = new NavigationMenuItem("Controller", ApplicationData.IMAGE_MASTERPAGE, new MasterCategoryNavigationAction(), item);
		this.view.renderMenus(this.leftMenu);
	}
	
	public void onMenuSelected(NavigationMenuItem item)
	{
		if(item.getMenuAction() != null)
		{
			item.getMenuAction().go();
		}
	}
	
	private NavigationMenuItem addRootMenuItem(String label, String imageKey)
	{
		NavigationMenuItem item = new NavigationMenuItem(label, imageKey);
		this.leftMenu.getRoot().getChildren().add(item);
		return item;
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
