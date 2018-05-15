package com.glenwood.kernai.ui.view;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.opal.roundedtoolbar.RoundedToolItem;
import org.eclipse.nebula.widgets.opal.roundedtoolbar.RoundedToolbar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.glenwood.kernai.ui.ApplicationData;

public class EntityContainerView extends Composite{

	public EntityContainerView(Composite parent, int style) {
		super(parent, style);
		this.init();
	}
	
	private void init()
	{
		GridLayout layout = new GridLayout(1, false);
		final Composite toolbarContainer = new Composite(this, SWT.NONE);
		toolbarContainer.setLayout(layout);
		/* rounded-> radio style images not working, nor is hide selection setting */
		/* add the toolbar */
		final RoundedToolbar toolbar = new RoundedToolbar(toolbarContainer, SWT.HIDE_SELECTION);
		toolbar.setCornerRadius(8);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(toolbar);
		
		//toolbar.setBackground(grey1);
		final RoundedToolItem home = new RoundedToolItem(toolbar, SWT.RADIO);
		home.setWidth(85);
		//home.setText("Home");
		home.setSelection(true);
		home.setSelectionImage(ApplicationData.instance().getImageRegistry().get(ApplicationData.IMAGE_GO_HOME));
		home.setImage(ApplicationData.instance().getImageRegistry().get(ApplicationData.IMAGE_GO_HOME));
		
		final RoundedToolItem entityItem = new RoundedToolItem(toolbar, SWT.RADIO);
		//mailItem.setSelectionImage(emailw);
		//mailItem.setImage(emailb);
		entityItem.setWidth(100);
		entityItem.setText("Attributes");
		entityItem.setTextColorSelected(this.getDisplay().getSystemColor(SWT.COLOR_GREEN));
		//modelItem.setDisabledImage(ApplicationData.instance().getImageRegistry().get(ApplicationData.IMAGE_ADD_SMALL));
		//modelItem.setEnabled(false);
		//modelItem.setTextColor(this.getDisplay().getSystemColor(SWT.COLOR_GRAY));

		
		
		Composite bottomContainer = new Composite(this, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(bottomContainer);
		
		EntityView modelView = new EntityView(bottomContainer, SWT.NONE, ApplicationData.instance().getCurrentModel());
		ApplicationData.instance().setCurrentEntityView(modelView);

		bottomContainer.setLayout(new FillLayout());
		bottomContainer.layout();
		this.setLayout(new GridLayout(1, true));
		

		home.addListener( SWT.Selection, event -> {
			for(Control control : bottomContainer.getChildren())
			{
				control.dispose();
			}

			if(home.getSelection())
			{
				ApplicationData.instance().setCurrentEntityView(new EntityView(bottomContainer, SWT.NONE, ApplicationData.instance().getCurrentModel()));
			}
			else if(entityItem.getSelection())
			{
				AttributeView entityView = new AttributeView(bottomContainer, SWT.NONE, ApplicationData.instance().getCurrentEntity());
				ApplicationData.instance().setCurrentEntityView(entityView);
			}

			bottomContainer.layout();
		});
	}

}
