package com.playacademy.user.model;

import org.springframework.data.repository.CrudRepository;

public interface UserBaseRepo <T extends User> extends CrudRepository<T,Long>{
	
	public T findByEmail(String email);
	public T findByEmailAndPassword(String email,String password);
}
