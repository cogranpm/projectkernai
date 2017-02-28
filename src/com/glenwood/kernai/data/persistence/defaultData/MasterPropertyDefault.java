package com.glenwood.kernai.data.persistence.defaultData;

import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.persistence.ListDetailRepository;
import com.glenwood.kernai.data.persistence.ListHeaderRepository;
import com.glenwood.kernai.ui.ApplicationData;

public final class MasterPropertyDefault {
	
	ListHeaderRepository listHeaderRepository;
	ListDetailRepository listDetailRepository;
	
	public MasterPropertyDefault()
	{
		this.listHeaderRepository = new ListHeaderRepository(ApplicationData.instance().getPersistenceManager());
		this.listDetailRepository = new ListDetailRepository(ApplicationData.instance().getPersistenceManager());
	}
	
	public void createLookupData()
	{
		List<ListDetail> details = new ArrayList<ListDetail>();
		ListDetail aDataType = new ListDetail();
		aDataType.setKey("String");
		aDataType.setLabel("String");
		details.add(aDataType);
		createLookupRecord(ApplicationData.LIST_DATATYPE_NAME, details);
		
		details.clear();
		aDataType = new ListDetail();
		aDataType.setKey("ManyToOne");
		aDataType.setLabel("Many To One");
		details.add(aDataType);
		aDataType = new ListDetail();
		aDataType.setKey("OneToOne");
		aDataType.setLabel("One To One");
		details.add(aDataType);
		aDataType = new ListDetail();
		aDataType.setKey("ManyToMany");
		aDataType.setLabel("Many To Many");
		details.add(aDataType);
		aDataType = new ListDetail();
		aDataType.setKey("Lookup");
		aDataType.setLabel("Lookup");
		details.add(aDataType);
		createLookupRecord(ApplicationData.LIST_ASSOCIATION_TYPE_NAME, details);
		
		
	}
	
	private void createLookupRecord(String headerName, List<ListDetail> detailList)
	{
		ListHeader listHeaderData = listHeaderRepository.getByName(headerName);
		if(listHeaderData == null)
		{
			listHeaderData = new ListHeader();
			listHeaderData.setName(headerName);
			this.listHeaderRepository.save(listHeaderData);
			
			for(ListDetail item : detailList)
			{
				item.setListHeaderId(listHeaderData.getId());
				this.listDetailRepository.save(item);
			}
		}
	}
	

}
