package com.example.dao;

import com.example.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDAO extends CrudRepository<Customer, Long> {

}