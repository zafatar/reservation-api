package com.zafatar.reservation.api.v1.repository;

import java.util.Map;

import com.zafatar.reservation.api.v1.exceptions.TableNotFoundException;
import com.zafatar.reservation.api.v1.model.Table;

public interface TableRepositoryInterface {
	void save(Table Table);
	void update(Table Table); 

	Table findById(int id) throws TableNotFoundException;	
	
	Map<String, Table> findAll();
	void delete(Table Table);	
	void deleteAll();
	
	int getNextId();
	void resetId();

	long count();
}
