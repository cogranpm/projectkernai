package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.ListDetailMapping;
import com.glenwood.kernai.data.entity.ListHeaderMapping;

public class ListHeaderMappingRepository extends BaseRepository<ListHeaderMapping> {

	private ListDetailMappingRepository listDetailMappingRepository;
	
	public ListHeaderMappingRepository(IPersistenceManager manager) {
		super(manager);
		this.listDetailMappingRepository = new ListDetailMappingRepository(manager);
	}
	
	@Override
	public void delete(ListHeaderMapping entity) {
		List<ListDetailMapping> listDetailItems = this.listDetailMappingRepository.getAllByListHeaderMapping(entity.getId());
		for(ListDetailMapping detail : listDetailItems)
		{
			listDetailMappingRepository.delete(detail);
		}
		super.delete(entity);
	}

}
