package com.github.marcus99661.ostukorv.Repository;

import java.util.List;

import com.github.marcus99661.ostukorv.Data.Kasutaja;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KasutajaRepository extends MongoRepository<Kasutaja, String> {

    public List<Kasutaja> findByFirstName(String firstName);

    public List<Kasutaja> findByEmail(String email);

}