package com.ontius.ignite.repo;

import java.util.List;

import org.apache.ignite.Ignite;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.springframework.beans.factory.annotation.Autowired;

import com.ontius.ignite.model.Item;

public class CustomItemIngniteRepositoryImpl implements CustomItemIngniteRepository {
	
	@Autowired
	private Ignite ignite;
	
	public void deleteItem(Item item) {
		ignite.getOrCreateCache("ItemCache").clear(item.getId());
	}
	
	public List<List<?>> getItemsWithHigherPrice(double price) {
		// Preparing the query that uses the custom defined 'sqr' function.
		SqlFieldsQuery query = new SqlFieldsQuery("SELECT * FROM Item WHERE attr(22) > 0");

		// Executing the query.
		return ignite.getOrCreateCache("ItemCache").query(query).getAll();
	}
}
