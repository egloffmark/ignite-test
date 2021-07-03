package com.ontius.ignite.app;

import java.util.Map;
import java.util.TreeMap;

import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.ontius.ignite.model.Item;
import com.ontius.ignite.repo.ItemIgniteRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages = { "com.ontius.ignite.config" })
@Slf4j
public class MyIgniteMapApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MyIgniteMapApplication.class, args);
	}

	@Autowired
    private ConfigurableApplicationContext applicationContext;

	@Autowired
	private ItemIgniteRepository itemRepo;

	@Override
	public void run(String... args) throws Exception {
		log.info("XXXXXXXXXXX  application started... XXXXXXXXXXX");

		long itemsCount = itemRepo.count();
		log.info("Number of Items: {}", itemsCount);
		if (itemsCount == 0) {
		
			// store some items
			Map<Long, Item> items = new TreeMap<Long,Item>();
			
			Item item = new Item("AIX-1","Advanced Xtra Item");
			item.setAttribute("Price",3.90);
			item.setAttribute("Color","red");
			items.put(item.getId(),item);
			
			item = new Item("AIX-2","Advanced Hyper Item");
			item.setAttribute("Price",9.90);
			item.setAttribute("Color","blue");
			items.put(item.getId(),item);
	
			item = new Item("AIX-3","Advanced Biggest Item");
			item.setAttribute("Price",8.95);
			item.setAttribute("Color","pink");
			items.put(item.getId(),item);
			log.info("storing items...");
			itemRepo.save(items);
			
		}
		
		// query items
		log.info("reading items...");
		Iterable<Item> items = itemRepo.findAll();
		for(Item item : items) {
			log.info(item.toString());
		}
		
		// find by code
		items = itemRepo.findByCode("AIX-3");
		for(Item item : items) {
			log.info(item.toString());
		}
		
		SqlFieldsQuery q = new SqlFieldsQuery("SELECT * FROM Item");
		log.info(itemRepo.cache().query(q).getFieldName(1));
		
		// delete item
		log.info("deleting items...");
		
		//items = itemRepo.findAll();
		for(Item item : items) {
			log.info("item: {}", item);
			itemRepo.deleteById(item.getId());
			//itemRepo.cache().remove(item.getId());
			//itemRepo.myDelete(item.getId());
			// ids.add(item.getId());
			//itemRepo.deleteItem(item);
		}
		//itemRepo.deleteAllById(ids);
		// log.info(itemRepo.cache().toString());
		// itemRepo.cache().clearAll(ids);
		//itemRepo.cache().remove(1L);
		// itemRepo.deleteAll();
		itemsCount = itemRepo.count();
		log.info("Number of Items: {}", itemsCount);
		log.info("waiting for shutdown...");
		// System.in.read();
		log.info("XXXXXXXXXXX  application finished... XXXXXXXXXXX");
		applicationContext.close();
	}

}
