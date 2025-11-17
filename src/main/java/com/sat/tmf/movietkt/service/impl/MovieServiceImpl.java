package com.sat.tmf.movietkt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sat.tmf.movietkt.dao.MovieDao;
import com.sat.tmf.movietkt.entities.Movie;
import com.sat.tmf.movietkt.service.MovieService;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Override
    public Movie addMovie(Movie movie) {
        movieDao.save(movie);
        return movie;
    }

    @Override
    public Movie updateMovie(Movie movie) {
        movieDao.saveOrUpdate(movie);
        return movie;
    }

    @Override
    public void deleteMovie(Integer id) {
        Movie movie = movieDao.findById(id);
        if (movie != null) {
            movieDao.delete(movie);
        }
    }

    @Override
    public Movie findById(Integer id) {
        return movieDao.findById(id);
    }

    @Override
    public List<Movie> findAllMovies() {
        return movieDao.findAll();
    }

    @Override
    public List<Movie> searchMovies(String keyword) {
        return movieDao.searchMovies(keyword);
    }
}

