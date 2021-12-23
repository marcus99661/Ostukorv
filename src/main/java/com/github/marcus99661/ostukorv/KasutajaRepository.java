package com.github.marcus99661.ostukorv;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface KasutajaRepository extends MongoRepository<Kasutaja, String> {

    public List<Kasutaja> findByName(String name);

}