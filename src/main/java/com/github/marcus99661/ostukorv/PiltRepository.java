package com.github.marcus99661.ostukorv;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PiltRepository extends MongoRepository<Pilt, String> {

    public Pilt findByHash(String hash);

    public Pilt findByName(String name);

}
