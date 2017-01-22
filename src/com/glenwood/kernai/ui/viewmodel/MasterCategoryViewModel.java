package com.glenwood.kernai.ui.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import com.glenwood.kernai.data.entity.MasterCategory;
import com.glenwood.kernai.ui.ApplicationData;

public class MasterCategoryViewModel {

	private MasterCategory currentItem;
	private List<MasterCategory> items;
	private Boolean dirty;
	
	public MasterCategory getCurrentItem() {
		return currentItem;
	}
	public void setCurrentItem(MasterCategory currentItem) {
		MasterCategory oldItem = this.currentItem;
		this.currentItem = currentItem;
		this.firePropertyChange("currentItem", oldItem, this.currentItem);
	}
	public List<MasterCategory> getItems() {
		return items;
	}
	public void setItems(List<MasterCategory> items) {
		this.items = items;
		firePropertyChange("items", null, null);
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
	
	
}