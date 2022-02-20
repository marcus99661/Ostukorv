package com.github.marcus99661.ostukorv.Repository;

import com.github.marcus99661.ostukorv.Data.Tellimus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TellimuseRepository extends MongoRepository<Tellimus, String> {

    public Tellimus findByKood(String kood);

    public List<Tellimus> findByDone(boolean done);

}
