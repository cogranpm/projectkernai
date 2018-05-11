package com.glenwood.kernai.ui.view;

import java.util.List;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.text.AbstractDocument;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.Template;
import com.glenwood.kernai.ui.abstraction.BaseEntityView;
import com.glenwood.kernai.ui.presenter.TemplateViewPresenter;
import com.glenwood.kernai.ui.view.helpers.ListSorterHelper;
import com.glenwood.kernai.ui.view.helpers.TemplateSourceConfiguration;
import com.glenwood.kernai.ui.viewmodel.TemplateViewModel;


public class TemplateView extends BaseEntityView<Template> {

	private Label lblName;
	private Text txtName;
	
	private Label lblEngine;
	private ComboViewer cboEngine;

	private SourceViewer txtBody;
	private Label lblBody;

	TemplateViewModel aModel;
	
	public TemplateView(Composite parent, int style) {
		super(parent, style);

	}

	
	@Override
	protected void setupModelAndPresenter() {
		this.model = new TemplateViewModel();
		this.presenter = new TemplateViewPresenter(this, (TemplateViewModel)this.model);
		aModel = (TemplateViewModel)this.model;
	}
	
	
	@Override
	protected void setupListColumns()
	{
		this.listViewer.setComparator(new ViewerComparator());
//		TableViewerColumn nameColumn = new TableViewerColumn(listViewer, SWT.LEFT);
//		nameColumn.getColumn().setText("Name");
//		nameColumn.getColumn().setResizable(false);
//		nameColumn.getColumn().setMoveable(false);
		
		TableViewerColumn nameColumn = this.viewHelper.getListColumn(listViewer, "Name");
		TableViewerColumn engineColumn = this.viewHelper.getListColumn(listViewer, "Engine");

		nameColumn.setEditingSupport(new EditingSupport(this.listViewer) {
			
			@Override
			protected void setValue(Object element, Object value) {
				((Template)element).setName(String.valueOf(value));
				listViewer.update(element, null);
			}
			
			@Override
			protected Object getValue(Object element) {
				return ((Template)element).getName();
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(listViewer.getTable());
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
		
		
		engineColumn.setEditingSupport(new EditingSupport(this.listViewer) {
			
			@Override
			protected void setValue(Object element, Object value) {

				if (element == null)
				{
					return;
				}
				if (value == null)
				{
					return;
				}
				Template template = (Template)element;
				ListDetail listDetail = (ListDetail)value;
				template.setEngineLookup(listDetail);
				listViewer.update(element, null);
			}
			
			@Override
			protected Object getValue(Object element) {
				Template template = (Template)element;
				return template.getEngineLookup();
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				//return new TextCellEditor(listViewer.getTable());
				ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(listViewer.getTable(), SWT.READ_ONLY);
				IStructuredContentProvider contentProvider = new ArrayContentProvider();
				editor.setContentProvider( contentProvider );
				editor.setLabelProvider(new LabelProvider(){
					@Override
					public String getText(Object element)
					{
						ListDetail item = (ListDetail)element;
						return item.getLabel();

					}
				});
				TemplateViewModel aModel = (TemplateViewModel)model;
				editor.setInput(aModel.getEngineLookup());
				return editor;
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});

	
		
		nameColumn.getColumn().addSelectionListener(this.getSelectionAdapter(nameColumn.getColumn(), 0));
		TableColumnLayout tableLayout = new TableColumnLayout();
		listContainer.setLayout(tableLayout);
		tableLayout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
		tableLayout.setColumnData(engineColumn.getColumn(), new ColumnWeightData(100));
		
	}
	
	@Override
	protected void onSetupEditingContainer()
	{

		lblName = new Label(editMaster, SWT.NONE);
		lblName.setText("Name");
		txtName = viewHelper.getTextEditor(editMaster);
		viewHelper.layoutEditLabel(lblName);
		viewHelper.layoutEditEditor(txtName);

		lblEngine= new Label(editMaster, SWT.NONE);
		lblEngine.setText("Engine");
		cboEngine = new ComboViewer(editMaster);
		cboEngine.setContentProvider(ArrayContentProvider.getInstance());
		cboEngine.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element)
			{
				ListDetail item = (ListDetail)element;
				return item.getLabel();
			}
		});
		
		cboEngine.setInput(aModel.getEngineLookup());
		viewHelper.layoutEditLabel(lblEngine);
		viewHelper.layoutComboViewer(cboEngine);
		
		lblBody = new Label(editMaster, SWT.NONE);
		lblBody.setText("Template Body");
		
		Composite bodyComposite = new Composite(editDetail, SWT.NONE);
		//GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(bodyComposite);
		bodyComposite.setLayout(new FillLayout());
		
		
		int VERTICAL_RULER_WIDTH = 12;
		IOverviewRuler overviewRuler = new OverviewRuler(null, VERTICAL_RULER_WIDTH, null);
		CompositeRuler ruler = new CompositeRuler(VERTICAL_RULER_WIDTH);

		txtBody = new SourceViewer(bodyComposite, ruler, overviewRuler, false, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		viewHelper.layoutEditLabel(lblBody);
		
		AnnotationModel annotationModel = new AnnotationModel();
		annotationModel.connect(aModel.getDocument());
		txtBody.configure(new TemplateSourceConfiguration());
		txtBody.setDocument(aModel.getDocument(), annotationModel);
		ruler.addDecorator(0, new LineNumberRulerColumn());
		
	}



	@Override
	public void onAdd() {
		this.txtName.setFocus();
	}
	
	@Override
	protected void onListSelectionChangedHandler(Template entity) {
		//aModel.getDocument().set(model.getCurrentItem().getBody());
	}

	@Override
	protected void onAfterSelection() {
		// TODO Auto-generated method stub
		super.onAfterSelection();
		//aModel.getDocument().set(model.getCurrentItem().getBody());
	}

	protected void onInitDataBindings() {

        IObservableSet<Template> knownElements = contentProvider.getKnownElements();
        final IObservableMap names = BeanProperties.value(Template.class, "name").observeDetail(knownElements);
        final IObservableMap engines = BeanProperties.value(Template.class, "engine").observeDetail(knownElements);
        
        IObservableMap[] labelMaps = {names, engines};
        ILabelProvider labelProvider = new ObservableMapLabelProvider(labelMaps) {
                @Override
                public String getColumnText(Object element, int columnIndex) {
                	Template mc = (Template)element;
                	switch(columnIndex)
                	{
                	case 0:
                    	return mc.getName();
                	case 1:
                		if(mc.getEngineLookup() != null)
                		{
                			return mc.getEngineLookup().getLabel();
                		}
                		else
                		{
                			return null;
                		}

                    default:
                    	return "";
                	}
                }
        };
        
        listViewer.setLabelProvider(labelProvider);
        List<Template> el = model.getItems();
        input = new WritableList(el, Template.class);
        listViewer.setInput(input);
        
        IObservableValue nameTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtName);
        IObservableValue nameModelObservable = BeanProperties.value("name").observeDetail(value);
		
        IObservableValue engineTargetObservable = ViewerProperties.singleSelection().observe(cboEngine);
        IObservableValue engineModelObservable = BeanProperties.value("engineLookup").observeDetail(value);
        
        IObservableValue bodyTargetObservable = WidgetProperties.text(SWT.Modify).observe(txtBody.getTextWidget());
        IObservableValue bodyModelObservable = BeanProperties.value("body").observeDetail(value);

        /* just the validators and decorators in the name field */
        IValidator nameValidator = new IValidator() {
            @Override
            public IStatus validate(Object value) {
                String nameValue = String.valueOf(value).replaceAll("\\s", "");
               // String namveValue = String.valueOf(value).trim();
                if (nameValue.length() > 0){
                  return ValidationStatus.ok();
                }
                return ValidationStatus.error("Name must be entered");
            }
            
          };
        UpdateValueStrategy nameUpdateStrategy = new UpdateValueStrategy();
        nameUpdateStrategy.setAfterConvertValidator(nameValidator);
        Binding nameBinding = ctx.bindValue(nameTargetObservable, nameModelObservable, nameUpdateStrategy, null);
        Binding dataTypeBinding = ctx.bindValue(engineTargetObservable, engineModelObservable);
        
        aModel.getDocument().addDocumentListener(new IDocumentListener() {
			
			@Override
			public void documentChanged(DocumentEvent event) {
				presenter.modelChanged();
				
			}
			
			@Override
			public void documentAboutToBeChanged(DocumentEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
        
        ControlDecorationSupport.create(nameBinding, SWT.TOP | SWT.LEFT);
        final IObservableValue errorObservable = WidgetProperties.text().observe(errorLabel);
        allValidationBinding = ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null);
        IObservableList bindings = ctx.getValidationStatusProviders();
	}
	
	private class ViewerComparator extends ListSorterHelper
	{

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 == null || e2 == null)
			{
				return 0;
			}
			Template p1 = (Template)e1;
			Template p2 = (Template)e2;
			int rc = 0;
			switch(this.propertyIndex)
			{
			case 0:
				rc = this.compareName(p1, p2);
				break;
			default:
				rc = 0;
			}
			
			if (this.direction == DESCENDING)
			{
				rc = -rc;
			}
			return rc;
		}
		
		private int compareName(Template p1, Template p2)
		{
			if (p1 == null || p2 == null)
			{
				return 0;
			}
			String nameOne = "";
			String nameTwo = "";
			nameOne = p1.getName() == null ? "" : p1.getName();
			nameTwo = p2.getName() == null ? "" : p2.getName();
			return nameOne.compareTo(nameTwo);
		}
	}
	
}
