package com.example.ffdemo.repository;

import com.example.ffdemo.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends PagingAndSortingRepository<Article, String> {
    public List<Article> findByUserId(String userId);
    public List<Article> findByTitleContaining(String title);
    public Optional<Article> findById(String id);

    Page<Article> findByType(String type, Pageable pageable);
    @Query(value = "{'title': {$regex : ?0, $options: 'i'}}")
    Page<Article> findByTitleRegex(String title, Pageable pageable);
}
