package com.github.marcus99661.ostukorv.Repository;

import com.github.marcus99661.ostukorv.Tellimus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TellimuseRepository extends MongoRepository<Tellimus, String> {

}
