package com.glenwood.kernai.data.modelimport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.abstractions.IImportEngine;

import schemacrawler.schema.Catalog;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.ExcludeAll;
import schemacrawler.schemacrawler.IncludeAll;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.utility.SchemaCrawlerUtility;

public class ImportEngineSchemaCrawler implements IImportEngine {
	
	IConnection connection;
	SchemaCrawlerOptions options;

	@Override
	public void init(IConnection connection) {
		this.connection = connection;
		this.options = new SchemaCrawlerOptions();
		options.setSchemaInfoLevel(SchemaInfoLevelBuilder.standard());
		options.setTableInclusionRule(new IncludeAll());
		options.setSchemaInclusionRule(new IncludeAll());
		options.setRoutineColumnInclusionRule(new ExcludeAll());
		
	}

	@Override
	public List<DatabaseDefinition> getDatabases() {
		try {
			final Catalog catalog = SchemaCrawlerUtility.getCatalog(this.connection.getConnection(), this.options);
			Collection<Schema> schemas = catalog.getSchemas();
			for(Schema schema : schemas)
			{
				System.out.println("Schema: " + schema.getFullName());
				
				Collection<Table> tables = catalog.getTables(schema);
				for(Table table : tables)
				{
					System.out.println("Table: " + table.getName());
				}
			}

			

			System.out.print(catalog.getFullName());
		} catch (SchemaCrawlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<DatabaseDefinition>();
	}

	@Override
	public List<TableDefinition> getTables(DatabaseDefinition database) {
		return null;
	}

	@Override
	public List<ColumnDefinition> getColumns(DatabaseDefinition database, TableDefinition table) {
		return null;
	}

}
