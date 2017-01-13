package com.glenwood.kernai.ui.viewmodel;

import java.util.List;

import com.glenwood.kernai.data.entity.ListHeader;

public class ListHeaderViewModel {
	
	private List<ListHeader> items;
	private ListHeader currentItem;
	public List<ListHeader> getItems() {
		return items;
	}
	public void setItems(List<ListHeader> items) {
		this.items = items;
	}
	public ListHeader getCurrentItem() {
		return currentItem;
	}
	public void setCurrentItem(ListHeader currentItem) {
		this.currentItem = currentItem;
	}
	
	

}
