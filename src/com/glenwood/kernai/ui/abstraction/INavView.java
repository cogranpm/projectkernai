package com.glenwood.kernai.ui.abstraction;

import java.util.List;

import com.glenwood.kernai.ui.viewmodel.NavigationMenu;

public interface INavView {
	
	public void renderProjects(List<String> results);
	public void renderMenus(NavigationMenu menu);

}
