package com.github.marcus99661.ostukorv;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ToodeRepository extends MongoRepository<Toode, String> {

        public List<Toode> findByName(String name);

        public List<Toode> findByKood(String kood);

        Toode deleteToodeByKood(String kood);
}
