package com.example.ffdemo.service;

import com.example.ffdemo.dto.SeriesDto;
import com.example.ffdemo.model.Series;
import com.example.ffdemo.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SeriesServiceImpl implements SeriesService {
    @Autowired
    private SeriesRepository repository;

    public SeriesServiceImpl(SeriesRepository repository) {
        this.repository = repository;
    }

    public SeriesServiceImpl() {
    }

    @Override
    public Series saveSeries(SeriesDto seriesDto) {
        Series series = new Series(seriesDto.getName(),
                seriesDto.getUserId(),
                seriesDto.getArticleList());
        return repository.save(series);
    }

    @Override
    public Series saveSeries(SeriesDto seriesDto, String id) {
        if (repository.findById(id).isPresent()) {
            Series series = repository.findById(id).get();
            series.setName(seriesDto.getName());
            series.setArticleList(seriesDto.getArticleList());
            return repository.save(series);
        }
        return null;
    }

    @Override
    public boolean containArticle(String id, String articleId) {
        if (repository.findById(id).isPresent()) {
            Series series = repository.findById(id).get();
            return series.getArticleList().contains(articleId);
        }
        return false;
    }

    @Override
    public Collection<Series> getSeriesByUserId(String userId) {
        return repository.getSeriesByUserId(userId);
    }

    @Override
    public Optional<Series> getSeriesById(String id) {

        return repository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Series> getAllSeries(String title, int page) {
        Pageable pageable = PageRequest.of(0, 10);
        return repository.getAllByNameContainsIgnoreCase(title, pageable);
    }
}
