package com.box.config.db;

import javax.annotation.Nullable;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyRoutingDataSource extends AbstractRoutingDataSource {
	@Nullable
	@Override
	protected Object determineCurrentLookupKey() {
		return DBContextHolder.get();
	}

}