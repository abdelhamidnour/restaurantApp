package com.restaurantApp.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.restaurantApp.model.User;

@Repository
public interface UserDao extends MongoRepository<User,String>{
	Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

}
