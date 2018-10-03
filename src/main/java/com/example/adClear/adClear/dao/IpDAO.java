package com.example.adClear.adClear.dao;

import com.example.adClear.adClear.entity.Ip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpDAO extends CrudRepository<Ip, String> {

    @Query(nativeQuery = true, value = "select * from ip_blacklist h where h.ip =:ip")
    Optional<Ip> findByIp(@Param("ip") String ip );
}