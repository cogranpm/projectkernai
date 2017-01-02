package com.glenwood.kernai.ui.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class NavigationMenuItem {
	
	private String label;
	private String imageKey;
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
	
	public NavigationMenuItem()
	{
		this(null, null, null);
	}
	
	public NavigationMenuItem(String label)
	{
		this(label, null, null);
	}
	
	public NavigationMenuItem(String label, String imageKey)
	{
		this(label, imageKey, null);
	}
	
	public NavigationMenuItem(String label, String imageKey, NavigationMenuItem parent)
	{
		this.parent = parent;
		this.label = label;
		this.imageKey = imageKey;
		if (parent != null)
		{
			parent.getChildren().add(this);
		}
	}
}
