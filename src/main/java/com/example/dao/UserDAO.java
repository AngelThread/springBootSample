package com.example.dao;

import com.example.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends CrudRepository<User, String> {

    @Query(nativeQuery = true, value = "select * from ua_blacklist h where h.ua =:userAgent")
    Optional<User> findUserAgent(@Param("userAgent") String userAgent );
}