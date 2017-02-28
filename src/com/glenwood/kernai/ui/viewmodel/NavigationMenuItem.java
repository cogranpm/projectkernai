package com.glenwood.kernai.ui.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.ui.abstraction.INavigationMenuAction;

public class NavigationMenuItem {
	
	private String label;
	private String imageKey;
	private INavigationMenuAction menuAction;
	private NavigationMenuItem parent;
	private List<NavigationMenuItem> children;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getImageKey() {
		return imageKey;
	}
	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}
	
	public NavigationMenuItem getParent() {
		return parent;
	}
	public void setParent(NavigationMenuItem parent) {
		this.parent = parent;
	}
	public List<NavigationMenuItem> getChildren() {
		if (children == null)
		{
			children = new ArrayList<NavigationMenuItem>();
		}
		return children;
	}
	public void setChildren(List<NavigationMenuItem> children) {
		this.children = children;
	}
	
	
	
	public INavigationMenuAction getMenuAction() {
		return menuAction;
	}
	public void setMenuAction(INavigationMenuAction menuAction) {
		this.menuAction = menuAction;
	}
	
	public NavigationMenuItem()
	{
		this(null, null, null, null);
	}
	
	public NavigationMenuItem(String label)
	{
		this(label, null, null, null);
	}
	
	public NavigationMenuItem(String label, String imageKey)
	{
		this(label, imageKey, null, null);
	}
	
	public NavigationMenuItem(String label, String imageKey, INavigationMenuAction menuAction)
	{
		this(label, imageKey, menuAction, null);
	}
	
	public NavigationMenuItem(String label, String imageKey, INavigationMenuAction menuAction, NavigationMenuItem parent)
	{
		this.parent = parent;
		this.label = label;
		this.imageKey = imageKey;
		this.menuAction = menuAction;
		if (parent != null)
		{
			parent.getChildren().add(this);
		}
	}
}
