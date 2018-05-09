package com.glenwood.kernai.ui.viewmodel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.Script;
import com.glenwood.kernai.ui.abstraction.BaseViewModel;

public class ScriptViewModel  extends BaseViewModel<Script> {

	public ScriptViewModel()
	{
		super();
		this.engineLookup = new ArrayList<ListDetail>();
		document = new Document();
	}
	
	private List<ListDetail> engineLookup;

	public List<ListDetail> getEngineLookup() {
		return engineLookup;
	}

	public void setEngineLookup(List<ListDetail> engineLookup) {
		this.engineLookup = engineLookup;
	}
	
	private final IDocument document;
	public IDocument getDocument()
	{
		return document;
	}
	
}
