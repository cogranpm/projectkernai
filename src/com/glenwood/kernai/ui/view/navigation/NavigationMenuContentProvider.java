package com.glenwood.kernai.ui.view.navigation;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.glenwood.kernai.ui.viewmodel.NavigationMenu;
import com.glenwood.kernai.ui.viewmodel.NavigationMenuItem;

public class NavigationMenuContentProvider implements ITreeContentProvider {

		
	@Override
	public Object[] getElements(Object inputElement) {
		Object[] val = new Object[0];
		NavigationMenu menu = (NavigationMenu)inputElement;
		if (menu != null && menu.getRoot() != null && menu.getRoot().getChildren() != null)
		{
			return menu.getRoot().getChildren().toArray();
		}
		return val;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] val = new Object[0];
		if (parentElement != null)
		{
			val = ((NavigationMenuItem)parentElement).getChildren().toArray();
		}
		return val;
	}

	@Override
	public Object getParent(Object element) {
		return ((NavigationMenuItem)element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		NavigationMenuItem item = (NavigationMenuItem)element;
		if (item == null)
		{
			return false;
		}
		return (item.getChildren() != null && item.getChildren().size() > 0);
	}

}
