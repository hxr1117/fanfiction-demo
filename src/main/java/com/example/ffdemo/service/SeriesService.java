package com.example.ffdemo.service;

import com.example.ffdemo.dto.SeriesDto;
import com.example.ffdemo.model.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SeriesService {

    Series saveSeries(SeriesDto seriesDto);
    Series saveSeries(SeriesDto seriesDto, String id);

    boolean containArticle(String id, String articleId);

    Page<Series> getSeriesByUserId(String userId, int page);

    Optional<Series> getSeriesById(String id);
    void deleteById(String id);
    Page<Series> getAllSeries(String title, int page);
}
