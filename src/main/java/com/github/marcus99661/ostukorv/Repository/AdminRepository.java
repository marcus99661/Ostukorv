package com.github.marcus99661.ostukorv.Repository;

import com.github.marcus99661.ostukorv.Data.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdminRepository extends MongoRepository<Admin, String> {

    public List<Admin> findByName(String name);
}
