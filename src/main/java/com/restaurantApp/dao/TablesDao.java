package com.restaurantApp.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.restaurantApp.model.Table;
@Repository
public interface TablesDao extends MongoRepository<Table,String>{
	List<Table> findByBookedIsFalse();	
}
