package com.ontius.ignite.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QuerySqlFunction;

import lombok.Data;

@Data
public class Item {
	
	private static final AtomicLong ID_GEN = new AtomicLong();

	@QuerySqlField(index = true)
    private Long id;
	
    @QuerySqlField
    public String name;

    @QuerySqlField
    public String code;
    
    @QuerySqlField
    public Map<String,Serializable> attributes = new HashMap<String,Serializable>();

   	public Item(Long id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}
    
   	public Item(String code, String name) {
		this(ID_GEN.incrementAndGet(),code, name);
	}
   	
   	public void setAttribute(String name,Serializable value) {
   		attributes.put(name, value);
   	}

   	public Serializable getAttribute(String name) {
   		return attributes.get(name);
   	}
}
