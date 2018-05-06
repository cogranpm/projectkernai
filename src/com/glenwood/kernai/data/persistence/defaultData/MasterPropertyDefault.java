package com.glenwood.kernai.data.persistence.defaultData;

import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListDetailMapping;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.entity.ListHeaderMapping;
import com.glenwood.kernai.data.persistence.ListDetailMappingRepository;
import com.glenwood.kernai.data.persistence.ListDetailRepository;
import com.glenwood.kernai.data.persistence.ListHeaderMappingRepository;
import com.glenwood.kernai.data.persistence.ListHeaderRepository;
import com.glenwood.kernai.ui.ApplicationData;

public final class MasterPropertyDefault {
	
	ListHeaderRepository listHeaderRepository;
	ListDetailRepository listDetailRepository;
	ListHeaderMappingRepository listHeaderMappingRepository;
	ListDetailMappingRepository listDetailMappingRepository;
	
	public MasterPropertyDefault()
	{
		this.listHeaderRepository = new ListHeaderRepository(ApplicationData.instance().getPersistenceManager());
		this.listDetailRepository = new ListDetailRepository(ApplicationData.instance().getPersistenceManager());
		this.listHeaderMappingRepository = new ListHeaderMappingRepository(ApplicationData.instance().getPersistenceManager());
		this.listDetailMappingRepository = new ListDetailMappingRepository(ApplicationData.instance().getPersistenceManager());
	}
	
	public void createLookupData()
	{
		// data types
		List<ListDetail> details = new ArrayList<ListDetail>();
		ListDetail item = new ListDetail();
		item.setKey("String");
		item.setLabel("String");
		details.add(item);
		details.add(new ListDetail("Boolean", "Boolean"));
		details.add(new ListDetail("Short", "Short"));
		details.add(new ListDetail("Int", "Int"));
		details.add(new ListDetail("Long", "Long"));
		details.add(new ListDetail("Float", "Float"));
		details.add(new ListDetail("Double", "Double"));
		details.add(new ListDetail("BigDecimal", "Big Decimal"));
		details.add(new ListDetail("Date", "Date"));
		details.add(new ListDetail("Time", "Time"));
		details.add(new ListDetail("Timestamp", "Timestamp"));
		details.add(new ListDetail("Byte", "Byte"));
		createLookupRecord(ApplicationData.LIST_DATATYPE_NAME, details);
		
		// sql types
		details.clear();
		details.add(new ListDetail("2003", "Array"));
		details.add(new ListDetail("-5", "Big Int"));
		details.add(new ListDetail("-2", "Binary"));
		details.add(new ListDetail("-7", "Bit"));
		details.add(new ListDetail("2004", "Blob"));
		details.add(new ListDetail("16", "Boolean"));
		details.add(new ListDetail("1", "Char"));
		details.add(new ListDetail("2005", "Clob"));
		details.add(new ListDetail("70", "Data Link"));
		details.add(new ListDetail("91", "Date"));
		details.add(new ListDetail("3", "Decimal"));
		details.add(new ListDetail("2001", "Distinct"));
		details.add(new ListDetail("8", "Double"));
		details.add(new ListDetail("6", "Float"));
		details.add(new ListDetail("4", "Integer"));
		details.add(new ListDetail("2000", "Java Object"));
		details.add(new ListDetail("-16", "Long NVarchar"));
		details.add(new ListDetail("-4", "Long Varbinary"));
		details.add(new ListDetail("-1", "Long Varchar"));
		details.add(new ListDetail("-15", "Nchar"));
		details.add(new ListDetail("2011", "Nclob"));
		details.add(new ListDetail("0", "Null"));
		details.add(new ListDetail("2", "Numeric"));
		details.add(new ListDetail("-9", "Nvarchar"));
		details.add(new ListDetail("1111", "Other"));
		details.add(new ListDetail("7", "Real"));
		details.add(new ListDetail("2006", "Ref"));
		details.add(new ListDetail("-8", "Row Id"));
		details.add(new ListDetail("5", "Small Int"));
		details.add(new ListDetail("2009", "SQL XML"));
		details.add(new ListDetail("2002", "Struct"));
		details.add(new ListDetail("92", "Time"));
		details.add(new ListDetail("93", "Timestamp"));
		details.add(new ListDetail("-6", "Tiny Int"));
		details.add(new ListDetail("-3", "Varbinary"));
		details.add(new ListDetail("12", "Varchar"));
		createLookupRecord(ApplicationData.LIST_SQL_DATATYPE_NAME, details);
		
		// association types
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

		
		//template engines 
		details.clear();
		item = new ListDetail();
		item.setKey("Fizzed");
		item.setLabel("Fizzed Rocker");
		details.add(item);
		createLookupRecord(ApplicationData.LIST_TEMPLATE_ENGINE_NAME, details);
		
	}
	
	public void createListMappings()
	{
		//need to pass in the data type lists, sql list mapped to application data type list
		ListHeaderMapping item = new ListHeaderMapping();
		//item.setFromHeaderId(fromHeaderId);
	}
	
	private void createLookupRecord(String headerName, List<ListDetail> detailList)
	{
		ListHeader listHeaderData = listHeaderRepository.getByName(headerName);
		if(listHeaderData == null)
		{
			listHeaderData = new ListHeader(headerName, true);
			this.listHeaderRepository.save(listHeaderData);
			
			for(ListDetail item : detailList)
			{
				item.setListHeaderId(listHeaderData.getId());
				this.listDetailRepository.save(item);
			}
		}
	}
	
	private void createListMappingRecord(ListHeaderMapping headerMapping, List<ListDetailMapping> detailMappings)
	{
		//make sure does not already exist
		List<ListHeaderMapping> headers = this.listHeaderMappingRepository.getAll(ListHeaderMapping.TYPE_NAME, ListHeaderMapping.class);
		ListHeaderMapping existingItem = null;
		for(ListHeaderMapping item : headers)
		{
			if(item.getFromHeaderId().equalsIgnoreCase(headerMapping.getFromHeaderId())
				&& item.getToHeaderId().equalsIgnoreCase(headerMapping.getToHeaderId()))
			{
				existingItem = item;
				break;
			}
			
		}
		if(existingItem == null)
		{
			this.listHeaderMappingRepository.save(headerMapping);
			existingItem = headerMapping;
		}
		
		for(ListDetailMapping item : detailMappings)
		{
			this.listDetailMappingRepository.save(item);
		}
		
	}
	
	private Boolean compareListDetailMappings(ListDetailMapping a, ListDetailMapping b)
	{
		if(a.getId() == null || b.getId() == null)
		{
			return (a.getListHeaderMappingId() == b.getListHeaderMappingId() 
				&& a.getFromDetailId() == b.getFromDetailId()
				&& a.getToDetailId() == b.getToDetailId());
		}
		else
		{
			return a.getId() == b.getId();
		}
	}
	

}
