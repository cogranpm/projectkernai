package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.SourceDocument;

public class SourceDocumentRepository extends BaseRepository<SourceDocument>  implements IEntityRepository<SourceDocument>  {

	public SourceDocumentRepository(IPersistenceManager manager) {
		super(manager);

	}

}
