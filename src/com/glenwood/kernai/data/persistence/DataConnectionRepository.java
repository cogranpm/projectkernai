package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.entity.ListDetail;

public class DataConnectionRepository extends BaseRepository<DataConnection> {

	
	ListDetailRepository listDetailRepository;
	
	public DataConnectionRepository(IPersistenceManager manager) {
		super(manager);
		this.listDetailRepository = new ListDetailRepository(manager);
	}
	
	
	@Override
	public List<DataConnection> getAll(String type, Class<DataConnection> aClass) {
		List<DataConnection> list = super.getAll(type, aClass);
		for(DataConnection item : list)
		{
			item.setVendorNameLookup(this.listDetailRepository.get(item.getVendorName(), ListDetail.class));
		}
		return list;
	}
	
	@Override
	public DataConnection get(String id, Class<DataConnection> aClass) {
		DataConnection item = super.get(id, aClass);
		item.setVendorNameLookup(this.listDetailRepository.get(item.getVendorName(), ListDetail.class));
		return item;
	}
	
	@Override
	public void save(DataConnection entity) {
		if(entity.getVendorNameLookup() != null)
		{
			entity.setVendorName(entity.getVendorNameLookup().getId());
		}
		else
		{
			entity.setVendorName(null);
		}
		super.save(entity);
	}

}
