package com.glenwood.kernai.ui.abstraction;

import org.eclipse.jface.viewers.Viewer;

public interface IListSortComparator {
	public void setColumn(int column);
	public int getDirection();

}
