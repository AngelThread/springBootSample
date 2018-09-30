package com.example.adClear.adClear.dao;

import com.example.adClear.adClear.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDAO extends CrudRepository<Customer, Long> {

     Optional<Customer> findById(long id);


}