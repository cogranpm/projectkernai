package com.glenwood.kernai.ui.view.navigation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.MainWindow;
import com.glenwood.kernai.ui.viewmodel.NavigationMenuItem;

public class NavigationMenuLabelProvider extends LabelProvider {
	
	@Override
	public Image getImage(Object element) {
		NavigationMenuItem item = (NavigationMenuItem)element;
		if (item != null && item.getImageKey() != null)
		{
			return ApplicationData.smallIcons.get(item.getImageKey());
		}
		return super.getImage(element);
	}

	
	
	@Override
	public String getText(Object element) {
		NavigationMenuItem item = (NavigationMenuItem)element;
		if (item != null)
		{
			return item.getLabel();
		}
		return super.getText(element);
	}
	
	
	
}
