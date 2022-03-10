package com.juicy.sprout.repo;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface LogRepo extends CrudRepository<Log, Long> {
    List<Log> findByDate(Date date);

    Log findById(long id);
}
