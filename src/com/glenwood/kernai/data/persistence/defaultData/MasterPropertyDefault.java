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
		ListDetail item = new ListDetail();
		item.setKey("String");
		item.setLabel("String");
		details.add(item);
		createLookupRecord(ApplicationData.LIST_DATATYPE_NAME, details);
		
		details.clear();
		item = new ListDetail();
		item.setKey("ManyToOne");
		item.setLabel("Many To One");
		details.add(item);
		item = new ListDetail();
		item.setKey("OneToOne");
		item.setLabel("One To One");
		details.add(item);
		item = new ListDetail();
		item.setKey("ManyToMany");
		item.setLabel("Many To Many");
		details.add(item);
		item = new ListDetail();
		item.setKey("Lookup");
		item.setLabel("Lookup");
		details.add(item);
		createLookupRecord(ApplicationData.LIST_ASSOCIATION_TYPE_NAME, details);
		
		// db vendors
		details.clear();
		item = new ListDetail();
		item.setKey("oracle");
		item.setLabel("Oracle");
		details.add(item);

		item = new ListDetail();
		item.setKey("mssql");
		item.setLabel("SQL Server");
		details.add(item);

		item = new ListDetail();
		item.setKey("mysql");
		item.setLabel("MYSQL");
		details.add(item);

		createLookupRecord(ApplicationData.LIST_DATABASE_VENDOR_NAME, details);
		
		
		
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
