package com.glenwood.kernai.ui.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;

public class ListDetailViewModel {
	
	private List<ListDetail> items;
	private ListDetail currentItem;
	private ListHeader listHeader;
	
	public List<ListDetail> getItems() {
		return items;
	}
	public void setItems(List<ListDetail> items) {
		this.items = items;
		firePropertyChange("items", null, null);
	}

	public ListDetail getCurrentItem() {
		return currentItem;
	}
	public void setCurrentItem(ListDetail currentItem) {
		ListDetail oldValue = this.currentItem;
		this.currentItem = currentItem;
		firePropertyChange("currentItem", oldValue, this.currentItem);
	}

	public ListHeader getListHeader()
	{
		return this.listHeader;
	}
	
	protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.removePropertyChangeListener(listener);
	}
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	public ListDetailViewModel(ListHeader listHeader)
	{
		this.listHeader = listHeader;
	}
	
	private ListDetailViewModel()
	{
		
	}

}
