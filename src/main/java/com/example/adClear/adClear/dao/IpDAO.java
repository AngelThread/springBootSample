package com.example.adClear.adClear.dao;

import com.example.adClear.adClear.entity.Ip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpDAO extends CrudRepository<Ip, String> {

}