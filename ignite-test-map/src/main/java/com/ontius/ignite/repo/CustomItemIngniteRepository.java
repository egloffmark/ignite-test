package com.ontius.ignite.repo;

import java.util.List;

import com.ontius.ignite.model.Item;

public interface CustomItemIngniteRepository {

	public void deleteItem(Item item);
	
	public List<List<?>> getItemsWithHigherPrice(double price);
}
