/* interface to allow views to become container of DataConnectionInlineView view
 * and receive notifications from the embedded view
 */
package com.glenwood.kernai.ui.abstraction;

import com.glenwood.kernai.data.entity.DataConnection;

public interface IConnectionContainer {
	public void OnModelChanged(DataConnection dataConnection);

}
