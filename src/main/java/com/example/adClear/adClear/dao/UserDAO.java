package com.example.adClear.adClear.dao;

import com.example.adClear.adClear.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends CrudRepository<User, String> {

}