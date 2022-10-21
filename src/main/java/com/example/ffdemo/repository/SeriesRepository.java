package com.example.ffdemo.repository;

import com.example.ffdemo.model.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends PagingAndSortingRepository<Series, String> {
    Collection<Series> getSeriesByUserId(String userId);
    Series findByName(String name);
    Optional<Series> findById(String id);
//    Page<Series> getAll(Pageable pageable);
    Page<Series> getAllByNameContainsIgnoreCase(String title, Pageable pageable);

}
