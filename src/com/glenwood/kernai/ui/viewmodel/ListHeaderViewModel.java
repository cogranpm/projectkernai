package com.glenwood.kernai.ui.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;

public class ListHeaderViewModel {
	
	private List<ListHeader> items;
	private ListHeader currentItem;
	private Boolean dirty;
	private List<ListDetail> childItems;
	
	public List<ListHeader> getItems() {
		return items;
	}
	public void setItems(List<ListHeader> items) {
		this.items = items;
		firePropertyChange("items", null, null);
	}
	public ListHeader getCurrentItem() {
		return currentItem;
	}
	public void setCurrentItem(ListHeader currentItem) {
		ListHeader oldValue = this.currentItem;
		this.currentItem = currentItem;
		firePropertyChange("currentItem", oldValue, this.currentItem);
	}
	
	public Boolean getDirty()
	{
		return this.dirty;
	}
	
	public void setDirty(Boolean dirty)
	{
		Boolean oldValue = this.dirty;
		this.dirty = dirty;
		firePropertyChange("dirty", oldValue, this.dirty);
		//ApplicationData.instance().getAction("Save").setEnabled(!this.dirty);
	}
	
	
	
	
	
	public List<ListDetail> getChildItems() {
		return childItems;
	}
	public void setChildItems(List<ListDetail> childItems) {
		this.childItems = childItems;
		firePropertyChange("childItems", null, null);
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
	
	public ListHeaderViewModel()
	{
		
	}

}
