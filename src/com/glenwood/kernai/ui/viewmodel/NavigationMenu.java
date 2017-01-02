package com.glenwood.kernai.ui.viewmodel;

import java.util.ArrayList;

public class NavigationMenu {
	
	private NavigationMenuItem root;
	
	
	
	public NavigationMenuItem getRoot() {
		return root;
	}



	public NavigationMenu(String label)
	{
		root = new NavigationMenuItem();
		root.setLabel(label);
		root.setChildren(new ArrayList<NavigationMenuItem>());
		root.setParent(null);
	}
	

	

}
