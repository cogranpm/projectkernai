package com.glenwood.kernai.ui.abstraction;

public interface IEntityView {
	public void delete();
	public void add();
	public void save();
	public void refreshView();
	public void afterAdd();
	public void afterSelection();

}
