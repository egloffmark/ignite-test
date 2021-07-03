package com.ontius.ignite.repo;

import java.io.Serializable;
import java.util.Map;

import org.apache.ignite.cache.query.annotations.QuerySqlFunction;

public class SQLFunctions {
	
   	@QuerySqlFunction()
   	public static double attr(int x) {
   		return 11.0;
   	}
}
