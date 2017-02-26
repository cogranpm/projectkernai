package com.glenwood.kernai.data.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.persistence.views.MasterPropertyViewBuilder;

public class ListHeaderRepository extends BaseRepository<ListHeader> implements IEntityRepository<ListHeader> {

	private ListDetailRepository listDetailRepository;
	
	public ListHeaderRepository(IPersistenceManager manager) {
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
	
	public List<ListDetail> getListItemsByName(String name)
	{
		List<ListDetail> detailList = new ArrayList<ListDetail>();
		ListHeader header = this.getByName(name);
		if(header != null)
		{
			detailList = this.listDetailRepository.getAllByListHeader(header.getId());
		}
		return detailList;
	}
	
	public ListHeader getByName(String name)
	{
		ListHeader header = null;
		Query aquery = this.getManager().getDatabase().getView(MasterPropertyViewBuilder.QUERY_LISTHEADER_BY_NAME).createQuery();
		List<Object> keys = new ArrayList<Object>();
        keys.add(name);
		aquery.setKeys(keys);
		QueryEnumerator result = null;
		try {
			result = aquery.run();
		} catch (CouchbaseLiteException e) {
			e.printStackTrace();
		}
		for(Iterator<QueryRow> it = result; it.hasNext();)
		{
			QueryRow row = it.next();
			header = this.getManager().getEntityMapper().toEntity(row.getDocument(), ListHeader.class);
			break;
		}
		return header;

	}

}
