package com.glenwood.kernai.ui.view.helpers;

import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

public class ListSorterHelper extends ViewerComparator {
	
	protected static final int DESCENDING = 1;
	protected int direction = DESCENDING;
	protected int propertyIndex;
	
	public ListSorterHelper() {
		super();
		this.propertyIndex = 0;
		this.direction = DESCENDING;
	}
	
   public void setColumn(int column) {
       if (column == this.propertyIndex) {
               // Same column as last sort; toggle the direction
               direction = 1 - direction;
       } else {
               // New column; do an ascending sort
               this.propertyIndex = column;
               direction = SWT.DOWN;
       }
   }
	
	public int getDirection()
	{
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

}
