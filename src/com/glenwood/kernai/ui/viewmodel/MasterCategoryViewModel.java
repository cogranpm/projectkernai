package com.glenwood.kernai.ui.viewmodel;

import java.util.List;

import com.glenwood.kernai.data.entity.MasterCategory;

public class MasterCategoryViewModel {

	private MasterCategory currentItem;
	private List<MasterCategory> items;
	
	public MasterCategory getCurrentItem() {
		return currentItem;
	}
	public void setCurrentItem(MasterCategory currentItem) {
		this.currentItem = currentItem;
	}
	public List<MasterCategory> getItems() {
		return items;
	}
	public void setItems(List<MasterCategory> items) {
		this.items = items;
	}
	
	
}
