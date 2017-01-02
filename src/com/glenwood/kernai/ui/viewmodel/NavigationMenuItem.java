package com.glenwood.kernai.ui.viewmodel;

import java.util.List;

public class NavigationMenuItem {
	
	private String label;
	private NavigationMenuItem parent;
	private List<NavigationMenuItem> children;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public NavigationMenuItem getParent() {
		return parent;
	}
	public void setParent(NavigationMenuItem parent) {
		this.parent = parent;
	}
	public List<NavigationMenuItem> getChildren() {
		return children;
	}
	public void setChildren(List<NavigationMenuItem> children) {
		this.children = children;
	}
	
	

}
