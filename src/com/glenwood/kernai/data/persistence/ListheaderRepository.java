package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;

public class ListheaderRepository extends BaseRepository<ListHeader> implements IEntityRepository<ListHeader> {

	private ListDetailRepository listDetailRepository;
	
	public ListheaderRepository(IPersistenceManager manager) {
		super(manager);
		this.listDetailRepository = new ListDetailRepository(manager);
	}
	
	@Override
	public void delete(ListHeader entity) {
		/* delete all associated children when header is deleted
		 * todo - should change this to a batch delete aka delete from where */
		List<ListDetail> listDetailItems = this.listDetailRepository.getAllByListHeader(entity.getId());
		for(ListDetail detail : listDetailItems)
		{
			listDetailRepository.delete(detail);
		}
		
		super.delete(entity);
		

	}

}
