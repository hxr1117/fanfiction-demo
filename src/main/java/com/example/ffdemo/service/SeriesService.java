package com.example.ffdemo.service;

import com.example.ffdemo.dto.SeriesDto;
import com.example.ffdemo.model.Series;

import java.util.Collection;
import java.util.Optional;

public interface SeriesService {

    Series saveSeries(SeriesDto seriesDto);
    Series saveSeries(SeriesDto seriesDto, String id);

    boolean containArticle(String id, String articleId);

    Collection<Series> getSeriesByUserId(String userId);

    Optional<Series> getSeriesById(String id);
}
