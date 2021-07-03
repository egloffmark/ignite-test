package com.ontius.ignite.repo;

import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.Query;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;

import com.ontius.ignite.model.Item;


@RepositoryConfig(cacheName = "ItemCache")
public interface ItemIgniteRepository extends IgniteRepository<Item, Long>, CustomItemIngniteRepository {
	
	public Iterable<Item> findByCode(String code);
    
}
